package com.attendance.system;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Get_doc_attendActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    DatabaseReference mDatabase;
    ListView listView;
    ArrayList<Student> Students;
    long total;
    String sub_code;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_doc_attend);
        listView = findViewById(R.id.list);
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
        sub_code = editText.getText().toString();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e(TAG, "onDataChange: " + snapshot);
                total = snapshot.child("subject").child(sub_code).child("numoflecture").getValue(long.class);

                for (DataSnapshot data : snapshot.child("student").getChildren()) {
                    Log.e(TAG, "onDataChange: " + data);

                    Student student = data.getValue(Student.class);
                    Students.add(student);

                }
                Sadaptor adapter = new Sadaptor(Get_doc_attendActivity.this, Students, sub_code, total);

                //attach

                listView.setAdapter(adapter);
                Students = new ArrayList<>();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Get_doc_attendActivity.this, "nnn", Toast.LENGTH_LONG).show();


            }
        });
    }
}