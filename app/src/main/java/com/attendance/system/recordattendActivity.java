package com.attendance.system;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Calendar;

public class recordattendActivity extends AppCompatActivity {
    int year,month,day;
    EditText editdat,editid ,editccode;
    ListView listView;
    TextView textView;
    String id;
    ArrayList<String>arrayList=new ArrayList<String>();
    static   ArrayList<String>arrayscan=new ArrayList<String>();

    ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordattend);


        listView =findViewById(R.id.list);
        textView=findViewById(R.id.numtxt);
        editdat = findViewById(R.id.datepicker);
        editid =findViewById(R.id.stu_id);
        editccode =findViewById(R.id.cours_code);


        Intent data=getIntent();
        arrayscan=data.getStringArrayListExtra("list");
        setlist(arrayscan);

        Calendar calendar = Calendar.getInstance();
        year= 2019;
        month=7;
        day=9;

        editdat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdatediloge();
            }


        });

    }
    public void setlist(ArrayList array) {

        if(array!=null) {
            arrayList.addAll(array);
            itemsAdapter=new ArrayAdapter<String>(this,R.layout.item,R.id.texti, arrayList);
            listView.setAdapter(itemsAdapter);
            textView.setText("number of student :"+ arrayList.size());
        }
        else {            Toast.makeText(this, "sad", Toast.LENGTH_SHORT).show();
        }

    }
    private void showdatediloge() {

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editdat.setText(dayOfMonth+"/"+month+ "/"+year);

            }
        };


        DatePickerDialog datePickerDialog= new DatePickerDialog(recordattendActivity.this,  listener, year, month, day);

        datePickerDialog.show();

    }
    public void go_home(View view) {
        Intent intent= new Intent(recordattendActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void goto_qr(View view) {
        Toast.makeText(this, "scan", Toast.LENGTH_SHORT).show();
    }

    public void addid(View view)
    {
        arrayscan.add(editid.getText().toString());
        setlist(arrayscan);



    }

    public void uploud(View view) {

        Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();

    }
}
