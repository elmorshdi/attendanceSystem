package com.attendance.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResetPasswordActivity extends AppCompatActivity {
    EditText editId;
    RadioButton radioStudent, radioLecture;
    DatabaseReference mDatabase;
    String id;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        textView = findViewById(R.id.red);

        editId = findViewById(R.id.id);
        radioLecture = findViewById(R.id.Radiolecturer);
        radioStudent = findViewById(R.id.RadioStudent);
    }

    public void reset(final View view) {
        id = editId.getText().toString();
        if (!(id.isEmpty() || id.equals(" "))) {
            if (radioStudent.isChecked()) {

                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("student").hasChild(id)) {

                            Student student = dataSnapshot.child("student").child(id).getValue(Student.class);
                            assert student != null;
                            String s = "Almost done!" + "\n" + student.getName() + "\n" + "Your password [" + student.getPassword() + "]";
                            JavaMailAPI mailAPI = new JavaMailAPI(ResetPasswordActivity.this, student.getEmail(),
                                    "[AttendanceSystem] reset your password", s);
                            mailAPI.execute();
                            textView.setText("* Password sent Check your email");
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setText("* id not correct or not registered");
                            textView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else if (radioLecture.isChecked()) {


                mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("doctor").hasChild(id)) {
                            Doctor doctor = dataSnapshot.child("doctor").child(id).getValue(Doctor.class);
                            assert doctor != null;
                            String s = "Almost done!" + "\n" + doctor.getName() + "\n" + "Your password [" + doctor.getPassword() + "]";
                            JavaMailAPI mailAPI = new JavaMailAPI(ResetPasswordActivity.this, doctor.getEmail(),
                                    "[AttendanceSystem] reset your password", s);
                            mailAPI.execute();
                            textView.setText("* Password sent Check your email");
                            textView.setVisibility(View.VISIBLE);
                        } else {
                            textView.setText("* id not correct or not registered");
                            textView.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            } else {
                textView.setText("* check  student or doctor first");
                textView.setVisibility(View.VISIBLE);
            }

        } else {
            editId.setError("enter your id");
        }
    }

    public void back(View view) {
        Intent intent=new Intent(ResetPasswordActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
