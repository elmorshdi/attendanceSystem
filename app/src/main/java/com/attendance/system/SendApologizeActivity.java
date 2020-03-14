package com.attendance.system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class SendApologizeActivity extends AppCompatActivity {
    EditText edCode, edMessage;
    String codeTxt, messageTxt, drId;
    Student student;
    ProgressDialog progressDialog;

    private DatabaseReference mDatabase, getData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_apologize);

        edCode = findViewById(R.id.ed_code);
        edMessage = findViewById(R.id.ed_message);
        student = getStudent("student");
        getData = FirebaseDatabase.getInstance().getReference();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void goHome(View view) {
        Intent intent = new Intent(this, StudentHomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void send(View view) {
        codeTxt = edCode.getText().toString();
        messageTxt = edMessage.getText().toString();

        if (!codeTxt.isEmpty() && !messageTxt.isEmpty()) {
            progressDialog = ProgressDialog.show(this, "Sending ", "Please wait...", false, false);

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        sleep(3000);
                        getData.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                drId = dataSnapshot.child("subject").child(codeTxt).child("drId").getValue(String.class);

                                assert drId != null;
                                Log.e("tess", drId);
                                Message message = new Message(student.getId(), student.getName(), codeTxt, messageTxt);
                                String key = mDatabase.child("massage").child(drId).push().getKey();
                                assert key != null;
                                mDatabase.child("massage").child(drId).child(key).setValue(message);

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
            thread.run();
            Toast.makeText(this, "sent", Toast.LENGTH_SHORT).show();
            progressDialog.cancel();
            edCode.setText("");
            edMessage.setText("");
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
