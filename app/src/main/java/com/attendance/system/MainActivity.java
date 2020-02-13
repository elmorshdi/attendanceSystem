package com.attendance.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText user, password;
    RadioGroup radioGroup;
    RadioButton radiostudent,radiolecture;
    ArrayList<student>students;
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
        String emailtext, passwordtext;
        emailtext = user.getText().toString();
        passwordtext = password.getText().toString();

        if (emailtext.isEmpty() || emailtext.equals(" ")) {
            user.setError("enter email");
            return;
        }
        if (passwordtext.isEmpty() || passwordtext.equals(" ")) {
            password.setError("enter passward");
            return;
        }



    }
    public void go_signup(View view) {
        Intent intent = new Intent(this, sing_up.class);
        startActivity(intent);
        finish();
    }
}
