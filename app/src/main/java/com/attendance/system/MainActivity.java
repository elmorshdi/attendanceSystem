package com.attendance.system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText user, password;
    RadioGroup radioGroup;
    RadioButton radiostudent,radiolecture;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String type, email, id, passtxt, firepass;
    String TAG = " MainActivity Error";
    SharedPreferences pref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        if (pref.contains("susername") && pref.contains("spassword")) {
            Intent intent = new Intent(this, student_home_Activity.class);
            startActivity(intent);
            finish();
        } else if (pref.contains("dusername") && pref.contains("dpassword")) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        radioGroup=findViewById(R.id.RadioGroup);
        radiolecture=findViewById(R.id.Radiolecturer);
        radiostudent=findViewById(R.id.RadioStudent);


    }

    private void getpassword(final String typee) {
        ValueEventListener Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // email= dataSnapshot.child("doctor").child(id).child("email").getValue(String.class);
                firepass = dataSnapshot.child(typee).child(id).child("password").getValue(String.class);
                if (firepass == null) {
                    user.setError("id not correct");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }


        };
        mDatabase.addValueEventListener(Listener);

    }
    public void login(View view) {


        passtxt = password.getText().toString();
        id = user.getText().toString();

        if (radiostudent.isChecked()) {
            getpassword("student");
            if (passtxt.equals(firepass)) {
                progressDialog.show();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("susername", id);
                editor.putString("spassword", passtxt);
                editor.apply();
                studentlogin(id);
            } else {
                Toast.makeText(MainActivity.this, "password not correct", Toast.LENGTH_SHORT).show();
            }
        } else if (radiolecture.isChecked()) {
            getpassword("doctor");
            if (passtxt.equals(firepass)) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("dusername", id);
                editor.putString("dpassword", passtxt);
                editor.apply();
                doctorlogin(id);
            } else {

                Log.d("TAG", "Value: " + firepass);
                Toast.makeText(MainActivity.this, "password not correct", Toast.LENGTH_SHORT).show();
            }
        } else {

            radiolecture.setError("check the box");
            Toast.makeText(MainActivity.this, "hhhhh", Toast.LENGTH_SHORT).show();
        }

    }

    private void dsin() {
        if (radiolecture.isChecked() && firepass == null) {
            progressDialog.show();
            getpassword("doctor");
        }
    }

    public void go_signup(View view) {
        Intent intent = new Intent(this, sing_up.class);
        startActivity(intent);
        finish();
    }

    private void studentlogin(String id) {
        Intent intent = new Intent(this, student_home_Activity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();


    }

    private void doctorlogin(String id) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        finish();


    }


}
