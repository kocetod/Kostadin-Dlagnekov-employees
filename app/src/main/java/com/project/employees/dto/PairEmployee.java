package com.project.employees.dto;

import java.util.Objects;

public class PairEmployee {
    private Employee firstEmployee;
    private Employee secondEmployee;
    private int projectId;
    private int daysWorked;

    public int getProjectId() {
        return projectId;
    }

    public int getDaysWorked() {
        return daysWorked;
    }

    public PairEmployee(Employee firstEmployee, Employee secondEmployee, int projectId) {
        this.firstEmployee = firstEmployee;
        this.secondEmployee = secondEmployee;
        this.projectId = projectId;
    }

    public Employee getFirstEmployee() {
        return firstEmployee;
    }

    public Employee getSecondEmployee() {
        return secondEmployee;
    }

    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }

}
