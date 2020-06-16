package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.crudtest.Adapter.MapelAdapter;
import com.example.crudtest.Adapter.RequestHandler;
import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.Include.Constant;
import com.example.crudtest.Mapel;
import com.example.crudtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMapelActivity extends AppCompatActivity implements MapelAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    MapelAdapter adapter;

    List<Mapel> MapelList;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mapel);

        MapelList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loading = new ProgressDialog(this);
        loading.setMessage("Mohon Tunggu...");

        loadMapel();
    }

    public void loadMapel(){
        final int statIndo = SharedPrefManager.getInstance(this).getBindo();
        final int statMtk = SharedPrefManager.getInstance(this).getMatematika();
        final int statPkn = SharedPrefManager.getInstance(this).getPkn();
        final int statIpa = SharedPrefManager.getInstance(this).getIpa();
        final int statIps = SharedPrefManager.getInstance(this).getIps();
        final int statSbdp = SharedPrefManager.getInstance(this).getSbdp();
        final int statPjok = SharedPrefManager.getInstance(this).getPjok();
        loading.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constant.URL_TAMPIL_MAPEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray mapel = new JSONArray(response);
                            int j = 1;

                            for(int i=0; i<mapel.length(); i++ ){
                                JSONObject mapelObject = mapel.getJSONObject(i);

                                String namamapel = mapelObject.getString("mapel");

                                Mapel m = new Mapel(j, namamapel);

                                MapelList.add(m);
                                j++;
                            }

                            adapter = new MapelAdapter(ListMapelActivity.this, MapelList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(ListMapelActivity.this);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        Toast.makeText(getApplicationContext(), "Terjadi Kelsalahan, Harap Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bindonesia", String.valueOf(statIndo));
                params.put("matematika", String.valueOf(statMtk));
                params.put("pkn", String.valueOf(statPkn));
                params.put("ipa", String.valueOf(statIpa));
                params.put("ips", String.valueOf(statIps));
                params.put("pjok", String.valueOf(statPjok));
                params.put("sbdp", String.valueOf(statSbdp));
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onItemClick(int position) {
        finish();
        Intent intent = new Intent(this, MulaiQuizActivity.class);
        Mapel clickItem = MapelList.get(position);
        SharedPrefManager.getInstance(this).setMapelActive(clickItem.getNamamapel());
        intent.putExtra("namamapel", clickItem.getNamamapel());
        startActivity(intent);
    }
}
