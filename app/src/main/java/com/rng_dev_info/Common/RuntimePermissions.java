package com.rng_dev_info.Common;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class RuntimePermissions {
    public static final int PERMISSION_REQUEST_CODE = 200;


    public static boolean checkAllPermisions(Context context) {

        List<String> allPermisionTemp = new ArrayList<>();
        String[] allPermision1 = new String[0];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            allPermision1 = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.INTERNET, Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE};
        }
        for (String perm : allPermision1) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                allPermisionTemp.add(perm);
            }
        }
        if (!allPermisionTemp.isEmpty()) {
            ActivityCompat.requestPermissions((Activity) context, allPermisionTemp.toArray(new String[allPermisionTemp.size()]), PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }
}
