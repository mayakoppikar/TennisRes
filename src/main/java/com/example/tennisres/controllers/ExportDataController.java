package com.example.tennisres.controllers;

import com.example.tennisres.Constants;
import com.example.tennisres.oclasses.ReservationClass;
import com.example.tennisres.oclasses.UserClass;
import com.example.tennisres.forms.ExportForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ExportDataController {

    private static final String[] times = {"07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00", "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00", "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00", "19:30:00", "20:00:00", "20:30:00", "21:00:00"};

    private static final String USERNAME = Constants.USERNAME;
    private static final String PASSWORD = Constants.PASSWORD;


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String addReservation(Model model){



return "exportformadmin";


    }

    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public String postpost(@ModelAttribute("ef") ExportForm ef, Model model) throws SQLException, IOException {

String startdate = ef.getStartdate();
String enddate = ef.getEnddate();

LocalDate s = LocalDate.parse(startdate);
LocalDate e = LocalDate.parse(enddate);

        List<LocalDate> listOfDates = s.datesUntil(e)
                .collect(Collectors.toList());

        System.out.println(listOfDates);

        ArrayList<ReservationClass> res = new ArrayList<>();
        ArrayList<UserClass> users = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSet rso = null;


        try {
            conn = DriverManager.getConnection(com.example.tennisres.Constants.M_CONN_STRING, USERNAME, PASSWORD);
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


        ArrayList<ReservationClass> rrr = new ArrayList<>();

  for(int i=0; i< listOfDates.size();i++){
      for(int m=0; m<res.size();m++){
          if(res.get(m).getDate().equals(String.valueOf(listOfDates.get(i)))){
              rrr.add(res.get(m));
          }
      }
  }

        System.out.println(rrr.size());

        Exporting.getExportlistDates("Export Data", rrr);



        return "reroutingadmin";


    }


}
