//package com.example.thejaswi.libraryapplication.view.activities;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.hardware.Camera;
//import android.os.Bundle;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.example.thejaswi.libraryapplication.R;
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
//
///**
// * Created by Mak on 12/5/17.
// */
//
//public class ScanActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Button btn = (Button) findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                int frontId = 0, backId = 0;
//                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//                int numberOfCameras = Camera.getNumberOfCameras();
//                for (int i = 0; i < numberOfCameras; i++) {
//                    Camera.getCameraInfo(i, cameraInfo);
//                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                        frontId = i;
//                    } else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
//                        backId = i;
//                    }
//                }
//                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                    //ask for authorisation
//                    ActivityCompat.requestPermissions(ScanActivity.this, new String[]{Manifest.permission.CAMERA}, 50);
//                else
//
//                {
//
//                    IntentIntegrator scanIntegrator = new IntentIntegrator(ScanActivity.this);
//
//                    scanIntegrator.initiateScan();
//
//                }
//            }
//        });
//
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//
//        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//        if (scanningResult != null) {
//
//            String scanContent = scanningResult.getContents();
//            String scanFormat = scanningResult.getFormatName();
//
//            Log.e("RESULT_SCAN", "Content" + scanContent);
//            Log.e("RESULT_SCAN", "Format" + scanFormat);
//        } else {
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "No scan data received!", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }
//
//
//}
