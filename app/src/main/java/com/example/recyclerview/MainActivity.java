package com.example.recyclerview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewItemClickListener{
    private static final String TAG = "Track Items";
    private SharedPreferences prefs;
    private Set<String> progress;
    final ArrayList<String> listItems = new ArrayList<String>();
    private final int categoryIcon = R.drawable.ic_launcher_foreground;
    private RecyclerView mainRecyclerView;
    private MainRecyclerAdapter mainRecyclerAdapter;
    private  ArrayList<MainModel> mainModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerView = findViewById(R.id.my_recycler_view);
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
        startAdapter();
    }

    private void startAdapter(){
        Collections.sort(listItems);
        mainModelArrayList = prepareList();
        mainRecyclerAdapter = new MainRecyclerAdapter(this,mainModelArrayList);
        mainRecyclerAdapter.setOnRecyclerViewItemClickListener(this);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
    }

    private ArrayList<MainModel> prepareList() {
        ArrayList<MainModel> mainModelList = new ArrayList<>();
        for (int i = 0; i < listItems.size(); i++) {
            MainModel mainModel = new MainModel();
            mainModel.setOfferName(listItems.get(i));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "INSIDE: onDestroy");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Text_Name", null);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "INSIDE: onPause");
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Text_Name", null);
        SharedPreferences.Editor editPrefs = prefs.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(listItems);
        editPrefs.putStringSet("myProgress", set);
        editPrefs.commit();
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "INSIDE: onResume");
        try{
            String data = prefs.getString("Text_Name", null); //no id: default value
            if(data.length() != 0){
                listItems.add(data);
                startAdapter();
            }
        }catch (Exception e){
            Log.d(TAG, "INSIDE: No extra string");
        }
    }


}