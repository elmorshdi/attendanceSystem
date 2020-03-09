package com.attendance.system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Student_Fragment extends Fragment {
    private EditText email, password, confirm_password, fullname, id;
    private String emailtext, passwordtext, confirm_passwordtext, idtxt, fnametxt;
    private DatabaseReference mDatabase;
    private SharedPreferences pref;

    public Student_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_student, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        android.widget.Button button = view.findViewById(R.id.ssingup);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        confirm_password = view.findViewById(R.id.confirm_password);
        id = view.findViewById(R.id.id);
        fullname = view.findViewById(R.id.fullname);
        pref = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnametxt = fullname.getText().toString();
                idtxt = id.getText().toString();
                emailtext = email.getText().toString();
                passwordtext = password.getText().toString();
                confirm_passwordtext = confirm_password.getText().toString();

                if (fnametxt.isEmpty() || fnametxt.equals(" ")) {
                    fullname.setError("enter your name");
                } else if (idtxt.isEmpty() || idtxt.equals(" ")) {
                    id.setError("enter id");
                } else if (emailtext.isEmpty() || emailtext.equals(" ")) {
                    email.setError("enter email");
                } else if (passwordtext.isEmpty() || passwordtext.equals(" ")) {
                    password.setError("enter passward");
                } else if (passwordtext.length() < 8) {
                    password.setError("lengh mast be 8 or greater");

                } else if (confirm_passwordtext.isEmpty() || confirm_passwordtext.equals(" ") || !confirm_passwordtext.equals(passwordtext)) {
                    confirm_password.setError("password not match");
                } else {

                    Student student = new Student(emailtext, idtxt, fnametxt, passwordtext);
                    mDatabase.child("student").child(student.getId()).setValue(student);
                    studentlogin(student.getId());
                }


            }
        });


        return view;
    }

    private void studentlogin(String id) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("susername", id);
        editor.putString("spassword", passwordtext);
        editor.apply();
        Intent intent = new Intent(getActivity(), Student_Home_Activity.class);
        startActivity(intent);
        getActivity().finish();
    }


}