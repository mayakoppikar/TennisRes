package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.forms.ForgotPassForm;
import com.example.tennisres.logfunctions.LoginStatus;
import com.example.tennisres.oclasses.ReservationClass;
import com.example.tennisres.oclasses.UserClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;
import java.util.ArrayList;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@Controller
public class ForgotPasswordController {

    private static final String[] times = {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00"};

    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    @RequestMapping(value = "/forgotpass", method = RequestMethod.GET)
    public String returnFpass(Model model){
model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());

        return "forgotpassform";
    }

    @RequestMapping(value = "/forgotpass", method = RequestMethod.POST)
    public String returnFpasspost(@ModelAttribute("form") ForgotPassForm form, Model model){
        String email = form.getEmail();
        String address = form.getAddress();
        String secretanswer = form.getSecretanswer();
        String newpassword = form.getNewpassword();
        String newpasswordretype = form.getNewpasswordretype();


        ArrayList<UserClass> users = new ArrayList<>();
        ArrayList<ReservationClass> res = new ArrayList<>();

        ArrayList<Integer> resids = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rsn = null;
        ResultSet rso;



        try {
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);

            //SQL
            String allusersdatas = "SELECT * FROM users";

            rso = stmt.executeQuery(allusersdatas);
            while (rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"), rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"), rso.getString("secretanswer"), rso.getString("password")));
            }

            String allusersdata = "SELECT * FROM reservations";

            rs = stmt.executeQuery(allusersdata);
            while (rs.next()) {
                res.add(new ReservationClass(rs.getString("reservationId"), rs.getString("userId"), rs.getString("resDate"), rs.getString("resTime"), rs.getString("timespan"), rs.getString("location"), rs.getString("restype")));

                resids.add(rs.getInt("reservationId"));

            }
        } catch (Exception e){
            System.out.println(e);
        }




    for(int i=0; i<users.size();i++){
        if((users.get(i).getEmail().equals(email)) && (users.get(i).getSecretanswer().equals(secretanswer))){
           if(newpasswordretype.equals(newpassword)){
               try(Connection conns = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
                   Statement stmts = conns.createStatement();
               ) {
                   String sql = "UPDATE users " +
                           "SET password = '" + newpassword + "'  WHERE address = '" + address + "'";
                   stmts.executeUpdate(sql);

               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }

        }
    }




        model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());

       return "rerouting";
    }
}
