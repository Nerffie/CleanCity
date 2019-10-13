package com.example.cleancity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleancity.models.Poubelle;
import com.example.cleancity.models.PoubelleLocation;

import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Home extends AppCompatActivity {
    private TextView nbPoubelle;
    private ArrayList<PoubelleLocation> mPoubelleLocations = new ArrayList<>();
    private ArrayList<Poubelle> mPoubelle = new ArrayList<>();
    private boolean vider = false;
    private ImageView bell;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        getPoubellesLocation();
        //doWork();
        Runnable runnable = new CountDownRunner();
        Thread myThread = null;
        myThread= new Thread(runnable);
        myThread.start();

        if (savedInstanceState == null){
            inflatePoubelleListFragment();
        }
    }

    private void getPoubellesLocation() {

        Poubelle p1 = new Poubelle(1,20,50,"1","Moitié");
        Poubelle p2 = new Poubelle(2,21,85,"2","Pleine");
        Poubelle p3 = new Poubelle(3,22,90,"3","Pleine");
        Poubelle p4 = new Poubelle(4,15,20,"4","Léger");
        Poubelle p5 = new Poubelle(5,10,10,"5","Léger");
        Poubelle p6 = new Poubelle(6,18,30,"6","Léger");
        Poubelle p7 = new Poubelle(7,30,89,"7","Pleine");
        Poubelle p8 = new Poubelle(8,14,24,"8","Léger");
        PoubelleLocation pl1 = new PoubelleLocation(p1,45.774811, 3.079328);
        PoubelleLocation pl2 = new PoubelleLocation(p2,45.772746, 3.082836);
        PoubelleLocation pl3 = new PoubelleLocation(p3,45.771433, 3.076801);
        PoubelleLocation pl4 = new PoubelleLocation(p4,45.768320, 3.079468);
        PoubelleLocation pl5 = new PoubelleLocation(p5,45.769980, 3.084121);
        PoubelleLocation pl6 = new PoubelleLocation(p6,45.771984, 3.090157);
        PoubelleLocation pl7 = new PoubelleLocation(p7,45.765695, 3.089017);
        PoubelleLocation pl8 = new PoubelleLocation(p8,45.767077, 3.097808);


        mPoubelle.add(p1);
        mPoubelle.add(p2);
        mPoubelle.add(p3);
        mPoubelle.add(p4);
        mPoubelle.add(p5);
        mPoubelle.add(p6);
        mPoubelle.add(p7);
        mPoubelle.add(p8);
        mPoubelleLocations.add(pl1);
        mPoubelleLocations.add(pl2);
        mPoubelleLocations.add(pl3);
        mPoubelleLocations.add(pl4);
        mPoubelleLocations.add(pl5);
        mPoubelleLocations.add(pl6);
        mPoubelleLocations.add(pl7);
        mPoubelleLocations.add(pl8);


    }

    private void inflatePoubelleListFragment(){
        hideSoftKeyboard();
        Map fragment = Map.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.intent_poubelle_list), mPoubelle);
        bundle.putParcelableArrayList(getString(R.string.intent_poubelle_locations), mPoubelleLocations);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        transaction.add(android.R.id.content,fragment).commit();

    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void doWork() {
        runOnUiThread(new Runnable() {
              public void run() {
                  try {
                      bell = findViewById(R.id.bell);
                      bell.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent intent = new Intent(getApplicationContext(),BellActivity.class);
                              startActivity(intent);
                          }
                      });

                      nbPoubelle = (TextView) findViewById(R.id.nbPoubelle);
                      String str = "Poubelle : "+mPoubelle.size();
                      nbPoubelle.setText(str);
                      TextView txtCurrentTime= (TextView)findViewById(R.id.time);
                      Date dt = new Date();
                      int hours = dt.getHours();
                      int minutes = dt.getMinutes();
                      int seconds = dt.getSeconds();
                      String curTime = "Time: "+hours + ":" + minutes + ":" + seconds;
                      txtCurrentTime.setText(curTime);
                  } catch (Exception e) { }
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










