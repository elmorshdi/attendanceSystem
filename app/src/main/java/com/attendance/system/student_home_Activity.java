package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class student_home_Activity extends AppCompatActivity {
    SharedPreferences prf;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        id = prf.getString("susername", null);

    }

    public void st_accunt(View view) {
        Intent intent = new Intent(this, student_account_Activity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void sign_out(View view) {
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
