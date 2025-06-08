package com.example.demo.controller;

import com.example.demo.entity.PdfDetails;
import com.example.demo.repository.PdfDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
        ClassPathResource resource = new ClassPathResource("static/" + fileName);

        // 🔍 Check if file exists
        if (!resource.exists()) {
            System.out.println("❌ File not found: " + fileName); // log for debugging
            return ResponseEntity.notFound().build();
        }

        // ✅ Get file size in a safe way (from classpath resource)
        long fileSize = resource.contentLength();

        // ⏰ Timestamp formatting
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        String formattedDate = LocalDateTime.now().format(formatter);

        // 📥 Save log in DB
        PdfDetails pdfDetails = new PdfDetails();
        pdfDetails.setPdfName(fileName);
        pdfDetails.setPdfUrl("https://bv-resource.onrender.com/" + fileName); // this is your hosted link
        pdfDetails.setFileSize(fileSize);
        pdfDetails.setMetadata("Downloaded by: " + collegeId);
        pdfDetails.setDownloadTime(formattedDate);
        pdfRepository.save(pdfDetails);

        // ✅ Return PDF as stream
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(fileSize)
                .body(new InputStreamResource(resource.getInputStream()));
    }
}
