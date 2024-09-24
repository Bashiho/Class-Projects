#Russell de Vries
#Lab 2
#1/30/24
######
##add: sub, GUI IN: prompt+INbuf
#register map
#$al=string data, $a2=string ptr, $a3=heap ptr
#$a0=loop count (N)
.data
hello: .asciiz "Hello World\n"
.align 2 #align to word
name: .asciiz "Russell de Vries\n"
.align 2
prompt: .asciiz "Input your name:"
.align 2
ovfl: .asciiz "Error: max length exceeded!\n"
error: .asciiz "Error: No input"
#define
.eqv heap, 0x10040000
.eqv mask3, 0xff000000
.eqv hello_len, 3
.eqv name_len, 4
.eqv in_buf, 0x10040020 #input buffer	

.macro done
li $v0, 10
syscall
.end_macro

.text
li $a0, hello_len
lw $a1, hello #data
la $a2, hello #pointer
la $a3, heap #0x10040000
#call sub for hello
jal write_heap
#name -> heap
li $a0, name_len #N=4
lw $a1, name #data
la $a2, name #pointer
jal write_heap
#==end write to heap
#---print on console using syscall
li $v0, 4 #Sets syscall to print string command
la $a0, hello #loads address of hello into $a0
syscall
la $a0, name
syscall
la $a0, heap #print string into heap
syscall
#print on GUI
li $v0, 55
la $a0, hello
li $a1, 1 #1 sets message type of GUI to be normal message
syscall
#input GUI msg
li $v0, 54 #GUI msg code
la $a0, prompt
la $a1, in_buf
li $a2, 20 #max input length
syscall
jal check #immediately checks the input to ensure it is valid
#output GUI msg
li $v0, 59 #GUI msg code
la $a0, hello
la $a1, in_buf #msg type is info
syscall
done
#--end of main--

#Start of subroutines

#Write string to heap
#var length-count: $a0=count
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
andi $a1,mask3 #mask off lower 3 bytes
beqz $a1, exit #return
addi $a2,$a2, 4 #adjust pointers to move to next word
addi $a3, $a3, 4
lw $a1, ($a2) #loads next word
b loop2
#return
exit: jr $ra

check:
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
la $a0, error
syscall
jr $ra
ovflow:
la $a0, ovfl
syscall
jr $ra
bad:
jr $ra

#Requirements 
#Add to lab 1
#GUI input <your name>
#Copy "Hello World!" (use loop)
#from data seg > into heap seg
#print heap seg on console
