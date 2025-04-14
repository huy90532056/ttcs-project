package com.shopee.ecommerce_web.controller;

import com.shopee.ecommerce_web.entity.FileS3;
import com.shopee.ecommerce_web.service.FileS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileS3Controller {
    private final FileS3Service fileS3Service;

    @PostMapping
    public ResponseEntity<FileS3> uploadVideo(@RequestParam("file") MultipartFile file,
                                              @RequestParam("name") String name) {
        FileS3 savedFile = fileS3Service.uploadFile(file, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
    }

    @GetMapping
    public ResponseEntity<List<FileS3>> getAllFiles() {
        List<FileS3> allFiles = fileS3Service.getAllFiles();
        return ResponseEntity.ok(allFiles);
    }
}
