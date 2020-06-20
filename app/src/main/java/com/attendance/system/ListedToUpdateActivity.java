package com.attendance.system;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
import java.util.Map;

public class ListedToUpdateActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> arrayList;
    TextView textView;
    SharedPreferences pref;
    String date, sub_code;
    long total;
    private static final String TAG = "Listed_Toupdate_Activit";
    private DatabaseReference mData, mDatabase;
    ArrayList<Student> students;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        mDatabase = FirebaseDatabase.getInstance().getReference();
//get date from preference
        pref = getSharedPreferences("id", MODE_PRIVATE);
        arrayList = new ArrayList<>();
        students = new ArrayList<>();
        arrayList = getArrayList("all_id");
        if (arrayList == null) arrayList = new ArrayList<>();
        date = pref.getString("date", "");
        sub_code = pref.getString("cours_code", " ");
        //
        listView = findViewById(R.id.list);
        textView = findViewById(R.id.numtxt);
        //
        adapter = new ArrayAdapter<>(this, R.layout.item, arrayList);
        listView.setAdapter(adapter);
        //
        mData = FirebaseDatabase.getInstance().getReference("subject");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(sub_code)) {
                    total = snapshot.child(sub_code).child("numOfLecture").getValue(long.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        textView.setText("number of student: " + adapter.getCount() + "\n" + "Date of day :" + date +
                "\n" + "subject code :" + sub_code);

    }

    public void go_attend(View view) {
        onBackPressed();

    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @SuppressLint("SetTextI18n")
    public void uploud(View view) {
        if (!arrayList.isEmpty()) {
            mData.child(sub_code).child("numOfLecture").setValue(total + 1);
            for (String s : arrayList) {
                mDatabase.child("student").child(s).child("subjects").child(sub_code).child(date).setValue("true");
            }

        }


        SharedPreferences.Editor editor = pref.edit();
        textView.setText("number of student: " + "\n" + "Date of day :" + "\n" +
                "subject code :");
        editor.remove("all_id").apply();
        adapter.clear();
        editor.remove("date").commit();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void sendMails() {
        if (!(sub_code.equals(" "))) {
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.e(TAG, "onDataChange: " + snapshot);
                    for (DataSnapshot data : snapshot.child("student").getChildren()) {
                        Log.e(TAG, "onDataChange: " + data);

                        Student student = data.getValue(Student.class);
                        students.add(student);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            for (Student student : students) {
                Log.e(TAG, "onDataChange: " + student);

                int totalLecS = 0;
                Map<String, Map<String, String>> subjects = student.getSubjects();
                if (subjects.containsKey(sub_code)) {
                    Map<String, String> course = subjects.get(sub_code);
                    assert course != null;
                    totalLecS = course.size();
                }
                int totalLec = ((int) total);

                int m = totalLec - totalLecS;
                if (m == 2) {

                    String email = student.getEmail();
                    JavaMailAPI mail = new JavaMailAPI(this, email, "Important notification", "You have exceeded the number of allowed absences, two lectures, this first warning, if multiple absences are repeated again without sending an acceptable excuse, you will be deprived of taking the exam.");
                    mail.execute();
                } else if (m == 4) {
                    String email = student.getEmail();
                    JavaMailAPI mail = new JavaMailAPI(this, email, "Important notification", "You have exceeded the number of allowed absences 4 times this second warning due to multiple absences again without sending an acceptable excuse You have been deprived of taking the exam");
                    mail.execute();

                }

            }
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("cours_code").apply();

        } else {
            Toast.makeText(this, "Enter course code first ", Toast.LENGTH_SHORT).show();
        }
    }

    public void send(View view) {
        sendMails();
    }
}