package com.example.hustw.notepad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Summary> summaryList = new ArrayList<>();
    private Summary clickData;
    TextAdapter adapter;

    //debug
    //private String timeString = "2019-12-5";
/*    private void initSummary(){
        Summary one = new Summary("One", Date.valueOf(timeString));
        summaryList.add(one);
        Summary two = new Summary("Two", Date.valueOf(timeString));
        summaryList.add(two);
        Summary three = new Summary("Three", Date.valueOf(timeString));
        summaryList.add(three);
        Summary four = new Summary("Four", Date.valueOf(timeString));
        summaryList.add(four);
        Summary five = new Summary("Five", Date.valueOf(timeString));
        summaryList.add(five);
        Summary six = new Summary("Six", Date.valueOf(timeString));
        summaryList.add(six);
        Summary seven = new Summary("Seven", Date.valueOf(timeString));
        summaryList.add(seven);
        Summary eight = new Summary("Eight", Date.valueOf(timeString));
        summaryList.add(eight);
        Summary nine = new Summary("Nine", Date.valueOf(timeString));
        summaryList.add(nine);
        Summary ten = new Summary("Ten", Date.valueOf(timeString));
        summaryList.add(ten);
        Summary eleven = new Summary("Eleven", Date.valueOf(timeString));
        summaryList.add(eleven);
        Summary twelve = new Summary("Twelve", Date.valueOf(timeString));
        summaryList.add(twelve);
        Summary thirteen = new Summary("Thirteen", Date.valueOf(timeString));
        summaryList.add(thirteen);
        Summary fourteen = new Summary("Fourteen", Date.valueOf(timeString));
        summaryList.add(fourteen);
        Summary fifteen = new Summary("Fifteen", Date.valueOf(timeString));
        summaryList.add(fifteen);
        Summary sixteen = new Summary("Sixteen", Date.valueOf(timeString));
        summaryList.add(sixteen);
    }
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_search = (Button) findViewById(R.id.search_button);
        button_search.setOnClickListener(this);

        //initial the summary : debug
        //initSummary();

        SharedPreferences pref = getSharedPreferences("notepad", MODE_APPEND);
        int num = pref.getInt("INDEX", 0);
        for (int i = 0; i < num; i++){
            String strData = pref.getString(String.format("%d", i), "");
            TextData data = TextData.fromString(strData);
            Summary summary = data.getSummary();
            summaryList.add(summary);
        }

        //Initiate a recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Set Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Set adapter
        adapter = new TextAdapter(this, summaryList);
        recyclerView.setAdapter(adapter);
    }


    public void getClick(int index, Summary summary){
        clickData = summary;
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("hasExtra", true);
        intent.putExtra("i", index);
        intent.putExtra("title", summary.getTitle());
        startActivity(intent);
        //Get callback information and write in file 'notepad'
        //SharedPreferences.Editor editer = getSharedPreferences("notepad", MODE_APPEND).edit();
        //editer.putString("", summary.toString());
        //editer.apply();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences pref = getSharedPreferences("notepad", MODE_APPEND);
        int num = pref.getInt("INDEX", 0);
        summaryList.clear();
        for (int i = 0; i < num; i++){
            String strData = pref.getString(String.format("%d", i), "");
            TextData data = TextData.fromString(strData);
            Summary summary = data.getSummary();
            summaryList.add(summary);
        }

        //Initiate a recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //Set Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        //Set adapter
        adapter = new TextAdapter(this, summaryList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_button:
                //Debug
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("hasExtra", false);
                startActivity(intent);
                SharedPreferences pref = getSharedPreferences("notepad", MODE_PRIVATE);
                String data = pref.getString("1", "");
                //TextData textData = TextData.fromString(data);
                Log.d("MainActivity", data);
                break;

            default:
                break;
        }
    }
}
