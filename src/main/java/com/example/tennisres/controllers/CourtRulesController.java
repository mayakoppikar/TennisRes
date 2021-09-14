package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.oclasses.RulesClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class CourtRulesController {

    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    @RequestMapping(value = "/courtrules", method = RequestMethod.GET)
    public String getRules(Model model) throws SQLException {

        LocalDate currentdate = LocalDate.now(); // Create a date object

        ArrayList<RulesClass> rules= new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try {
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //SQL
            String crules = "SELECT * FROM courtrules";

            rso = stmt.executeQuery(crules);
            while (rso.next()) {
rules.add(new RulesClass(rso.getInt("ruleId"), rso.getString("rule")));
            }



        } catch (SQLException throwables) {
            System.out.println(throwables);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }


       for(int i=0; i< rules.size(); i++){
           model.addAttribute("rules", rules );
       }



        return "courtrules";
    }
}
