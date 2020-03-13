package com.attendance.system;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorFragment extends Fragment {

    private SharedPreferences pref;
    private EditText drEmail, drPassword, drConfirmPassword, drFName, drId, drLName;
    private String drEmailTxt, drPasswordTxt, drConfirmPasswordTxt, drIdTxt, drFNameTxt, drLNameTxt;
    private DatabaseReference mDatabase;


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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drFNameTxt = drFName.getText().toString() + " " + drLName.getText().toString();
                drLNameTxt = drLName.getText().toString();
                drIdTxt = drId.getText().toString();
                drEmailTxt = drEmail.getText().toString();
                drPasswordTxt = drPassword.getText().toString();
                drConfirmPasswordTxt = drConfirmPassword.getText().toString();

                if (drFNameTxt.isEmpty() || drFNameTxt.equals(" ")) {
                    drFName.setError("enter your name");
                } else if (drLNameTxt.isEmpty() || drLNameTxt.equals(" ")) {
                    drLName.setError("enter your name");
                } else if (drIdTxt.isEmpty() || drIdTxt.equals(" ")) {
                    drId.setError("enter id");
                } else if (drEmailTxt.isEmpty() || drEmailTxt.equals(" ")) {
                    drEmail.setError("enter email");
                } else if (drPasswordTxt.isEmpty() || drPasswordTxt.equals(" ")) {
                    drPassword.setError("enter password");
                } else if (drPasswordTxt.length() < 8) {
                    drPassword.setError("length mast be 8 or greater");

                } else if (drConfirmPasswordTxt.isEmpty() || drConfirmPasswordTxt.equals(" ") || !drConfirmPasswordTxt.equals(drPasswordTxt)) {
                    drConfirmPassword.setError("password not match");
                } else {

                    Doctor doctor = new Doctor(drFNameTxt, drIdTxt, drEmailTxt, drPasswordTxt);
                    mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);
                    doctorLogin(drIdTxt);
                }


            }
        });

        return view;
    }

    private void doctorLogin(String id) {


        SharedPreferences.Editor editor = pref.edit();
        editor.putString("dusername", id);
        editor.putString("dpassword", drPasswordTxt);
        editor.apply();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }


}
