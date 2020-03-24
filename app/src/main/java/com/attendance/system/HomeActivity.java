package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;


public class HomeActivity extends AppCompatActivity {
    String id;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        Doctor doctor = getDoctor("doctor");
        id = doctor.getId();
        Log.e("doctor", doctor.getName());

    }

    public Doctor getDoctor(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Doctor>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void signOut(View view) {
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void record(View view) {
        Intent intent = new Intent(HomeActivity.this, RecordAttendActivity.class);
        startActivity(intent);
        finish();

    }

    public void goAddSub(View view) {
        Intent intent = new Intent(HomeActivity.this, AddSubjectActivity.class);
        startActivity(intent);

    }

    public void myAccount(View view) {
        Intent intent = new Intent(this, DoctorAccountActivity.class);
        startActivity(intent);

    }

    public void getAttend(View view) {
        Intent intent = new Intent(HomeActivity.this, GetDocAttendActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void message(View view) {
        Intent intent = new Intent(HomeActivity.this, GetMessageActivity.class);
        startActivity(intent);
    }
}
