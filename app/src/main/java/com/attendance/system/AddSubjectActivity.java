package com.attendance.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSubjectActivity extends AppCompatActivity {
    EditText edName, edCode, edDrId;
    String name, code, drId;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsubject);


        edName = findViewById(R.id.ed_name);
        edDrId = findViewById(R.id.ed_drname);
        edCode = findViewById(R.id.ed_code);


        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    public void addCourse(View view) {

        name = edName.getText().toString();
        code = edCode.getText().toString();
        drId = edDrId.getText().toString();
        if (name.isEmpty() || name.equals(" ")) {
            edName.setError("enter name");
        } else if (code.isEmpty() || code.equals(" ")) {
            edCode.setError("enter code");
        } else if (drId.isEmpty() || drId.equals(" ")) {
            edDrId.setError("enter name");
        } else {
            Subject subject = new Subject(code, name, drId, 0);
            mDatabase.child("subject").child(subject.getCode()).setValue(subject);
            Toast.makeText(this, "The course was added", Toast.LENGTH_SHORT).show();
            edCode.setText("");
            edName.setText("");
            edDrId.setText("");

        }
    }

    public void goHome(View view) {
        Intent intent = new Intent(AddSubjectActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
