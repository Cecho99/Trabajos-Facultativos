package com.Ccho.primeraWeb.entities;


import org.hibernate.annotations.NotFound;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Honorario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean pago;
    private double monto;
    private double deuda;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaPago;
    
    @ManyToOne
    @JoinColumn(name = "persona_id")
    private Persona persona;

	public Honorario(Long id, boolean pago, double monto, double deuda,LocalDate fechaPago, Persona persona) {
		super();
		this.id = id;
		this.pago = false;
		this.deuda = deuda;
		this.monto = monto;
		this.fechaPago = LocalDate.now();
		this.persona = persona;
	}
    
	
	public Honorario() {
		fechaPago = LocalDate.now();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public boolean isPago() {
		return pago;
	}


	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public double getDeuda() {
		return deuda;
	}
	
	public void setDeuda(double deuda) {
		this.deuda = deuda;
	}

	public double getMonto() {
		return monto;
	}
	
	public boolean getPago() {
		return pago;
	}


	public void setMonto(double monto) {
		this.monto = monto;
	}


	public LocalDate getFechaPago() {
		return fechaPago;
	}


	public void setFechaPago(LocalDate fechaPago) {
		this.fechaPago = fechaPago;
	}


	public Persona getPersona() {
		return persona;
	}


	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	
    
    
}
