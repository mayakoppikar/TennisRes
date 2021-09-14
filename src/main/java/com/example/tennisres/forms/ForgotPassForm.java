package com.example.tennisres.forms;

public class ForgotPassForm {
    private String email;
    private String address;
    private String secretanswer;
    private String newpassword;
    private String newpasswordretype;

    public ForgotPassForm(String email, String address, String secretanswer, String newpassword, String newpasswordretype) {
        this.email = email;
        this.address = address;
        this.secretanswer = secretanswer;
        this.newpassword = newpassword;
        this.newpasswordretype = newpasswordretype;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecretanswer() {
        return secretanswer;
    }

    public void setSecretanswer(String secretanswer) {
        this.secretanswer = secretanswer;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getNewpasswordretype() {
        return newpasswordretype;
    }

    public void setNewpasswordretype(String newpasswordretype) {
        this.newpasswordretype = newpasswordretype;
    }
}
