.data
number1: .word 100
number2: .word 8

.text
.globl main
main:
    lw $a0, number1             # carga de valor en a0 
    lw $a1, number2             # carga de valor en a1
    jal MCD                     # salto hacia funcion MCD

    add $a0,$v0,$zero 
    li $v0,1
    syscall 
li $v0, 10
syscall

MCD:
    addi $sp, $sp, -12
    sw $ra, 0($sp)              # guardar funcion en stack
    sw $s0, 4($sp)              # guardar valor de s0 en stack
    sw $s1, 8($sp)              # guardar valor de s1 en stack

    add $s0, $a0, $zero         # colocar valor de a0 en a0
    add $s1, $a1, $zero         # colocar valor de a1 en s1

    addi $t1, $zero, 0          # set t1 a 0
    beq $s1, $t1, return        # si s1 == 0 salir de ejecucion

    add $a0, $zero, $s1         
    div $s0, $s1 
    mfhi $a1 

    jal MCD

end:
    lw $ra, 0 ($sp)             # lectura de registros del stack
    lw $s0, 4 ($sp)             # lectura de registros del stack
    lw $s1, 8 ($sp)             # lectura de registros del stack
    addi $sp,$sp , 12
    jr $ra

return:
    add $v0, $zero, $s0 
    j end