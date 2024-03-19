package com.Ccho.primeraWeb.services;

import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.entities.Persona;
import java.util.List;


public interface PersonaService {
    List<Persona> obtenerTodas(String palabraClave);
    Persona obtenerPorId(Long id);
    Persona addPersona(Persona p);
    Persona actualizarPersona(Long id, Persona p);
    void eliminarPersona(Long id);
    Long cantPersonas();
    void addHonorario(Long id, Honorario honorario);
    List<Honorario> obtenerTodos(Long p);
	double getDeudaTotal(Long id, Honorario honorario);

}
