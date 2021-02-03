package com.fm.fileprocessor.model;

public class EmployeeModel {
    private String id;
    private String name;


    public EmployeeModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public EmployeeModel() {
        // TODO Auto-generated constructor stub
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
