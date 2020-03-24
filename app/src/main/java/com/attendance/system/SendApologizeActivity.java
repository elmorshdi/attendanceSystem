package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.reflect.TypeToken;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendApologizeActivity extends AppCompatActivity {
    EditText edMessage;
    String codeTxt, messageTxt, idTxt, drId;
    Student student;
    Spinner spinner;
    Map<Integer, String> map;
    DatabaseReference mDatabase, mData;
    private DatabaseReference getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_apologize);

        edMessage = findViewById(R.id.ed_message);
        student = getStudent("student");
        getData = FirebaseDatabase.getInstance().getReference();

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
                SpinnerAdapter adapter = new ArrayAdapter<>(SendApologizeActivity.this, R.layout.itemm, strings);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                codeTxt = map.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void goHome(View view) {
        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void send(View view) {
        messageTxt = edMessage.getText().toString();

        if (!codeTxt.isEmpty() && !messageTxt.isEmpty()) {

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        getData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                drId = dataSnapshot.child("subject").child(codeTxt).child("drId").getValue(String.class);
                                if (drId == null) {
                                    Toast.makeText(SendApologizeActivity.this, "Course Code not correct", Toast.LENGTH_SHORT).show();
                                } else {
                                    String key = mDatabase.child("message").child(drId).push().getKey();
                                    Message message = new Message(student.getId(), key, student.getName(), codeTxt, messageTxt);
                                    assert key != null;
                                    mDatabase.child("message").child(drId).child(key).setValue(message);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    super.run();
                }
            };
            thread.run();
            Toast.makeText(this, "sent", Toast.LENGTH_SHORT).show();
            edMessage.setText("");
        } else {
            Toast.makeText(this, "Course and reason cannot be empty ", Toast.LENGTH_SHORT).show();
        }

    }

    public Student getStudent(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Student>() {
        }.getType();
        return gson.fromJson(json, type);
    }
}
