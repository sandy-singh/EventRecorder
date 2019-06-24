package com.example.sandeepsingh.eventrecorder;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class SettingActivity extends AppCompatActivity {

    //Variables
    EditText textName;
    TextView textTime;
    TextView textDate;
    Calendar calendar;
    int currentHour;
    int currentMinute;
    String amPm;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        textName = findViewById(R.id.editTextActivityName);
        textDate = findViewById(R.id.editTextDate);
        textTime = findViewById(R.id.editTextTime);

    }

    /*
        FOR DATA PERSISTENCE
     */
    protected void onPause() {
        super.onPause();
        // save shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("name",textName.getText().toString());
        editor.putString("date",textDate.getText().toString());
        editor.putString("time",textTime.getText().toString());
        // write shared pref to file
        editor.apply();
    }

    /*
        FOR DATA PERSISTENCE
     */
    @Override
    protected void onResume() {
        super.onResume();
        //restore our shared_pref
        SharedPreferences settings = getSharedPreferences("datapersistance",
                Context.MODE_PRIVATE);
        textName.setText(settings.getString("name",""));
        textDate.setText(settings.getString("date","Click to add Date"));
        textTime.setText(settings.getString("time","Click to add Time"));
    }


    public void OnClickBack(View view) {
        finish();
    }

    /*
     *   Method Name:OnClickSave
     *   Purpose:Saves the data to the text File
     */
    public void OnClickSave(View view) {
        FileOutputStream ofile = null;
        if(textName.getText().toString().trim().length() <= 0 ||
                textDate.getText().toString().equals("Click to add Date")
        || textTime.getText().toString().equals("Click to add Time"))
        {
            Context context = getApplicationContext();
            CharSequence time = "Please fill all the fields!!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, time, duration);
            toast.show();
        }
        else{
        try {

            ofile = openFileOutput("saveText.txt", MODE_APPEND);


            OutputStreamWriter osw = new OutputStreamWriter(ofile);
            osw.write("\n• " + textName.getText().toString() + " recorded at "
                    + textTime.getText().toString()
                    + " " + textDate.getText().toString() + "\n");
            osw.flush();
            osw.close();
            Context context = getApplicationContext();
            CharSequence time = "Good Job! Event Recorded";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, time, duration);
            toast.show();
            textName.getText().clear();
            textTime.setText("Click to add Time");
            textDate.setText("Click to add Date");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }

    /*
     *   Method Name:OnClickTime
     *   Purpose:Opens the clock to select time
     *   Research Resources:
     *   - https://developer.android.com/reference/android/app/TimePickerDialog
     */
    public void OnClickTime(View view) {
        calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(SettingActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                if (hourOfDay >= 12) {
                    amPm = "PM";
                    textTime.setText(String.format("%02d:%02d", hourOfDay-12, minutes) + amPm);
                } else {
                    amPm = "AM";
                    textTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                }

            }
        }, currentHour, currentMinute, false);

        timePickerDialog.show();
    }//End OnClickTime

    /*
    *   Method Name:OnClickDate
    *   Purpose: Open the Calendar View
    *   Research Resources:
    *   - https://developer.android.com/reference/android/app/DatePickerDialog
    */
    public void OnClickDate(View view) {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(SettingActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        textDate.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }//End OnClickDate


}//End Class
