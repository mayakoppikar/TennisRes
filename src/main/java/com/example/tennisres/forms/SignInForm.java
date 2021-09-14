package com.example.tennisres.forms;

public class SignInForm {

    private String userid;
    private String userpass;

    public SignInForm(String userid, String userpass) {
        this.userid = userid;
        this.userpass = userpass;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getuserpass() {
        return userpass;
    }

    public void setuserpass(String userpass) {
        this.userpass = userpass;
    }
}
