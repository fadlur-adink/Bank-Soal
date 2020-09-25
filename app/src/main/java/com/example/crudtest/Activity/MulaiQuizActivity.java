package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MulaiQuizActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewnamamapel, textViewjumlahmapel, textViewwaktupengerjaan, textViewbestscore;
    Button buttonMulaiQuiz;

    String kelas = "";
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mulai_quiz);

        textViewjumlahmapel = (TextView) findViewById(R.id.lbljumlahsoal);
        textViewnamamapel = (TextView) findViewById(R.id.lblnamamapel);
        textViewwaktupengerjaan = (TextView) findViewById(R.id.lbltotalwaktu);
        textViewbestscore = (TextView) findViewById(R.id.lblbestscore);

        int kelas = SharedPrefManager.getInstance(getApplicationContext()).getUserKelas();
        int soalkelas = SharedPrefManager.getInstance(this).getSoalKelas();
        if(kelas == 1 || kelas == 7 && soalkelas == 1){
            textViewwaktupengerjaan.setText("25 Menit");
            textViewjumlahmapel.setText("50");
        }else if(kelas == 2 || kelas == 7 && soalkelas == 2){
            textViewwaktupengerjaan.setText("25 Menit");
            textViewjumlahmapel.setText("50");
        }else if(kelas == 3 || kelas == 7 && soalkelas == 3){
            textViewwaktupengerjaan.setText("30 Menit");
            textViewjumlahmapel.setText("50");
        }else if(kelas == 4 || kelas == 7 && soalkelas == 4){
            textViewwaktupengerjaan.setText("35 Menit");
            textViewjumlahmapel.setText("50");
        }else if(kelas == 5 || kelas == 7 && soalkelas == 5){
            textViewwaktupengerjaan.setText("40 Menit");
            textViewjumlahmapel.setText("50");
        }else if(kelas == 6 || kelas == 7 && soalkelas == 6){
            textViewwaktupengerjaan.setText("45 Menit");
            textViewjumlahmapel.setText("50");
        }

        buttonMulaiQuiz = (Button) findViewById(R.id.btnmulaiquiz);
        buttonMulaiQuiz.setOnClickListener(this);

        loading = new ProgressDialog(this);
        loading.setMessage("Mohon Tunggu ...");

        Intent intent = getIntent();
        String namamapel = intent.getStringExtra("namamapel");

        textViewnamamapel.setText(namamapel);
        getJumlahSoalFromDb(namamapel);
    }

    public void getJumlahSoalFromDb(final String namamapel){
        if(SharedPrefManager.getInstance(this).getUserKelas() == 7){
            kelas = String.valueOf(SharedPrefManager.getInstance(this).getSoalKelas());
        }else{
            kelas = String.valueOf(SharedPrefManager.getInstance(this).getUserKelas());
        }
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_TAMPIL_JUMLAH_SOAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                SharedPrefManager.getInstance(MulaiQuizActivity.this).setJumlahSoal(jsonObject.getInt("jumlahsoal"));
                                SharedPrefManager.getInstance(MulaiQuizActivity.this).setBestScore(jsonObject.getInt("bestscore"));
                                textViewbestscore.setText(String.valueOf(jsonObject.getInt("bestscore")));

                            }else{
                                System.out.println("Jumlah Soal = "+jsonObject.getInt("jumlahsoal"));
                                Toast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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

    public void cekBestScore(){

    }

    @Override
    public void onClick(View v) {
        finish();
        startActivity(new Intent(getApplicationContext(), QuizActivity.class));
    }
}
