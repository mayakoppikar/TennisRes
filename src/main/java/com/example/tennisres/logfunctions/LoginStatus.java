package com.example.tennisres.logfunctions;

public class LoginStatus {

    public static int userid;
    public static boolean loggedInStatus;

    public static int getUserid() {
        return userid;
    }

    public static void setUserid(int userid) {
        LoginStatus.userid = userid;
    }

    public static boolean isLoggedInStatus() {
        return loggedInStatus;
    }

    public static void setLoggedInStatus(boolean loggedInStatus) {
        LoginStatus.loggedInStatus = loggedInStatus;
    }
}
