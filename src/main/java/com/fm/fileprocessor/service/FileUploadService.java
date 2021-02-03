package com.fm.fileprocessor.service;

import com.fm.fileprocessor.model.FileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public ResponseEntity<FileResponse> uploadFile(MultipartFile multipartFile);
}
