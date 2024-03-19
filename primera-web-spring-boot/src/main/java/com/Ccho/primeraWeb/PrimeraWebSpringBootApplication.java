package com.Ccho.primeraWeb;

import com.Ccho.primeraWeb.entities.Persona;
import com.Ccho.primeraWeb.repository.PersonaRepository;
import com.Ccho.primeraWeb.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;

@SpringBootApplication
public class PrimeraWebSpringBootApplication{ //implements CommandLineRunner {
	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private PersonaService personaService;

	public static void main(String[] args) {
		SpringApplication.run(PrimeraWebSpringBootApplication.class, args);
	}
}
