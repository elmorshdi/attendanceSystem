package com.attendance.system;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Record_Attend_Activity extends AppCompatActivity {
    int year = 2020, month = 7, day = 9;
    EditText editdat, editid, editccode;
    TextView textView;
    String date, cousecode, id, tag;
    ArrayList<String> arrayList = new ArrayList<String>();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordattend);


        textView = findViewById(R.id.numtxt);
        editdat = findViewById(R.id.datepicker);
        editid = findViewById(R.id.stu_id);
        editccode = findViewById(R.id.cours_code);

        pref = getSharedPreferences("id", MODE_PRIVATE);
        if (pref.contains("cours_code")) {
            editccode.setText(get_object("cours_code"));
        }
        if (pref.contains("date")) {
            editdat.setText(get_object("date"));
        }
        arrayList = getArrayList("all_id");
        if (arrayList == null) arrayList = new ArrayList<String>();
        editdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdatediloge();
            }


        });

    }

    @Override
    protected void onPause() {
        if (editccode.getText().length() > 4)
            saveobject(editccode.getText().toString(), "cours_code");
        if (editdat.getText().length() > 4) saveobject(editdat.getText().toString(), "date");
        super.onPause();
    }


    private void showdatediloge() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editdat.setText(dayOfMonth + "-" + month + 1 + "-" + year);

            }
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(Record_Attend_Activity.this, listener, year, month, day);

        datePickerDialog.show();

    }

    public void go_home(View view) {
        Intent intent = new Intent(Record_Attend_Activity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void goto_qr(View view) {
        Intent intent = new Intent(Record_Attend_Activity.this, Scan_Activity.class);
        startActivity(intent);
    }

    public void addid(View view) {
        id = editid.getText().toString();
        if (!(id.length() < 9)) {
            id = editid.getText().toString();
            editid.setText("");
            arrayList = getArrayList("all_id");
            if (arrayList == null) arrayList = new ArrayList<>();
            if (arrayList.contains(id))
                Toast.makeText(this, "duplicate id", Toast.LENGTH_SHORT).show();

            arrayList.add(id);
            saveArrayList(arrayList, "all_id");
            Toast.makeText(this, " id added", Toast.LENGTH_SHORT).show();

        } else editid.setError("add id");


    }

    public void uploud(View view) {
        Intent intent = new Intent(Record_Attend_Activity.this, Listed_Toupdate_Activity.class);
        startActivity(intent);
        finish();
    }

    private void saveobject(String s, String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, s);
        editor.commit();
    }

    private String get_object(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        String s = prefs.getString(key, null);
        return s;
    }

    private void saveArrayList(ArrayList<String> list, String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String list_in_json = gson.toJson(list);
        editor.putString(key, list_in_json);
        editor.commit();
    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}
