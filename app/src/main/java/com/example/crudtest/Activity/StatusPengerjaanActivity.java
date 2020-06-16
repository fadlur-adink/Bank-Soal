package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.R;

public class StatusPengerjaanActivity extends AppCompatActivity {

    TextView textViewstatbindo, textViewstatmtk, textViewstatpkn, textViewstatipa, textViewstatips, textViewstatsbdp, textViewstatpjok;
    ImageView imageViewstatusbindo, imageViewstatusmtk, imageViewstatuspkn, imageViewstatusipa, imageViewstatusips, imageViewstatussbdp;
    ImageView imageViewstatuspjok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_pengerjaan);

        String statbindo = "";
        String statmtk = "";
        String statpkn = "";
        String statipa = "";
        String statips = "";
        String statsbdp = "";
        String statpjok = "";

        textViewstatbindo = (TextView) findViewById(R.id.txtstatusbindo);
        textViewstatmtk = (TextView) findViewById(R.id.txtstatusmtk);
        textViewstatpkn = (TextView) findViewById(R.id.txtstatuspkn);
        textViewstatipa = (TextView) findViewById(R.id.txtstatusipa);
        textViewstatips = (TextView) findViewById(R.id.txtstatusips);
        textViewstatsbdp = (TextView) findViewById(R.id.txtstatussbdp);
        textViewstatpjok = (TextView) findViewById(R.id.txtstatuspjok);
        imageViewstatusbindo = (ImageView) findViewById(R.id.imgstatusbindo);
        imageViewstatusmtk = (ImageView) findViewById(R.id.imgstatusmatematika);
        imageViewstatuspkn = (ImageView) findViewById(R.id.imgstatuspkn);
        imageViewstatusipa = (ImageView) findViewById(R.id.imgstatusipa);
        imageViewstatusips = (ImageView) findViewById(R.id.imgstatusips);
        imageViewstatussbdp = (ImageView) findViewById(R.id.imgstatussbdp);
        imageViewstatuspjok = (ImageView) findViewById(R.id.imgstatuspjok);

        if(SharedPrefManager.getInstance(this).getBindo() == 0){
            statbindo = "Belum Selesai";
            imageViewstatusbindo.setImageResource(R.drawable.ic_clear_red_50dp);
        }else{
            statbindo = "Sudah Selesai";
            imageViewstatusbindo.setImageResource(R.drawable.ic_check_green_50dp);
        }

        if(SharedPrefManager.getInstance(this).getMatematika() == 0){
            statmtk = "Belum Selesai";
            imageViewstatusmtk.setImageResource(R.drawable.ic_clear_red_50dp);
        }else{
            statmtk = "Sudah Selesai";
            imageViewstatusmtk.setImageResource(R.drawable.ic_check_green_50dp);
        }

        if(SharedPrefManager.getInstance(this).getPkn() == 0){
            imageViewstatuspkn.setImageResource(R.drawable.ic_clear_red_50dp);
            statpkn = "Belum Selesai";
        }else{
            statpkn = "Sudah Selesai";
            imageViewstatuspkn.setImageResource(R.drawable.ic_check_green_50dp);
        }

        if(SharedPrefManager.getInstance(this).getIpa() == 0){
            imageViewstatusipa.setImageResource(R.drawable.ic_clear_red_50dp);
            statipa = "Belum Selesai";
        }else{
            statipa = "Sudah Selesai";
            imageViewstatusipa.setImageResource(R.drawable.ic_check_green_50dp);
        }


        if(SharedPrefManager.getInstance(this).getIps() == 0){
            imageViewstatusips.setImageResource(R.drawable.ic_clear_red_50dp);
            statips = "Belum Selesai";
        }else{
            statips = "Sudah Selesai";
            imageViewstatusips.setImageResource(R.drawable.ic_check_green_50dp);
        }

        if(SharedPrefManager.getInstance(this).getSbdp() == 0){
            imageViewstatussbdp.setImageResource(R.drawable.ic_clear_red_50dp);
            statsbdp = "Belum Selesai";
        }else{
            statsbdp = "Sudah Selesai";
            imageViewstatussbdp.setImageResource(R.drawable.ic_check_green_50dp);
        }

        if(SharedPrefManager.getInstance(this).getPjok() == 0){
            imageViewstatuspjok.setImageResource(R.drawable.ic_clear_red_50dp);
            statpjok = "Belum Selesai";
        }else{
            statpjok = "Sudah Selesai";
            imageViewstatuspjok.setImageResource(R.drawable.ic_check_green_50dp);
        }

        textViewstatbindo.setText(statbindo);
        textViewstatmtk.setText(statmtk);
        textViewstatpkn.setText(statpkn);
        textViewstatipa.setText(statipa);
        textViewstatips.setText(statips);
        textViewstatsbdp.setText(statsbdp);
        textViewstatpjok.setText(statpjok);
    }
}
