package com.example.crudtest.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.crudtest.Adapter.RequestHandler;
import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.Include.Constant;
import com.example.crudtest.R;
import com.example.crudtest.Soal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {

    long START_TIME_IN_MILLIS = 0;
    long mTimeLeftInMillis;

    boolean mTimerRunning;

    TextView textViewSisaSoal, textViewNyawa, textViewSoal, textViewSiswaWaktu, textViewSkor;
    Button buttonJawaba, buttonJawabb, buttonJawabc, buttonJawabd;
    ImageView imgsoal;
    ProgressDialog loading;
    MediaPlayer mediaPlayerSalah, mediaPlayerBenar;

    CountDownTimer mCountDownTimer;

    List<Soal> SoalList;

    int score = 0;
    int combo = 1;
    int i = 0;
    int sisasoal = 1;
    int nyawa = 3;
    String jawabanbenar, textjawabanbenar;
    String soalkelas = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        int kelas = SharedPrefManager.getInstance(getApplicationContext()).getUserKelas();
        int soalkelass = SharedPrefManager.getInstance(this).getSoalKelas();

        if(kelas == 1 || kelas == 7 && soalkelass == 1){
            START_TIME_IN_MILLIS = 1500000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }else if(kelas == 2 || kelas == 7 && soalkelass == 2){
            START_TIME_IN_MILLIS = 1500000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }else if(kelas == 3 || kelas == 7 && soalkelass == 3){
            START_TIME_IN_MILLIS = 1800000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }else if(kelas == 4 || kelas == 7 && soalkelass == 4){
            START_TIME_IN_MILLIS = 2100000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }else if(kelas == 5 || kelas == 7 && soalkelass == 5){
            START_TIME_IN_MILLIS = 2400000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }else if(kelas == 6 || kelas == 7 && soalkelass == 6){
            START_TIME_IN_MILLIS = 2700000;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
        }

        textViewNyawa = (TextView) findViewById(R.id.lblsisanyawa);
        textViewSisaSoal = (TextView) findViewById(R.id.lblsisasoal);
        textViewSoal = (TextView) findViewById(R.id.lblsoal);
        textViewSiswaWaktu = (TextView) findViewById(R.id.lblwaktu);
        textViewSkor = (TextView) findViewById(R.id.lblscore);
        imgsoal = (ImageView) findViewById(R.id.imgsoal);

        mediaPlayerBenar = MediaPlayer.create(this, R.raw.benar);
        mediaPlayerSalah = MediaPlayer.create(this, R.raw.salah);

        loading = new ProgressDialog(this);
        loading.setMessage("Memuat Soal ...");

        SoalList = new ArrayList<>();

        buttonJawaba = (Button) findViewById(R.id.btnjawabana);
        buttonJawabb = (Button) findViewById(R.id.btnjawabanb);
        buttonJawabc = (Button) findViewById(R.id.btnjawabanc);
        buttonJawabd = (Button) findViewById(R.id.btnjawaband);

        String namamapel = SharedPrefManager.getInstance(this).getMapelActive();

        startTimer();

        getSoalFromDb();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Keluar Quiz")
                .setMessage("Apakah Anda Yakin Ingin Keluar Quiz?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mCountDownTimer.cancel();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void getSoalFromDb(){
        final String namamapel = SharedPrefManager.getInstance(this).getMapelActive();
        if(SharedPrefManager.getInstance(this).getUserKelas() == 7){
            soalkelas = String.valueOf(SharedPrefManager.getInstance(this).getSoalKelas());
        }else{
            soalkelas = String.valueOf(SharedPrefManager.getInstance(this).getUserKelas());
        }
        final String kelas = soalkelas;
        System.out.println("Mapel : "+namamapel);
        System.out.println("Kelas : "+kelas);
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_GET_SOAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray quiz = new JSONArray(response);

                            for(int i=0; i<quiz.length(); i++ ){
                                JSONObject quizObject = quiz.getJSONObject(i);

                                String soal = quizObject.getString("soal");
                                String jawaba = quizObject.getString("jawaba");
                                String jawabb = quizObject.getString("jawabb");
                                String jawabc = quizObject.getString("jawabc");
                                String jawabd = quizObject.getString("jawabd");
                                String jawabbenar = quizObject.getString("jawabbenar");
                                String imgsoal = quizObject.getString("gambar");

                                prepareQuiz(soal, jawaba, jawabb, jawabc, jawabd, jawabbenar, imgsoal);
                            }
                            Collections.shuffle(SoalList);
                            loading.dismiss();
                            setQuiz(i,1,false);
                            buttonPressed();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("response",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("error reponse", "onErrorResponse: "+error.toString());
                        loading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mapel", namamapel);
                params.put("kelas", kelas);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void prepareQuiz(String soal, String jawaba, String jawabb, String jawabc, String jawabd, String jawabbenar, String imgsoal){
        Soal s = new Soal(soal, jawaba, jawabb, jawabc, jawabd, jawabbenar, imgsoal);

        SoalList.add(s);
    }

    public void setQuiz(int index, int sisasoal, boolean salah){
        int limitSoalByKelas = 0;
        int kelas = SharedPrefManager.getInstance(this).getUserKelas();
        int soalKelas = SharedPrefManager.getInstance(this).getSoalKelas();

        if(kelas == 1 || kelas == 7 && soalKelas == 1){
            limitSoalByKelas = 50;
        }else if(kelas == 2 || kelas == 7 && soalKelas == 2){
            limitSoalByKelas = 50;
        }else if(kelas == 3 || kelas == 7 && soalKelas == 3){
            limitSoalByKelas = 50;
        }else if(kelas == 4 || kelas == 7 && soalKelas == 4){
            limitSoalByKelas = 50;
        }else if(kelas == 5 || kelas == 7 && soalKelas == 5){
            limitSoalByKelas = 50;
        }else if(kelas == 6 || kelas == 7 && soalKelas == 6){
            limitSoalByKelas = 50;
        }

        if(index+1 > limitSoalByKelas){
            if(salah) {
                nyawa--;
            }
            mCountDownTimer.cancel();
            finish();
            Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
            intent.putExtra("sisanyawa", nyawa);
            intent.putExtra("hasilquiz", "menang");
            intent.putExtra("sisawaktu",mTimeLeftInMillis);
            intent.putExtra("score",score);
            startActivity(intent);
        }else{
            int sisa = Integer.valueOf(limitSoalByKelas-(index+1));
            System.out.println("Sisa Soal Dari List SoalList Untuk setQuiz : "+sisa);

            if(salah == false){
                score = score + combo;
                if(combo < 3){
                    combo++;
                }
            }

            if(salah) {
                nyawa--;
                combo = 1;
            }
            textViewSkor.setText(String.valueOf(score));

            if(nyawa < 0){
                mCountDownTimer.cancel();
                finish();
                Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                intent.putExtra("sisanyawa", nyawa);
                intent.putExtra("hasilquiz", "kalah");
                intent.putExtra("sisawaktu",mTimeLeftInMillis);
                intent.putExtra("score",score);
                startActivity(intent);
            }

            textViewSisaSoal.setText("Soal ke "+String.valueOf(sisasoal)+"/"+limitSoalByKelas);
            textViewNyawa.setText(String.valueOf(nyawa));

            System.out.println("Index : "+index);
            jawabanbenar = SoalList.get(index).getJawabbenar();
            if(jawabanbenar.equals("a")){
                textjawabanbenar = SoalList.get(index).getJawaba();
                System.out.println("Text jawaban benar : "+textjawabanbenar);
            }
            if(jawabanbenar.equals("b")){
                textjawabanbenar = SoalList.get(index).getJawabb();
                System.out.println("Text jawaban benar : "+textjawabanbenar);
            }
            if(jawabanbenar.equals("c")){
                textjawabanbenar = SoalList.get(index).getJawabc();
                System.out.println("Text jawaban benar : "+textjawabanbenar);
            }
            if(jawabanbenar.equals("d")){
                textjawabanbenar = SoalList.get(index).getJawabd();
                System.out.println("Text jawaban benar : "+textjawabanbenar);
            }

            if(!SoalList.get(index).getGambar().equals("Tidak_Ada.jpg")){
                Glide.with(QuizActivity.this).
                        load(Constant.ROOT_URL_GAMBAR+SoalList.get(index).getGambar()).
                        placeholder(android.R.drawable.progress_indeterminate_horizontal).
                        error(android.R.drawable.stat_notify_error).
                        apply(new RequestOptions().override(400, 450)).
                        into(imgsoal);
            }else{
                Glide.with(QuizActivity.this).
                        load(Constant.ROOT_URL_GAMBAR+SoalList.get(index).getGambar()).
                        placeholder(android.R.drawable.progress_indeterminate_horizontal).
                        error(android.R.drawable.stat_notify_error).
                        apply(new RequestOptions().override(1,1)).
                        into(imgsoal);
            }

            textViewSoal.setText(SoalList.get(index).getSoal());
            buttonJawaba.setText(SoalList.get(index).getJawaba());
            buttonJawabb.setText(SoalList.get(index).getJawabb());
            buttonJawabc.setText(SoalList.get(index).getJawabc());
            buttonJawabd.setText(SoalList.get(index).getJawabd());
        }
    }

    public void buttonPressed(){
        buttonJawaba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoalList.get(i).getJawaba().equals(textjawabanbenar)){
                    Toast.makeText(QuizActivity.this, "Jawaban Benar "+ ("\ud83d\ude01"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerBenar.start();
                    setQuiz(i,sisasoal,false);
                }else{
                    Toast.makeText(QuizActivity.this, "Jawaban salah "+ ("\ud83d\ude22"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerSalah.start();
                    setQuiz(i,sisasoal,true);
                }
            }
        });
        buttonJawabb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoalList.get(i).getJawabb().equals(textjawabanbenar)){
                    Toast.makeText(QuizActivity.this, "Jawaban Benar "+ ("\ud83d\ude01"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerBenar.start();
                    setQuiz(i,sisasoal,false);
                }else{
                    Toast.makeText(QuizActivity.this, "Jawaban salah "+ ("\ud83d\ude22"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerSalah.start();
                    setQuiz(i,sisasoal,true);
                }
            }
        });
        buttonJawabc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoalList.get(i).getJawabc().equals(textjawabanbenar)){
                    Toast.makeText(QuizActivity.this, "Jawaban Benar "+ ("\ud83d\ude01"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerBenar.start();
                    setQuiz(i,sisasoal,false);
                }else{
                    Toast.makeText(QuizActivity.this, "Jawaban salah "+ ("\ud83d\ude22"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerSalah.start();
                    setQuiz(i,sisasoal,true);
                }
            }
        });
        buttonJawabd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SoalList.get(i).getJawabd().equals(textjawabanbenar)){
                    Toast.makeText(QuizActivity.this, "Jawaban Benar "+ ("\ud83d\ude01"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerBenar.start();
                    setQuiz(i,sisasoal,false);
                }else{
                    Toast.makeText(QuizActivity.this, "Jawaban salah "+ ("\ud83d\ude22"), Toast.LENGTH_LONG).show();
                    i++;
                    sisasoal++;
                    mediaPlayerSalah.start();
                    setQuiz(i,sisasoal,true);
                }
            }
        });
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                intent.putExtra("sisanyawa", nyawa);
                intent.putExtra("hasilquiz", "waktuhabis");
                intent.putExtra("sisawaktu",mTimeLeftInMillis);
                intent.putExtra("score",score);
                startActivity(intent);

            }
        }.start();
        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        if(minutes <= 1){
            textViewSiswaWaktu.setTextColor(Color.RED);
        }

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewSiswaWaktu.setText(timeLeftFormatted);
    }
}
