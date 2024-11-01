package com.rng_dev_info;

import android.app.Application;
import android.os.Build;
import android.os.Environment;

import com.rng_dev_info.Common.Constants;

public class AppActivity  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String device = Build.DEVICE.toUpperCase();
        if (device.contains("GENERIC") || device.contains("SDK")) {
            Constants.EXTERNAL_ROOT_PATH = getExternalFilesDir(null).getAbsolutePath();
        } else {
            Constants.EXTERNAL_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        Constants.EXTERNAL_DATABASE_ROOT_PATH = getExternalFilesDir(null).getAbsolutePath();
        Constants.DATABASE_ROOT_PATH = getFilesDir().getAbsolutePath();
    }
}