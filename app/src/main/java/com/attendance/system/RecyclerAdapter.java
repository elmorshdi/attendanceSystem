package com.attendance.system;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.AtendViewHOlder> {
    private long total;
    private String subjCode;
    private ArrayList<Student> students;

    RecyclerAdapter(ArrayList<Student> Students, String subCode, long total) {
        this.subjCode = subCode;
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
        Map<String, Map<String, String>> subject = student.getSubjects();
        Map<String, String> sub_code;


        if (!subject.containsKey(subjCode)) {
            holder.count.setText(0 + " " + "out" + " " + total);
            holder.name.setText(student.getName());
            holder.id.setText(student.getId());
        } else if (subject.containsKey(subjCode)) {
            sub_code = subject.get(subjCode);
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

    static class AtendViewHOlder extends RecyclerView.ViewHolder {
        TextView id, name, count;

        AtendViewHOlder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            count = itemView.findViewById(R.id.count);
        }
    }
}
