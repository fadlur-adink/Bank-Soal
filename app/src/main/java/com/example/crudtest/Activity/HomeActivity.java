package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.R;

import java.util.concurrent.TimeUnit;

public class HomeActivity extends AppCompatActivity{

    TextView textViewNama, textViewKelas, textViewUserKelas;
    ImageView imageViewprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        MediaPlayer mpbenar = MediaPlayer.create(this, R.raw.selamatdatang);
        mpbenar.start();

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        textViewKelas = (TextView) findViewById(R.id.lblitemdashboard);
        textViewNama = (TextView) findViewById(R.id.lbldashboardname);
        textViewUserKelas = (TextView) findViewById(R.id.lbluserkelas);
        imageViewprofile = (ImageView) findViewById(R.id.imgprofile2);

        setHeader();

    }

    public void setHeader(){
        textViewKelas.setText("Kelas");
        if(SharedPrefManager.getInstance(this).getUserKelas() != 7) {
            textViewUserKelas.setText(String.valueOf(SharedPrefManager.getInstance(this).getUserKelas()));
        }else{
            textViewKelas.setText("");
            textViewUserKelas.setText("Lulus");
        }
        textViewNama.setText(SharedPrefManager.getInstance(this).getUserFullname());
        if(SharedPrefManager.getInstance(this).getUserKelas() == 1){
            imageViewprofile.setImageResource(R.drawable.person2);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 2){
            imageViewprofile.setImageResource(R.drawable.person4);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 3){
            imageViewprofile.setImageResource(R.drawable.person3);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 4){
            imageViewprofile.setImageResource(R.drawable.person5);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 5){
            imageViewprofile.setImageResource(R.drawable.person6);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 6){
            imageViewprofile.setImageResource(R.drawable.person7);
        }else if(SharedPrefManager.getInstance(this).getUserKelas() == 7){
            imageViewprofile.setImageResource(R.drawable.person8);
        }
    }

    public void kalkulator(View view){
        Intent myIntent = new Intent(getBaseContext(), KalkulatorActivity.class);
        startActivity(myIntent);
    }

    public void tentang(View view){
        Intent myIntent = new Intent(getBaseContext(), ActivityTentang.class);
        startActivity(myIntent);
    }

    public void profile(View view){
        Intent myIntent = new Intent(getBaseContext(), ProfileActivity.class);
        startActivity(myIntent);
    }

    public void mulaiquiz(View view){
        if(SharedPrefManager.getInstance(this).getUserKelas() == 7){
            Intent myIntent = new Intent(getBaseContext(), ListKelasActivity.class);
            startActivity(myIntent);
        }else {
            Intent myIntent = new Intent(getBaseContext(), ListMapelActivity.class);
            startActivity(myIntent);
        }
    }

    public void keluar(View view) throws InterruptedException {
        MediaPlayer mpkeluar = MediaPlayer.create(this, R.raw.keluar);
        mpkeluar.start();
        TimeUnit.SECONDS.sleep(3);
        finish();
        System.exit(0);
    }
}
