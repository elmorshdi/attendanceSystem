package com.attendance.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText user, password;
    RadioGroup radioGroup;
    RadioButton radiostudent,radiolecture;
    private FirebaseAuth mAuth;
    String TAG = " MainActivity Error";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        radioGroup=findViewById(R.id.RadioGroup);
        radiolecture=findViewById(R.id.Radiolecturer);
        radiostudent=findViewById(R.id.RadioStudent);




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    case R.id.Radiolecturer:


                        break;
                    case R.id.RadioStudent:


                        break;

                }
            }
        });

    }


    public void login(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();




    }
    public void go_signup(View view) {
        Intent intent = new Intent(this, sing_up.class);
        startActivity(intent);
        finish();
    }
}
