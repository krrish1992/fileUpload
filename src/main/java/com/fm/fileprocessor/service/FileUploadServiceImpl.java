package com.fm.fileprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.fileprocessor.exception.CustomFileNotFoundException;
import com.fm.fileprocessor.exception.InvalidFileException;
import com.fm.fileprocessor.model.EmployeeModel;
import com.fm.fileprocessor.model.FileResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class FileUploadServiceImpl implements FileUploadService{

    @Override
    @ExceptionHandler({CustomFileNotFoundException.class, InvalidFileException.class})
    public ResponseEntity<FileResponse> uploadFile(MultipartFile multipartFile){
       String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
       String fileType = FilenameUtils.getExtension(fileName);
       if(fileName.isEmpty()){
           throw new CustomFileNotFoundException("File not found");
       }
       if(StringUtils.isEmpty(fileType)){
           throw new InvalidFileException("File read not in right format.");
       }
       if(StringUtils.hasText(fileType) ){
           if(!fileType.contains("xls")){
               throw new InvalidFileException("File read not in right format.");
           }

       }
       List<EmployeeModel> employeeDataList = new ArrayList<EmployeeModel>();
       try {
            employeeDataList = readExcelFile((FileInputStream) multipartFile.getInputStream());
       }catch(IOException ioe){
           ioe.printStackTrace();
       }
        String jsonData = convertObjects2JsonString(employeeDataList);
        FileResponse fileResponse = new FileResponse(
                fileName,fileType,"File Read is Successful", jsonData);
        return  new ResponseEntity<FileResponse>(fileResponse,HttpStatus.OK);
    }

     private List<EmployeeModel> readExcelFile(FileInputStream excelFile) {
        // TODO Auto-generated method stub
        List<EmployeeModel> employeeList = new ArrayList<EmployeeModel>();
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheet("EmployeeData");
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();

                // skip header
                if(rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellsInRow = currentRow.iterator();

                EmployeeModel employee = new EmployeeModel();

                int cellIndex = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();

                    if(cellIndex==0) { // ID
                        employee.setId(String.valueOf(currentCell.getNumericCellValue()));
                    } else if(cellIndex==1) { // Name
                        employee.setName(currentCell.getStringCellValue());
                    }
                    cellIndex++;
                }

                employeeList.add(employee);
            }

        } catch (IOException e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }

        return employeeList;
    }

    public String convertObjects2JsonString(List<EmployeeModel> employeeList) {
        // TODO Auto-generated method stub
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(employeeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

}
