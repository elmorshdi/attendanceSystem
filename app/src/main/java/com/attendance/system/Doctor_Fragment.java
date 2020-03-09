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


/**
 * A simple {@link Fragment} subclass.
 */
public class Doctor_Fragment extends Fragment {

    SharedPreferences pref;
    private EditText dr_email, dr_password, dr_confirm_password, dr_fname, dr_id, dr_lname;
    private String dr_emailtext, dr_passwordtext, dr_confirm_passwordtext, dr_idtxt, dr_fnametxt, dr_lnametxt;
    private DatabaseReference mDatabase;


    public Doctor_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button button = view.findViewById(R.id.dr_singup);
        dr_email = view.findViewById(R.id.dr_email);
        dr_password = view.findViewById(R.id.dr_password);
        dr_confirm_password = view.findViewById(R.id.dr_confirm_password);
        dr_id = view.findViewById(R.id.dr_id);
        dr_fname = view.findViewById(R.id.dr_fname);
        dr_lname = view.findViewById(R.id.dr_lname);
        pref = this.getActivity().getSharedPreferences("user_details", Context.MODE_PRIVATE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_fnametxt = dr_fname.getText().toString() + " " + dr_lname.getText().toString();
                dr_lnametxt = dr_lname.getText().toString();
                dr_idtxt = dr_id.getText().toString();
                dr_emailtext = dr_email.getText().toString();
                dr_passwordtext = dr_password.getText().toString();
                dr_confirm_passwordtext = dr_confirm_password.getText().toString();

                if (dr_fnametxt.isEmpty() || dr_fnametxt.equals(" ")) {
                    dr_fname.setError("enter your name");
                } else if (dr_lnametxt.isEmpty() || dr_lnametxt.equals(" ")) {
                    dr_lname.setError("enter your name");
                } else if (dr_idtxt.isEmpty() || dr_idtxt.equals(" ")) {
                    dr_id.setError("enter id");
                } else if (dr_emailtext.isEmpty() || dr_emailtext.equals(" ")) {
                    dr_email.setError("enter email");
                } else if (dr_passwordtext.isEmpty() || dr_passwordtext.equals(" ")) {
                    dr_password.setError("enter passward");
                } else if (dr_passwordtext.length() < 8) {
                    dr_password.setError("lengh mast be 8 or greater");

                } else if (dr_confirm_passwordtext.isEmpty() || dr_confirm_passwordtext.equals(" ") || !dr_confirm_passwordtext.equals(dr_passwordtext)) {
                    dr_confirm_password.setError("password not match");
                } else {

                    Doctor doctor = new Doctor(dr_fnametxt, dr_idtxt, dr_emailtext, dr_passwordtext);
                    mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);
                    doctorlogin(dr_idtxt);
                }


            }
        });

        return view;
    }

    private void doctorlogin(String id) {


        SharedPreferences.Editor editor = pref.edit();
        editor.putString("dusername", id);
        editor.putString("dpassword", dr_passwordtext);
        editor.apply();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        getActivity().finish();
    }


}
