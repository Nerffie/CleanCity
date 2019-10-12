package com.example.cleancity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    private TextView nbPoubelle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Runnable runnable = new CountDownRunner();
        Thread myThread = null;
        myThread= new Thread(runnable);
        myThread.start();
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new Map()).commit();}
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    nbPoubelle = (TextView) findViewById(R.id.nbPoubelle);
                    String str = "Poubelle: 34";
                    nbPoubelle.setText(str);
                    TextView txtCurrentTime= (TextView)findViewById(R.id.time);
                    Date dt = new Date();
                    int hours = dt.getHours();
                    int minutes = dt.getMinutes();
                    int seconds = dt.getSeconds();
                    String curTime = "Time: "+hours + ":" + minutes + ":" + seconds;
                    txtCurrentTime.setText(curTime);
                }catch (Exception e) {}
            }
        });
    }

    class CountDownRunner implements Runnable{
        // @Override
        public void run() {

            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

}
