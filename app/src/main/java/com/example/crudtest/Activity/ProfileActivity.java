package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.R;

public class ProfileActivity extends AppCompatActivity {

    ImageView imageViewprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewprofile = (ImageView) findViewById(R.id.imgprofile);

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

    public void logout(View view){
        SharedPrefManager.getInstance(this).logOut();
        finish();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void ubahInfo(View view){
        Intent intent = new Intent(this, InfoAkunActivity.class);
        startActivity(intent);
    }

    public void statusPengerjaan(View view){
        Intent intent = new Intent (this, StatusPengerjaanActivity.class);
        startActivity(intent);
    }
}
