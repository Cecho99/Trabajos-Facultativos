package com.Ccho.primeraWeb.entities;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity     
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cuit;
    private String nombre;
    
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id")
    private List<Honorario> honorarios;
 
	public Persona(long id, String cuit, String nombre, List<Honorario> honorarios) {
		super();
		this.id = id;
		this.cuit = cuit;
		this.nombre = nombre;
		this.honorarios = honorarios;
	}
	
	public Persona() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Honorario> getHonorarios() {
		return honorarios;
	}

	public void setHonorarios(List<Honorario> honorarios) {
		this.honorarios = honorarios;
	}
    
    
    
}
