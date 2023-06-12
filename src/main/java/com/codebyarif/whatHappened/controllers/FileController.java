package com.codebyarif.whatHappened.controllers;

import com.codebyarif.whatHappened.models.File;
import com.codebyarif.whatHappened.services.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/api/v1/moment-attachment")
     public File saveMomentAttachment(MultipartFile file) {
        return fileService.saveMomentAttachment(file);
    }

}
