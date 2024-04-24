package com.example.todolistapp;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Task> tasks;
    private TaskAdapter adapter;
    private TextView txtTime;
    long timenew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tasks = new ArrayList<>();
        adapter = new TaskAdapter(this, tasks);
        txtTime = findViewById(R.id.txtTime);

        ListView listView = findViewById(R.id.listViewTasks);
        listView.setAdapter(adapter);

        EditText name = findViewById(R.id.taskname);
        EditText type = findViewById(R.id.nottype);
        Button addbut = findViewById(R.id.addbut);
        addbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = name.getText().toString();
                String taskType = type.getText().toString();
                if (!taskName.isEmpty() && !taskType.isEmpty()) {
                    tasks.add(new Task(taskName, taskType, timenew));
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void showTimePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Display selected time in TextView
                        txtTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                        int h = calendar.get(Calendar.HOUR_OF_DAY);
                        int m = calendar.get(Calendar.MINUTE);
                        long time = ((hourOfDay)*60*60*1000)+(minute*60*1000), time2 = ((h)*60*60*1000)+(m*60*1000);
                        String s, s2;
//                        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                        s = time+"";
                        s2 = time2+"";
                        timenew = time;
                        Log.w("time is: ", s);
                        Log.w("system time is: ", s2);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }
}