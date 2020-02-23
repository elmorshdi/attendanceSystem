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

public class doctor_account_Activity extends AppCompatActivity {
    String idtxt, nametxt, passwordtxt, emailtxt, TAG;
    EditText name, id, password, email;
    private DatabaseReference mdata;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_);
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
                nametxt = dataSnapshot.child("doctor").child(idtxt).child("name").getValue(String.class);
                emailtxt = dataSnapshot.child("doctor").child(idtxt).child("email").getValue(String.class);
                passwordtxt = dataSnapshot.child("doctor").child(idtxt).child("password").getValue(String.class);

                Toast.makeText(doctor_account_Activity.this, "succ", Toast.LENGTH_SHORT).show();
                name.setText(nametxt);
                email.setText(emailtxt);
                id.setText(idtxt);
                password.setText(passwordtxt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(doctor_account_Activity.this, "filed", Toast.LENGTH_SHORT).show();
                // ...
            }
        };
        mdata.addValueEventListener(listener);
        super.onStart();
    }




    private void go_home(View view) {
        Intent intent = new Intent(doctor_account_Activity.this, HomeActivity.class);
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
            doctor doctor = new doctor(emailtxt, idtxt, nametxt, passwordtxt);
            mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);

        }
    }

   /*public void updateUi() {
        name.setText(nametxt);
        email.setText(emailtxt);
        id.setText(idtxt);
        password.setText(passwordtxt);
    }

    */


}
