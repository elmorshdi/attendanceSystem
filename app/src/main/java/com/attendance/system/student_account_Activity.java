package com.attendance.system;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student_account_Activity extends AppCompatActivity {
    String idtxt, nametxt, passwordtxt, emailtxt, TAG;
    EditText name, id, password, email;
    private DatabaseReference mdata;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_account_);
        mdata = FirebaseDatabase.getInstance().getReference();
        idtxt = getIntent().getStringExtra("id");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.ed_name);
        id = findViewById(R.id.ed_id);
        password = findViewById(R.id.ed_password);
        email = findViewById(R.id.ed_email);
    }

    @Override
    protected void onStart() {

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                nametxt = dataSnapshot.child("student").child(idtxt).child("name").getValue(String.class);
                emailtxt = dataSnapshot.child("student").child(idtxt).child("email").getValue(String.class);
                passwordtxt = dataSnapshot.child("student").child(idtxt).child("password").getValue(String.class);

                name.setText(nametxt);
                email.setText(emailtxt);
                id.setText(idtxt);
                password.setText(passwordtxt);
                Toast.makeText(student_account_Activity.this, "succ", Toast.LENGTH_SHORT).show();
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
        super.onStart();
    }

    public void go_home(View view) {
        Intent intent = new Intent(student_account_Activity.this, student_home_Activity.class);
        startActivity(intent);
    }

    public void Update(View view) {
        nametxt = name.getText().toString();
        idtxt = id.getText().toString();
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();


        if (nametxt.isEmpty() || nametxt.equals(" ")) {
            name.setError("enter your name");
        } else if (emailtxt.isEmpty() || emailtxt.equals(" ")) {
            email.setError("Enter Email");
        } else if (idtxt.isEmpty() || idtxt.equals(" ")) {
            id.setError("enter ID");
        } else if (passwordtxt.isEmpty() || passwordtxt.equals(" ")) {
            password.setError("enter passward");
        } else if (passwordtxt.length() < 8) {
            password.setError("lengh mast be 8 or greater");

        } else {
            student student = new student(nametxt, emailtxt, idtxt, passwordtxt);
            mDatabase.child("student").child(student.getId()).setValue(student);

        }
    }

    public void updateUi() {
        name.setText(nametxt);
        email.setText(emailtxt);
        id.setText(idtxt);
        password.setText(passwordtxt);
    }
}
