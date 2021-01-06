package com.weather.models;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Date {

    private static final SimpleDateFormat date=new SimpleDateFormat("dd/MM/YYYY", Locale.FRANCE);
    private static final SimpleDateFormat heure=new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
    private static final SimpleDateFormat jour=new SimpleDateFormat("EEEE", Locale.FRANCE);


    private java.util.Date d=new java.util.Date();

    public void update(){
        d=null;
        System.gc();
        d=new java.util.Date();
    }

    public String getDate(){ return date.format(d);}

    public String getHours(){return heure.format(new java.util.Date());}

    public String getDay(){return jour.format(new java.util.Date());}
}
