package com.attendance.system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    EditText user, password;
    RadioButton radioStudent, radioLecture;
    DatabaseReference mDatabase;
    String id, passTxt, firePass;
    String TAG = " MainActivity Error";
    SharedPreferences pref;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        if (pref.contains("student")) {
            studentLogIn();
        } else if (pref.contains("doctor")) {
            doctorLogIn();
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();

        user = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpassword);
        radioLecture = findViewById(R.id.Radiolecturer);
        radioStudent = findViewById(R.id.RadioStudent);

        progressDialog = new ProgressDialog(MainActivity.this);


    }

    public void logIn(View view) {


        passTxt = password.getText().toString();
        id = user.getText().toString();

        if (radioStudent.isChecked()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

            Thread thread = new Thread() {

                @Override
                public void run() {
                    try {

                        // new task().execute("student");
                        sleep(2000);
                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Student student = dataSnapshot.child("student").child(id).getValue(Student.class);
                                assert student != null;
                                firePass = student.getPassword();
                                if (firePass.equals(passTxt)) {
                                    progressDialog.cancel();
                                    studentLogIn();
                                    SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String studentInJson = gson.toJson(student);
                                    Log.e(TAG, student + "\n" + studentInJson);
                                    editor.putString("student", studentInJson);
                                    editor.apply();
                                } else {
                                    progressDialog.cancel();
                                    Toast.makeText(MainActivity.this, "password not correct", Toast.LENGTH_SHORT).show();
                                }

                                Log.e(TAG, student + "\n" + firePass + "\n" + password);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            };
            thread.start();

        } else if (radioLecture.isChecked()) {
            progressDialog.setMessage("Please wait...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
            Thread thread = new Thread() {

                @Override
                public void run() {
                    try {
                        sleep(2000);

                        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Doctor doctor = dataSnapshot.child("doctor").child(id).getValue(Doctor.class);
                                assert doctor != null;
                                firePass = doctor.getPassword();
                                if (firePass.equals(passTxt)) {
                                    progressDialog.cancel();
                                    doctorLogIn();
                                    SharedPreferences.Editor editor = pref.edit();
                                    Gson gson = new Gson();
                                    String studentInJson = gson.toJson(doctor);
                                    Log.e(TAG, doctor + "\n" + studentInJson);
                                    editor.putString("doctor", studentInJson);
                                    editor.apply();
                                } else {
                                    progressDialog.cancel();
                                    Toast.makeText(MainActivity.this, "password not correct", Toast.LENGTH_SHORT).show();
                                }

                                Log.e(TAG, doctor + "\n" + firePass + "\n" + password);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });



                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.run();
                }
            };
            thread.start();

        } else {
            Toast.makeText(MainActivity.this, "check  student or doctor first", Toast.LENGTH_SHORT).show();
        }

    }


    public void goSignUp(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    private void studentLogIn() {

        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void doctorLogIn() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }



}
