package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class listviewActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> arrayList;
    TextView textView;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        pref = getSharedPreferences("id", MODE_PRIVATE);

        listView = findViewById(R.id.list);
        textView = findViewById(R.id.numtxt);

        arrayList = new ArrayList<String>();
        arrayList = getArrayList("all_id");
        if (arrayList == null) arrayList = new ArrayList<String>();


        adapter = new ArrayAdapter<String>(this, R.layout.item, arrayList);
        listView.setAdapter(adapter);
        textView.setText("number of student: " + adapter.getCount());

    }

    public void go_attend(View view) {
        Intent intent = new Intent(listviewActivity.this, recordattendActivity.class);
        startActivity(intent);
    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void uploud(View view) {
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("all_id").commit();
        editor.remove("cours_code").commit();
        editor.remove("date").commit();

    }
}
