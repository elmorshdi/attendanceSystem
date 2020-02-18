package com.attendance.system;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class doctorFragment extends Fragment {

    private Button Button;
    private EditText dr_email, dr_password, dr_confirm_password,dr_fname,dr_id,dr_lname;
    private String dr_emailtext, dr_passwordtext, dr_confirm_passwordtext ,dr_idtxt,dr_fnametxt,dr_lnametxt;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    public doctorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Button=view.findViewById(R.id.dr_singup);
        dr_email = view.findViewById(R.id.dr_email);
        dr_password = view.findViewById(R.id.dr_password);
        dr_confirm_password = view.findViewById(R.id.dr_confirm_password);
        dr_id=view.findViewById(R.id.dr_id);
        dr_fname=view.findViewById(R.id.dr_fname);
        dr_lname=view.findViewById(R.id.dr_lname);

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dr_fnametxt=dr_fname.getText().toString()+" "+dr_lname.getText().toString() ;
                dr_lnametxt=dr_lname.getText().toString();
                dr_idtxt=dr_id.getText().toString();
                dr_emailtext = dr_email.getText().toString();
                dr_passwordtext = dr_password.getText().toString();
                dr_confirm_passwordtext = dr_confirm_password.getText().toString();

                if (dr_fnametxt.isEmpty() || dr_fnametxt.equals(" ")) {
                    dr_fname.setError("enter your name");
                }
                else if (dr_lnametxt.isEmpty() || dr_lnametxt.equals(" ")) {
                    dr_lname.setError("enter your name");
                }
                else if (dr_idtxt.isEmpty() || dr_idtxt.equals(" ")) {
                    dr_id.setError("enter id");
                }

                else if (dr_emailtext.isEmpty() || dr_emailtext.equals(" ")) {
                    dr_email.setError("enter email");
                }
                else if (dr_passwordtext.isEmpty() || dr_passwordtext.equals(" ")) {
                    dr_password.setError("enter passward");
                }
                else if (dr_passwordtext.length() < 8) {
                    dr_password.setError("lengh mast be 8 or greater");

                }

                else if (dr_confirm_passwordtext.isEmpty() || dr_confirm_passwordtext.equals(" ") || !dr_confirm_passwordtext.equals(dr_passwordtext)) {
                    dr_confirm_password.setError("password not match");
                }
                else {

                    doctor doctor=new doctor(dr_fnametxt,dr_idtxt,dr_emailtext,dr_passwordtext);
                    mDatabase.child("doctor").child(doctor.getId()).setValue(doctor);

                    mAuth.createUserWithEmailAndPassword(dr_emailtext, dr_passwordtext)
                            .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    String tag="";
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(tag, "createUserWithEmail:success");
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(tag, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                                    }

                                }
                            });

                }
            }
        });

        return view;
    }



    public void go_signin(View view) {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);

    }
}
