#ifndef LISTA_H
#define LISTA_H
using namespace std;


template <typename T>
class Lista
{
    public:
        Lista();

        ~Lista();

        void agregar_elemento(const T & nuevo_elemento);

        void Borrar_elemento(const T & elemento);

        int Longitud() const;

        bool existe_elemento(const T & elemento) const;

        const T & ver_elem(int pos) const;

        T & Iterador();

        void Iterador_Reset();

        bool Iterador_Fin();

        T & retornar_primer_elemento() const;

        T & retornar_ultimo_elemento() const;

        void Mostrar_lista() const;


    private:
        struct nodo {
        T elemento;
        nodo * siguiente;
    };

    nodo * Cursor;

    nodo * Crear_nodo(const T & elemento, nodo * siguiente);

    nodo * plista;

    void vaciarlista();
};

#endif // LISTA_H

