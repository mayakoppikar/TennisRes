package com.example.tennisres.oclasses;

public class SiteSetupClass {

    private String daysinadvancebooking;
    private String reservationblocklimit;
    private String samedaywindowforreserving;
    private String blocksamedayreserving;
    private String samedaywindowcancel;
    private String resallowedperday;
    private String resallowedperweek;

    public SiteSetupClass(String daysinadvancebooking, String reservationblocklimit, String samedaywindowforreserving, String blocksamedayreserving, String samedaywindowcancel, String resallowedperday, String resallowedperweek) {
        this.daysinadvancebooking = daysinadvancebooking;
        this.reservationblocklimit = reservationblocklimit;
        this.samedaywindowforreserving = samedaywindowforreserving;
        this.blocksamedayreserving = blocksamedayreserving;
        this.samedaywindowcancel = samedaywindowcancel;
        this.resallowedperday = resallowedperday;
        this.resallowedperweek = resallowedperweek;
    }

    public String getDaysinadvancebooking() {
        return daysinadvancebooking;
    }

    public void setDaysinadvancebooking(String daysinadvancebooking) {
        this.daysinadvancebooking = daysinadvancebooking;
    }

    public String getReservationblocklimit() {
        return reservationblocklimit;
    }

    public void setReservationblocklimit(String reservationblocklimit) {
        this.reservationblocklimit = reservationblocklimit;
    }

    public String getSamedaywindowforreserving() {
        return samedaywindowforreserving;
    }

    public void setSamedaywindowforreserving(String samedaywindowforreserving) {
        this.samedaywindowforreserving = samedaywindowforreserving;
    }

    public String getBlocksamedayreserving() {
        return blocksamedayreserving;
    }

    public void setBlocksamedayreserving(String blocksamedayreserving) {
        this.blocksamedayreserving = blocksamedayreserving;
    }

    public String getSamedaywindowcancel() {
        return samedaywindowcancel;
    }

    public void setSamedaywindowcancel(String samedaywindowcancel) {
        this.samedaywindowcancel = samedaywindowcancel;
    }

    public String getResallowedperday() {
        return resallowedperday;
    }

    public void setResallowedperday(String resallowedperday) {
        this.resallowedperday = resallowedperday;
    }

    public String getResallowedperweek() {
        return resallowedperweek;
    }

    public void setResallowedperweek(String resallowedperweek) {
        this.resallowedperweek = resallowedperweek;
    }
}
