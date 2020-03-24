package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class StudentHomeActivity extends AppCompatActivity {
    SharedPreferences prf;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        Student student = getStudent("student");
        id = student.getId();

    }

    public Student getStudent(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Student>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void stAccount(View view) {
        Intent intent = new Intent(this, StudentAccountActivity.class);
        startActivity(intent);

    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void signOut(View view) {
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void getAttend(View view) {
        Intent intent = new Intent(this, GetStuAttendActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void message(View view) {
        Intent intent = new Intent(this, SendApologizeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }
}
