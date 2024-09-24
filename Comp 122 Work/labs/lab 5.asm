#Russell de Vries
#Lab 5
#2/20/24
#Register map
#$s0 = size
#$t0 = array ptr, $t1 = count, $t2 = F, $t3 = i++, $t4 = hi
.eqv size, 20
.eqv npl, 8

.data
#setup
header: .asciiz "The Fibonacci numbers are:\n"
title: "Lab 5: Factorial Sequence by Russell de Vries\n"
newLn: .asciiz "\n"
.align 2 #start on word boundary
space: .asciiz "\t" #space to insert between numbers
.align 2
begArray: .ascii ">GEB" #Beginning, reversed due to little end
facts: .word 0:size
.align 2
endArray: .ascii ">DNE" #End
ovfl: .asciiz "Overflow at Factorial = "

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

.macro newLine
prt_str(newLn)
.end_macro

.macro check
mfhi $t4
beqz $t4 end_chk #skip
    prt_str(ovfl)
    prt_int_reg($t3)
    newLine
end_chk: nop
.end_macro

.text
GUI_msg(title) #show title in GUI
#set pointers
la $t0, facts #pointer
li $s0, size #final value
subu $t1, $s0 ,1 #counter
#initialize first 2 numbers (1,2)
li $t2, 1
li $t3, 2 #factor
sw $t2,($t0) 
addi $t0, $t0, 4 #increase pointer
loop_main: 
multu $t2, $t3 #next fact
mflo $t2
sw $t2, ($t0)
#check #check hi for ovfl, use check macro
check
addi $t0, $t0, 4 #increase pointer
addiu $t3,$t3, 1 #increase factor
subi $t1, $t1, 1 # counter
bgtz $t1, loop_main
#call sub to print
jal print
done
#--end of main--
#Start of subroutines

print: #subroutine label
prt_str(title)
prt_str(header)
move $t5, $s0 #save size
la $t0, facts #ptr
li $t6, npl #number per line
#print facts
#print the loop
loop_prt:
prt_int($t0)
prt_str(space)
addi $t0,$t0,4 #incr ptr
#check for overflow
check #check hi for overflow
#check for newline
subi $t6, $t6,1 #npl count
bgtz $t6, dec #skip if >0
	newLine #else print \n
	li $t6, npl #reset
	#end newline
dec: subi $t5,$t5, 1 #countー
bgtz $t5, loop_prt
#return
jr $ra


#reqs in class
#In
##Set N=20
##Create an array facts[N[
#Process
##Generate a factorial sequence for N
##Store in array facts
##use 32-bit integers
##detect overflow & signal: Checl "hi" != 0
#Out
##Count and header
##Factorial Sequence (Console)
##print 5-8 numbers per line
##Signal when overflow occurs
