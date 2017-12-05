package com.example.thejaswi.libraryapplication.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Mak on 12/4/17.
 */

public class AccessPermissions {
    Context context;
    Activity activity;
    private int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE ;

    public AccessPermissions(Context context, Activity activity ,int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
        this.context = context;
        this.activity = activity;
        this.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    }

    public  boolean validateStorageAccessPermission() {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            }

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

            return false;
        }
        return true;
    }
}
