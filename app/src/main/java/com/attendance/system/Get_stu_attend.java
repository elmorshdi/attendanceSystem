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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Get_stu_attend extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference mDatabase;
    ArrayList<Student> Students;
    long total;
    String sub_code, idtxt;
    EditText editText;
    Button button;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_stu_attend);
        recyclerView = findViewById(R.id.recyclerview2);
        SharedPreferences prf = getSharedPreferences("user_details", MODE_PRIVATE);
        idtxt = prf.getString("susername", null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Students = new ArrayList<>();
        button = findViewById(R.id.bu2);
        editText = findViewById(R.id.code_s2);

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
        sub_code = editText.getText().toString();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: " + snapshot);
                total = snapshot.child("subject").child(sub_code).child("numoflecture").getValue(long.class);

                Student student = snapshot.child("student").child(idtxt).getValue(Student.class);
                Students.add(student);

//                for (DataSnapshot data : snapshot.child("student").getValue()) {
//                    Log.e(TAG, "onDataChange: " + data);
//
//                    data.getValue(Student.class);
//
//
//                }
                Radapter Radapter = new Radapter(Students, sub_code, total);
                recyclerView.setAdapter(Radapter);

                Students = new ArrayList<>();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Get_stu_attend.this, "nnn", Toast.LENGTH_LONG).show();


            }
        });

    }
}