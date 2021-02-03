package com.fm.fileprocessor.controller;

import javax.servlet.http.HttpServletRequest;

import com.fm.fileprocessor.model.FileResponse;
import com.fm.fileprocessor.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fm.fileprocessor.service.FileStorageService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file-manager")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping("/readFile")
    //multipart file.
    public ResponseEntity<String> readFile(@RequestParam(name="fileName",required=true) String fileName,
                                           HttpServletRequest request) {
        // Load file as Resource
        String jsonData = "";

        try {
            jsonData = fileStorageService.loadFileAsResource(fileName);
        } catch (RuntimeException ex) {
            logger.info("Could not determine file type."+ex.getMessage());
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(jsonData, HttpStatus.OK);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file")  MultipartFile file) {
        ResponseEntity<FileResponse> response = fileUploadService.uploadFile(file);
        return response;
    }

    }