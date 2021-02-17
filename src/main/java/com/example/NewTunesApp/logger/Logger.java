package com.example.NewTunesApp.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// This class serves to log the messages to the console, this is to remove the responsibility from the repos.
public class Logger {
    public void log(String message){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date) + " : " + message);
    }
}
