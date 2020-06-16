package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class InfoAkunActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUsername, editTextFullname, editTextPassword;
    Button buttonUbah, buttonBack;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_akun);

        editTextFullname = (EditText) findViewById(R.id.txteditfullname);
        editTextUsername = (EditText) findViewById(R.id.txteditusername);
        editTextPassword = (EditText) findViewById(R.id.txteditpassword);
        buttonUbah = (Button) findViewById(R.id.btneditinfo);
        buttonBack = (Button) findViewById(R.id.btnbataledit);

        editTextUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        editTextFullname.setText(SharedPrefManager.getInstance(this).getUserFullname());

        buttonBack.setOnClickListener(this);
        buttonUbah.setOnClickListener(this);

        loading = new ProgressDialog(this);

    }

    public void ubahInfoAkun() {
        final String namalengkap = editTextFullname.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final int idsiswa = SharedPrefManager.getInstance(this).getId();
        final int kelas = SharedPrefManager.getInstance(this).getUserKelas();

        loading.setMessage("Merubah Info Akun...");
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_UBAH_INFO_AKUN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                            SharedPrefManager.getInstance(InfoAkunActivity.this).refreshInfo(idsiswa, username, namalengkap, kelas);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.d("Error Response",error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idsiswa", String.valueOf(idsiswa));
                params.put("username", username);
                params.put("namalengkap", namalengkap);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonBack){
            finish();
        }
        if(v == buttonUbah){
            if(editTextPassword.getText().equals(" ") || editTextPassword.getText().equals("")){
                Toast.makeText(getApplicationContext(), "Harap Isi Kolom Password!!", Toast.LENGTH_SHORT).show();
            }else {
                ubahInfoAkun();
            }
        }
    }
}
