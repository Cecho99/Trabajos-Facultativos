package com.Ccho.primeraWeb.services;

import com.Ccho.primeraWeb.entities.Honorario;
import java.util.List;


public interface HonorarioService {
    List<Honorario> obtenerTodos();
    Honorario addHonorario(Honorario h);
    Honorario actualizarHonorario(Long id, Honorario h);
    void eliminarHonorario(Long id);
    
}
