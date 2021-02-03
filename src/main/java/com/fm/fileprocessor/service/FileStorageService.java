package com.fm.fileprocessor.service;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.fileprocessor.exception.CustomFileNotFoundException;
import com.fm.fileprocessor.model.EmployeeModel;

@Service
public class FileStorageService {


    public String loadFileAsResource(String fileName) {
        String jsonString = "";
        try {
            URL url = getClass().getResource("/excelFiles/"+fileName);
            Resource resource = null;
            if(null != url)
                resource = new UrlResource(url);
            else
                throw new CustomFileNotFoundException(" File not found...");
            if(resource.exists()) {
                List<EmployeeModel> employee = readExcelFile((BufferedInputStream)resource.getInputStream());//reading excel file data to EmployeeModel class
                jsonString = convertObjects2JsonString(employee);//converting employee object data to json
            } else {
                throw new CustomFileNotFoundException("File not found " + fileName);
            }
        }catch (RuntimeException ex) {
            throw new CustomFileNotFoundException(ex.getMessage() + fileName, ex);
        } catch (IOException ioe) {
            throw new CustomFileNotFoundException(ioe.getMessage() + fileName, ioe);
        }
        return jsonString;
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

    private List<EmployeeModel> readExcelFile(BufferedInputStream excelFile) {
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
}
