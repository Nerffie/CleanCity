package com.example.cleancity;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.cleancity.models.Poubelle;
import com.example.cleancity.models.PoubelleLocation;
import com.example.cleancity.models.User;
import com.example.cleancity.models.UserLocation;
import com.example.cleancity.service.LocationService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;


import android.widget.TextView;
import com.example.cleancity.models.Poubelle;
import com.example.cleancity.models.PoubelleLocation;

import java.util.ArrayList;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import lombok.NonNull;

import static com.example.cleancity.Constants.ERROR_DIALOG_REQUEST;
import static com.example.cleancity.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Home extends AppCompatActivity {
    private TextView nbPoubelle;
    private ArrayList<PoubelleLocation> mPoubelleLocations = new ArrayList<>();
    private ArrayList<Poubelle> mPoubelle = new ArrayList<>();
    private User mUser;
    private UserLocation mUserLocation = new UserLocation();
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getPoubellesLocation();

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
        mUser = new User(1,"John Doe","Lambda");


        getLastKnownLocation();

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
        //if (savedInstanceState == null){
        //    getSupportFragmentManager().beginTransaction()
        //            .add(android.R.id.content, new Map()).commit();}
        Map fragment = Map.newInstance();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(getString(R.string.intent_poubelle_list), mPoubelle);
        bundle.putParcelableArrayList(getString(R.string.intent_poubelle_locations), mPoubelleLocations);
        bundle.putParcelable(getString(R.string.intent_user),mUser);
        bundle.putParcelable(getString(R.string.intent_user_location),mUserLocation);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
        transaction.add(android.R.id.content,fragment).commit();
        //transaction.replace(R.id.content, fragment, "Fragment List");
        //transaction.addToBackStack("Fragment List");
        //transaction.commit();
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

    private void getLastKnownLocation() {
        Log.d("Home", "getLastKnownLocation: called.");


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        /*mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                if (task.isSuccessful()) {
                    Location location = task.getResult();
                    mUserLocation.setLat(location.getLatitude());
                    mUserLocation.setLon(location.getLongitude());
                    mUserLocation.setTimestamp(null);
                    Log.d("HOME","J'ai lancé le get last connection");
                    startLocationService();
                }
            }
        });*/
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mUserLocation.setLon(location.getLongitude());
                            mUserLocation.setLat(location.getLatitude());
                            mUserLocation.setTimestamp(null);
                            startLocationService();

                        }
                    }
                });

    }


    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, LocationService.class);
//        this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                Home.this.startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
        }
    }




    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.codingwithmitch.googledirectionstest.services.LocationService".equals(service.service.getClassName())) {
                Log.d("HOME", "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d("HOME", "isLocationServiceRunning: location service is not running.");
        return false;
    }












}



