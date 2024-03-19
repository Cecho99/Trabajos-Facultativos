package com.Ccho.primeraWeb.services.impl;

import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.repository.HonorarioRepository;
import com.Ccho.primeraWeb.services.HonorarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HonorarioServiceImpl implements HonorarioService {
	
    @Autowired
    HonorarioRepository honorarioRepository;

    @Override
    public List<Honorario> obtenerTodos() {
        return honorarioRepository.findAll();
    }

    
    @Override
    public Honorario addHonorario(Honorario h){
        return honorarioRepository.save(h);
    }

    @Override
    public Honorario actualizarHonorario(Long id, Honorario h) {
        Honorario honorarioBdD = honorarioRepository.findById(id).orElse(null);
        if(honorarioBdD != null){
            honorarioRepository.save(honorarioBdD);
            return honorarioBdD;
        }
        return null;
    }

    @Override
	public void eliminarHonorario(Long id){
        honorarioRepository.deleteById(id);
    }

}
