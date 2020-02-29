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

public class student_account_Activity extends AppCompatActivity {
    String idtxt, nametxt, passwordtxt, emailtxt, TAG;
    EditText name, password, email;
    TextView textView;
    private DatabaseReference mdata;
    private DatabaseReference mDatabase;
    SharedPreferences prf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_);
        mdata = FirebaseDatabase.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        idtxt = prf.getString("susername", null);
        textView = findViewById(R.id.welcome);
        name = findViewById(R.id.ed_name);
        password = findViewById(R.id.ed_password);
        email = findViewById(R.id.ed_email);
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                nametxt = dataSnapshot.child("student").child(idtxt).child("name").getValue(String.class);
                emailtxt = dataSnapshot.child("student").child(idtxt).child("email").getValue(String.class);
                passwordtxt = dataSnapshot.child("student").child(idtxt).child("password").getValue(String.class);
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
                Toast.makeText(student_account_Activity.this, "filed", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        mdata.addValueEventListener(listener);
    }

    @Override
    protected void onStart() {


        super.onStart();
    }

    public void go_home(View view) {
        Intent intent = new Intent(student_account_Activity.this, student_home_Activity.class);
        startActivity(intent);
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
            student student = new student(nametxt, emailtxt, idtxt, passwordtxt);
            mDatabase.child("student").child(student.getId()).setValue(student);

        }
    }

}
