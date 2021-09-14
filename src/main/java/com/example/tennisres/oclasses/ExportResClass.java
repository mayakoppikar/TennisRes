package com.example.tennisres.oclasses;

public class ExportResClass {
    private String userid;
    private String address;
    private String lastname;
    private String date;
    private String time;
    private String location;

    public ExportResClass(String userid, String address, String lastname, String date, String time, String location) {
        this.userid = userid;
        this.address = address;
        this.lastname = lastname;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
