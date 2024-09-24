#Russell de Vries
#Lab 1
#1/23/24
#####
.data
hello: .asciiz "Hello World\n"
helloMe: .ascii "Hello "
name: .asciiz "Russell de Vries"

.text
#print on console using "Syscall 4"
li $v0, 4 #Sets syscall to print code command
la $a0, hello #address (pointer)
syscall
la $a0, helloMe #address (pointer)
syscall
#print on GUI using "Syscall 55"
li $v0, 55 #Sets syscall to print code in GUI command
la $a0, hello #address (pointer)
li $a1, 1 #msg type for GUI
syscall
nop #extra
#break 0 #System.exit(0)
