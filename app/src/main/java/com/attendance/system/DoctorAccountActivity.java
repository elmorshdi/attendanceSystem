package com.attendance.system;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.lang.reflect.Type;

public class DoctorAccountActivity extends AppCompatActivity {
    String idTxt, nameTxt, passwordTxt, emailTxt, TAG;
    EditText edName, edPassword, edEmail;
    SharedPreferences prf;
    TextView textView;
    private DatabaseReference mDatabase;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_);
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        Doctor doctor = getDoctor("doctor");
        idTxt = doctor.getId();
        edName = findViewById(R.id.ed_drname);
        edPassword = findViewById(R.id.ed_drpassword);
        edEmail = findViewById(R.id.ed_dremail);
        textView = findViewById(R.id.welcome);
//        nameTxt = dataSnapshot.child("doctor").child(idTxt).child("name").getValue(String.class);
//        emailTxt = dataSnapshot.child("doctor").child(idTxt).child("email").getValue(String.class);
//        passwordTxt = dataSnapshot.child("doctor").child(idTxt).child("password").getValue(String.class);
        String[] names = doctor.getName().split(" ");
        String fname = names[0];
        textView.setText("Welcome!" + " " + fname);
        edName.setText(doctor.getName());
        edEmail.setText(doctor.getEmail());
        edPassword.setText(doctor.getPassword());

    }

    public Doctor getDoctor(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Doctor>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    public void update(View view) {
        nameTxt = edName.getText().toString();
        emailTxt = edEmail.getText().toString();
        passwordTxt = edPassword.getText().toString();

        if (nameTxt.isEmpty() || nameTxt.equals(" ")) {
            edName.setError("enter your name");
        } else if (emailTxt.isEmpty() || emailTxt.equals(" ")) {
            edEmail.setError("Enter Email");

        } else if (passwordTxt.isEmpty() || passwordTxt.equals(" ")) {
            edPassword.setError("enter password");
        } else if (passwordTxt.length() < 8) {
            edPassword.setError("length mast be 8 or greater");

        } else {
            Doctor doctor = new Doctor(nameTxt, idTxt, emailTxt, passwordTxt);
            mDatabase.child("doctor").child(doctor.getId()).child("name").setValue(nameTxt);
            mDatabase.child("doctor").child(doctor.getId()).child("email").setValue(emailTxt);
            mDatabase.child("doctor").child(doctor.getId()).child("password").setValue(passwordTxt);

        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(DoctorAccountActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();

    }
}
