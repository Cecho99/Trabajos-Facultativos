#include <iostream>
#include <cassert>
#include "Lista.h"
#include "Auto.h"
using namespace std;


template <typename T>
Lista<T>::Lista()
{
    plista = NULL;
}



template <typename T>
Lista<T>::~Lista()
{
    //vaciarlista();
}




template <typename T>
void Lista<T>::agregar_elemento(const T & nuevo_elemento){

        nodo * nuevo = Crear_nodo(nuevo_elemento, plista);
        nodo * aux1 = plista;
        nodo * aux2;

        while ((aux1 != NULL) && (aux1->elemento > nuevo_elemento)){
            aux2 = aux1;
            aux1 = aux1->siguiente;
        }

        if (plista == aux1)
            plista = nuevo;
        else
            aux2->siguiente = nuevo;

        nuevo->siguiente = aux1;
}




template <typename T>
typename Lista<T>::nodo * Lista<T>::Crear_nodo(const T & elemento, nodo * siguiente)
{
    nodo * nuevo = new nodo();
    nuevo -> elemento = elemento;
    nuevo -> siguiente = siguiente;

    return nuevo;
}



template <typename T>
void Lista<T>::Borrar_elemento(const T & elemento) {

    if (plista != NULL) {
        nodo * cursor;
        nodo * borrar = NULL;
        cursor = plista;

    while (cursor != NULL && cursor->elemento != elemento){
        borrar = cursor;
        cursor = cursor->siguiente;
    }
    if (cursor == NULL)
        cout<<"El elemento"" "<<elemento<<" ""no existe"<<endl;
    else if (borrar == NULL){
        plista = plista->siguiente;
        delete cursor;
    }
    else{
        borrar->siguiente = cursor->siguiente;
        delete cursor;
    }

    }


}



template <typename T>
int Lista<T>::Longitud() const
{
    int contador=0;
    nodo * cursor = plista;

    while (cursor != NULL){
        contador = contador + 1;
        cursor = cursor->siguiente;
    }

    return contador;

}



template <typename T>
bool Lista<T>::existe_elemento(const T & elemento) const
{
    nodo * cursor = plista;

    while (cursor != NULL && cursor->elemento != elemento)
        cursor = cursor->siguiente;

    if (cursor == NULL)
        return false;
    else
        return true;
}


template <typename T> T & Lista<T>::Iterador()
{
    assert(!Iterador_Fin());
    nodo * aux = Cursor;
    Cursor = Cursor->siguiente;
    return aux->elemento;
}



template <typename T>
bool Lista<T>::Iterador_Fin()
{
    return Cursor == NULL;
}


template <typename T>
void Lista<T>::Iterador_Reset()
{
    Cursor = plista;
}




template <typename T>
void Lista<T>::Mostrar_lista() const
{
    nodo * cursor = plista;

    while (cursor != NULL)
    {
        cout << cursor->elemento<<" "<<endl;
        cursor = cursor->siguiente;

    }
}



template <typename T>
T & Lista<T>::retornar_primer_elemento() const
{
    nodo * cursor = plista;
    return cursor->elemento;
}




template <typename T>
T & Lista<T>::retornar_ultimo_elemento() const
{
    nodo * cursor = plista;
    while(cursor->siguiente != NULL)
        cursor = cursor->siguiente;
    return cursor->elemento;
}





template <typename T>
const T & Lista<T>::ver_elem(int pos) const
{
    assert(pos >= 1);
    assert(pos <= Longitud());
    if (pos <= Longitud() && pos >= 1)
    {
        int i = 1;
        nodo * cursor = plista;
        while (i < pos)
        {
            cursor = cursor->siguiente;
            i++;
        }
        return cursor->elemento;
    }
}




template <typename T>
void Lista<T>::vaciarlista(){

    while (plista != NULL){
    nodo * elim = plista;
    plista = elim->siguiente;
    delete elim;
}
}




template class Lista<int>;
template class Lista<Auto>;
template class Lista<float>;
template class Lista<double>;
template class Lista<string>;
