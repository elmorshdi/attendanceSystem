package com.attendance.system;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

public class recordattendActivity extends AppCompatActivity {
    int year,month,day;
    EditText editdat,editid ,editccode;
    ListView listView;
    TextView textView;
    String id;
    ArrayList<String>arrayList=new ArrayList<String>();
    ArrayList<String>arrayscan=new ArrayList<String>();

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
        itemsAdapter=new ArrayAdapter<String>(this,R.layout.item,R.id.texti, arrayList);
        listView.setAdapter(itemsAdapter);


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

    @Override
    protected void onStart() {

        super.onStart();
    }
    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(recordattendActivity.this);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
    @Override
    protected void onRestart() {

        for (String s : arrayscan = getArrayList("arraylistscand")) {
            itemsAdapter.add(s);

        }


       /* if (get_id_from_scan()!=null)
        {
            if(arrayscan!=null){arrayscan.addAll(get_id_from_scan());}
            arrayscan=get_id_from_scan();
        }

        itemsAdapter.addAll(arrayscan);
        textView.setText("vv");*/
        super.onRestart();


    }

    public ArrayList<String> get_id_from_scan() {
        Intent data=getIntent();
        ArrayList<String>array=new ArrayList<String>();
        if (data.getStringArrayListExtra("list")!=null)
        {
            array=data.getStringArrayListExtra("list");
        return array;
        }
        else {
            return null;
        }

    }

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(recordattendActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String list_in_json = gson.toJson(list);
        editor.putString(key, list_in_json);
        editor.commit();
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
        Intent intent= new Intent(recordattendActivity.this,scanActivity.class);
        startActivity(intent);
    }

    public void addid(View view)
    { id=editid.getText().toString();
        if (!id.isEmpty()){ arrayscan.add(id);}
        itemsAdapter.add(id);
        textView.setText("vv");
    }

    public void uploud(View view) {

        Toast.makeText(this, "added", Toast.LENGTH_SHORT).show();

    }
}
