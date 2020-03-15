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

public class RecordAttendActivity extends AppCompatActivity {
    int year = 2020, month = 7, day = 9;
    EditText edDate, edId, edCCode;
    TextView textView;
    String date, cousecode, id, tag;
    ArrayList<String> arrayList = new ArrayList<String>();
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordattend);


        textView = findViewById(R.id.numtxt);
        edDate = findViewById(R.id.datepicker);
        edId = findViewById(R.id.stu_id);
        edCCode = findViewById(R.id.cours_code);

        pref = getSharedPreferences("id", MODE_PRIVATE);
        if (pref.contains("cours_code")) {
            edCCode.setText(getObject("cours_code"));
        }
        if (pref.contains("date")) {
            edDate.setText(getObject("date"));
        }
        arrayList = getArrayList("all_id");
        if (arrayList == null) arrayList = new ArrayList<String>();
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdatediloge();
            }


        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }

    @Override
    protected void onPause() {
        if (edCCode.getText().length() > 4)
            saveObject(edCCode.getText().toString(), "cours_code");
        if (edDate.getText().length() > 4) saveObject(edDate.getText().toString(), "date");
        super.onPause();
    }


    private void showdatediloge() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                edDate.setText(dayOfMonth + "-" + month + "-" + year);

            }
        };


        DatePickerDialog datePickerDialog = new DatePickerDialog(RecordAttendActivity.this, listener, year, month, day);

        datePickerDialog.show();

    }


    public void gotoQr(View view) {
        Intent intent = new Intent(RecordAttendActivity.this, ScanActivity.class);
        startActivity(intent);
    }

    public void addId(View view) {
        id = edId.getText().toString();
        if (!(id.length() < 9)) {
            id = edId.getText().toString();
            edId.setText("");
            arrayList = getArrayList("all_id");
            if (arrayList == null) arrayList = new ArrayList<>();
            if (arrayList.contains(id))
                Toast.makeText(this, "duplicate id", Toast.LENGTH_SHORT).show();

            arrayList.add(id);
            saveArrayList(arrayList, "all_id");
            Toast.makeText(this, " id added", Toast.LENGTH_SHORT).show();

        } else edId.setError("add id");


    }

    public void upLoud(View view) {
        Intent intent = new Intent(RecordAttendActivity.this, ListedToUpdateActivity.class);
        startActivity(intent);
        finish();

    }

    private void saveObject(String s, String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, s);
        editor.commit();
    }

    private String getObject(String key) {
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


    public void goHome(View view) {
        Intent intent = new Intent(RecordAttendActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
