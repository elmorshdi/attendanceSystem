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
    EditText name, id, password, Email;
    private DatabaseReference mdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_);
        mdata = FirebaseDatabase.getInstance().getReference();
        idtxt = getIntent().getStringExtra("id");

        name = findViewById(R.id.ed_name);
        id = findViewById(R.id.ed_id);
        password = findViewById(R.id.ed_password);
        Email = findViewById(R.id.ed_email);
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
                updateUi();
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

    public void updateUi() {
        name.setText(nametxt);
        Email.setText(emailtxt);
        id.setText(idtxt);
        password.setText(passwordtxt);
    }


    private void go_home(View view) {
        Intent intent = new Intent(doctor_account_Activity.this, HomeActivity.class);
        startActivity(intent);
    }

   /*public void updateUi(View view) {
        name.setText(nametxt);
        Email.setText(emailtxt);
        id.setText(idtxt);
        password.setText(passwordtxt);
    }

    */

}
