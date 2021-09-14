package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.forms.FAQform;
import com.example.tennisres.forms.ResDeleteForm;
import com.example.tennisres.logfunctions.LoginSession;
import com.example.tennisres.logfunctions.LoginStatus;
import com.example.tennisres.oclasses.FAQclass;
import com.example.tennisres.oclasses.ReservationClass;
import com.example.tennisres.oclasses.UserClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.sql.*;
import java.util.ArrayList;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

@Controller
public class MyReservationsController {


    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    @RequestMapping(value = "/myreservations", method = RequestMethod.GET)
    public String addReservation(Model model){
        ArrayList<UserClass> users = new ArrayList<>();

if(!(LoginStatus.isLoggedInStatus())){
    model.addAttribute("courtisbookedmessage", "Please log in.");
    return "rerouting";
}






        ArrayList<ReservationClass> res = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING,  USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM users";
            rso = stmt.executeQuery(allusersdata);
            while(rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"),rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"),  rso.getString("secretanswer"),  rso.getString("password")));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }

        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM reservations WHERE userId = " + LoginStatus.getUserid() + "";
            rs = stmt.executeQuery(allusersdata);
            while(rs.next()) {
                res.add(new ReservationClass(rs.getString("reservationId"), rs.getString("userId"), rs.getString("resDate"), rs.getString("resTime"), rs.getString("timespan"), rs.getString("location"), rs.getString("restype")));

            }
        }
        catch (Exception e){
            System.out.println(e);
        }


        for(int i=0; i< res.size(); i++){
            model.addAttribute("res", res );
        }




        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );

        if(LoginStatus.isLoggedInStatus()){
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );

            return "myreservations";


        }
        else{
            model.addAttribute("notsignedinmessage", "Please log in to reserve a court.");
            return "signin";



        }





    }




    @RequestMapping(value = "/deleteres", method = RequestMethod.POST)
    public String rr(@ModelAttribute(name = "rdf")ResDeleteForm rdf, Model model) throws SQLException, MessagingException {
        ArrayList<UserClass> users = new ArrayList<>();
        if(!(LoginStatus.isLoggedInStatus())){
            model.addAttribute("courtisbookedmessage", "Please log in.");
            return "rerouting";
        }

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM users";
            rso = stmt.executeQuery(allusersdata);
            while(rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"),rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"),  rso.getString("secretanswer"),  rso.getString("password")));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }



        int iddelete = Integer.parseInt(rdf.getResid());
        int userid = LoginStatus.getUserid();
        String email = "";


        System.out.println(email);

        for(int i=0; i < users.size(); i++){
            if(String.valueOf(users.get(i).getUserid()).equals(String.valueOf(userid))){
                email = users.get(i).getEmail();
            }
        }

        System.out.println(email);




        Connection conns = null;
        Statement stmts = null;
        Statement stmtss = null;

        conns = (Connection) DriverManager.getConnection(Constants.M_CONN_STRING, "dbuser", "dbpassword");
        stmts = (Statement) conns.createStatement();
        stmtss = (Statement) conns.createStatement();



        String sql = "DELETE FROM reservations " +
                "WHERE reservationId = '" + iddelete +"'";



        if( LoginStatus.isLoggedInStatus()){
            JavaMailUtil.sendMailCancelRes(email);
            stmtss.executeUpdate(sql);
        }

        return "rerouting";
    }
}
