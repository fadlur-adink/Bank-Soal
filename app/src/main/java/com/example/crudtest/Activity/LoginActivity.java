package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.crudtest.MainActivity;
import com.example.crudtest.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextusername, editTextpassword;
    Button buttonGotoregis, buttonLogin;
    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        editTextusername = (EditText) findViewById(R.id.txtusernamelogin);
        editTextpassword = (EditText) findViewById(R.id.txtpasswordlogin);
        buttonGotoregis = (Button) findViewById(R.id.btngotoregister);
        buttonLogin = (Button) findViewById(R.id.btnlogin);

        loading = new ProgressDialog(this);
        loading.setMessage("Mohon Tunggu...");

        buttonLogin.setOnClickListener(this);
        buttonGotoregis.setOnClickListener(this);
    }

    private void userLogin() {
        final String username = editTextusername.getText().toString().trim();
        final String password = editTextpassword.getText().toString().trim();
        loading.show();

        StringRequest stringRequest = new StringRequest(
            Request.Method.POST,
            Constant.URL_LOGIN,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        if(!jsonObject.getBoolean("error")){
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("username"),
                                    jsonObject.getString("namalengkap"),
                                    jsonObject.getInt("kelas"),
                                    jsonObject.getInt("bindonesia"),
                                    jsonObject.getInt("matematika"),
                                    jsonObject.getInt("pkn"),
                                    jsonObject.getInt("ipa"),
                                    jsonObject.getInt("ips"),
                                    jsonObject.getInt("sbdp"),
                                    jsonObject.getInt("pjok")
                            );
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    loading.dismiss();
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
                }
            }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String ,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonGotoregis) {
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if(v == buttonLogin){
            userLogin();
        }
    }
}
