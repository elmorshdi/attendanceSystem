package com.attendance.system;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class StudentAccountActivity extends AppCompatActivity {
    String idTxt, nameTxt, passwordTxt, emailTxt;
    EditText name, password, email;
    TextView textView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Student student = getStudent("student");

        idTxt = student.getId();
        textView = findViewById(R.id.welcome);
        name = findViewById(R.id.ed_name);
        password = findViewById(R.id.ed_password);
        email = findViewById(R.id.ed_email);


        String[] names = student.getName().split(" ");
        String fname = names[0];
        textView.setText("Welcome!" + " " + fname);
        name.setText(student.getName());
        email.setText(student.getEmail());
        password.setText(student.getPassword());
    }

    public Student getStudent(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Student>() {
        }.getType();
        return gson.fromJson(json, type);
    }



    public void goHome(View view) {
       onBackPressed();

    }

    public void update(View view) {
        nameTxt = name.getText().toString();
        emailTxt = email.getText().toString();
        passwordTxt = password.getText().toString();


        if (nameTxt.isEmpty() || nameTxt.equals(" ")) {
            name.setError("enter your name");
        } else if (emailTxt.isEmpty() || emailTxt.equals(" ")) {
            email.setError("Enter Email");

        } else if (passwordTxt.isEmpty() || passwordTxt.equals(" ")) {
            password.setError("enter password");
        } else if (passwordTxt.length() < 8) {
            password.setError("length mast be 8 or greater");

        } else {
            Student student = new Student(emailTxt, idTxt, nameTxt, passwordTxt);
            mDatabase.child("student").child(student.getId()).child("name").setValue(nameTxt);
            mDatabase.child("student").child(student.getId()).child("email").setValue(emailTxt);
            mDatabase.child("student").child(student.getId()).child("password").setValue(passwordTxt);
            Toast.makeText(this, "Updated", Toast.LENGTH_LONG).show();

        }
    }


}
