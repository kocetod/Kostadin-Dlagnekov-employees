package com.project.employees;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.opencsv.bean.CsvToBeanBuilder;
import com.project.employees.dto.Employee;
import com.project.employees.dto.PairEmployee;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class CsvDataManager {

    private List<PairEmployee> pairEmployeeList;

    public CsvDataManager() {
        this.pairEmployeeList = new LinkedList<>();
    }

    private String escapeFilePath(String filePath){
        String[] parts = filePath.split("raw:");
        String newPath = "";
        if (parts.length > 1) {
            newPath = parts[1];

            if (newPath.startsWith("/")) {
                newPath = newPath.substring(1);
            }
        }
        return parts[1];
    }

    private List parseData(String pathToCsvFile) {
        List<Employee> employees = null;
        try {
            File csvFile = new File(pathToCsvFile);
            FileReader fileReader = new FileReader(escapeFilePath(csvFile.getAbsolutePath()));

            if (isString(fileReader)) {
                employees = new CsvToBeanBuilder(new FileReader(escapeFilePath(csvFile.getAbsolutePath()))).withType(Employee.class).withSkipLines(1).build().parse();
            } else {
                employees = new CsvToBeanBuilder(new FileReader(escapeFilePath(csvFile.getAbsolutePath()))).withType(Employee.class).build().parse();
            }
            System.out.println();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return employees;
    }

    private boolean isString(FileReader fileReader) {

        boolean isString = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String firstLine = bufferedReader.readLine();
            boolean isFirstLineHeader = containsHeaderValues(firstLine);

            if (isFirstLineHeader) {
                isString = true;
            }
            bufferedReader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isString;
    }

    private boolean containsHeaderValues(String line) {
        return line != null && line.matches(".*[a-zA-Z].*");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<PairEmployee> pairCommonProjects(String pathToCsvFile) {

        List<Employee> employees = parseData(pathToCsvFile);

        if (employees != null) {
            for (int i = 0; i < employees.size() - 1; i++) {
                for (int e = i + 1; e < employees.size(); e++) {
                    Employee firstEmployee = employees.get(i);
                    Employee secondEmployee = employees.get(e);
                    if (firstEmployee.getEmpID() != secondEmployee.getEmpID() && firstEmployee.getProjectID() == secondEmployee.getProjectID()) {

                        if (firstEmployee.getDateTo().equalsIgnoreCase("null")) {

                            LocalDate dateObj = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                            String date = dateObj.format(formatter);

                            firstEmployee.setDateTo(date);
                        }
                        PairEmployee pairEmployee = new PairEmployee(firstEmployee, secondEmployee, firstEmployee.getProjectID());

                        pairEmployeeList.add(pairEmployee);
                    }
                }
            }
        }
        return this.pairEmployeeList;
    }
}
