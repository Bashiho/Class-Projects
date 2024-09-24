#Russell de Vries
#Lab 8
#3/12/24
#Register map
#

.data
#setup
XO: .asciiz ".XO"
.align 2
marker: .ascii "Games!!"
.align 2
gmStr1: .ascii "Gam1"
game1: .asciiz "111000220" #X wins top
.align 2
gmStr2: .ascii "Gam2"
game2: .asciiz "121220121" #O wins mid col
.align 2
gmStr3: .ascii "Gam3"
game3: .asciiz "212112121" #Cat's game
.align 2
gmStr4: .ascii "Gam4"
game4: .asciiz "120012001"
.align 2
gmStr5: .ascii "Gam5"
game5: .asciiz "012021210"
#add 2 more games: X by LD, O by RD
.align 2
mkState: .ascii "XXXX"
stateStr1X: .half 0x1c0
stateStr1O: .half 0x1ff
stateStr2X: .half 0x145
stateStr20: .half 0x0b2
stateStr3X: .half 0x0b5
stateStr30: .half 0x14a
#edit for 2 more games
stateStr4X: .half 0x111	#100010001
stateStr4O: .half 0x88	#010001000
stateStr5X: .half 0x8a	#010001010
stateStr5O: .half 0x54	#001010100


topStr: .asciiz "Top Row"
midStr: .asciiz "Mid Row"
botStr: .asciiz "Bot Row"
LCStr: .asciiz "Lft Col"
MCStr: .asciiz "Mid Col"
RCStr: .asciiz "Rgt Col"
lftdStr: .asciiz "Lt Diag"
rtdStr: .asciiz "Rt Diag"
#add diagonals and rows
cats: .asciiz "Cat's game!"

.align 2
mkBin: .ascii "&&&&"
topBin: .word 0x1c0 #111000000
midBin: .word 0x038 #999111999
botBin: .word 0x007 #999999111
LcolBin: .word 0x124 #100100100
McolBin: .word 0x092 #010010010
RcolBin: .word 0x049 #001001001
LdiagBin: .word 0x111 #100010001
RdiagBin: .word 0x054 #001010100

.align 2
header: .asciiz "Lab 8: Tic-Tac-Toe by Russell de Vries \n"
.align 2
line: .asciiz "\n----\n"
.align 2
gameStr: .asciiz "\nGameNumber "
.align 2
newLnStr: .asciiz "\n-----------------\n"
.align 2
winStr: .asciiz" wins by "
.align 2
chkStr: .asciiz "checking "
sStr: .asciiz ", state="
.align 2
newLn: .asciiz "\n"
.eqv X, 0x50
.eqv O, 0x4f 

#macros
.macro _done
li $v0, 10
syscall
.end_macro
.macro _prt_char(%ptr)
la $a0, %ptr
li $v0, 11
syscall
.end_macro
.macro _prt_char2(%ptr)
move $a0, %ptr
li $v0, 11
syscall
.end_macro
.macro _prt_string(%ptr)
la $a0, %ptr
li $v0, 4
syscall
.end_macro
.macro _prt_string2(%ptr)
move $a0, %ptr
li $v0, 4
syscall
.end_macro
.macro _prt_int
#$a0 has int
li $v0,1
syscall
.end_macro
.macro _prt_int(%int)
li $v0, 1 #print int code
lw $a0, (%int)
syscall
.end_macro
.macro _prt_int_reg(%int)
li $v0, 1 #print int code
move $a0,%int
syscall
.end_macro
.macro _GUI_msg(%ptr)
li $v0, 55 #GUI msg code
la $a0, %ptr
li $a1, 1 #set message type to info
syscall
.end_macro
.macro _GUI_in(%pmpt, %sz)
li $v0, 54
la $a0, %pmpt
la $a1, in_buf
li $a2, %sz #max input length
syscall
.end_macro
.macro _GUI_inFP(%pmpt)
li $v0, 52
la $a0, %pmpt
la $a1, in_buf
syscall
.end_macro
.macro _GUI_inInt(%pmpt)
li $v0, 51
la $a0, %pmpt
la $a1, in_buf
syscall
.end_macro
.macro _check
beq $a1,0,good
beq $a1,-2,cancel
beq $a1,-3,empty
beq $a1,-1,max
bad: _prt_str(inv_msg) #error message
b top
good:
empty:
max:
b end_chk #break
cancel:
	_prt_str(quit)
	_GUI_msg(quit)
	_done #exit program
end_chk:
.end_macro
.macro _newLine
_prt_string(newLnStr)
.end_macro
.macro _print_bin(%ptr)
move $a0,%ptr
li $v0, 35
syscall
.end_macro
.macro _newLn
_prt_string(newLn)
.end_macro

.text
_GUI_msg(header) #show title in GUI
#main
li $t1,1 #game#
li $t3,0 #Win = false
#main loop
li $t0,5
la $t2,game1
la $s7,stateStr1X
loop_main:
_prt_string(newLnStr)
jal prtBoard
#find winner
_prt_string(chkStr)
_prt_char('X')
_prt_string(sStr)
jal state
_print_bin($s6)
_newLn
jal score
beqz $a0,tryO #x not win
#else x wins, print results
_prt_char(X)
addiu $s7,$s7,2
b win #print and end game

tryO:
_prt_string(chkStr)
_prt_char(O)
_prt_string(sStr)
jal state
_print_bin($s6)
_newLn
jal score
beqz $a0,catGm
_prt_char(O)
b win
catGm: _prt_string(cats)
b next
win:
_prt_string(winStr)
_prt_string2($t5)
next:
addiu $t2,$t2,16
addiu $t1,$t1,1
subiu $t0,$t0,1
bgtz $t0,loop_main
_done
#--end of main--

prtBoard:
move $s0,$t2
_prt_string(gameStr)
move $a0,$t1
_prt_int
_newLine
li $s1,3
row_loop:
li $s2,3
	col_loop:
	lbu $s3,($s0)
	subiu $s3,$s3,0x30
	lbu $a0,XO($s3)
	_prt_char2($a0)
	addiu $s0,$s0,1
	subiu $s2,$s2,1
	bgtz $s2,col_loop
_newLn
subiu $s1,$s1,1
bgtz $s1,row_loop
jr $ra

score:
li $s1,8
la $s0,topBin
la $t5,topStr
loop_score:
lw $s4,($s0) #fetch soln Bstr
#AND mask off extras
and $s3,$s6,$s4
#compare M to all solns
beq $s3,$s4,winXO
#else try next
addiu $s0,$s0,4
addiu $t5,$t5,8
subiu $s1,$s1,1
bgtz $s1,loop_score
li $a0,0
jr $ra
#end score loop

state:
lhu $s6,($s7)
addiu $s7,$s7,2
jr $ra
##state_loop:
##sll $s0,$s0,0
##sll $s0,$s0,1
##move $a0,$s0
##jr $ra

winXO:
li $a0,1
jr $ra
#end score


#I/O
#output GUI message
outputGUI:
li $v0, 55 #GUI msg code
li $a1, 1 #msg type info
syscall
jr $ra

#reqs in class
#In
##
#Process
##
#Out
##
