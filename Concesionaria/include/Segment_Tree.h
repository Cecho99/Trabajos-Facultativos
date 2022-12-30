#ifndef SEGMENT_TREE_H
#define SEGMENT_TREE_H
#include "Auto.h"
#include "Lista.h"
#include <iostream>
using namespace std;


class Segment_Tree
{
    public:

        Segment_Tree();

        ~Segment_Tree();

        void construir_segment_tree(Lista<Auto> Arr[]);

        Lista<Auto> Consultar(int rango1, int rango2);

        void imprimir() const;


    private:
        struct nodo{
            nodo * izq;
            nodo * der;
            Auto mayor;
            Auto menor;
            int indice_inicio;
            int indice_fin;
        };

        nodo * raiz;

        nodo * Crear_nodo(int izq,int der);

        nodo * construir_segment_tree(Lista<Auto> Arr[], int izq, int der);

        void Imprimir_recu(nodo * raiz) const;

        Lista<Auto> Consultar_priv(const nodo *raiz, int rango1,int rango2);

        Lista<Auto> combinar(Lista<Auto> & B, Lista<Auto> & C);

        void Vaciar(nodo *& raiz);

};

#endif // SEGMENT_TREE_H
