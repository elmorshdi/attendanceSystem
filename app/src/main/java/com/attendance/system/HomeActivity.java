package com.attendance.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void sign_out(View view) {
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
}
