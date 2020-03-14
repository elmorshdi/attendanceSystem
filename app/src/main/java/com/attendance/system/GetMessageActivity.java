package com.attendance.system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

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

public class GetMessageActivity extends AppCompatActivity {
    String id;
    ArrayList<Message> messages;
    ListView listView;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_message);
        listView = findViewById(R.id.list);
        Doctor doctor = getDoctor("doctor");
        id = doctor.getId();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message").child("8001123456");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messages = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Message message = postSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                //adapter
                MessageAdapter adapter = new MessageAdapter(GetMessageActivity.this, messages, id);

                //attach
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Doctor getDoctor(String key) {
        SharedPreferences prefs = getSharedPreferences("user_details", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<Doctor>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}
