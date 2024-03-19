package com.Ccho.primeraWeb.reportes;

import com.Ccho.primeraWeb.entities.Persona;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfTable;

import java.util.List;

public class PersonaExportarPDF {
    private List<Persona> listaClientes;

    public PersonaExportarPDF(List<Persona> listaClientes){
        super();
        this.listaClientes = listaClientes;
    }

    private void escribirCaberaTabla(PdfTable tabla){
        PdfPCell celda = new PdfPCell();

    }
}
