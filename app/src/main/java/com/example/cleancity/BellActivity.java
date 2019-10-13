package com.example.cleancity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cleancity.RecyclerViewItems.MyAdapter;

import java.util.ArrayList;

public class BellActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_bell);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerSignal);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

//        myAdapter= new MyAdapter();
//        recyclerView.setAdapter(myAdapter);
        //définit l'agencement des cellules, ici de façon verticale, comme une ListView
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
