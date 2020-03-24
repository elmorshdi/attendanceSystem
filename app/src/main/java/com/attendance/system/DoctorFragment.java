package com.attendance.system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Objects;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

    private static final String TAG = "random";
    private SharedPreferences pref;
    private EditText drEmail, drPassword, drConfirmPassword, drFName, drId, drLName;
    private String drEmailTxt, drPasswordTxt, drConfirmPasswordTxt, drIdTxt, drFNameTxt, drLNameTxt;
    private DatabaseReference mDatabase;
    private Doctor doctor;

    public DoctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button button = view.findViewById(R.id.dr_singup);
        drEmail = view.findViewById(R.id.dr_email);
        drPassword = view.findViewById(R.id.dr_password);
        drConfirmPassword = view.findViewById(R.id.dr_confirm_password);
        drId = view.findViewById(R.id.dr_id);
        drFName = view.findViewById(R.id.dr_fname);
        drLName = view.findViewById(R.id.dr_lname);
        pref = Objects.requireNonNull(this.getActivity()).getSharedPreferences("user_details", Context.MODE_PRIVATE);

        Log.e(TAG, "onCreateView: " + getRandom());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drFNameTxt = drFName.getText().toString() + " " + drLName.getText().toString();
                drLNameTxt = drLName.getText().toString();
                drIdTxt = drId.getText().toString();
                drEmailTxt = drEmail.getText().toString();
                drPasswordTxt = drPassword.getText().toString();
                drConfirmPasswordTxt = drConfirmPassword.getText().toString();

                String[] domain = drEmailTxt.split("@");

                if (drFNameTxt.isEmpty() || drFNameTxt.equals(" ")) {
                    drFName.setError("enter your name");
                } else if (drLNameTxt.isEmpty() || drLNameTxt.equals(" ")) {
                    drLName.setError("enter your name");
                } else if (drIdTxt.isEmpty() || drIdTxt.equals(" ")) {
                    drId.setError("enter id");
                } else if (drEmailTxt.isEmpty() || drEmailTxt.equals(" ")) {
                    drEmail.setError("enter email");
                } else if (!drEmailTxt.contains("@")) {
                    drEmail.setError("Email not valid");
                } else if (!(domain[1].equals("mans.edu.eg"))) {
                    drEmail.setError("Email not valid");
                } else if (drPasswordTxt.isEmpty() || drPasswordTxt.equals(" ")) {
                    drPassword.setError("enter password");
                } else if (drPasswordTxt.length() < 8) {
                    drPassword.setError("length mast be 8 or greater");

                } else if (drConfirmPasswordTxt.isEmpty() || drConfirmPasswordTxt.equals(" ") || !drConfirmPasswordTxt.equals(drPasswordTxt)) {
                    drConfirmPassword.setError("password not match");
                } else {

                    doctor = new Doctor(drFNameTxt, drIdTxt, drEmailTxt, drPasswordTxt);
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child("doctor").hasChild(doctor.getId())) {
                                drId.setError(" this id had registered");
                                Toast.makeText(getActivity(), " this id had registered", Toast.LENGTH_SHORT).show();
                            } else {


                                doctorLogin(getRandom());

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

    private String getRandom() {
        String number = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            number += random.nextInt(9);
        }
        return number;
    }

    private void doctorLogin(String random) {
        JavaMailAPI mailAPI = new JavaMailAPI(getContext(), drEmailTxt, "[AttendanceSystem] Please verify your email Address",
                "Almost done!" + "\n" + drFNameTxt + "\n" + "Your verify code [" + random + "]");
        mailAPI.execute();
        SharedPreferences.Editor editor = pref.edit();
        Gson gson = new Gson();
        String studentInJson = gson.toJson(doctor);
        Log.e(TAG, doctor + "\n" + random);
        editor.putString("pendingDoctor", studentInJson);
        editor.apply();
        Intent intent = new Intent(getActivity(), PendingActivity.class);
        intent.putExtra("random", random);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }


}
