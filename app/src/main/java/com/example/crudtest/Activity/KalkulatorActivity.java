package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.crudtest.R;

public class KalkulatorActivity extends AppCompatActivity {

    TextView lblhasil, lbltemp;
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnkoma;
    Button btnbagi, btnce, btnhapus, btnclear, btnkali, btntambah, btnkurang, btnhasil;

    private String operator;
    private String angka1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalkulator);

        lblhasil = findViewById(R.id.textView2);
        lbltemp = findViewById(R.id.textView);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn0 = findViewById(R.id.btn_0);
        btnkoma = findViewById(R.id.btn_koma);
        btntambah = findViewById(R.id.btn_Tambah);
        btnkurang = findViewById(R.id.btn_kurang);
        btnkali = findViewById(R.id.btn_kali);
        btnbagi = findViewById(R.id.btn_bagi);
        btnhasil = findViewById(R.id.btn_hasil);
        btnhapus = findViewById(R.id.btn_hapus);
        btnce = findViewById(R.id.btn_ce);
        btnclear = findViewById(R.id.btn_c);

        btnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblhasil.setText("");
                lbltemp.setText("");
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lblhasil.setText("");
            }
        });

        btnhapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                int length = temp.length();
                if(length != 0){
                    String hapus = temp.substring(0,length-1);
                    lblhasil.setText(String.valueOf(hapus));
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"1"));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"2"));
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"3"));
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"4"));
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"5"));
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"6"));
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"7"));
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"8"));
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"9"));
            }
        });

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"0"));
            }
        });

        btnkoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lblhasil.setText(String.valueOf(temp+"."));
            }
        });

        btnbagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lbltemp.setText(String.valueOf(temp+" /"));
                angka1 = temp;
                lblhasil.setText("");
                operator = "bagi";
            }
        });

        btnkali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lbltemp.setText(String.valueOf(temp+" X"));
                angka1 = temp;
                lblhasil.setText("");
                operator = "kali";
            }
        });

        btnkurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lbltemp.setText(String.valueOf(temp+" -"));
                angka1 = temp;
                lblhasil.setText("");
                operator = "kurang";
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String temp = lblhasil.getText().toString();
                lbltemp.setText(String.valueOf(temp+" +"));
                angka1 = temp;
                lblhasil.setText("");
                operator = "tambah";
            }
        });

        btnhasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String angka = lblhasil.getText().toString();
                double angka2 = Double.parseDouble(angka);
                double angkasatu = Double.parseDouble(angka1);
                if (operator.equals("tambah")){
                    Double hasil = angkasatu + angka2;
                    lblhasil.setText(hasil.toString());
                    lbltemp.setText(String.valueOf(angkasatu)+" + "+String.valueOf(angka2));
                }else if(operator.equals("kurang")){
                    Double hasil = angkasatu - angka2;
                    lblhasil.setText(hasil.toString());
                    lbltemp.setText(String.valueOf(angkasatu)+" - "+String.valueOf(angka2));
                }else if(operator.equals("kali")){
                    Double hasil = angkasatu * angka2;
                    lblhasil.setText(hasil.toString());
                    lbltemp.setText(String.valueOf(angkasatu)+" X "+String.valueOf(angka2));
                }else if(operator.equals("bagi")){
                    Double hasil = angkasatu / angka2;
                    lblhasil.setText(hasil.toString());
                    lbltemp.setText(String.valueOf(angkasatu)+" / "+String.valueOf(angka2));
                }
            }
        });
    }
}
