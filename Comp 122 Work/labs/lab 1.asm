#Russell de Vries
#Lab 1
#1/23/24
#####
.macro done #used to end the program
li $v0, 10
syscall
.end_macro

.data
hello: .asciiz "Hello World\n"
helloMe: .ascii "Hello "
name: .asciiz "Russell de Vries"

.text
#print in console
li $v0, 4 #Sets syscall to print string command
la $a0, hello #loads address of hello into $a0
syscall
la $a0, helloMe
syscall
#print on GUI
li $v0, 55
la $a0, hello
li $a1, 1 #1 sets message type of GUI to be normal message
syscall
done #exits program, effectively System.exit(0)
