package com.attendance.system;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;


public class HomeActivity extends AppCompatActivity {
    String id;
    SharedPreferences prf;
    Doctor doctor;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        doctor = getDoctor("doctor");
        id = doctor.getId();
        Log.e("doctor", doctor.getName());



         doubleBackToExitPressedOnce = false;


    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("sign Out")
                .setMessage("Are you sure you want to sign out ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences.Editor editor = prf.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

    public void record(View view) {
        Intent intent = new Intent(HomeActivity.this, RecordAttendActivity.class);
        startActivity(intent);

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



    public void message(View view) {
        Intent intent = new Intent(HomeActivity.this, GetMessageActivity.class);
        startActivity(intent);
    }

    public void goFeedback(View view) {
        Intent intent = new Intent(HomeActivity.this, FeedbackActivity.class);
        String name = doctor.getName();
        intent.putExtra("name",name);
        startActivity(intent);

    }
}
