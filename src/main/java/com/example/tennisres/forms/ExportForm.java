package com.example.tennisres.forms;

import java.time.LocalDate;

public class ExportForm {

    private String startdate;
    private String enddate;

    public ExportForm(String startdate, String enddate) {
        this.startdate = startdate;
        this.enddate = enddate;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
