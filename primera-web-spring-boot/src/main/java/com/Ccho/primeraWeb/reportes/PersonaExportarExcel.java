package com.Ccho.primeraWeb.reportes;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.Ccho.primeraWeb.entities.Persona;
import javax.servlet.http.HttpServletResponse;

public class PersonaExportarExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;
	private Persona persona;

	public PersonaExportarExcel(Persona persona) {
		  this.persona = persona;
	}
	
	
	private void modificarExcel() throws FileNotFoundException, IOException {
		
		String filePath = "C:\\Spring Boot\\modeloHonorario";
		try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
			    FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))) {
			  	libro = new XSSFWorkbook(fileInputStream);
	          
		        Sheet sheet = libro.getSheetAt(0); // Obtener la primera hoja del libro

		         
		        Row row = sheet.getRow(6);
		        Cell cell = row.getCell(4);
		        cell.setCellValue(persona.getNombre()); 
			} catch (IOException e) {
			    e.printStackTrace();
			}       
    }
	
    
	public void exportar(HttpServletResponse response) throws IOException {
		try {
	        modificarExcel();
	        ServletOutputStream outputStream = response.getOutputStream();
	        libro.write(outputStream);
	        outputStream.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	    	if (this.libro != null) {
	    	    this.libro.close();
	    	}

	    }
	}
}
