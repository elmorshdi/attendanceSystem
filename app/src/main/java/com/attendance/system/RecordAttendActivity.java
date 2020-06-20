package com.attendance.system;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class RecordAttendActivity extends AppCompatActivity {
    int year = 2020, month = 7, day = 9;
    EditText edDate, edId;
    TextView textView;
    String date, subCode, id, tag;
    ArrayList<String> arrayList = new ArrayList<String>();
    SharedPreferences pref;
    Spinner spinner;
    Map<Integer, String> map;
    ArrayList<Subject> subjects;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordattend);
        mData = FirebaseDatabase.getInstance().getReference();

        map = new HashMap<>();
        spinner = findViewById(R.id.spiner);
        textView = findViewById(R.id.numtxt);
        edDate = findViewById(R.id.datepicker);
        edId = findViewById(R.id.stu_id);
        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> strings = new ArrayList<>();
                subjects = new ArrayList<>();

                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.child("subject").getChildren()) {
                    Subject subject = postSnapshot.getValue(Subject.class);
                    subjects.add(subject);
                    assert subject != null;
                    String code = subject.getCode();
                    String s = subject.getName();
                    strings.add("  " + s);
                    map.put(i, code);
                    i++;


                }
                SpinnerAdapter adapter = new ArrayAdapter<>(RecordAttendActivity.this, R.layout.itemm, strings);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subCode = map.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pref = getSharedPreferences("id", MODE_PRIVATE);

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

    }

    @Override
    protected void onPause() {
        saveObject(subCode, "cours_code");
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
       onBackPressed();
    }
}
