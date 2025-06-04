package com.example.demo.controller;

import com.example.demo.entity.PdfDetails;
import com.example.demo.repository.PdfDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        // Populate PdfDetails object with incoming data from frontend
        pdfDetails.setDownloadTime(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        // Save the PDF details in MongoDB
        pdfRepository.save(pdfDetails);

        // Respond back with a success message
        return ResponseEntity.ok("Download logged successfully");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PdfDetails>> getAllPdfDetails() {
        List<PdfDetails> pdfList = pdfRepository.findAll();  // Corrected here
        return ResponseEntity.ok(pdfList);
    }
    @GetMapping("/download")
    public ResponseEntity<Resource> downloadPdf(@RequestParam String fileName, @RequestParam String collegeId) throws IOException {
        // Step 1: Locate file
        Path filePath = Paths.get("E:\\BV-resource\\demo\\src\\main\\resources\\static", fileName);
        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        // Step 2: Fetch file size in bytes
        long fileSize = Files.size(filePath);

        // Step 3: Format current date & time beautifully
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        String formattedDate = LocalDateTime.now().format(formatter);

        // Step 4: Create and save PDF log
        PdfDetails pdfDetails = new PdfDetails();
        pdfDetails.setPdfName(fileName);
        pdfDetails.setPdfUrl("http://localhost:8080/" + fileName);
        pdfDetails.setFileSize(fileSize);
        pdfDetails.setMetadata("Downloaded by: " + collegeId);
        pdfDetails.setDownloadTime(formattedDate);
        pdfRepository.save(pdfDetails);

        // Step 5: Prepare download response
        Resource resource = new UrlResource(filePath.toUri());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}
