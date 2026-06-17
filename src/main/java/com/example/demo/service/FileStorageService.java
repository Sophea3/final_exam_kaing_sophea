package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    public String saveFile(MultipartFile file) {
        return file.getOriginalFilename();
    }
}