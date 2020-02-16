package com.attendance.system;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.Result;
import com.google.zxing.client.android.BeepManager;

import java.util.ArrayList;
import java.util.LinkedList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class scanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    // Camera Permission Request Code
    private  final int CAMERA_PERMISSION_REQUEST_CODE = 2;

    ZXingScannerView mScannerView;
    private BeepManager beepManager;
    private String lastText;
    ArrayList<String>arrayListscand=new ArrayList<String>();
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
        if (ContextCompat.checkSelfPermission(scanActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            // Request Camera Permission
            ActivityCompat.requestPermissions(scanActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }

    }
  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case CAMERA_PERMISSION_REQUEST_CODE:

                // Check Camera permission is granted or not
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    onStart();

                    Toast.makeText(scanActivity.this, "Camera  permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    onBackPressed();
                    Toast.makeText(scanActivity.this, "Camera  permission denied", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(scanActivity.this, recordattendActivity.class);
        intent.putExtra("list", arrayListscand);
        startActivity(intent);
        super.onBackPressed();
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



    @Override
    public void handleResult(Result result) {
        if (result.getText() == null || result.getText().equals(lastText)) {
            // Prevent duplicate scans
            Toast.makeText(this, "duplicate scans", Toast.LENGTH_SHORT).show();

            onStart();
        }
        else {
            lastText = result.getText();
            arrayListscand.add(result.getText());
            beepManager.playBeepSoundAndVibrate();
            Toast.makeText(this, result.getText(), Toast.LENGTH_SHORT).show();
            onStart();
        }

    }


}



