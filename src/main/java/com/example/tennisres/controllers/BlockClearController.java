package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.forms.BlockClearForm;
import com.example.tennisres.logfunctions.LoginSession;
import com.example.tennisres.logfunctions.LoginStatus;
import com.example.tennisres.oclasses.UserClass;
//import com.mysql.jdbc.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.sql.*;
import java.util.ArrayList;

@Controller
public class BlockClearController {


    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    private static  String[] times = {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00"};


    @RequestMapping(value = "/blockclear", method = RequestMethod.GET)
    public String getHomeCourt(Model model) throws SQLException {
model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());
        ArrayList<UserClass> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, Constants.USERNAME, Constants.PASSWORD);
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


if(!(LoginStatus.isLoggedInStatus())){
    model.addAttribute("courtisbookedmessage", "Please log in to access this page.");
model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());
return "rerouting";
}


if((users.get((LoginSession.Id) - 1).getAdm().equals("f"))){
                model.addAttribute("courtisbookedmessage", "You must be an admin to access this page.");
                model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
                model.addAttribute("isadmin", "true");
                return "reroutingadmin";
 }


        return "blockclearadmin";
}

    @RequestMapping(value = "/blockclear", method = RequestMethod.POST)
    public String postHomeCourt(@ModelAttribute("form") BlockClearForm form, Model model) throws SQLException {

        ArrayList<UserClass> users = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try{
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, Constants.USERNAME, Constants.PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM users";
            rso = stmt.executeQuery(allusersdata);
            while(rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"),rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"),  rso.getString("secretanswer"), rso.getString("password")));
            }
        }
        catch (Exception e){
            System.out.println(e);
        }


        if(!(LoginStatus.isLoggedInStatus())){
            model.addAttribute("courtisbookedmessage", "Please log in to access this page.");
            model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());
            return "rerouting";
        }


        if((users.get((LoginSession.Id) - 1).getAdm().equals("f"))){
            model.addAttribute("courtisbookedmessage", "You must be an admin to access this page.");
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
            model.addAttribute("isadmin", "true");
            return "reroutingadmin";
        }


String date = form.getResdates();
String location1 = form.getLocation1();
String location2 = form.getLocation2();
 String location3 = form.getLocation3();
String location4 = form.getLocation4();
String starttime = form.getStarttime();
String endtime = form.getEndtime();
ArrayList<Integer> timesint = new ArrayList<>();


        Connection conns = null;
        Statement stmts = null;
        Statement stmtss = null;

        conns = (Connection) DriverManager.getConnection(Constants.M_CONN_STRING, "dbuser", "dbpassword");
        stmts = (Statement) conns.createStatement();
        stmtss = (Statement) conns.createStatement();

      if(!(location1 == null)){

for(int i=0; i< times.length;i++){
    if(times[i].equals(starttime)){
starttime = String.valueOf(i);
    }
    if(times[i].equals(endtime)){
        endtime = String.valueOf(i);
    }

}

for(int i=0; i< times.length;i++){
    if((i >= Integer.parseInt(starttime)) && (i <= Integer.parseInt(endtime))){
timesint.add(i);
    }
}


for(int i=0; i < timesint.size();i++){
    String sql = "DELETE FROM reservations " +
                "WHERE resTime = '" + String.valueOf(times[Integer.parseInt(String.valueOf(timesint.get(i)))]) +"' " +
            " AND resDate = '" +  date + "' AND location = 'KB1'";

    if( LoginStatus.isLoggedInStatus()){
        stmtss.executeUpdate(sql);
    }
}



      }

        if(!(location2 == null)){
            for(int i=0; i< times.length;i++){
                if(times[i].equals(starttime)){
                    starttime = String.valueOf(i);
                }
                if(times[i].equals(endtime)){
                    endtime = String.valueOf(i);
                }

            }

            for(int i=0; i< times.length;i++){
                if((i >= Integer.parseInt(starttime)) && (i <= Integer.parseInt(endtime))){
                    timesint.add(i);
                }
            }


            for(int i=0; i < timesint.size();i++){
                String sql = "DELETE FROM reservations " +
                        "WHERE resTime = '" + String.valueOf(times[Integer.parseInt(String.valueOf(timesint.get(i)))]) +"' " +
                        " AND resDate = '" +  date + "' AND location = 'KB2'";

                if( LoginStatus.isLoggedInStatus()){
                    stmtss.executeUpdate(sql);
                }
            }
        }


        if(!(location3 == null)){
            for(int i=0; i< times.length;i++){
                if(times[i].equals(starttime)){
                    starttime = String.valueOf(i);
                }
                if(times[i].equals(endtime)){
                    endtime = String.valueOf(i);
                }

            }

            for(int i=0; i< times.length;i++){
                if((i >= Integer.parseInt(starttime)) && (i <= Integer.parseInt(endtime))){
                    timesint.add(i);
                }
            }


            for(int i=0; i < timesint.size();i++){
                String sql = "DELETE FROM reservations " +
                        "WHERE resTime = '" + String.valueOf(times[Integer.parseInt(String.valueOf(timesint.get(i)))]) +"' " +
                        " AND resDate = '" +  date + "' AND location = 'BW1'";

                if( LoginStatus.isLoggedInStatus()){
                    stmtss.executeUpdate(sql);
                }
            }
        }

        if(!(location4 == null)){
            for(int i=0; i< times.length;i++){
                if(times[i].equals(starttime)){
                    starttime = String.valueOf(i);
                }
                if(times[i].equals(endtime)){
                    endtime = String.valueOf(i);
                }

            }

            for(int i=0; i< times.length;i++){
                if((i >= Integer.parseInt(starttime)) && (i <= Integer.parseInt(endtime))){
                    timesint.add(i);
                }
            }


            for(int i=0; i < timesint.size();i++){
                String sql = "DELETE FROM reservations " +
                        "WHERE resTime = '" + String.valueOf(times[Integer.parseInt(String.valueOf(timesint.get(i)))]) +"' " +
                        " AND resDate = '" +  date + "' AND location = 'BW2'";

                if( LoginStatus.isLoggedInStatus()){
                    stmtss.executeUpdate(sql);
                }
            }
        }

        model.addAttribute("loginstatus", LoginStatus.isLoggedInStatus());

        return "reroutingadmin";
    }
}
