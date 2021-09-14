package com.example.tennisres.logfunctions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Operations {

    public boolean isLoggedIn(int userId, String password){
        try {
            Connection myConn = MySQLConnection.getConnection();
//            String query =
//                    "SELECT userId, lastname, email FROM users WHERE userId='" +
//                            userId + "'" ;

            String query =
                    "SELECT userId, lastname, email, adm  FROM users WHERE userId='" +
                            userId + "' AND password = '" + password + "'";

            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                LoginSession.Id = rs.getInt("userId");
                LoginSession.lastname = rs.getString("lastname");
                LoginSession.email = rs.getString("email");

                return true;
            }


        }catch (Exception e){
            System.out.println("DATABASE ERROR: " + e.getMessage());
        }
            return false;
    }
}
