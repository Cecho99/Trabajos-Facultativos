#ifndef AUTO_H
#define AUTO_H
#include "Lista.h"
#include <iostream>
using namespace std;

class Auto
{
    public:
        Auto ();

        ~Auto();

        Auto(int modelo, string marca, string patente, float precio);

        void Agregar_caracteristicas(string caracteristicas);

        int obtener_modelo() const;

        string obtener_marca() const;

        string obtener_patente() const;

        float obtener_precio() const;

        const Lista<string> & obtener_caracteristicas() const;

        bool operator > (const Auto & nuevo_auto);

        bool operator != (const Auto & nuevo_auto);

        //Auto & operator = (const Auto & nuevo_auto);


    private:
        int modelo;

        string marca;

        string patente;

        float precio;

        Lista<string> Lcaracteristicas;

};

ostream & operator << (ostream & salida,const Auto & nuevo_auto);

#endif // AUTO_H
