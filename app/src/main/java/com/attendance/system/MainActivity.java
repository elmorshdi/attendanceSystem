package com.attendance.system;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText user, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);

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

}
