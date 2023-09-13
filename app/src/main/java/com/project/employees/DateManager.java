package com.project.employees;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.project.employees.dto.Employee;
import com.project.employees.dto.PairEmployee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DateManager {
    private List<PairEmployee> pairEmployeeList;

    String[] datePatterns = {
            "yyyy-MM-dd",
            "dd/MM/yyyy",
            "MM/dd/yyyy",
            "dd-MM-yyyy"
    };


    public DateManager(){
        this.pairEmployeeList = new LinkedList<>();
    }

    public List<PairEmployee> getPairEmployeeList() {
        return pairEmployeeList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Date checkPossiblePatern(String dateString){
        Date dateTime = null;
        for (int index = 0; index <= datePatterns.length; index++) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(datePatterns[index]);
                dateTime = simpleDateFormat.parse(dateString);
                break;
            } catch (DateTimeParseException | ParseException e) {
                e.printStackTrace();
            }
        }

        return dateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<PairEmployee> matchingDays(List<PairEmployee> commonProjects) {
        List<PairEmployee> matchingEmployeeList = new LinkedList<>();
        try {
            for (PairEmployee value : commonProjects) {

                //Employee one
                int firstEmplId = value.getFirstEmployee().getEmpID();
                int firstProjId = value.getFirstEmployee().getProjectID();
                String firstDateFrom = value.getFirstEmployee().getDateFrom();
                String firstDateTo = value.getFirstEmployee().getDateTo();

                //Employee two
                int secondEmplId = value.getSecondEmployee().getEmpID();
                String secondDateFrom = value.getSecondEmployee().getDateFrom();
                String secondDateTo = value.getSecondEmployee().getDateTo();

                Date startDateOne = checkPossiblePatern(firstDateFrom); //simpleDateFormat.parse(firstDateFrom);
                Date endDateOne = checkPossiblePatern(firstDateTo);

                Date startDateTwo = checkPossiblePatern(secondDateFrom);
                Date endDateTwo = checkPossiblePatern(secondDateTo);

                assert startDateOne != null;
                Date date = startDateOne.after(startDateTwo) ? startDateOne : startDateTwo;
                assert endDateOne != null;
                Date date1 = endDateOne.before(endDateTwo) ? endDateOne : endDateTwo;

                long overlapDays = 0;

                assert date != null;
                if (date.before(date1) || date.equals(date1)) {
                    long diffMillis = date1.getTime() - date.getTime();
                    overlapDays = (diffMillis / (24 * 60 * 60 * 1000) + 1); // Adding 1 to include the last day
                }

                if(overlapDays != 0){
                    value.setDaysWorked((int)overlapDays);
                    Log.d("Debug", firstEmplId + " " + secondEmplId + " " + firstProjId + " " + (int)overlapDays);
                    matchingEmployeeList.add(value);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return matchingEmployeeList;
    }
}
