package com.attendance.system;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class GetDocAttendActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference mDatabase;
    ArrayList<Student> Students;
    long total;
    String subCode;
    EditText editText;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_doc_attend);

        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Students = new ArrayList<>();
        button = findViewById(R.id.bu);
        editText = findViewById(R.id.code_s);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String Input = editText.getText().toString().trim();

                button.setEnabled(!Input.isEmpty() && !Input.equals(" "));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    public void show(View view) {
        subCode = editText.getText().toString();
        try {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e(TAG, "onDataChange: " + snapshot);
                    if (snapshot.child("subject").hasChild(subCode)) {
                        total = snapshot.child("subject").child(subCode).child("numOfLecture").getValue(long.class);

                        for (DataSnapshot data : snapshot.child("student").getChildren()) {
                            Log.e(TAG, "onDataChange: " + data);

                            Student student = data.getValue(Student.class);
                            Students.add(student);

                        }
                        RecyclerAdapter RecyclerAdapter = new RecyclerAdapter(Students, subCode, total);

                        recyclerView.setAdapter(RecyclerAdapter);
                        Students = new ArrayList<>();

                    } else {
                        Toast.makeText(GetDocAttendActivity.this, "Course Code not correct", Toast.LENGTH_LONG).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(GetDocAttendActivity.this, "fail", Toast.LENGTH_LONG).show();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}