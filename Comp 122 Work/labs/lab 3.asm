#Russell de Vries
#Lab 2
#2/06/24
######
#register map:
#$t0=calc result, $t1=hexu data, $t2=val16

.data
#setup
hex2dec: .ascii "****" #reserve word & mark
val16: .word 256, 16, 1 #int[] val16=(256,16,1)
#hex to dec: used to generate output string
hexu: .word 0x1050c #unpacked
hexp: .ascii "15c" #packed; start of output string
hexStr: .asciiz " in decimal= " #word align
title: .asciiz "Lab 3: convert Hex to Dec"
prompt: .asciiz "Input your name:"
#define
.eqv heapHi, 0x1004
.eqv hexy2, 0x20b04
.eqv hexps, 0x2b4
.eqv in_buf, 0x10040020 #input buffer

.macro done
li $v0, 10
syscall
.end_macro

.text
jal GUI_msg
#convert hex to dec
sw $0, hex2dec #initialize to 0
jal convert
#jal convert 2nd

#print all
jal print_string
jal print_integer

done
#--end of main--

#Start of subroutines

#app specific subroutines
#convert hex value to decimal
convert:
lb $t1,hexu+2 #d2
lw $t2,val16 #v2
mult $t1,$t2 #clear Hi,Lo
lb $t1, hexu+1 #d1 (next byte)
lb $t2, val16+4 #v1
madd $t1, $t2
lb $t1, hexu #d0
lb $t2, val16+8 #v0
madd $t1, $t2 #[Hi,Lo]+= $t1*$t2
mflo $t0 #dec value
sw $t0,hex2dec
jr $ra

GUI_in:
li $v0, 54 #GUI msg code
la $a0, prompt
la $a1, in_buf
li $a2, 10 #max in length
syscall
#no return, fall through to check


#output a GUI message
GUI_msg:
li $v0, 55 #GUI msg code
la $a0, title
li $a1, 1 #set message type to info
syscall
jr $ra

GUI_msg2:
li $v0, 59 #GUI msg code
#set $a0 before
la $a1, in_buf #msg type is info
syscall
#no return: fall through
#check
li $v0, 4 #print str code
beq $a1, 0,good
beq $a1, -2, cancel
beq $a1, -3, empty
beq $a1, -4, ovflow
b bad
good:
jr $ra
cancel:
jr $ra
empty:
jr $ra
ovflow:
jr $ra
bad:
jr $ra

#print integer value
print_integer:
li $v0, 1 #loads print int command
move $a0, $t0
syscall
jr $ra

#print string
print_string:
li $v0, 4 #loads print string command
la $a0, hexp
syscall
jr $ra

#general subroutines
#write to heap
write_heap:
loop: sw $a1, ($a3) #count N= $a0
addi $a2,$a2, 4 #adjust pointers
addi $a3, $a3, 4
lw $a1, ($a2) #loads next word
subi $a0, $a0, 1 #count
bgtz $a0, loop
#return
jr $ra


#sub 2: dynamic sized via mask
write_heap2:
loop2: sw $a1, ($a3) #count N= $a0
#test for $a1.byte3 =null
andi $a1, 0xff000000 #mask off lower 3 bytes
beqz $a1, exit #return
addi $a2,$a2, 4 #adjust pointers to move to next word
addi $a3, $a3, 4
lw $a1, ($a2) #loads next word
b loop2
#return
exit: jr $ra



#reqs in class
#I/o 
##GUI: create subroutines for each GUI, combine check sub into Input
##console: create subroutines for each print
#Strings: Add title string, keep all promt & error strings for check, delete other strings
#eqv: keep pointer to heap & input buffer
