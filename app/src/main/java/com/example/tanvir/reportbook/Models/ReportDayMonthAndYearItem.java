package com.example.tanvir.reportbook.Models;

public class ReportDayMonthAndYearItem {
    private String monthname,year;

    public ReportDayMonthAndYearItem(String monthname, String year) {
        this.monthname = monthname;
        this.year = year;
    }

    public String getMonthname() {
        return monthname;
    }

    public String getYear() {
        return year;
    }
}
