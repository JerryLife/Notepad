package com.example.hustw.notepad;

import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by HUSTW on 2017/3/11.
 */

public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder> {

    private List<Summary> summaryList;
    private Summary clickSummary = new Summary("", Calendar.getInstance());
    private int clickIndex;
    protected MainActivity activity; //Callback parent activity

    public TextAdapter(MainActivity parentActivity, List<Summary> summaryList){
        this.summaryList = summaryList;
        this.activity = parentActivity;
    }

    public Summary getClickSummary(){
        return clickSummary;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.text_summary, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        class Listener implements View.OnClickListener{
            @Override
            public void onClick(View v) {
                int pos = holder.getAdapterPosition();
                clickSummary = summaryList.get(pos);
                //Callback : use MainActivity's method to return value
                activity.getClick(pos, clickSummary);
            }
        }
        Listener listener = new Listener();
        holder.summaryView.setOnClickListener(listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView title = holder.title;
        TextView time = holder.time;
        TextView index = holder.index;

        //get the content now
        Summary content = summaryList.get(position);

        //set texts now
        title.setText(content.getTitle());
        Date date = content.getTime().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String strTime = sdf.format(date);
        time.setText(strTime);
    }

    @Override
    public int getItemCount() {
        return summaryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View summaryView;
        TextView title;
        TextView time;
        TextView index;

        public ViewHolder(View view) {
            super(view);
            summaryView = view;
            title = (TextView) view.findViewById(R.id.title);
            time = (TextView) view.findViewById(R.id.time);
            index = (TextView) view.findViewById(R.id.index);
        }
    }
}