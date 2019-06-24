package com.example.sandeepsingh.eventrecorder;

//All the imports
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //initialize the variables
    RadioButton RadioBtnMedicine;
    RadioButton RadioBtnRun;
    RadioButton RadioBtnMeditate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RadioBtnMedicine = findViewById(R.id.radioButtonMedicine);
        RadioBtnRun = findViewById(R.id.radioButtonRun);
        RadioBtnMeditate = findViewById(R.id.radioButtonMeditate);
    }

    /*
     *   Method Name: OnSettingsClick
     *   Purpose: Runs when user clicks on the settings button
     */
    public void OnSettingsClick(View view) {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent); //Open the settings activity
    }


    /*
     *   Method Name:OnClickMedicine
     *   Purpose:  Runs when user clicks on the Medicine event button
     */
    public void OnClickMedicine(View view) {
        if(RadioBtnMedicine.isChecked()) {
            saveData(RadioBtnMedicine.getText().toString()); //calls the saveData Method
            RadioBtnMedicine.setChecked(false);
        }

    }


    /*
     *   Method Name: OnRecordClick
     *   Purpose: Runs when user clicks on the Medicine event button
     */
    public void OnRecordClick(View view) {
        Intent intent = new Intent(this,ReportActivity.class);
        startActivity(intent); //Open the Record activity


    }


    /*
     *   Method Name:OnClickRun
     *   Purpose:Runs when user clicks on the Run event button
     */
    public void OnClickRun(View view) {
        if(RadioBtnRun.isChecked()) {
            saveData(RadioBtnRun.getText().toString());
            RadioBtnRun.setChecked(false);
        }
    }

    /*
     *   Method Name:OnClickMeditate
     *   Purpose:Runs when user clicks on the Meditate event button
     */
    public void OnClickMeditate(View view) {
        if(RadioBtnMeditate.isChecked()) {
            saveData(RadioBtnMeditate.getText().toString());
            RadioBtnMeditate.setChecked(false);
        }
    }

    /*
     *   Method Name: saveData
     *   Purpose: Saves the String Data to the text file
     *   Input Argument: String data
     *   Output: void
     *   Research Resources:
     *   - Class Notes
     *   - https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html
     */
    public void saveData(String eventName)
    {
        try {
            //Calendar instance
            Calendar rightNow = Calendar.getInstance();

            //Getting the date
            String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(rightNow.getTime());

            //Getting the time
            SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm aa");
            String Time = currentTime.format(rightNow.getTime());

            //Writing to the file
            FileOutputStream ofile = openFileOutput("saveText.txt", MODE_APPEND);
            OutputStreamWriter osw = new OutputStreamWriter(ofile);
            osw.write("\n•  " + eventName + " recorded at " +
                    Time + " " + currentDate + "\n");
            osw.flush();
            osw.close();

            //For Toast Message
            Context context = getApplicationContext();
            CharSequence time = "Good Job! Event Recorded";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, time, duration);
            toast.show();


        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
