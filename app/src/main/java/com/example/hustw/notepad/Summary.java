package com.example.hustw.notepad;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;
import java.util.Locale;

/**
 * Created by HUSTW on 2017/3/12.
 */

public class Summary {

    private String title;
    private Calendar time = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd-kk-mm", Locale.getDefault());

    public Summary(String title, Calendar time){
        this.title = title;
        this.time = time;
    }

    public Summary(String strSummary){
        int start = "yy-MM-dd-kk-mm".length();
        String strTime = strSummary.substring(0, start);
        ParsePosition pp = new ParsePosition(0);
        Date date = sdf.parse(strTime, pp);
        time.setTime(date);
        this.title = strSummary.substring(start);
    }

    public String toString(){
        String strTime = sdf.format(time.getTime());
        return (time + title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
