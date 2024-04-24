package com.example.todolistapp;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class TaskAdapter extends ArrayAdapter<Task> {

    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    Task task;
    Context mycon;
    public TaskAdapter(Context context, ArrayList<Task> tasks){
        super(context, 0, tasks);
        mycon = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        task = getItem(position);
//        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) mycon.getSystemService(Context.ALARM_SERVICE);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView textViewName = convertView.findViewById(R.id.itemname);
        TextView notname = convertView.findViewById(R.id.itemnot);

        textViewName.setText(task.getName());
        notname.setText(task.getType());
        alarm();
        return convertView;
    }

    public void alarm(){
        Toast.makeText(getContext(), "ALARM ON", Toast.LENGTH_SHORT).show();
        long time = task.getTime();
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        long time2 = ((h)*60*60*1000)+(m*60*1000);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        // we call broadcast using pendingIntent
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

//        if(time<=time2){
//            Log.w("alarm alert", "time up");
//        }
//        Log.w("pending intent", getContext()+"");
        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() >= time) {
            // setting time as AM and PM
            Log.w("testing", "here");
            if (Calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }
        // Alarm rings continuously until toggle button is turned off
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+60000, 10000, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(), 10000, pendingIntent);

    }
}
