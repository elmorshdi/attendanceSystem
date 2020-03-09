package com.attendance.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sadaptor extends ArrayAdapter<Student> {
    private long total;
    private String subj_codetxt;
    private Map<String, Map<String, String>> subject = new HashMap<>();
    ArrayList<Student>students=new ArrayList<>();
    public Sadaptor(Context context, ArrayList<Student> Students, String sub_codetxt, long total) {
        super(context, 0, Students);
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        subj_codetxt = sub_codetxt;
        this.students=Students;

        this.total = total;
    }

    @NonNull
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        final Student student = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.atend_item, parent, false);
        }

        TextView id, name, count;
        assert student != null;
        subject = student.getSubjects();
        Map<String, String> sub_code = subject.get(subj_codetxt);


        name = convertView.findViewById(R.id.name);
        id = convertView.findViewById(R.id.id);
        count = convertView.findViewById(R.id.count);


        name.setText(student.getName());
        id.setText(student.getId());
        assert sub_code != null;
        count.setText(sub_code.size() + "out" + total);


        return convertView;
    }
}