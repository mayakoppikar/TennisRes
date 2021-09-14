package com.example.tennisres.controllers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMailUtil {

    public static void sendMailNewRes(String rec, String date, String time, String location) throws MessagingException {
        System.out.println("PREPARING");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "trescwealth@gmail.com";
        String pass = "tennisres21";

       Session session = Session.getInstance(properties, new Authenticator(){
           @Override
           protected PasswordAuthentication getPasswordAuthentication(){
               return new PasswordAuthentication(myAccount, pass);
           }

       });


        Message message = prepareMessageNewRes(session, myAccount, rec, date, time, location);

        Transport.send(message);
        System.out.println("MESSAGE SENT");
    }

    private static Message prepareMessageNewRes(Session session, String myaccount, String rec, String date, String time, String location) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myaccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject("New Reservation.");
            message.setText("You have made a reservation for " + date + " at " + time + " at the " + location + " courts. If you did not make this reservation, please contact the admin at 'trescwealth@gmail.com'. ");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public static void sendMailCancelRes(String rec) throws MessagingException {
        System.out.println("PREPARING");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccount = "trescwealth@gmail.com";
        String pass = "tennisres21";

        Session session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myAccount, pass);
            }

        });


        Message message = prepareMessageCancelRes(session, myAccount, rec);

        Transport.send(message);
        System.out.println("MESSAGE SENT");
    }

    private static Message prepareMessageCancelRes(Session session, String myaccount, String rec) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myaccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(rec));
            message.setSubject("Cancel Reservation.");
            message.setText("You have cancelled a reservation. If you did not cancel your  reservation, please contact the admin at 'trescwealth@gmail.com'. ");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
