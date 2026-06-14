package com.knowledgemesh.ingestion.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PdfExtractor {

    public String extractText(MultipartFile file) {
        try (
                InputStream inputStream = file.getInputStream();
                PDDocument document = Loader.loadPDF(
                        inputStream.readAllBytes()
                )
        ) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to extract PDF text",
                    e
            );
        }
    }
}