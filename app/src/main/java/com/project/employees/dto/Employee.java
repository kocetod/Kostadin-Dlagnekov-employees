package com.project.employees.dto;

import com.opencsv.bean.CsvBindByPosition;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class Employee {
    @CsvBindByPosition(position = 0)
    private int empID;
    @CsvBindByPosition(position = 1)
    private int projectID;
    @CsvBindByPosition(position = 2)
    private String dateFrom;
    @CsvBindByPosition(position = 3)
    private String dateTo;


    public Employee(){

    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empID=" + empID +
                ", projectID=" + projectID +
                ", dateFrom='" + dateFrom + '\'' +
                ", dateTo='" + dateTo + '\'' +
                '}';
    }
}
