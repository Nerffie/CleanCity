package com.example.cleancity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    private TextView loadQrCode;
    private EditText identifiantInput;
    private EditText passwordInput;
    private TextView confirmation;
    private RelativeLayout relativeLayout;
//    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        identifiantInput = (EditText) findViewById(R.id.identifiant);
        passwordInput = findViewById(R.id.password);
        relativeLayout = findViewById(R.id.relativeAuth);

        Toast.makeText(MainActivity.this, "Reason can not be blank", Toast.LENGTH_SHORT).show();
//        progressBar = findViewById(R.id.progressBar);
//        loadQrCode = (TextView) findViewById(R.id.qr_code_button);
//       loadQrCode.setOnClickListener(new View.OnClickListener() {
////           @Override
////            public void onClick(View v) {
//////                Intent intent = new Intent(v.getContext(),ScanCodeActivity.class);
//////                startActivityForResult(intent,100);
////            }
////        });

        confirmation = (TextView) findViewById(R.id.confirm);
        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //test if identifiant field is not empty
                if(!identifiantInput.getText().toString().trim().isEmpty() && !passwordInput.getText().toString().trim().isEmpty()){
                    // test if this identifiant id in the data base
                    lunchMapsActivity();
//                    postToastMessage("hello world");
                    Log.i("toast","affichage toast");
                    Toast.makeText(getApplicationContext(),"Connexion ...",Toast.LENGTH_SHORT).show();
                }else{
                    //toast message to informe that the identifiant is empty
                    Log.i("toast","affichage toast veuillz entre ...");
//                    postToastMessage("hello world");
                    Toast.makeText(getApplicationContext(),"Veuillez entrer l'identifiant ou le mot de passe.",Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, "Reason can not be blank", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    public void postToastMessage(final String message) {
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        handler.post(new Runnable() {
//
//            @Override
//            public void run() {
//                Toast.makeText(, message, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    private void lunchMapsActivity() {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        Toast.makeText(getApplicationContext(),"Bienvenu "+identifiantInput.getText().toString().trim().isEmpty()+" .",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 100){
//            if(resultCode == 2000)
//                identifiantInput.setText(data.getExtras().getString("identifiant"));
//        }else{
//            //testing for the result code
//            identifiantInput.setText(null);
//            passwordInput.setText(null);
//            progressBar.setVisibility(View.INVISIBLE);
//            relativeLayout.setVisibility(View.VISIBLE);
//        }
//
//    }

//    private void getPolicierData(String identifiant,String password){
//
//        HashMap<String,String> parameters = new HashMap<>();
//        parameters.put("id_policier",identifiant);
//        parameters.put("password",password);
//
//        relativeLayout.setVisibility(View.INVISIBLE);
//        progressBar.setVisibility(View.VISIBLE);
//
//        Call<AuthApi> response = RetrofitClient.getInstance().getApi().getPolicierData(parameters);
//
//        response.enqueue(new Callback<AuthApi>() {
//            @Override
//            public void onResponse(Call<AuthApi> call, Response<AuthApi> response) {
//                if(!response.isSuccessful()){
//                    Log.i("Erreur","Code : "+response.code());
//                    AuthFails();
//                    return;
//                }
//                Log.i("Success",response.body().getToken());
//                Log.i("Success",response.body().getPolicier().toString());
//                AuthSuccess(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<AuthApi> call, Throwable t) {
//                Log.i("Erreur","message : "+t.getMessage());
//                AuthErreur();
//            }
//        });
//
//    }

//    private void AuthSuccess(AuthApi response) {
//        //then lunch the id barrage activity
//        Policier policier = response.getPolicier();
//        Intent intent = new Intent(getApplicationContext(),BarrageActivity.class);
//        intent.putExtra("auth",response);
//        Toast.makeText(getApplicationContext(),"Bienvenu monsieur "+policier.getNom()+" "+policier.getPrenom()+" .",Toast.LENGTH_SHORT).show();
//        startActivityForResult(intent,150);
//    }
//
//    private void AuthFails(){
//        //else toast message to informe that the identifiant is wrong
//        Toast.makeText(getApplicationContext(),"Verifier svp la connection!!",Toast.LENGTH_SHORT).show();
//        identifiantInput.setText(null);
//        passwordInput.setText(null);
//        progressBar.setVisibility(View.INVISIBLE);
//        relativeLayout.setVisibility(View.VISIBLE);
//    }
//    private void AuthErreur(){
//        Toast.makeText(getApplicationContext(),"Identifiant ou password et incorrect!!",Toast.LENGTH_SHORT).show();
//        identifiantInput.setText(null);
//        passwordInput.setText(null);
//        progressBar.setVisibility(View.INVISIBLE);
//        relativeLayout.setVisibility(View.VISIBLE);
//    }

}
