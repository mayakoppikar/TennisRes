package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.logfunctions.LoginSession;
import com.example.tennisres.logfunctions.LoginStatus;
import com.example.tennisres.logfunctions.Operations;
import com.example.tennisres.oclasses.NewsClass;
import com.example.tennisres.oclasses.UserClass;
import com.example.tennisres.forms.SignInForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class SignInController {

    ArrayList<UserClass> users = new ArrayList<>();
    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;



    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public String signIn(Model model){
        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );


        return "signin";
    }


    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public String signInPost(@ModelAttribute(name = "sf") SignInForm sf, Model model){

int id = Integer.parseInt(sf.getUserid());
String pass = sf.getuserpass();
 Operations operations = new Operations();
        ArrayList<NewsClass> nc = new ArrayList<NewsClass>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;
        LocalDate currentdate = LocalDate.now();
        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM users";

            rso = stmt.executeQuery(allusersdata);
            while(rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"),rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"),  rso.getString("secretanswer"),  rso.getString("password")));
            }




        }catch (Exception e){
            System.out.println(e);
        }




        try {

            if(operations.isLoggedIn(id,pass)){
LoginStatus.setLoggedInStatus(true);
LoginStatus.setUserid(id);

if(users.get((LoginSession.Id) - 1).getAdm().equals("t")){
    model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
    model.addAttribute("isadmin", "true");
    return "home";
}
else{
    model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
    model.addAttribute("isadmin", "false");

    return "rerouting";
}




            }

        } catch (Exception e){
            System.out.println("ERROR: " + e);
        }





        LoginStatus.setLoggedInStatus(false);
        LoginStatus.setUserid(-1);
        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );

        return "rerouting";
    }
}
