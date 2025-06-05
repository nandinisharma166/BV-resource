package com.example.demo.entity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "pdf_details")
public class PdfDetails {

    @Id
    private String id;
    private String pdfName;
    private String pdfUrl;
    private long fileSize;
    private String metadata;
    private String downloadTime;

}
