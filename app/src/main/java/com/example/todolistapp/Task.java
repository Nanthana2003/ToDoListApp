package com.example.todolistapp;

import android.widget.TextView;

public class Task {
    private String taskname;
    private String nottype;

    private long time;

    public Task(String taskname, String nottype, long time){
        this.taskname = taskname;
        this.nottype = nottype;
        this.time = time;
    }
    public String getName(){
        return this.taskname;
    }
    public String getType(){
        return this.nottype;
    }

    public long getTime(){
        return this.time;
    }
}
