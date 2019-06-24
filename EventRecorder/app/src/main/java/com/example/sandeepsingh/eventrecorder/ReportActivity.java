package com.example.sandeepsingh.eventrecorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextDirectionHeuristic;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class ReportActivity extends AppCompatActivity {

    TextView recordOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        recordOutput = findViewById(R.id.textViewRecord);
        ReadData();
    }


    public void OnClickBack(View view) {
        finish();
    }

    /*
     *   Method Name:OnClickDelete
     *   Purpose:Deletes the last record of the file
     */
    public void OnClickDelete(View view) throws IOException {
        File dir = getFilesDir();
        //Log.d("Entry","dir" + dir.toString());
        RandomAccessFile f= new RandomAccessFile(dir + "/saveText.txt", "rw");
        long length = f.length() - 1;
        //bytes
        byte bi;
        if(length > 0) {
            do {
                length -= 1;
                f.seek(length);
                bi = f.readByte();
            } while (bi != 10 && length > 0);
            if (length == 0) {
                f.setLength(length);
            } else {
                f.setLength(length + 1);
            }
        //Refresh the data
        ReadData();

        //For Toast Message
        Context context = getApplicationContext();
        CharSequence msg = "Last Record Deleted!";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 10);
        toast.show();
        }
        else
        {
            //For Toast Message
            Context context = getApplicationContext();
            CharSequence msg = "Nothing to Delete!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 10);
            toast.show();
        }
    }

    /*
     *   Method Name:OnClickReset
     *   Purpose:Resets the whole data
     */
    public void OnClickReset(View view) {
        File dir = getFilesDir();
        File file = new File(dir,"saveText.txt");
        //if the file is empty
        if(file.length() <= 0){
            Context context = getApplicationContext();
            CharSequence msg = "Nothing to Reset!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 10);
            toast.show();
        }
        //If there is some record
        else {
            boolean deleted = file.delete();
            recordOutput.setText("");
            Context context = getApplicationContext();
            CharSequence msg = "All Records Deleted!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, msg, duration);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER, 0, 10);
            toast.show();
        }
    }

    /*
     *   Method Name:ReadData
     *   Purpose: Reads the Data from a file and displays to the textview
     *   Input Argument: Nothing
     *   Output: Void
     */
    public void ReadData()
    {
        try
        {
            // reading from data/data/packagename/File
            FileInputStream fin = openFileInput("saveText.txt");
            InputStreamReader isr = new InputStreamReader(fin);
            char [] inputBuffer = new char[1000];
            String str="";
            int charRead;
            while((charRead = isr.read(inputBuffer)) > 0)
            {
                String readString =String.copyValueOf(inputBuffer,0,charRead);
                str += readString;

            }
            //Activates the Scrollbar
            recordOutput.setMovementMethod(new ScrollingMovementMethod());
            recordOutput.setText(str);

        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
