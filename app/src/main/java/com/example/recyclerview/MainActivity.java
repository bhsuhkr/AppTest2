package com.example.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener{
    private SharedPreferences prefs;
    private Set<String> progress;
    final ArrayList<String> listItems = new ArrayList<String>();
    private final int categoryIcon = R.drawable.ic_launcher_foreground;
    private final String categoryName[] = {
            "Apple",
            "Samsung",
            "MI",
            "Motorola",
            "Nokia",
            "Oppo",
            "Micromax",
            "Honor",
            "Lenovo"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView mainRecyclerView = findViewById(R.id.my_recycler_view);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mainRecyclerView.setLayoutManager(linearLayoutManager);

        // Saved values
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        progress = prefs.getStringSet("myProgress", new HashSet<String>());

        if(progress != null){
            Iterator<String> iterator = progress.iterator();
            while(iterator.hasNext()){
                String id = iterator.next();
                listItems.add(id);
            }
        }


        //Recycler Adapter
        final ArrayList<MainModel> mainModelArrayList = prepareList();
        final MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(this,mainModelArrayList);
        mainRecyclerAdapter.setOnRecyclerViewItemClickListener(this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
    }

    private ArrayList<MainModel> prepareList() {
        ArrayList<MainModel> mainModelList = new ArrayList<>();
        for (int i = 0; i < categoryName.length; i++) {
            MainModel mainModel = new MainModel();
            mainModel.setOfferName(categoryName[i]);
            mainModel.setOfferIcon(categoryIcon);
            mainModelList.add(mainModel);
        }
        return mainModelList;
    }

    @Override
    public void onItemClick(int position, View view) {
        MainModel mainModel = (MainModel) view.getTag();
        switch (view.getId()) {
            case R.id.row_main_adapter_linear_layout:

                Toast.makeText(this,"Position clicked: " + String.valueOf(position) + ", "+ mainModel.getOfferName(),Toast.LENGTH_LONG).show();

                break;
        }
    }

    public void addItems(View v) {
        startActivity(new Intent(MainActivity.this, AddWorkActivity.class));
    }
}