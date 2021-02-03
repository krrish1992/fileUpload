package com.fm.fileprocessor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileResponse {
    private String filename;
    private String fileType;
    private String message;
    private String employeeData;

    public FileResponse(String filename, String fileType, String message, String employeeData){
        this.filename = filename;
        this.fileType = fileType;
        this.message = message;
        this.employeeData = employeeData;
    }


   public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmployeeData() {
        return employeeData;
    }

    public void setEmployeeData(String employeeData) {
        this.employeeData = employeeData;
    }
}
