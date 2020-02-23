package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {
    String id;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id = getIntent().getStringExtra("id");
        prf = getSharedPreferences("user_details", MODE_PRIVATE);


    }

    public void sign_out(View view) {
        SharedPreferences.Editor editor = prf.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void recordact(View view) {
        Intent intent = new Intent(HomeActivity.this,recordattendActivity.class);
        startActivity(intent);
    }
    public void go_addsub(View view) {
        Intent intent = new Intent(HomeActivity.this,addsubjectActivity.class);
        startActivity(intent);
    }

    public void my_account(View view) {
        Intent intent = new Intent(this, doctor_account_Activity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
