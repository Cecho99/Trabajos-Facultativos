package com.Ccho.primeraWeb.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.Ccho.primeraWeb.entities.Honorario;
import com.Ccho.primeraWeb.services.HonorarioService;
import com.Ccho.primeraWeb.entities.Persona;
import com.Ccho.primeraWeb.reportes.PersonaExportarExcel;
import com.Ccho.primeraWeb.services.PersonaService;
import com.lowagie.text.DocumentException;

import org.apache.poi.hpsf.Date;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/clientes")
public class PersonaController {
    @Autowired
    private PersonaService personaService;
    @Autowired
    private HonorarioService honorarioService;

    @GetMapping
    public String paginaInicio(Model model, @Param("palabraClave") String palabraClave){ //la clase model se utiliza para transferir objetos del controller a la vista
        List<Persona> personas = personaService.obtenerTodas(palabraClave);
        model.addAttribute("personas", personas);  //key-valor
        model.addAttribute("palabraClave", palabraClave);
        return "listar.html";
    }

    
    @GetMapping("/nueva")
    public String formularioNuevaPersona(Model model){
        model.addAttribute("persona", new Persona());
        model.addAttribute("accion", "/clientes/nueva");
        return "formularioNuevo.html";
    }

    
    @PostMapping("/nueva")
    public String agregarPersonaNueva(@ModelAttribute Persona persona){
        personaService.addPersona(persona);
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String formularioEditarPersona(@PathVariable Long id, @ModelAttribute Persona persona, Model model){
        model.addAttribute("persona", persona);
        model.addAttribute("accion", "/clientes/editar/"+id);
        return "formularioEditar.html";
    }

    
    @PostMapping("/editar/{id}")
    public String editarPersona(@PathVariable Long id, @ModelAttribute Persona persona){
        personaService.actualizarPersona(id, persona);
        return "redirect:/clientes";
    }
    
    
    @GetMapping("/eliminar/{id}")
    public String eliminarPersona(@PathVariable Long id){
        personaService.eliminarPersona(id);
        return "redirect:/clientes";
    }

    
    @GetMapping("/honorarios/{id}")
    public String agregarHonorarios(Model model, @PathVariable Long id){
        Persona p1 = personaService.obtenerPorId(id);
        model.addAttribute("persona", p1);
        model.addAttribute("titulo", "Detalle de " + p1.getNombre());
        model.addAttribute("honorario", new Honorario());
        model.addAttribute("accion", "/clientes/honorarios/"+id);
        List<Honorario> honorarios = personaService.obtenerTodos(id);
        model.addAttribute("honorarios", honorarios);
        List<Honorario> honorariosFormBackup = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            honorariosFormBackup.add(new Honorario());
        }
        model.addAttribute("honorariosFormBackup", honorariosFormBackup);
        return "controlarHonorarios.html";
    }
    
    
    @PostMapping("/honorarios/{id}")
    public String setHonorarios( @PathVariable Long id, @ModelAttribute Honorario honorario){
    	if (honorario.getMonto() > 0) {
    		honorario.setPago(false);
    		personaService.getDeudaTotal(id, honorario);
    	}else {
    		honorario.setPago(true);
    		double aux = personaService.getDeudaTotal(id, honorario);
    		if (aux < 0){
   			 honorario.setDeuda(0);
    		}
    		else { 
    			aux -= honorario.getMonto();
    			honorario.setDeuda(aux);
    			personaService.getDeudaTotal(id, honorario);
    		}
    	}
    	personaService.addHonorario(id, honorario);
        return "redirect:/clientes/honorarios/{id}";
    }
    
    
    
    @GetMapping("/exportarExcel/{id}")
	public void exportarExcelExcel( @PathVariable Long id, HttpServletResponse response) throws DocumentException, IOException {
		response.setContentType("application/octet-stream");
		
		LocalDateTime fechaActual = LocalDateTime.now();

		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_" + fechaActual + ".xlsx";
		
		response.setHeader(cabecera, valor);
		
		Persona p1 = personaService.obtenerPorId(id);
		
		PersonaExportarExcel exporter = new PersonaExportarExcel(p1);
		exporter.exportar(response);
	}
}
