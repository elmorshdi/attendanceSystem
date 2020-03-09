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
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        id = prf.getString("dusername", null);


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
        Intent intent = new Intent(HomeActivity.this, Record_Attend_Activity.class);
        startActivity(intent);
    }
    public void go_addsub(View view) {
        Intent intent = new Intent(HomeActivity.this, Add_subjectActivity.class);
        startActivity(intent);
    }

    public void my_account(View view) {
        Intent intent = new Intent(this, Doctor_Account_Activity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void get_attend(View view) {
        Intent intent = new Intent(HomeActivity.this, Get_doc_attendActivity.class);
        startActivity(intent);
    }
}
