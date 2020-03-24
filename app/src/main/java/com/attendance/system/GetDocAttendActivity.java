package com.attendance.system;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
import java.util.HashMap;
import java.util.Map;


public class GetDocAttendActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference mData, mDatabase;
    ArrayList<Student> Students;
    long total;
    String subCode;
    Button button;
    RecyclerView recyclerView;
    Spinner spinner;
    Map<Integer, String> map;
    ArrayList<Subject> subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_doc_attend);
        map = new HashMap<>();
        spinner = findViewById(R.id.spiner);
        recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mData = FirebaseDatabase.getInstance().getReference();

        Students = new ArrayList<>();
        button = findViewById(R.id.bu);

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> strings = new ArrayList<>();
                subjects = new ArrayList<>();

                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.child("subject").getChildren()) {
                    Subject subject = postSnapshot.getValue(Subject.class);
                    subjects.add(subject);
                    assert subject != null;
                    String code = subject.getCode();
                    String s = subject.getName();
                    strings.add("  " + s);
                    map.put(i, code);
                    i++;


                }
                SpinnerAdapter adapter = new ArrayAdapter<>(GetDocAttendActivity.this, R.layout.itemm, strings);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subCode = map.get(i);
                button.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void show(View view) {
        try {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                        total = snapshot.child("subject").child(subCode).child("numOfLecture").getValue(long.class);
                        for (DataSnapshot data : snapshot.child("student").getChildren()) {

                            Student student = data.getValue(Student.class);
                            Students.add(student);

                        }
                        RecyclerAdapter RecyclerAdapter = new RecyclerAdapter(Students, subCode, total);

                        recyclerView.setAdapter(RecyclerAdapter);
                        Students = new ArrayList<>();


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