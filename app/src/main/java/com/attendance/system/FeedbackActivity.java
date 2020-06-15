package com.attendance.system;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {
    String name,message;
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        name=getIntent().getStringExtra("name");
        editText=findViewById(R.id.editTextTextPersonName);
    }

    public void send(View view) {
        message=editText.getText().toString();
        if (!message.isEmpty()){
        JavaMailAPI mail = new JavaMailAPI(this,"elmorshdi53@gmail.com", "Feedback from "+name, message);
        mail.execute();
        editText.setText("");
        }
        else {editText.setError("type her..");}
    }
}