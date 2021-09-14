package com.example.tennisres.forms;

public class BlockClearForm {

    public String resdates;
    public String location1;
    public String location2;
    public String location3;
    public String location4;
    public String starttime;
    public String endtime;

    public BlockClearForm(String resdates, String location1, String location2, String location3, String location4, String starttime, String endtime) {
        this.resdates = resdates;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.location4 = location4;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getResdates() {
        return resdates;
    }

    public void setResdates(String resdates) {
        this.resdates = resdates;
    }

    public String getLocation1() {
        return location1;
    }

    public void setLocation1(String location1) {
        this.location1 = location1;
    }

    public String getLocation2() {
        return location2;
    }

    public void setLocation2(String location2) {
        this.location2 = location2;
    }

    public String getLocation3() {
        return location3;
    }

    public void setLocation3(String location3) {
        this.location3 = location3;
    }

    public String getLocation4() {
        return location4;
    }

    public void setLocation4(String location4) {
        this.location4 = location4;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }
}
