package com.example.crudtest;

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
import com.example.crudtest.Activity.LoginActivity;
import com.example.crudtest.Activity.HomeActivity;
import com.example.crudtest.Adapter.RequestHandler;
import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.Include.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextusername, editTextpassword, editTextnamalengkap;
    private Button buttonRegister, buttonloginScreen;
    private ProgressDialog loading;
    boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        editTextnamalengkap = (EditText) findViewById(R.id.txtnamalengkap);
        editTextpassword = (EditText) findViewById(R.id.txtpassword);
        editTextusername = (EditText) findViewById(R.id.txtusername);

        buttonRegister = (Button) findViewById(R.id.btnregistrasi);
        buttonloginScreen = (Button) findViewById(R.id.btngotologin);

        loading = new ProgressDialog(this);

        buttonRegister.setOnClickListener(this);
        buttonloginScreen.setOnClickListener(this);
    }

    public void RegisterUser() {
        final String namalengkap = editTextnamalengkap.getText().toString().trim();
        final String username = editTextusername.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();

        loading.setMessage("Mendaftarkan User...");
        loading.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constant.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getBoolean("error");
                            System.out.println(status);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
                params.put("namalengkap", namalengkap);
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

        if(status == false){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else{
            editTextpassword.setText("");
            editTextusername.setText("");
            editTextnamalengkap.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if(v == buttonRegister){
            RegisterUser();
        }
        if(v == buttonloginScreen){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
