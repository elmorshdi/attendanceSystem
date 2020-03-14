package com.attendance.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends ArrayAdapter<Message> {
    private DatabaseReference mData, mDatabase;
    private String id;

    public MessageAdapter(Context context, ArrayList<Message> products, String id) {
        super(context, 0, products);
        this.id = id;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("message").child(id);
        mData = FirebaseDatabase.getInstance().getReference().child("student");

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Message message = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message, parent, false);
        }

        TextView name, id, code, messageTxt;
        Button accept, delete;


        name = convertView.findViewById(R.id.name);
        id = convertView.findViewById(R.id.id);
        code = convertView.findViewById(R.id.code);
        messageTxt = convertView.findViewById(R.id.message);

        name.setText(message.getName());
        id.setText(message.getId());
        code.setText(message.getCourseCode());
        messageTxt.setText("reason of absence" + "\n" + message.getReasonOfAbsence());

        accept = convertView.findViewById(R.id.true_bu);
        delete = convertView.findViewById(R.id.false_bu);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mData.child(message.getId()).child("subjects").child(message.getCourseCode()).child("Ask permission").setValue("true");
                mDatabase.child(message.getKey()).removeValue();

            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(message.getKey()).removeValue();
            }
        });


        return convertView;
    }

}
