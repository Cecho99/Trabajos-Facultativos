#include "Segment_Tree.h"
#include "Auto.h"
#include "Lista.h"
#include <iostream>
using namespace std;


Segment_Tree::Segment_Tree()
{
    raiz = NULL;
}




Segment_Tree::~Segment_Tree()
{
    //Vaciar(raiz);
}





Segment_Tree::nodo * Segment_Tree::Crear_nodo(int izq, int der){

    nodo * nuevo = new nodo();
    nuevo->mayor = Auto();
    nuevo->menor = Auto();
    nuevo->izq = NULL;
    nuevo->der = NULL;
    nuevo->indice_inicio = 2000 + izq;
    nuevo->indice_fin = 2000 + der;

    return nuevo;
}





Segment_Tree:: nodo * Segment_Tree::construir_segment_tree (Lista<Auto> Arr[], int izq, int der)
{

        if (izq < der){
        int med = (der + izq ) / 2;
        nodo * I = construir_segment_tree (Arr, izq, med);
        nodo * D = construir_segment_tree (Arr, med + 1, der);
        nodo * N;
        N = Crear_nodo(izq,der);
        N->der = D;
        N->izq = I;

        if (N->izq->mayor > N->der->mayor)
            N->mayor = N->izq->mayor;
        else
            N->mayor = N->der->mayor;

        if (N->izq->menor > N->der->menor)
            N->menor = N->der->menor;
        else
            N->menor = N->izq->menor;

        raiz = N;
        return N;
}
    else {
        nodo * N;
        N = Crear_nodo(izq,der);
        N->mayor = Arr[izq].retornar_primer_elemento();
        N->menor = Arr[izq].retornar_ultimo_elemento();
       return N;
}
}






void Segment_Tree::construir_segment_tree(Lista<Auto> Arr[]){
    construir_segment_tree(Arr,0,20);
}






void Segment_Tree::imprimir() const{
    Imprimir_recu(raiz);
}








void Segment_Tree::Imprimir_recu(nodo * raiz) const{

    if (raiz != NULL){
        cout<<"["<<raiz->indice_inicio<<","<<raiz->indice_fin<<"]"<<endl;
        cout<<raiz->menor<<"    "<<raiz->mayor<<endl;
        cout<<" "<<endl;
        Imprimir_recu(raiz->izq);
        Imprimir_recu(raiz->der);
    }
}





Lista<Auto> Segment_Tree::Consultar_priv(const nodo * raiz, int rango1, int rango2){

        Lista<Auto> A;
        int mid = (raiz->indice_inicio + raiz->indice_fin)/2;

        if (rango1 == raiz->indice_inicio && rango2 == raiz->indice_fin){
            A.agregar_elemento(raiz->mayor);
            A.agregar_elemento(raiz->menor);
            return A;
        }

        else if (rango2 <= mid)
            return Consultar_priv(raiz->izq,rango1,rango2);

        else if (rango1 > mid)
            return Consultar_priv(raiz->der,rango1,rango2);

        else{
            Lista<Auto> B;
            Lista<Auto> C;
            B = Consultar_priv(raiz->izq, rango1, mid);
            C = Consultar_priv(raiz->der, mid+1, rango2);
            return combinar(B,C);
}
}


Lista<Auto> Segment_Tree::combinar(Lista<Auto> & B, Lista<Auto> & C){

        Lista<Auto> nueva;
        if(B.retornar_primer_elemento() > C.retornar_primer_elemento())
            nueva.agregar_elemento(B.retornar_primer_elemento());
        else
            nueva.agregar_elemento(C.retornar_primer_elemento());


        if(B.retornar_ultimo_elemento() > C.retornar_ultimo_elemento())
            nueva.agregar_elemento(C.retornar_ultimo_elemento());
        else
            nueva.agregar_elemento(B.retornar_ultimo_elemento());

        return nueva;
}



Lista<Auto> Segment_Tree::Consultar(int rango1, int rango2){
    return Consultar_priv(raiz,rango1,rango2);
}





void Segment_Tree::Vaciar(nodo *& raiz){

    if (raiz->der != NULL){
        Vaciar(raiz->der);
        delete raiz;
        }

    if (raiz->izq != NULL){
        Vaciar(raiz->izq);
        delete raiz;
}
}

