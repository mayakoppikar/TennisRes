package com.example.tennisres.controllers;

import com.example.tennisres.logfunctions.LoginStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;

@Controller
public class LogOutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String getHomeCourt(Model model) throws SQLException {

        LoginStatus.setLoggedInStatus(false);
        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );

        return "signin";
    }


}
