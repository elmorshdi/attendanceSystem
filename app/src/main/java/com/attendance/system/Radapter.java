package com.attendance.system;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Radapter extends RecyclerView.Adapter<Radapter.AtendViewHOlder> {
    private long total;
    private String subj_codetxt;
    private Map<String, Map<String, String>> subject = new HashMap<>();
    private ArrayList<Student> students = new ArrayList<>();

    public Radapter(ArrayList<Student> Students, String sub_codetxt, long total) {
        this.subj_codetxt = sub_codetxt;
        this.students = Students;

        this.total = total;
    }

    @NonNull
    @Override
    public AtendViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AtendViewHOlder(LayoutInflater.from(parent.getContext()).inflate(R.layout.atend_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AtendViewHOlder holder, int position) {


        Student student = students.get(position);
        subject = student.getSubjects();
        Map<String, String> sub_code = new HashMap<>();


        if (!subject.containsKey(subj_codetxt)) {
            holder.count.setText(0 + " " + "out" + " " + total);
            holder.name.setText(student.getName());
            holder.id.setText(student.getId());
        } else if (subject.containsKey(subj_codetxt)) {
            sub_code = subject.get(subj_codetxt);
            assert sub_code != null;
            holder.count.setText(sub_code.size() + " " + "out" + " " + total);
            holder.name.setText(student.getName());
            holder.id.setText(student.getId());
        }
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class AtendViewHOlder extends RecyclerView.ViewHolder {
        TextView id, name, count;

        public AtendViewHOlder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            count = itemView.findViewById(R.id.count);
        }
    }
}
