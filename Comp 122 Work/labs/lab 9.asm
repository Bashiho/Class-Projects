#Russell de Vries
#Lab 8
#3/12/24
#Register map
#

.data
#setup
vector: .asciiz "#cev" #reserve 4 bytes 	
header: .asciiz "Lab 9: Vectored Interrupts by Russell de Vries \n"
.align 2
prompt1: .asciiz "Enter Int TYPE: 0=NMI, 1=NVI, 2=VI, 9=Halt"
.align 2
prompt2: .asciiz "Enter Int Vector (0-15):"
.align 2
NMI_str: .asciiz "NMI interrupt!"
.align 2
NVI_str: .asciiz "NVI interrupt!"
.align 2
VI_str: .asciiz "Vectored interrupt!"
.align 2
VectStr: .asciiz" ...Vector="
.align 2
Err_msg1: .asciiz "Error: illegal INT Type"
.align 2
Err_msg2: .asciiz "Error: illegal Entry"
.align 2
Halt_msg: .asciiz "Halted! Good-bye"
.align 2
Stop_msg: .asciiz "Stopped"
newLn: .asciiz "\n"
.align 2
end_data: .asciiz "$***$***"
#equivs
.eqv heap, 0x10040000
.eqv in_buf, 0x10040020
.eqv exc_seg, 0x80000180
.eqv stop,10
#macros
.macro _done
li $v0, 10
syscall
.end_macro
.macro _prt_int
#$a0 has int
li $v0,1
syscall
.end_macro
.macro print_mac (%str)
la $a0, %str
li $v0, 4
syscall
.end_macro
.macro _msgbox (%str)
la $a0, %str
li $v0, 55
li $a1, 1
syscall
.end_macro
.macro _ISR (%str)
la $a0, %str
jal GUI_out
jal printStr
teq $0,$0
b loop_main
.end_macro
.macro _newLine
la $a0, newLn
li $v0, 11
syscall
.end_macro


.text
li $t9,stop
la $a0, header
jal printStr #show title in GUI
#main
loop_main:
	subiu $t9,$t9,1
	blez $t9,Stop
	la $a0,prompt1
	jal GUI_in
	move $s0,$a0
	beq $a0,0,NMI
	beq $a0,1,NVI
	beq $a0,2,VI
	beq $a0,9,Halt
	b Err
	NMI: _ISR(NMI_str)
	NVI: _ISR(NVI_str)
	VI:
		la $a0,prompt2
		jal GUI_in #get vector in $a0
		move $s1,$a0 #save vector in $s0
		_ISR(VI_str)
	Halt:
		la $a0,Halt_msg
		jal GUI_out
		jal printStr
		_done
	Err:
		la $a0,Err_msg1
		jal GUI_out
		b loop_main
	Stop:
		la $a0,Stop_msg
		jal printStr
		_done
#end main loop

#subroutines
GUI_out:
li $v0, 55
li $a1, 1
syscall
jr $ra

GUI_in:
li $v0, 51
syscall
bltz $a1, in_error
jr $ra
in_error:
	_msgbox(Err_msg2)
	li $a0, 5
jr $ra

printStr:
li $v0,4
syscall
jr $ra

.kdata
kmsg: .asciiz " ...starting Interrupt handler for: "
.align 2
def_msg: .asciiz "Error: unimplemented vector\n"
.align 2
v0Str: .asciiz "now handling the keyboard"
.align 2
v1Str: .asciiz "now handling the mouse"
.align 2
v2Str: .asciiz "now handling the printer"
.align 2
v3Str: .asciiz "now handling the trackpad"
.align 2
EC_str: .asciiz "Exception code="
.align 2
end_Kdata: .asciiz "&&&&$$$$"
#start of macros
.macro _push_k
move $k0, $a0
move $k1, $a1
.end_macro
.macro _pop_k
move $a0, $k0
move $a1, $k1
eret
.end_macro
.macro _print (%str)
_print_str(%str)
_newline
_print_str(EC_str)
_print_int($s2)
_newline
b return
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
_push_k
print_mac kmsg #prt msg via macro
mfc0 $t0, $14
addi $t0,$t0,4
mtc0 $t0,$14
mfc0 $t5, $13
srl $t5,$t5,2
andi $s2, $t5, 0x1f
beq $s0, 0, NMI_handler
beq $s0, 1, NVI_handler
print_mac VI_str
print_mac VectStr
_print_int($s1)
_newline

beq $s1, 0, v0
beq $s1, 1, v1
beq $s1, 2, v2
beq $s1, 3, v3

b def

NMI_handler:
	_print NMI_str
NVI_handler:
	_print NVI_str
v0: _print (v0Str)
v1: _print (v1Str)
v2: _print (v2Str)
v3: _print (v3Str)
def:
print_mac def_msg
return:
	_pop_k
	eret

_pop_k


#reqs in class
#In
##
#Process
##
#Out
##
