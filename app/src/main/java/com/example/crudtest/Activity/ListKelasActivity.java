package com.example.crudtest.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.crudtest.Adapter.KelasAdapter;
import com.example.crudtest.Adapter.MapelAdapter;
import com.example.crudtest.Adapter.SharedPrefManager;
import com.example.crudtest.Kelas;
import com.example.crudtest.Mapel;
import com.example.crudtest.R;

import java.util.ArrayList;
import java.util.List;

public class ListKelasActivity extends AppCompatActivity implements KelasAdapter.OnItemClickListener{

    RecyclerView recyclerView;
    KelasAdapter adapter;

    List<Kelas> KelasList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_kelas);

        KelasList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recylerViewKelas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        KelasList.add(new Kelas (1));
        KelasList.add(new Kelas (2));
        KelasList.add(new Kelas (3));
        KelasList.add(new Kelas (4));
        KelasList.add(new Kelas (5));
        KelasList.add(new Kelas (6));

        adapter = new KelasAdapter(this, KelasList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        finish();
        Intent intent = new Intent(this, ListMapelActivity.class);
        Kelas clickItem = KelasList.get(position);
        SharedPrefManager.getInstance(this).setSoalKelas(clickItem.getKelas());
        intent.putExtra("soalkelas", clickItem.getKelas());
        startActivity(intent);
    }
}
