package com.attendance.system;

import android.content.SharedPreferences;
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

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GetStuAttendActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference mDatabase;
    ArrayList<Student> Students;
    long total;
    String subCode, idTxt;
    EditText editText;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stu_attend);
        //
        button = findViewById(R.id.bu2);
        editText = findViewById(R.id.code_s2);
        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Students = new ArrayList<>();
        //
        Student student = getStudent("student");
        idTxt = student.getId();

        //to disable button in empty input
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

    public Student getStudent(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Student>() {
        }.getType();
        return gson.fromJson(json, type);
    }
    public void show(View view) {
        subCode = editText.getText().toString();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: " + snapshot);
                if (snapshot.child("subject").hasChild(subCode)) {

                    total = snapshot.child("subject").child(subCode).child("numOfLecture").getValue(long.class);

                    Student student = snapshot.child("student").child(idTxt).getValue(Student.class);
                    Students.add(student);

                    RecyclerAdapter RecyclerAdapter = new RecyclerAdapter(Students, subCode, total);
                    recyclerView.setAdapter(RecyclerAdapter);

                    Students = new ArrayList<>();
                } else {
                    Toast.makeText(GetStuAttendActivity.this, "Course Code not correct", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}