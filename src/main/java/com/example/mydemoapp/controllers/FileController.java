package com.example.mydemoapp.controllers;

import com.example.mydemoapp.models.FileEntity;
import com.example.mydemoapp.models.FileInfo;
import com.example.mydemoapp.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/file")
public class FileController {
    private FileService fileService;

    @Autowired
    public FileController(final FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            log.info("Received file: {}", file.getOriginalFilename());
            log.info("Received file size: {}", file.getSize());
            log.info("Received file type: {}", file.getContentType());

            final FileEntity fileEntity = fileService.storeFile(file);
            return ResponseEntity.ok("File uploaded successfully! File ID: " + fileEntity.getId());
        } catch (final IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Could not upload file: " + file.getOriginalFilename() + ". Error " + e.getMessage());
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable final long fileId) throws FileNotFoundException {
        final FileEntity fileEntity = fileService.retrieveFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .body(fileEntity.getData());
    }

    @GetMapping("/info")
    public List<FileInfo> getAllFileInfo() {
        return fileService.getFileInfo();
    }
/*
    @GetMapping("/{fileId}")
    public ResponseEntity<byte[]> retrieveFile(@PathVariable final long fileId) throws FileNotFoundException {
        final FileEntity fileEntity = fileService.retrieveFile(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                .body(fileEntity.getData());
    }*/
}
