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
    public static int ITEMS_QUANTITY = 5;

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
        for(int i = 0; i <= ITEMS_QUANTITY; i++) {
            strings.add(new StringConstructor("text" + i, i));
        }
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
