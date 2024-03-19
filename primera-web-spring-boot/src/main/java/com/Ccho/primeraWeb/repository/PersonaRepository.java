package com.Ccho.primeraWeb.repository;

import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {

    @Query(" SELECT p FROM Persona p WHERE" + " CONCAT(p.cuit, p.nombre)" + " LIKE %?1%")
    public List<Persona> findAll(String palabraClave);
}
