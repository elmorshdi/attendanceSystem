package com.attendance.system;

import android.os.Bundle;
import android.util.Log;
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

public class GetStuAttendActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ArrayList<Student> Students;
    long total = 0;
    String subCode, idTxt;
    Button button;
    RecyclerView recyclerView;
    Spinner spinner;
    Map<Integer, String> map;
    DatabaseReference mDatabase, mData;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stu_attend);
        //

        button = findViewById(R.id.bu2);
        recyclerView = findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Students = new ArrayList<>();
        //
        idTxt = getIntent().getStringExtra("id");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        map = new HashMap<>();
        spinner = findViewById(R.id.spiner);
        mData = FirebaseDatabase.getInstance().getReference();

        mData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> strings = new ArrayList<>();
                int i = 0;

                for (DataSnapshot postSnapshot : dataSnapshot.child("student").child(idTxt).child("subjects").getChildren()) {
                    String key = postSnapshot.getKey();
                    assert key != null;
                    if (dataSnapshot.child("subject").hasChild(key)) {
                        String s = dataSnapshot.child("subject").child(key).child("name").getValue(String.class);
                        strings.add("  " + s);
                        map.put(i, key);
                        i++;
                    }

                }
                SpinnerAdapter adapter = new ArrayAdapter<>(GetStuAttendActivity.this, R.layout.itemm, strings);
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
                Log.e(TAG, "onDataChange: " + map);

                Log.e(TAG, "onDataChange: " + i);

                // adapterView.getItemAtPosition(i).toString();
                button.setEnabled(true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void show(View view) {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Log.e(TAG, "onDataChange: " + snapshot);

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