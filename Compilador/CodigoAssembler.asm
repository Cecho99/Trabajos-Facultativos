.586
.model flat, stdcall
option casemap :none
include \masm32\include\windows.inc
include \masm32\include\kernel32.inc
include \masm32\include\user32.inc
includelib \masm32\lib\kernel32.lib
includelib \masm32\lib\user32.lib

.data
_@aux2bytes dw ? 
msj_inicio db "Inicio del programa", 0
msj_fin db "Fin del programa", 0
error_division_por_cero db "ERROR: Division por cero", 0
error_overflow_producto db "ERROR: Overflow en operacion de producto", 0
error_overflow_suma db "ERROR: Overflow en operacion de suma", 0
_a@main@ca dw ?
_1_i dw 1
_b@main@cb dw ?
_@aux0 dw ?
msj_print_OKK db "OKK", 0
_4_i dw 4
msj_f1@main@ca db "Mensaje metodo f1@main@ca", 0
_instancia@maina@main@ca dw ?
_instancia@mainb@main@cb dw ?


.code


f1@main@ca:
invoke MessageBox, NULL, addr msj_f1@main@ca, addr msj_f1@main@ca, MB_OK 
MOV CX, _1_i
CMP _a@main@ca, CX
MOV _@aux0, 0FFh
JE @aux0
MOV _@aux0, 00h
@aux0:
JNE Label13
invoke MessageBox, NULL, addr msj_print_OKK, addr msj_print_OKK, MB_OK 
JMP Label13
Label13: 
ret  


start: 
invoke MessageBox, NULL, addr msj_inicio, addr msj_inicio, MB_OK 

MOV CX, _4_i
MOV _instancia@maina@main@ca, CX
MOV CX , _instancia@maina@main@ca
MOV _a@main@ca, CX
CALL f1@main@ca; 

fin: 
invoke MessageBox, NULL, addr msj_fin, addr msj_fin, MB_OK 
invoke ExitProcess, 0
end start