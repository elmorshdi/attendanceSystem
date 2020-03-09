package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Doctor_Account_Activity extends AppCompatActivity {
    String idtxt, nametxt, passwordtxt, emailtxt, TAG;
    EditText name, password, email;
    SharedPreferences prf;
    TextView textView;
    private DatabaseReference mdata;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_);
        mdata = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        idtxt = prf.getString("dusername", null);
        name = findViewById(R.id.ed_name);
        password = findViewById(R.id.ed_password);
        email = findViewById(R.id.ed_email);
        textView = findViewById(R.id.welcome);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                nametxt = dataSnapshot.child("doctor").child(idtxt).child("name").getValue(String.class);
                emailtxt = dataSnapshot.child("doctor").child(idtxt).child("email").getValue(String.class);
                passwordtxt = dataSnapshot.child("doctor").child(idtxt).child("password").getValue(String.class);
                String[] names = nametxt.split(" ");
                String fname = names[0];
                textView.setText("Welcome!" + " " + fname);
                name.setText(nametxt);
                email.setText(emailtxt);
                password.setText(passwordtxt);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(Doctor_Account_Activity.this, "filed", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        mdata.addValueEventListener(listener);

    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    public void Update(View view) {
        nametxt = name.getText().toString();
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();

        if (nametxt.isEmpty() || nametxt.equals(" ")) {
            name.setError("enter your name");
        } else if (emailtxt.isEmpty() || emailtxt.equals(" ")) {
            email.setError("Enter Email");

        } else if (passwordtxt.isEmpty() || passwordtxt.equals(" ")) {
            password.setError("enter passward");
        } else if (passwordtxt.length() < 8) {
            password.setError("lengh mast be 8 or greater");

        } else {
            Doctor doctor = new Doctor(emailtxt, idtxt, nametxt, passwordtxt);
            mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);

        }
    }

    public void go_home(View view) {
        Intent intent = new Intent(Doctor_Account_Activity.this, HomeActivity.class);
        startActivity(intent);
    }
}
