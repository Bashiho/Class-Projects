#Russell de Vries
#Lab 7
#3/5/24
#Register map
# $t1,2,3 and %f0 for temp as Float

.data
#setup
in_data: .asciiz "****"
title: .asciiz "Lab 7: Prime Numbers by Russell de Vries \n"
.align 2
oddEven_str: .asciiz " isODD: "
.align 2
prime_str: .asciiz " is a Prime number: "
.align 2
sqrt_str: .asciiz " has a square root of: "
.align 2
T_str: .asciiz "True\n"
F_str: .asciiz "False\n"
eq: .asciiz "= "
newLn: .asciiz "\n"
line: .asciiz "\n----\n"
.align 2
prompt: .asciiz "Input an integer larger than 1  (or cancel to quit): "
.align 2
quit: .asciiz "User Quit Application\n"
.align 2
inv_msg: .asciiz "\nError: invalid input\n"
#define
.eqv heap, 0x10040000
.eqv in_buf, 0x10040020 #input buffer

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
.macro GUI_inFP(%pmpt)
li $v0, 52
la $a0, %pmpt
la $a1, in_buf
syscall
.end_macro
.macro GUI_inInt(%pmpt)
li $v0, 51
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
.macro newLine
prt_str(newLn)
.end_macro
#app specific
.macro sqrt(%N) #N is an integer value
mtc1 %N, $f0 #N
cvt.s.w $f1,$f0
sqrt.s $f0, $f1
#check
c.eq.s $f0, $f1
#bc1t bye #branch on coprocessor 1 true
cvt.w.s $f2,$f0
mfc1 $s0,$f2 #$a1 sqrt(N) (floor)
.end_macro
.macro TFmacro(%TF)
#case True || False (1/0 in $a0)
beqz %TF,Fl #if false, branch to false, else fall through
prt_str(T_str)
b end_TF
Fl: prt_str(F_str)
end_TF:
.end_macro 	

.text
GUI_msg(title) #show title in GUI
#main
top:
jal inputN
beq $a2,0,odd_test
prt_str(inv_msg)
b top
odd_test:
jal isOdd #T/F in $t5
beqz $t5,even
jal isPrime #T/F $t2, N is odd
b print_lab
even: li $t2,0 #not prime
sqrt($t0)
bne $t0,2,print_lab #handles 2
li $t2,1
#print results
print_lab:
prt_int_reg($t0)
prt_str(oddEven_str)
TFmacro($t5)
#Prime
prt_int_reg($t0)
prt_str(prime_str)
TFmacro($t2)
#print square root
prt_int_reg($t0)
prt_str(sqrt_str)
printFP
prt_str(line)
end_loop: b top
#--end of main--


#Start of subroutines
inputN:
GUI_inInt(prompt) #input int to $a0
check
move $t0,$a0
#check for valid
bgt $a0,1,cont
li $a2,-1
jr $ra
cont:
li $a2,0
jr $ra

#$t0=N, $t5 for t/f
isOdd: #returns T/F
#n%2
li $t7, 2
div $t0, $t7
mflo $s0,
mfhi $s1
li $v0, 4
beq $s1, 1, Odd
j Even
jr $ra

Odd:
li $t5,1
jr $ra

Even:
li $t5,0
jr $ra


#isPrime: $t0=N $t1 for remainder
isPrime: #returns F=0 T=1 in $a0
sqrt($t0)
#for(div=3;div<=lim;div+=2)
beq $t0,2,True
bgt $t0,7,initDiv
li $t2, 1
jr $ra

initDiv:
li $t1,3
div_loop:
rem $t3,$t0,$t1
beqz $t3,False
addiu $t1,$t1,2
ble $t1,$s0,div_loop
#return True or False
True: 
li $t2,1 #True
jr $ra
False: #False
li $t2,0
jr $ra

#I/O
#output GUI message
outputGUI:
li $v0, 55 #GUI msg code
li $a1, 1 #msg type info
syscall
jr $ra

#reqs in class
#In
##An integer N
#Process
##Input an Integer
##Check n>1 & =2 (3,5,7?)
##Chec kif odd/even
##call isPrime sub (if n>2 & odd)
##output result
##repeat (loop)
#Out
##Intro GUI
##Output result:
###print "X is odd/even"
###print "X is Prime: True/False"
###print #Sqrt N= nn.mm"
