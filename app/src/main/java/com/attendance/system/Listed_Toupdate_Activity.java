package com.attendance.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Listed_Toupdate_Activity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> arrayList;
    TextView textView;
    SharedPreferences pref;
    String date, sub_code;
    long total;
    private DatabaseReference mData, mDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mDatabase = FirebaseDatabase.getInstance().getReference();
//get date from preference
        pref = getSharedPreferences("id", MODE_PRIVATE);
        arrayList = new ArrayList<>();
        arrayList = getArrayList("all_id");
        if (arrayList == null) arrayList = new ArrayList<>();
        date = pref.getString("date", "");
        sub_code = pref.getString("cours_code", "");
        //
        listView = findViewById(R.id.list);
        textView = findViewById(R.id.numtxt);
        //
        adapter = new ArrayAdapter<>(this, R.layout.item, arrayList);
        listView.setAdapter(adapter);
        //
        mData = FirebaseDatabase.getInstance().getReference("subject");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                total = snapshot.child(sub_code).child("numoflecture").getValue(long.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        textView.setText("number of student: " + adapter.getCount() + "\n" + "Date of day :" + date +
                "\n" + "subject code :" + sub_code );

    }

    public void go_attend(View view) {
        Intent intent = new Intent(Listed_Toupdate_Activity.this, Record_Attend_Activity.class);
        startActivity(intent);
        finish();
    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @SuppressLint("SetTextI18n")
    public void uploud(View view) {

        mData.child(sub_code).child("numoflecture").setValue(total + 1);
        for (String s : arrayList) {
            mDatabase.child("student").child(s).child("subjects").child(sub_code).child(date).setValue("true");

        }


        SharedPreferences.Editor editor = pref.edit();
        textView.setText("number of student: " + "\n" + "Date of day :" + "\n" +
                "subject code :" );
        editor.remove("all_id").apply();
        adapter.clear();
        editor.remove("cours_code").commit();
        editor.remove("date").commit();

    }
}