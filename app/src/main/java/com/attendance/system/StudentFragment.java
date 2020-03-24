package com.attendance.system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudentFragment extends Fragment {
    private EditText edEmail, edPassword, edConfirmPassword, edFullName, edId;
    private String emailTxt, passwordTxt, confirmPasswordTxt, idTxt, fnameTxt;
    private DatabaseReference mDatabase;
    private SharedPreferences pref;

    public StudentFragment() {
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
        edEmail = view.findViewById(R.id.email);
        edPassword = view.findViewById(R.id.password);
        edConfirmPassword = view.findViewById(R.id.confirm_password);
        edId = view.findViewById(R.id.id);
        edFullName = view.findViewById(R.id.fullname);
        pref = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnameTxt = edFullName.getText().toString();
                idTxt = edId.getText().toString();
                emailTxt = edEmail.getText().toString();
                passwordTxt = edPassword.getText().toString();
                confirmPasswordTxt = edConfirmPassword.getText().toString();
                String[] domain = emailTxt.split("@");

                if (fnameTxt.isEmpty() || fnameTxt.equals(" ")) {
                    edFullName.setError("enter your name");
                } else if (idTxt.isEmpty() || idTxt.equals(" ")) {
                    edId.setError("enter id");
                } else if (emailTxt.isEmpty() || emailTxt.equals(" ")) {
                    edEmail.setError("enter email");
                } else if (!emailTxt.contains("@")) {
                    edEmail.setError("Email not valid");
                } else if (!(domain[1].equals("students.mans.edu.eg"))) {
                    edEmail.setError("Email not valid");
                } else if (passwordTxt.isEmpty() || passwordTxt.equals(" ")) {
                    edPassword.setError("enter password");
                } else if (passwordTxt.length() < 8) {
                    edPassword.setError("length mast be 8 or greater");

                } else if (confirmPasswordTxt.isEmpty() || confirmPasswordTxt.equals(" ") || !confirmPasswordTxt.equals(passwordTxt)) {
                    edConfirmPassword.setError("password not match");
                } else {

                    final Student student = new Student(emailTxt, idTxt, fnameTxt, passwordTxt);
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("student").hasChild(student.getId())) {
                                Toast.makeText(getActivity(), " this id had registered", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = pref.edit();
                                Gson gson = new Gson();
                                String studentInJson = gson.toJson(student);
                                editor.putString("student", studentInJson);
                                editor.apply();
                                mDatabase.child("student").child(student.getId()).setValue(student);
                                mDatabase.child("student").child(student.getId()).child("subjects").child("sub_code").child("date").setValue("true");

                                studentLogIn();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }
        });


        return view;
    }

    private void studentLogIn() {

        Intent intent = new Intent(getActivity(), StudentHomeActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }


}
