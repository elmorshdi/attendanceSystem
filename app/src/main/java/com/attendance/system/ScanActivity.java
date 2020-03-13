package com.attendance.system;


import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;

import java.lang.reflect.Type;
import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    // Camera Permission Request Code
    private final int CAMERA_PERMISSION_REQUEST_CODE = 2;

    ZXingScannerView mScannerView;
    ArrayList<String> arrayListScanned = new ArrayList<String>();
    private BeepManager beepManager;
    private String lastText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        beepManager = new BeepManager(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Request Camera Permission
            ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {// Check Camera permission is granted or not
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onStart();
            } else {
                onBackPressed();
                Toast.makeText(ScanActivity.this, "Camera  permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onStart() {
        super.onStart();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mScannerView.stopCameraPreview();
        mScannerView.stopCamera();

    }

    public void saveArrayList(ArrayList<String> list, String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String list_in_json = gson.toJson(list);
        editor.putString(key, list_in_json);
        editor.apply();
    }

    @Override
    public void handleResult(Result result) {
        arrayListScanned = getArrayList("all_id");

        if (result.getText() == null || result.getText().equals(lastText)) {
            // Prevent duplicate scans
            if (arrayListScanned.contains(result.getText()))
                Toast.makeText(this, "duplicate id", Toast.LENGTH_SHORT).show();

            onStart();
        } else {
            lastText = result.getText();
            arrayListScanned.add(result.getText());
            beepManager.playBeepSoundAndVibrate();
            Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
            onStart();
        }
        saveArrayList(arrayListScanned, "all_id");

    }

    public ArrayList<String> getArrayList(String key) {
        SharedPreferences prefs = getSharedPreferences("id", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {
        }.getType();
        return gson.fromJson(json, type);
    }


}



