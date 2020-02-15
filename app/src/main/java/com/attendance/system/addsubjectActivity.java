package com.attendance.system;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addsubjectActivity extends AppCompatActivity {
    EditText ed_name,ed_code,ed_drname;
    String name,code,drname;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubject);
        ed_name=findViewById(R.id.ed_name);
        ed_drname=findViewById(R.id.ed_drname);
        ed_code=findViewById(R.id.ed_code);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void go_home(View view) {
        Intent intent =new Intent(addsubjectActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void add_cours(View view)
    {
        name=ed_name.getText().toString();
        code=ed_code.getText().toString();
        drname= ed_drname.getText().toString();
        subject subject=new subject(name,code,drname,"0");
        mDatabase.child("subject").child(subject.getCode()).setValue(subject);
        Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();

    }
}
