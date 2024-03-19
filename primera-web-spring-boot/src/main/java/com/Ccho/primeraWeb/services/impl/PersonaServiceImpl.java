package com.Ccho.primeraWeb.services.impl;

import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.entities.Persona;
import com.Ccho.primeraWeb.repository.HonorarioRepository;
import com.Ccho.primeraWeb.repository.PersonaRepository;
import com.Ccho.primeraWeb.services.PersonaService;
import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PersonaServiceImpl implements PersonaService {
	
    @Autowired
    PersonaRepository personaRepository;
    
    @Autowired
    HonorarioRepository honorarioRepository;

    @Override
    public List<Persona> obtenerTodas(String palabraClave) {
        if(palabraClave != null){
            return personaRepository.findAll(palabraClave);
        }
        return personaRepository.findAll();
    }

    @Override
    public Persona obtenerPorId(Long id) {
        return personaRepository.findById(id).orElse(null);
    }

    @Override
    public Persona addPersona(Persona p) {
        return personaRepository.save(p);
    }

    @Override
    public Persona actualizarPersona(Long id, Persona p) {
        Persona personaBdD = personaRepository.findById(id).orElse(null);
        if(personaBdD != null){
            personaBdD.setCuit(p.getCuit());
            personaBdD.setNombre(p.getNombre());
            personaRepository.save(personaBdD);
            return personaBdD;
        }
        return null;
    }
    
    
    @Override
    public void eliminarPersona(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public Long cantPersonas(){
        return personaRepository.count();
    }
    
    
    @Override
    public void addHonorario(Long idPersona, Honorario honorario) {
        Persona personaBdD = personaRepository.findById(idPersona).orElse(null);

        if (personaBdD != null) {
        	honorarioRepository.save(honorario); // Guardar honorario en la base de datos
        	personaBdD.getHonorarios().add(honorario); // Agregar el nuevo honorario a la lista
        	personaRepository.save(personaBdD); // Guardar la persona actualizada
        	}
     }
    
    
    
    public double getDeudaTotal(Long idPersona, Honorario honorario) {
        Persona personaBdD = personaRepository.findById(idPersona).orElse(null);
        double aux = 0;
    	List<Honorario> h = obtenerTodos(personaBdD.getId());
    	for (int i=0; i<h.size(); i++) {
    		if (!h.get(i).getPago()) {
    			aux += h.get(i).getMonto();
    		}
    	}
    	aux += honorario.getMonto();
    	honorario.setDeuda(aux);
    	return aux;
    }

    
    
    @Override
    public List<Honorario> obtenerTodos(Long personaId) {
        return honorarioRepository.findByPersonaId(personaId);
    }

}
