#Russell de Vries
#Lab 11
#4/30/24
#register map
#$s0-3=X Ax; $s4-7=Y
#$t0-3=Y2; $t4-7=Y1; $t8=a; $t9=bb
#floats: $f30-1=a;$f0-7=X aX; $f8-15=Y;$f16-23=Y1;$f24-31=y2
.data
#initial values
a: .word 13
bb: .word 5
X: .word 2,7,14,23
Y: .word 1,2,3,4
aX: .word 111,222,333,444 #reserving locations for results of aX
Y1: .word 0,0,0,0
Y2: .word 0:4
#strings
mark: .asciiz "dne**of*atad****"
header: .asciiz "Lab 11: Pseudo-Instructions by Russell de Vries \n"
Y1str: .asciiz "\nY1=aX+Y ="
Y2str: .asciiz "\nY2=aX+b ="
spc: .asciiz ", "
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
#program specific macros, subject to change
#Ldm/Stm 4x Loads and Stores
.macro LdmLo(%adr) #Y2
lw $s0, %adr
lw $s1, %adr+4
lw $s2, %adr+8
lw $s3, %adr+12
.end_macro
.macro LdmHi(%adr) #Y1
lw $s4, %adr
lw $s5, %adr+4
lw $s6, %adr+8
lw $s7, %adr+12
.end_macro
.macro StmS(%adr) #aX
sw $s0,%adr
sw $s1,%adr+4
sw $s2,%adr+8
sw $s3,%adr+12
.end_macro
.macro StmLo(%adr) #Y2
sw $t0, %adr
sw $t1, %adr+4
sw $t2, %adr+8
sw $t3, %adr+12
.end_macro
.macro StmHi(%adr) #Y1
sw $t4, %adr
sw $t5, %adr+4
sw $t6, %adr+8
sw $t7, %adr+12
.end_macro
.macro MultVS(%s) #aX
mul $s0,%s,$s0
mul $s1,%s,$s1
mul $s2,%s,$s2
mul $s3,%s,$s3
.end_macro
.macro AddVS(%s) #Y2
add $t0,%s,$s0
add $t1,%s,$s1
add $t2,%s,$s2
add $t3,%s,$s3
.end_macro
.macro AddV(%y0,%y1,%y2,%y3) #Y1
add $t4,$s0,%y0
add $t5,$s1,%y1
add $t6,$s2,%y2
add $t7,$s3,%y3
.end_macro
.macro movemLo
move $s0, $t0
move $s1,$t1
move $s2,$t2
move $s3,$t3
.end_macro
.macro _print_vec4(%1,%2,%3,%4)
li $v0,1
move $a0,%1
syscall
li $v0,4
la $a0,spc
syscall
li $v0,1
move $a0,%2
syscall
li $v0,4
la $a0,spc
syscall
li $v0,1
move $a0,%3
syscall
li $v0,4
la $a0,spc
syscall
li $v0,1
move $a0,%4
syscall
.end_macro

.text
_print_str(header)
_GUI_out(header)
#main
lw $t8, a 
lw $t9, bb
#load vectors X,Y
LdmLo(X)
LdmHi(Y)
MultVS($t8)
StmS(aX)
AddV($s4,$s5,$s6,$s7)
StmHi(Y1)
_print_str(Y1str)
_print_vec4($t4,$t5,$t6,$t7)
AddVS($t9)
StmLo(Y2)
_print_str(Y2str)
_print_vec4($t0,$t1,$t2,$t3)
_done
#end main loop

#reqs in class
#In
##
#Process
##
#Out
##print Y1 = aX+Y
##Y2 = aX+b
##as 4 numbers (eg. 23, 45, 66, 109) or label as being Y10, Y11, etc.
