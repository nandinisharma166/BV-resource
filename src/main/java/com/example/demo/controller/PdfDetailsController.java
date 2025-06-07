package com.example.demo.controller;

import com.example.demo.entity.PdfDetails;
import com.example.demo.repository.PdfDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/pdf")
@CrossOrigin(origins = "*")
public class PdfDetailsController {

    @Autowired
    private PdfDetailsRepository pdfRepository;

    // Endpoint to log PDF download details into the database
    @PostMapping("/logDownload")
    public ResponseEntity<String> logPdfDownload(@RequestBody PdfDetails pdfDetails) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        pdfDetails.setDownloadTime(LocalDateTime.now().format(formatter));
        pdfRepository.save(pdfDetails);
        return ResponseEntity.ok("Download logged successfully");
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<PdfDetails>> getAllPdfDetails() {
        List<PdfDetails> pdfList = pdfRepository.findAll();
        return ResponseEntity.ok(pdfList);
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadPdf(@RequestParam String fileName, @RequestParam String collegeId) throws IOException {
        // Adjust the path if your files are stored differently on Render
        Path filePath = Paths.get(new ClassPathResource("static/" + fileName).getURI());
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        long fileSize = Files.size(filePath);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        String formattedDate = LocalDateTime.now().format(formatter);


        PdfDetails pdfDetails = new PdfDetails();
        pdfDetails.setPdfName(fileName);
        pdfDetails.setPdfUrl("https://bv-resource.onrender.com/" + fileName);
        pdfDetails.setFileSize(fileSize);
        pdfDetails.setMetadata("Downloaded by: " + collegeId);
        pdfDetails.setDownloadTime(formattedDate);
        pdfRepository.save(pdfDetails);

        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
}
