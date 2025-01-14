package com.isep.javafxdemo;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Reporter {
    private String report;

    public Reporter() {
        this.report = "";
    }

    public String generateReport(Projet projet) {
        this.report = projet.toString();
        for (Tache tache : projet.getTaches()) {
            this.report += '\n' +tache.toString();
        }
        System.out.println(this.report);
        return this.report;
    }

    public void exportReport(String report) {
        // export report to a pdf file
        Document pdfDocument = new Document();
        try {
            PdfWriter.getInstance(pdfDocument, new FileOutputStream("report.pdf"));
            pdfDocument.open();
            pdfDocument.add(new Paragraph(report));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            pdfDocument.close();
        }
    }
}
