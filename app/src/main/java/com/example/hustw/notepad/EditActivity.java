package com.example.hustw.notepad;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private boolean isOld;
    private int nYear, nMonth, nDay, nHour, nMinute;
    private int id = 0;
    final Calendar cAdd = Calendar.getInstance();
    private String strText;
    private String strTitle;
    private TextData oldData;
    DatePickerDialog datePickerDialog = null;
    TimePickerDialog timePickerDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Button btnDate = (Button) findViewById(R.id.button_set_date);
        btnDate.setOnClickListener(this);

        Button btnTime = (Button) findViewById(R.id.button_set_time);
        btnTime.setOnClickListener(this);

        Button btnSave = (Button) findViewById(R.id.title_save);
        btnSave.setOnClickListener(this);

        Button btnCancel = (Button) findViewById(R.id.title_cancel);
        btnCancel.setOnClickListener(this);

        //Invalid the default title
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        Intent intent = getIntent();
        isOld = intent.getBooleanExtra("hasExtra", false);
        if (isOld) {
            id = intent.getIntExtra("i", 0);
            String title = intent.getStringExtra("title");
            oldData = load(id);
            //assert (oldData.getTitle() == title);
            String content = oldData.getText();

            // Show the content in edit text
            EditText editTitle = (EditText) findViewById(R.id.edit_title);
            editTitle.setText(title.toCharArray(), 0, title.length());
            EditText editContent = (EditText) findViewById(R.id.edit_text);
            editContent.setText(content.toCharArray(), 0, content.length());

            nYear = oldData.getYear();
            nMonth = oldData.getMonth();
            nDay = oldData.getDay();
            nHour = oldData.getHour();
            nMinute = oldData.getMinute();
        }
        else{
            Calendar c = Calendar.getInstance();
            nYear = c.get(Calendar.YEAR);
            nMonth = c.get(Calendar.MONTH);
            nDay = c.get(Calendar.DAY_OF_MONTH);
            nHour = c.get(Calendar.HOUR_OF_DAY);
            nMinute = c.get(Calendar.MINUTE);
        }
        final TextView textDate = (TextView) findViewById(R.id.text_date);
        datePickerDialog = new DatePickerDialog(
                EditActivity.this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                nYear = year;
                nMonth = month;
                nDay = dayOfMonth;

                //show the date
                String str_date = String.format("%4d-%02d-%02d", nYear, nMonth+1, nDay);
                textDate.setText(str_date);
            }
        }, nYear, nMonth, nDay);
        //show the date
        String str_date = String.format("%4d-%02d-%02d", nYear, nMonth+1, nDay);
        textDate.setText(str_date);

        final TextView textTime = (TextView) findViewById(R.id.text_time);

        timePickerDialog = new TimePickerDialog(
                EditActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                nHour = hourOfDay;
                nMinute = minute;

                //show the time
                String str_time = String.format("%02d:%02d", nHour, nMinute);
                textTime.setText(str_time);
            }
        }, nHour, nMinute, true);   //24-hour
        //show the time
        String str_time = String.format("%02d:%02d", nHour, nMinute);
        textTime.setText(str_time);

        // set date and time
        /*DatePicker datePicker = (DatePicker)findViewById(R.id.date_picker);
        datePicker.updateDate(oldData.getYear(), oldData.getMonth(), oldData.getDay());
        TimePicker timePicker = (TimePicker)findViewById(R.id.time_picker);
        timePicker.setCurrentHour(oldData.getHour());
        timePicker.setCurrentHour(oldData.getMinute());*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_set_date:
                cAdd.set(nYear, nMonth, nDay);
                datePickerDialog.show();
                break;

            case R.id.button_set_time:
                cAdd.set(nYear, nMonth, nDay, nHour, nMinute);
                timePickerDialog.show();
                break;

            case R.id.title_save:
                EditText editText = (EditText) findViewById(R.id.edit_text);
                strText = editText.getText().toString();

                EditText editTitle = (EditText) findViewById(R.id.edit_title);
                strTitle = editTitle.getText().toString();

                TextData textData = new TextData(
                        nYear, nMonth, nDay, nHour, nMinute, strTitle, strText
                );
                save(textData);
                finish();
                break;
            case R.id.title_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    public void save(TextData data){
        SharedPreferences pref = getSharedPreferences("notepad", MODE_APPEND);
        SharedPreferences.Editor editor = null;
        int index = pref.getInt("INDEX", -10);
        if (isOld){
            editor = getSharedPreferences("notepad", MODE_APPEND).edit();
            editor.putString(String.format("%d", id), data.toString());
        }
        else{
            if (index < 0) {
                editor = getSharedPreferences("notepad", MODE_PRIVATE).edit();
                index = 0;
            } else {
                editor = getSharedPreferences("notepad", MODE_APPEND).edit();
            }
            editor.putString(String.format("%d", index), data.toString());
            editor.putInt("INDEX", index + 1);
        }
        editor.apply();
        Toast.makeText(this, "Successfully Saved", Toast.LENGTH_SHORT).show();
    }

    public TextData load(int index){
        SharedPreferences pref = getSharedPreferences("notepad", MODE_APPEND);
        String strData = pref.getString(String.format("%d", index), "");
        TextData data = TextData.fromString(strData);
        return data;
    }
}
