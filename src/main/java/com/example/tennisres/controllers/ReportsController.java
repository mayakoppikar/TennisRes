package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.forms.ReportsForm;
import com.example.tennisres.logfunctions.LoginSession;
import com.example.tennisres.logfunctions.LoginStatus;
import com.example.tennisres.oclasses.ExportResClass;
import com.example.tennisres.oclasses.ReservationClass;
import com.example.tennisres.oclasses.UserClass;
import com.example.tennisres.oclasses.UserReserverClass;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReportsController {

    private static final String[] times = {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00"};
private String typeres;
    private String typerest;
    List<LocalDate> dateslookup;
    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    ArrayList<String> r = new ArrayList<>();


    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public String getReports(Model model) {
        ArrayList<UserClass> users = new ArrayList<>();
        ArrayList<ReservationClass> res = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;

        try {
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String allusersdata = "SELECT * FROM users";
            rso = stmt.executeQuery(allusersdata);
            while (rso.next()) {
                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"), rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"), rso.getString("secretanswer"),  rso.getString("password")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }


        if (!(LoginStatus.isLoggedInStatus())) {
            model.addAttribute("courtisbookedmessage", "Please log in to access this page.");
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
model.addAttribute("isadmin", "false");
            return "rerouting";
        }

        if((users.get((LoginSession.Id) - 1).getAdm().equals("f"))){
            model.addAttribute("courtisbookedmessage", "You must be an admin to access this page");
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
            model.addAttribute("isadmin", "false");
            return "rerouting";
        }


        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
        model.addAttribute("isadmin", "true");
        System.out.println(users.size());

        return "reportsfileadmin";
    }





    @RequestMapping(value = "/reports", method = RequestMethod.POST)
    public String getReportsPost(@ModelAttribute(name = "rf") ReportsForm rf, Model  model) throws SQLException, IOException {
        ArrayList<UserClass> users = new ArrayList<>();
        ArrayList<ReservationClass> res = new ArrayList<>();
String rfo = rf.getRf();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try {
            conn = DriverManager.getConnection(Constants.M_CONN_STRING, USERNAME, PASSWORD);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            //SQL
            String allusersdata = "SELECT * FROM users";

            String currentdatequery = "SELECT * FROM reservations";
            rso = stmt.executeQuery(allusersdata);
            while (rso.next()) {

                users.add(new UserClass(rso.getString("userId"), rso.getString("firstName"), rso.getString("lastName"), rso.getString("email"), rso.getString("address"), rso.getString("adm"),  rso.getString("secretanswer"),  rso.getString("password")));
            }

            rs = stmt.executeQuery(currentdatequery);
            while (rs.next()) {

                res.add(new ReservationClass(rs.getString("reservationId"), rs.getString("userId"), rs.getString("resDate"), rs.getString("resTime"), rs.getString("timespan"), rs.getString("location"), rs.getString("restype")));

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

        System.out.println(res.size());

        System.out.println(users.size());


//one week
        LocalDate endDate = LocalDate.now().minusDays(7);

        List<LocalDate> listOfDates = endDate.datesUntil((LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());





        //two week

        LocalDate endDates = LocalDate.now().minusDays(14);

        List<LocalDate> listOfDatess = endDates.datesUntil((LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());



//one month


        LocalDate endDatess = LocalDate.now().minusDays(30);

        List<LocalDate> listOfDatesss = endDatess.datesUntil((LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());



        //90 day

        LocalDate endDatesss = LocalDate.now().minusDays(90);

        List<LocalDate> listOfDatessss = endDatesss.datesUntil((LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());



        //365 day

        LocalDate endDatessss = LocalDate.now().minusDays(365);

        List<LocalDate> listOfDatesssss = endDatessss.datesUntil((LocalDate.now().plusDays(1)))
                .collect(Collectors.toList());

ArrayList<ExportResClass> finres = new ArrayList<>();



        //user list
        if(rfo == null){
            Exporting.getExportlistUsers("User List", users);
        }

        else {
            typeres = String.valueOf(rfo.charAt(0));
            typerest = String.valueOf(rfo).substring(1, (rfo.length()));

            dateslookup = listOfDates;
            if(typeres.equals("s")){
                dateslookup = listOfDates;

                        if(typerest.equals("groupedbyname")){
                            for(int i=0; i<users.size();i++){
                                for(int m=0; m< res.size(); m++){
                                    if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                        r.add(String.valueOf(users.get(i).getUserid()));
                                        r.add(String.valueOf(users.get(i).getAddress()));

                                        r.add(users.get(i).getLastname());
                                        r.add(res.get(m).getDate());
                                        r.add(res.get(m).getTime());
                                        r.add(res.get(m).getLocation());

                                        finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));
                                    }
                                }
                            }
                            System.out.println(r);

                        } else if(typerest.equals("groupedbyreserver")){

for(int i=0; i<users.size();i++){
    for(int m=0; m< res.size(); m++){
        if(res.get(m).getUserid().equals(users.get(i).getUserid())){
            r.add(String.valueOf(users.get(i).getUserid()));
            r.add(users.get(i).getLastname());
            r.add(res.get(m).getDate());
            r.add(res.get(m).getTime());
            r.add(res.get(m).getLocation());
            finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

        }
    }
}
                            System.out.println(r);
                        }
                        else if(typerest.equals("countbyname")){

                            for(int i=0; i<users.size();i++){
                                for(int m=0; m< res.size(); m++){
                                    if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                        r.add(String.valueOf(users.get(i).getUserid()));
                                        r.add(String.valueOf(users.get(i).getAddress()));

                                        r.add(users.get(i).getLastname());
                                        r.add(res.get(m).getDate());
                                        r.add(res.get(m).getTime());
                                        r.add(res.get(m).getLocation());
                                        finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                                    }
                                }
                            }
                            System.out.println(r);


                        }else if(typerest.equals("countbyreserver")){

                            for(int i=0; i<users.size();i++){
                                for(int m=0; m< res.size(); m++){
                                    if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                        r.add(String.valueOf(users.get(i).getUserid()));
                                        r.add(users.get(i).getLastname());
                                        r.add(res.get(m).getDate());
                                        r.add(res.get(m).getTime());
                                        r.add(res.get(m).getLocation());
                                        finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                                    }
                                }
                            }
                            System.out.println(r);


                        }

            }
            else if(typeres.equals("f")){
                dateslookup = listOfDatess;
                if(typerest.equals("groupedbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                } else if(typerest.equals("groupedbyreserver")){

                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
                else if(typerest.equals("countbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }else if(typerest.equals("countbyreserver")){

                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }

            }
            else if(typeres.equals("t")){
                dateslookup = listOfDatesss;
                if(typerest.equals("groupedbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                } else if(typerest.equals("groupedbyreserver")){

                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
                else if(typerest.equals("countbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }else if(typerest.equals("countbyreserver")){

                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
            }
            else if(typeres.equals("n")){
                dateslookup = listOfDatessss;
                if(typerest.equals("groupedbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);
                } else if(typerest.equals("groupedbyreserver")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
                else if(typerest.equals("countbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);
                }else if(typerest.equals("countbyreserver")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }

            }
            else if(typeres.equals("y")){
                dateslookup = listOfDatesssss;

                if(typerest.equals("groupedbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);
                } else if(typerest.equals("groupedbyreserver")){

                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
                else if(typerest.equals("countbyname")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(String.valueOf(users.get(i).getAddress()));

                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }else if(typerest.equals("countbyreserver")){
                    for(int i=0; i<users.size();i++){
                        for(int m=0; m< res.size(); m++){
                            if(res.get(m).getUserid().equals(users.get(i).getUserid())){
                                r.add(String.valueOf(users.get(i).getUserid()));
                                r.add(users.get(i).getLastname());
                                r.add(res.get(m).getDate());
                                r.add(res.get(m).getTime());
                                r.add(res.get(m).getLocation());
                                finres.add(new ExportResClass(String.valueOf(users.get(i).getUserid()), String.valueOf(users.get(i).getAddress()), users.get(i).getLastname(), res.get(m).getDate(), res.get(m).getTime(), res.get(m).getLocation() ));

                            }
                        }
                    }
                    System.out.println(r);

                }
            }

        }



Exporting.getExportlistReports("Reports", finres);






        if (!(LoginStatus.isLoggedInStatus())) {
            model.addAttribute("courtisbookedmessage", "Please log in to access this page.");
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
            model.addAttribute("isadmin", "false");
            return "rerouting";
        }

        if((users.get((LoginSession.Id) - 1).getAdm().equals("f"))){
            model.addAttribute("courtisbookedmessage", "You must be an admin to access this page");
            model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
            model.addAttribute("isadmin", "false");
            return "rerouting";
        }


        model.addAttribute("loginstatus",String.valueOf(LoginStatus.isLoggedInStatus()) );
        model.addAttribute("isadmin", "true");
        return "signin";
    }

}