#include "Auto.h"
#include "Lista.h"
#include <iostream>
using namespace std;

Auto::Auto()
{

}

Auto::~Auto()
{
    //dtor
}


Auto::Auto(int modelo,string marca, string patente, float precio){

    this->modelo = modelo;
    this->marca = marca;
    this->patente = patente;
    this->precio = precio;
}

void Auto::Agregar_caracteristicas(string caracteristicas){
    Lcaracteristicas.agregar_elemento(caracteristicas);
}


int Auto::obtener_modelo() const{
    return modelo;
}

string Auto::obtener_marca() const{
    return marca;
}


string Auto::obtener_patente() const{
    return patente;
}


float Auto::obtener_precio() const{
    return precio;
}

const Lista<string> & Auto::obtener_caracteristicas() const{
    return Lcaracteristicas;
}


bool Auto::operator > (const Auto & nuevo_auto){
    return (precio > nuevo_auto.precio);

}

bool Auto::operator !=(const Auto & nuevo_auto){
    return (patente != nuevo_auto.patente);
}

/*
Auto & Auto::operator = (const Auto & nuevo_auto){
    modelo = nuevo_auto.modelo;
    marca = nuevo_auto.marca;
    patente = nuevo_auto.patente;
    precio = nuevo_auto.precio;
    int i = 1;
    while (i <= Lcaracteristicas.Longitud())
        Lcaracteristicas.agregar_elemento(nuevo_auto.obtener_caracteristicas().ver_elem(i));
    return *this;
}

*/

ostream & operator << (ostream & salida ,const Auto & nuevo_auto)
{
    salida <<"("<< nuevo_auto.obtener_modelo()<<","<<" "<<nuevo_auto.obtener_marca()<<","<<" "<< nuevo_auto.obtener_patente()<< "," <<" "<< nuevo_auto.obtener_precio()<<" "<<"usd"<<","<<" ";

    for (int i=1; i<=nuevo_auto.obtener_caracteristicas().Longitud(); i++)
        salida << nuevo_auto.obtener_caracteristicas().ver_elem(i)<<"-";

    salida <<")";
    return salida;
}
