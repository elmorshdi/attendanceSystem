package com.attendance.system;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class PendingActivity extends AppCompatActivity {
    EditText editText;
    String random, type;
    SharedPreferences pref;
    DatabaseReference mDatabase;
    Doctor doctor;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
        editText = findViewById(R.id.random);
        pref = getSharedPreferences("user_details", Context.MODE_PRIVATE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        random = getIntent().getStringExtra("random");
        type = getIntent().getStringExtra("type");
        if (type.equals("s")) {
            student = getStudent("pendingStudent");
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String input = editText.getText().toString().trim();
                    if (input.equals(random)) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String studentInJson = gson.toJson(student);
                                    editor.putString("student", studentInJson);
                                    editor.apply();
                                    mDatabase.child("student").child(student.getId()).setValue(student);
                                    mDatabase.child("student").child(student.getId()).child("subjects").child("sub_code").child("date").setValue("true");

                                    Intent intent = new Intent(PendingActivity.this, StudentHomeActivity.class);
                                    startActivity(intent);
                                    finish();
                               

                    } else editText.setError("not correct");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

        } else if (type.equals("d")) {
            doctor = getDoctor("pendingDoctor");
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    String input = editText.getText().toString().trim();
                    if (input.equals(random)) {

                                    SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String doctorInJson = gson.toJson(doctor);
                                    editor.putString("doctor", doctorInJson);
                                    editor.apply();
                                    mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);
                                    Intent intent = new Intent(PendingActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();

                    } else editText.setError("not correct");
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

    }

    public Student getStudent(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Student>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public Doctor getDoctor(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Doctor>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void reSend(View view) {
        JavaMailAPI mailAPI = new JavaMailAPI(this, doctor.getEmail(), "[AttendanceSystem] Please verify your email Address",
                "Almost done!" + "\n" + doctor.getName() + "\n" + "Your verify code [" + random + "]");
        mailAPI.execute();
    }

    public void goHome(View view) {
        Intent intent = new Intent(PendingActivity.this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }
}
