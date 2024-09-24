#Russell de Vries
#Lab 6
#2/29/24
#Register map
#$s0 = size
#$t0 = array ptr, $t1 = count, $t2 = F, $t3 = i++, $t4 = hi
.eqv size, 20
.eqv npl, 8

.data
#setup
in_data: .asciiz "****"
title: .asciiz "Temperature conversion by Russell de Vries \n"
C2F_str: .asciiz "C converts to F: "
F2C_str: .asciiz "F converts to C: "
eq: .asciiz "= "
Cch: .asciiz "C"
Fch: .asciiz "F"
newLn: .asciiz "\n"
Line: .asciiz "\n----\n"
.align 2
promptCF: .asciiz "Convert from C or F?"
.align 2
promptT: .asciiz "Input a temp: "
.align 2
quit: .asciiz "User Quit Application\n"
.align 2
inv_msg: .asciiz "\nError: invalid input\n"
#define
.eqv heap, 0x10040000
.eqv in_buf, 0x10040020 #input buffer
.eqv C, 0x43
.eqv F, 0x46

#macros
.macro done
li $v0, 10
syscall
.end_macro
.macro prt_str(%ptr)
li $v0, 4
la $a0, %ptr
syscall
.end_macro
.macro prt_int(%int)
li $v0, 1 #print int code
lw $a0, (%int)
syscall
.end_macro
.macro prt_int_reg(%int)
li $v0, 1 #print int code
move $a0,%int
syscall
.end_macro
.macro GUI_inFP(%pmpt)
li $v0, 52
la $a0, %pmpt
la $a1, in_buf
syscall
.end_macro
.macro printFP
li $v0, 2 #set print fload code
mov.s $f12, $f0 #move $f0 to $f12
syscall
.end_macro
.macro check
beq $a1,0,good
beq $a1,-2,cancel
beq $a1,-3,empty
beq $a1,-1,max
bad: prt_str(inv_msg) #error message
b top
good:
empty:
max:
b end_chk #break
cancel:
	prt_str(quit)
	GUI_msg(quit)
	done #exit program
end_chk:
.end_macro
.macro GUI_msg(%ptr)
li $v0, 55 #GUI msg code
la $a0, %ptr
li $a1, 1 #set message type to info
syscall
.end_macro
.macro GUI_in(%pmpt, %sz)
li $v0, 54
la $a0, %pmpt
la $a1, in_buf
li $a2, %sz #max input length
syscall
.end_macro
.macro newLine
prt_str(newLn)
.end_macro
.macro prep
li $t1, 32
li $t2,5
li $t3,9
mtc1 $t1,$f1 #32
mtc1 $t2,$f2 #5
mtc1 $t3,$f3 #9
cvt.s.w $f1,$f1
cvt.s.w $f2,$f2
cvt.s.w $f3,$f3
.end_macro

.text
GUI_msg(title) #show title in GUI
#main
top:
jal inCF #into in_buf
#jal move_data #in_data to $f0 cvt
jal inTemp
#parse input for F or C
lbu $t0, in_data #C/F character
beq $t0, 'C', C2F
beq $t0,F,F2C
b error
C2F: jal CtoF
b end_loop #skip F or b top
F2C: jal FtoC
b end_loop #or b top
error:
prt_str(inv_msg)
end_loop: b top
######
print:
prt_str(Line)
GUI_msg(F2C_str)
######
#test infinity & NaN
mtc1 $0,$f31
div.s $f9,$f0,$f31 #N/0
mov.s $f12, $f9 #prt arg
li $v0, 2
syscall
newLine
div.s $f10, $f31, $f31 #0/0
mov.s $f12, $f10 #prt arg
li $v0, 2
syscall
done
#--end of main--
#Start of subroutines
#output gui msg
outputGUI:
li $v0, 55 #GUI msg code
li $a1, 1 #msg type info
syscall
jr $ra

#input GUI float
inCF:
GUI_in(promptCF,2)
check
#copy from heap into data segment
lbu $t9, in_buf
sb $t9, in_data
jr $ra

inTemp: 
GUI_inFP(promptT)
check
jr $ra

#conversions
FtoC:
prep
printFP
sub.s $f0, $f0, $f1 #F-32
mul.s $f0, $f0, $f2 #*5
div.s $f0, $f0, $f3 #/9
prt_str(F2C_str)
printFP
newLine
jr $ra

CtoF:
prep
printFP
mul.s $f0, $f0, $f3 #*9
div.s $f0, $f0, $f2 #/5
add.s $f0, $f0, $f1 #C+32
prt_str(C2F_str)
printFP
newLine
jr $ra


#reqs in class
#In
##
#Process
##
#Out
##
