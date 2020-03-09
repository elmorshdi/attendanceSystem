package com.attendance.system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;


public class SignUp_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Page_Adapter pageAdapter;
    TabItem tabdoctor;
    TabItem tabstudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tabLayout = findViewById(R.id.tablayout);
        tabdoctor = findViewById(R.id.tab1);
        tabstudent = findViewById(R.id.tab2);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new Page_Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void go_signin(View view) {
        Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
        startActivity(intent);

    }

    public void go_home(View view) {
        Intent intent = new Intent(SignUp_Activity.this, MainActivity.class);
        startActivity(intent);
    }
}
