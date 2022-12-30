#include <iostream>
#include <fstream>
#include <cstdlib>
#include <stdlib.h>
#include "Lista.h"
#include "Auto.h"
#include "Segment_Tree.h"


using namespace std;


void procesar_archivo_entrada(string origen, Lista<Auto>  Arreglo [], int n, Lista <Auto> & a);

void Verificar_patente(Lista<Auto> & a);

void Autos_del_modelo_elegido (Lista<Auto> & a);

void Auto_mayor_precio (Lista<Auto> & a);


int main(int argc, char * argv[])
{
    Lista<Auto> a;
    int n=21;
    Lista<Auto> Arreglo[n];
    procesar_archivo_entrada("autos.csv", Arreglo, n, a);
    Segment_Tree A;
    A.construir_segment_tree(Arreglo);
    int num;

    while (num != 5){

    cout<<"------------------------------------------------------------------------"<<endl;
    cout<<"                            CONCESIONARIA                              |"<<endl;
    cout<<"------------------------------------------------------------------------"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"       1.  VERIFICAR SI EXISTE UN AUTO CON UNA PATENTE DADA            |"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"       2.  TODOS LOS AUTOS DE UN MODELO DADO                           |"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"       3.  EL AUTO DE MAYOR PRECIO                                     |"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"       4.  AUTO DE MAYOR Y MENOR VALOR ENTRE DOS MODELOS               |"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"       5.  SALIR                                                       |"<<endl;
    cout<<"                                                                       |"<<endl;
    cout<<"------------------------------------------------------------------------"<<endl;

    cin>>num;

    if (num == 1){
        system("cls");
        Verificar_patente(a);
        cout<<" "<<endl;
        system("pause");
        system("cls");
}

    if (num == 2){
        system("cls");
        Autos_del_modelo_elegido(a);
        cout<<" "<<endl;
        system("pause");
        system("cls");
}

    if (num == 3){
        system("cls");
        Auto_mayor_precio(a);
        cout<<" "<<endl;
        system("pause");
        system("cls");
    }
    system("cls");


    if (num == 4){
        system("cls");
        int rango1;
        int rango2;
        cout<<"INGRESE LOS MODELOS DE LOS AUTOS QUE DESEA BUSCAR:";
        cin>>rango1; cin>>rango2;
        A.Consultar(rango1,rango2).Mostrar_lista();
        cout<<" "<<endl;
        system("pause");
        system("cls");
}
    system("cls");
}
    return 0;
}




void procesar_archivo_entrada(string origen, Lista<Auto> Arreglo [], int n, Lista <Auto> & a){

    ifstream archivo(origen.c_str());
    if (!archivo.is_open())
        cout << "No se pudo abrir el archivo: " << origen << endl;
    else {
        string linea;
        getline(archivo, linea);
        int cantidad_autos = atoi(linea.c_str());
        cout << "Se cargarán " << cantidad_autos << " autos." << endl;

        while (getline(archivo, linea)) {
            Lista<string> Lcaracteristicas;
            int pos_inicial = 0;
            int pos_final = linea.find(';');
            int modelo = atoi(linea.substr(pos_inicial, pos_final).c_str());


            pos_inicial = pos_final + 1;
            pos_final = linea.find(';', pos_inicial);
            string marca = linea.substr(pos_inicial, pos_final - pos_inicial);


            pos_inicial = pos_final + 1;
            pos_final = linea.find(';', pos_inicial);
            string patente = linea.substr(pos_inicial, pos_final - pos_inicial);


            pos_inicial = pos_final + 1;
            pos_final = linea.find(';', pos_inicial);
            float precio = atof(linea.substr(pos_inicial, pos_final - pos_inicial).c_str());

            Auto car(modelo, marca, patente, precio);

            string caracteristicas = linea.substr(pos_final + 1, linea.size());
            int pos_inicial_guion = 0, pos_final_guion = 0;
            while (pos_final_guion != -1) {
                pos_final_guion = caracteristicas.find('-', pos_inicial_guion);
                string caracteristica = caracteristicas.substr(pos_inicial_guion, pos_final_guion - pos_inicial_guion);
                pos_inicial_guion = pos_final_guion + 1;
                car.Agregar_caracteristicas(caracteristica);
            }

            a.agregar_elemento(car);
            int i = 0;
            int aux = 2000;
            while ((aux != modelo) && (i < 21)){
                i++;
                aux++;
            }

            Arreglo[i].agregar_elemento(car);
        }
    }
}




void Verificar_patente(Lista<Auto> & a){

    cout<<"Ingrese la patente del auto que desea buscar:"<<" ";
    string patente_ingresada;
    cin>>patente_ingresada;
    bool esta = false;

    a.Iterador_Reset();

    while((!a.Iterador_Fin()) && (esta==false)){
             Auto auxAuto = a.Iterador();
             if (auxAuto.obtener_patente() == patente_ingresada)
                esta = true;
}
    if (esta == false)
        cout<<"El elemento ingresado no existe"<<endl;
    else
        cout<<"El elemento ingresado existe"<<endl;
}





void Autos_del_modelo_elegido (Lista<Auto> & a){

    int modelo_ingresado;
    cout<<"Ingrese el modelo de los autos que desea ver:"<<" ";
    cin>> modelo_ingresado;

    int hay = 0;
    a.Iterador_Reset();

    while(!a.Iterador_Fin()){
        Auto auxAuto = a.Iterador();
        if(auxAuto.obtener_modelo() ==  modelo_ingresado){
            cout<<auxAuto<<endl;
            hay = 1;
        }
    }
    if (hay == 0)
        cout<< "No hay ningun auto de ese modelo"<<endl;
}



void Auto_mayor_precio (Lista<Auto> & a){
    cout<<"El auto de mayor precio es:"" ";
    cout<<a.retornar_primer_elemento();
    cout<<" "<<endl;
}



