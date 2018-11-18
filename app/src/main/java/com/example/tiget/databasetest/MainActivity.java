package com.example.tiget.databasetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Adapter adapter;
    private Database mDatabase;
    List<StringConstructor> strings;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //editText = findViewById(R.id.editText);


        strings = new ArrayList<>();

        //Настраиваем Recycler View
        adapter = new Adapter(MainActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));


        mDatabase = new Database(this);
        mDatabase.setChangeListener(new Database.ChangeListener() {
            @Override
            public void onChange(List<StringConstructor> strings) {
                adapter.swap(strings);
            }
        });



        mDatabase.load();
        strings.add(new StringConstructor("Text", strings.size()+1));
        adapter.swap(strings);
        strings.add(new StringConstructor("Text1", strings.size()+1));
        adapter.swap(strings);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDatabase.setChangeListener(new Database.ChangeListener() {
            @Override
            public void onChange(List<StringConstructor> strings) {
                adapter.swap(strings);
            }
        });
        adapter.swap(mDatabase.getAlarms());

    }

}
