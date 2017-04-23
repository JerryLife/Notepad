package com.example.hustw.notepad;

import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;

/**
 * Created by HUSTW on 2017/3/20.
 */

public class TextData {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String title;
    private String text;

    public TextData(int year, int month, int day, int hour, int minute, String title, String text){
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.text = text;
    }

    public Summary getSummary(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        Summary summary = new Summary(title, calendar);
        return summary;
    }

    public String toString(){
        String date = String.format("%4d%2d%2d%2d%2d%50s",
                this.year, this.month, this.day, this.hour, this.minute, this.title, this.text);
        return (date + this.text);
    }

    static public TextData fromString(String str){
        int year = Integer.parseInt(str.substring(0, 4).trim());
        int month = Integer.parseInt(str.substring(4, 6).trim());
        int day = Integer.parseInt(str.substring(6, 8).trim());
        int hour = Integer.parseInt(str.substring(8, 10).trim());
        int minute = Integer.parseInt(str.substring(10, 12).trim());
        String title = str.substring(12, 62).trim();
        String text = str.substring(62, str.length()).trim();
        TextData textData = new TextData(year, month, day, hour, minute, title, text);
        return textData;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTitle() { return title; }

    public String getText() {
        return text;
    }

}
