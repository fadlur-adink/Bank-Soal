package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.crudtest.Adapter.RequestHandler;
import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.Include.Constant;
import com.example.crudtest.R;

import java.util.HashMap;
import java.util.Map;

public class ScoreActivity extends AppCompatActivity{

    Button  btnbacktomenu, buttoncobalagi;
    TextView lblpengumuman, lblsisanyawa, lbltotalscore, lblsisawaktu, lblscore;
    ImageView imageViewhasil;

    int statindo, statmtk, statpkn, statipa, statips, statsbdp, statpjok, totalscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        statindo = SharedPrefManager.getInstance(this).getBindo();
        statmtk = SharedPrefManager.getInstance(this).getMatematika();
        statipa = SharedPrefManager.getInstance(this).getIpa();
        statips = SharedPrefManager.getInstance(this).getIps();
        statpkn = SharedPrefManager.getInstance(this).getPkn();
        statsbdp = SharedPrefManager.getInstance(this).getSbdp();
        statpjok = SharedPrefManager.getInstance(this).getPjok();

        Intent intent = getIntent();
        int sisanyawa = intent.getIntExtra("sisanyawa",0);
        String hasilquiz = intent.getStringExtra("hasilquiz");
        int score = intent.getIntExtra("score",0);
        long sisawaktumili = intent.getLongExtra("sisawaktu", 0);
        int sisawaktu = (int) (sisawaktumili/1000);

        lblpengumuman = (TextView) findViewById(R.id.lblpengumuman);
        lblsisanyawa = (TextView) findViewById(R.id.lblsisanyawa);
        lbltotalscore = (TextView) findViewById(R.id.lbltotalscore);
        lblsisawaktu = (TextView) findViewById(R.id.lblsisawaktu);
        lblscore = (TextView) findViewById(R.id.lblscoreakhir);
        btnbacktomenu = (Button) findViewById(R.id.btnbacktomenu);
        buttoncobalagi = (Button) findViewById(R.id.btncobalagi);
        imageViewhasil = (ImageView) findViewById(R.id.imghasil);

        totalscore = sisawaktu + sisanyawa + score;
        lblscore.setText(String.valueOf(score));
        lblsisawaktu.setText(String.valueOf(sisawaktu)+" Detik");
        if(hasilquiz.equals("menang")){
            lblpengumuman.setText("Selamat Kamu Berhasil Menyelesaikan Quiz");
            lblpengumuman.setTextColor(Color.GREEN);
            lblsisanyawa.setText(String.valueOf(sisanyawa));
            lbltotalscore.setText(String.valueOf(totalscore));
            imageViewhasil.setImageResource(R.drawable.ic_sentiment_satisfied_black_24dp);
            buttoncobalagi.setVisibility(buttoncobalagi.GONE);
            if(SharedPrefManager.getInstance(this).getUserKelas() != 7) {
                updateStatusMapel();
            }
        }else if(hasilquiz.equals("waktuhabis")){
            lblpengumuman.setText("Yah Kamu Gagal Menyelesaikan Quiz Karena Waktu Habis, Jangan Menyerah Silahkan Coba Lagi!");
            lblsisawaktu.setTextColor(Color.RED);
            lblsisanyawa.setText(String.valueOf(sisanyawa));
            lbltotalscore.setText(String.valueOf(totalscore));
            imageViewhasil.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_100dp);
        }else{
            lblpengumuman.setText("Yah Kamu Gagal Menyelesaikan Quiz, Jangan Menyerah Silahkan Coba Lagi!");
            lblsisanyawa.setTextColor(Color.RED);;
            lblsisanyawa.setText("0");
            lbltotalscore.setText(String.valueOf(totalscore));
            imageViewhasil.setImageResource(R.drawable.ic_sentiment_dissatisfied_black_100dp);
        }

        btnbacktomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        buttoncobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(getApplicationContext(), QuizActivity.class));
            }
        });
    }

    public void updateStatusMapel(){
        final String username = SharedPrefManager.getInstance(this).getUsername();
        final String mapel = SharedPrefManager.getInstance(this).getMapelActive();
        final int kelas = SharedPrefManager.getInstance(this).getUserKelas();
        final String congratword= "Selamat Kamu Berhasil Menyelesaikan Quiz Mata Pelajaran "+mapel+" Kelas "+kelas;

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_UPDATE_STATUS_MAPEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(totalscore > SharedPrefManager.getInstance(getApplicationContext()).getBestScore()){
                            updateBestScore();
                        }
                        Toast.makeText(getApplicationContext(), congratword, Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(getApplicationContext()).updateStatusMapel(1,mapel);
                        if(SharedPrefManager.getInstance(getApplicationContext()).getBindo() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getPkn() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getIpa() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getIps() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getSbdp() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getMatematika() == 1
                                && SharedPrefManager.getInstance(getApplicationContext()).getPjok() == 1){
                            updateKelasSiswa();
                            lblpengumuman.setText("Selamat Kamu Berhasil Menyelesaikan Quiz dan Selamat Kamu Naik Kelas ke Kelas "+(kelas+1));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("mapel",mapel);
                params.put("username", username);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void updateKelasSiswa(){
        final String username = SharedPrefManager.getInstance(this).getUsername();
        final int kelas = SharedPrefManager.getInstance(this).getUserKelas();
        final String congratword= "Selamat Kamu Berhasil Naik Kelas Ke Kelas "+(kelas+1);

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_UPDATE_KELAS_SISWA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), congratword, Toast.LENGTH_LONG).show();
                        SharedPrefManager.getInstance(getApplicationContext()).updateKelasSiswa(kelas+1);
                        SharedPrefManager.getInstance(getApplicationContext()).resetStatusMapel();
                        btnbacktomenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("kelas",String.valueOf(kelas));
                params.put("username", username);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void updateBestScore(){
        final int kelas = SharedPrefManager.getInstance(this).getUserKelas();
        final String mapel = SharedPrefManager.getInstance(this).getMapelActive();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_INSERT_BEST_SCORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SharedPrefManager.getInstance(getApplicationContext()).setBestScore(totalscore);
                        lbltotalscore.getResources().getColor(R.color.highscore);
                        lbltotalscore.setText(String.valueOf(totalscore)+" NEW HIGH SCORE!!!");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("mapel", mapel);
                params.put("kelas", String.valueOf(kelas));
                params.put("score", String.valueOf(totalscore));
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
