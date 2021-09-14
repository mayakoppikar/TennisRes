package com.example.tennisres.oclasses;

public class UserReserverClass {
    private String userid;
    private String lastname;
private String resdate;
private String restime;
private String location;

    public UserReserverClass(String userid, String lastname, String resdate, String restime, String location) {
        this.userid = userid;
        this.lastname = lastname;
        this.resdate = resdate;
        this.restime = restime;
        this.location = location;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getResdate() {
        return resdate;
    }

    public void setResdate(String resdate) {
        this.resdate = resdate;
    }

    public String getRestime() {
        return restime;
    }

    public void setRestime(String restime) {
        this.restime = restime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
