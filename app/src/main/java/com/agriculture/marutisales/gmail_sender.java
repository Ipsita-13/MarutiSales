package com.agriculture.marutisales;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public class gmail_sender extends AsyncTask<String , Integer,Integer> {
    private String emailID,  subject,  msg;
    Context context;
    public gmail_sender(String emailID, String subject, String msg) {
        this.emailID = emailID;
        this.subject = subject;
        this.msg = msg;
    }

    public gmail_sender() {
    }

    @Override
    protected Integer doInBackground(String... strings) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.googlemail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class",
                    "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
//
                            return new PasswordAuthentication(
                                    "marutisalesoneline@gmail.com",
                                    "fjsgumetydzhjvmt");

                        }
                    });

            try {

                Message message = new MimeMessage(session);
                DataHandler handler = new DataHandler(new ByteArrayDataSource(msg.getBytes(), "text/plain"));
                message.setFrom(new InternetAddress("marutisalesoneline@gmail.com"));
                message.setSubject(subject);
                message.setDataHandler(handler);



                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(emailID));

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return 1;
    }

}

