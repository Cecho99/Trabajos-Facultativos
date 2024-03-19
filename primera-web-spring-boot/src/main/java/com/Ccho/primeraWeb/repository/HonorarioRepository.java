package com.Ccho.primeraWeb.repository;

import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.entities.Persona;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface HonorarioRepository extends JpaRepository<Honorario,Long> {
	    List<Honorario> findByPersonaId(Long personaId);

}
