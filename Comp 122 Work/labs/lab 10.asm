#Russell de Vries
#Lab 10
#4/16/24
#register map
#$s0=com char; $s2-3=*LED; $s4=stop val; $s5=no. blinks: $s6=delay val
#$t0-9 = temp
.data
#setup
Com: .ascii "X>--" #I/B/Z/C/Q
digits: .byte 0x3f, 0x06, 0x5b, 0x4f, 0x66, 0x6d, 0x7d, 0x07, 0x7f, 0x6f #0-9
.align 2
strings_mark: .ascii "$*$*" 	
header: .asciiz "Lab 10: Functions on LED by Russell de Vries \n"
.align 2
prompt: .asciiz "Enter Command: I, B, Z, C or Q to quit"
.align 2
Com_str: .asciiz "Now performing command: "
.align 2
Err_msg: .asciiz "Error: invalid Entry"
.align 2
Stop_msg: .asciiz "Program Stopped"
.align 2
Quit_msg: .asciiz "\nUser Quit"
.align 2
#GotHere_msg: .asciiz "Debug: got here"
#.align 2
newLn: .asciiz "\n"
.align 2
end_data: .asciiz "$***$***"
#equivs
.eqv heap, 0x10040000
.eqv in_buf, 0x10040020
.eqv exc_seg, 0x80000180
.eqv stop,10
.eqv newline, 0xa
.eqv space, 0x20
.eqv Ix, 0x49
.eqv Bx, 0x42
.eqv Zx, 0x5A
.eqv Cx, 0x43
.eqv Qx, 0x51
#macros
.macro _done
li $v0, 10
syscall
.end_macro
.macro _print_str(%str)
la $a0, %str
li $v0, 4
syscall
.end_macro
.macro _GUI_out(%str)
la $a0, %str
li $v0, 55
li $a1, 1
syscall
.end_macro
.macro _GUI_out2(%str1, %str2)
la $a0, %str1
la $a1, %str2
li $v0, 59
syscall
.end_macro
.macro _getChar
lb $s0, in_buf #load char
sb $s0, Com #store in .data
.end_macro

.text
la $a0, header
jal print #show title in GUI
_GUI_out(header)
#main
li $s4, stop
loop_main:
	la $a0, prompt
	jal GUI_in #get command
	bltz $a1, loop_main
	_getChar #$s0 = char
	_GUI_out(Com_str)
	beq $s0, Qx, Quit
	#check for stop
	subiu $s4, $s4, 1 #dec stop
	#check for infinite loop (#>Stop)
	blez $s4, stopped
	teq $0,$0 #Trap
b loop_main
#Quit or stopped
Quit:
la $a0, Quit_msg
b Exit
stopped:
la $a0, Stop_msg
Exit: 
jal print
_done
#end main loop
#subroutines
print:
li $v0, 4
syscall
jr $ra

GUI_out:
li $v0, 55
li $a1, 1
syscall
jr $ra

GUI_in:
li $v0, 54
li $a1, in_buf
li $a2, 2
syscall
bltz $a1, in_error
jr $ra
in_error:
	_GUI_out(Err_msg)
jr $ra

printStr:
li $v0,4
syscall
jr $ra

.kdata
kmsg: .asciiz " ...starting command handler... "
.align 2
Istr: .asciiz "Display Initials"
.align 2
Bstr: .asciiz "Blink"
.align 2
Zstr: .asciiz "Zero"
.align 2
Cstr: .asciiz "Counter"
.align 2
Xstr: .asciiz "Error: bad command"
.align 2
end_Kdata: .asciiz "$***$***"
.eqv LEDleft, 0xffff0011
.eqv LEDrt, 0xffff0010
.eqv Russell, 0xF6950 #maybe not correct
.eqv deVries, 0x5e
.eqv zero, 0x3f
.eqv all1, 0x7f
.eqv one, 0x6
#start of macros
.macro _push
move $k0, $v0
move $k1, $a0
.end_macro
.macro _pop_ret
move $v0, $k0
move $a0, $k1
eret
.end_macro
.macro _print_str(%ptr)
la $a0, %ptr
li $v0, 4
syscall
.end_macro
.macro _newline
_print_str(newLn)
.end_macro
.macro _print_int(%int)
li $v0, 1 #print int code
la $a0, (%int)
syscall
.end_macro

.ktext exc_seg
_push
_print_str(kmsg)
mfc0 $t0, $14
addi $t0, $t0, 4
mtc0 $t0, $14
_getChar
la $s2, LEDleft
la $s3, LEDrt

beq $s0, Ix, I
beq $s0, Bx, B
beq $s0, Zx, Z
beq $s0, Cx, C

_GUI_out(Xstr)
_pop_ret

loadI:
li $t0, Russell #name
sb $t0, LEDleft
li $t1, deVries #last name
sb $t1, LEDrt
jr $ra

delay:
subiu $s6, $s6, 1
bgtz $s6, delay
jr $ra

I:
_print_str(Istr)
_GUI_out2(kmsg,Istr)
jal loadI
_pop_ret

B:
_print_str(Bstr)
_GUI_out2(kmsg,Bstr)
li $s5, 7
li $s6, 5
B_loop:
jal loadI
jal delay
sb $zero, LEDleft
sb $zero, LEDrt
jal delay
subiu $s5, $s5, 1
bgtz $s5, B_loop
_pop_ret

#Zero flash
Z:
_print_str(Zstr)
_GUI_out2(kmsg,Zstr)
li $s5, 10 #number of flashes
li $s0, zero
li $s1, zero #blank
li $t0, 5 #5/10000 delay
Z_loop:
sb $s0, LEDleft
sb $s1, LEDrt
jal delay
#reverse
sb $s1, LEDleft
sb $s0, LEDrt
jal delay
subu $s5, $s5, 1
bgtz $s5, Z_loop
_pop_ret
#end of Z

#Count from 0-9
C:
_print_str(Cstr)
_GUI_out2(kmsg,Cstr)
li $s6, 5
li $s5, 1
li $t2, zero
li $t5, zero
li $t6, 0
add1:
li $t1, 0
li $t4, 10
sb $t2, LEDrt
sb $t5, LEDleft
jal delay
C_loop:
lb $t2, digits($t1)
sb $t2, LEDrt #count up
jal delay
addiu $t1, $t1, 1
subu $t4, $t4, 1
bgtz $t4, C_loop
#add a 1 to left
addiu $t6, $t6, 1
lb $t5, digits($t6)
subu $s5,$s5,1
bgtz $s5, add1
_pop_ret


#reqs in class
#In
##
#Process
##
#Out
##Show .data, .kdata, and MMIO in output
