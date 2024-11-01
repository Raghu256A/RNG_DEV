package com.rng_dev_info.Common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Constants {

    public static String EXTERNAL_ROOT_PATH = "";
    public static String DATABASE_ROOT_PATH = "";
    public static String EXTERNAL_DATABASE_ROOT_PATH = "";
    public static String DATABASE_FOLDER = "RNG_DEV_DB";//
    public static String DBPASSWORD = "!$ANGDEV@#";
    public static String TABLE_NAME_USER_MASTER = "UserMaster";
    public static String DATABASE_NAME = "RNGDEV_DB";//
    public static String getQuotedString(String A_strText) {
        String strReplaceString;
        String Result = "''";
        if (A_strText != null) {
            strReplaceString = A_strText.replace("'", "''");
            Result = "'" + strReplaceString + "'";
        }
        return Result;
    }


}
