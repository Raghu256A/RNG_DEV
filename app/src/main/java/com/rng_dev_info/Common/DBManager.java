package com.rng_dev_info.Common;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.util.Log;


import com.rng_dev_info.Pojo.userDo;

import net.sqlcipher.database.SQLiteDatabase;


import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DBManager {
    public static DBManager dbManager;
    public static boolean isDbCreated = false;
    private final AtomicInteger openCounter = new AtomicInteger();
    private SQLiteDatabase database;

    public static synchronized void initializeInstance(Context context) {
        try {
            if (dbManager == null) {
                dbManager = new DBManager();
                dbManager.openCounter.set(0);
                SQLiteDatabase.loadLibs(context);
                File fileData = new File(Constants.DATABASE_ROOT_PATH + File.separator + Constants.DATABASE_FOLDER
                        + File.separator + Constants.DATABASE_NAME);

                if (!fileData.exists()) {
                    dbManager.createDataBaseFile();
                    isDbCreated = true;
                } else {
                    if (dbManager.isDBOpened()) {
                        dbManager.existingDatabase(Constants.DBPASSWORD);
                    } else {
                        dbManager.existingDatabase("");
                    }
                }
            } else {
                if (dbManager.isDBOpened()) {
                    dbManager.existingDatabase(Constants.DBPASSWORD);
                } else {
                    dbManager.existingDatabase("");
                }
            }
            dbManager.createTables();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            throw new IllegalStateException(
                    DBManager.class.getSimpleName() + " is not initialized, call initializeInstance() method first.");
        }
        return dbManager;
    }

    private void createDataBaseFile() {

        try {
            File directory = new File(Constants.DATABASE_ROOT_PATH, Constants.DATABASE_FOLDER);
            File dbfile = null;

            if (!directory.exists()) {
                boolean result = directory.mkdir();
                if (result) {
                    Log.e(TAG, " DataBase Created");
                }
                Log.e(TAG, " DataBase Created" + result);
            }
            dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
            database = SQLiteDatabase.openOrCreateDatabase(dbfile, null, null);

        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }


    public void existingDatabase(String DBPassword) {

        try {
            File directory = new File(Constants.DATABASE_ROOT_PATH, Constants.DATABASE_FOLDER);
            File dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
            database = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), "", null, 0);
            Constants.DBPASSWORD = DBPassword;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
    }

    public synchronized SQLiteDatabase openDatabase(String callerMessage) {
        try {
            File directory = new File(Constants.DATABASE_ROOT_PATH, Constants.DATABASE_FOLDER);
            File dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
            if (dbfile.exists() && !database.isOpen()) {
                database = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), Constants.DBPASSWORD, null, 0);
            }


        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        }
        return database;
    }

    public synchronized void closeDatabase(String callerMessage) {
        if (openCounter.get() < 0)
            openCounter.set(0);
    }

    public synchronized void forceCloseDatabase() {
        openCounter.set(0);
        database.close();
    }

    public synchronized void createTables() {
        SQLiteDatabase database = DBManager.getInstance().openDatabase("createTables");
        try {
            if (isDbCreated) {
                executeCreateTableScript(database);
            } else {
                executeCreateTableScript(database);

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            DBManager.getInstance().closeDatabase("createTables");
        }
    }

    private void executeCreateTableScript(SQLiteDatabase database) {
        String CREATE_UserMasterTable = "CREATE TABLE IF NOT EXISTS " + Constants.TABLE_NAME_USER_MASTER
                + "(UserId INTEGER PRIMARY KEY AUTOINCREMENT, Password TEXT, UserName TEXT,EMail TEXT,Profile BLOB)";
        try {
            database.execSQL(CREATE_UserMasterTable);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }



    public long insertRecord(String tableName, ContentValues newTaskValue) {
        long result = -1;
        SQLiteDatabase database = DBManager.getInstance().openDatabase("insertRecord");
        try {
            result = database.insertOrThrow(tableName, null, newTaskValue);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } finally {
            DBManager.getInstance().closeDatabase("insertRecord");
        }
        return result;
    }




    public String getScalar(String query) {
        String result = "";
        Cursor cursor = null;
        // open database
        SQLiteDatabase database = DBManager.getInstance().openDatabase("getScalar");
        try {
            try {
                cursor = database.rawQuery(query, null);
                if (cursor.moveToFirst())
                    result = cursor.getString(0);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            } finally {
                if (cursor != null)
                    cursor.close();
            }
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            // close database
            DBManager.getInstance().closeDatabase("getScalar");
        }
        return result;
    }


    public Cursor getRawQuery(SQLiteDatabase database, String query) {
        return database.rawQuery(query, null);
    }


    public boolean isDBOpened() {
        File directory = new File(Constants.DATABASE_ROOT_PATH, Constants.DATABASE_FOLDER);
        File dbfile = new File(directory.getAbsolutePath(), Constants.DATABASE_NAME);
        boolean isDBOpen = false;
        try {
            database = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), Constants.DBPASSWORD, null, 0);
            // Constants.DBPASSWORD = Constants.ACTUALDBPASSWORD;
            isDBOpen = database.isOpen();
        } catch (Exception e) {
           /* Constants.DBPASSWORD = "";
            database = SQLiteDatabase.openDatabase(dbfile.getAbsolutePath(), Constants.DBPASSWORD, null, 0);*/

        }
        return isDBOpen;
    }

    public long insertUserDetails(String email, String name, String password,
                                  byte[] Profile) {
        long res = -1;
        try {
            //UserId TEXT, Password TEXT, UserName TEXT,EMail TEXT,Profile
            ContentValues newTaskValue = new ContentValues();
            newTaskValue.put("EMail", email);
            newTaskValue.put("UserName", name);
            newTaskValue.put("Password", password);
            newTaskValue.put("Profile", Profile);

            res = DBManager.getInstance().insertRecord(Constants.TABLE_NAME_USER_MASTER, newTaskValue);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return res;
    }


    @SuppressLint("Range")
    public static Boolean loginVerification(String email, String password) {
        boolean res = false;
        try {
            String query = "SELECT count(*) as isPresent FROM " + Constants.TABLE_NAME_USER_MASTER + " WHERE EMail = "
                    + Constants.getQuotedString(email) + " AND Password = "
                    + Constants.getQuotedString(password).trim();
            res = Integer.parseInt(DBManager.getInstance().getScalar(query)) > 0;


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return res;

    }

    @SuppressLint("Range")
    public static ArrayList<userDo> getUserData(String email) {
        // TODO Auto-generated method stub
        ArrayList<userDo> list = new ArrayList<>();

        Cursor cursor = null;
        try {


            String query = "SELECT * FROM " + Constants.TABLE_NAME_USER_MASTER + " WHERE EMail = "
                    + Constants.getQuotedString(email);

            SQLiteDatabase database = DBManager.getInstance().openDatabase("login");
            cursor = DBManager.getInstance().getRawQuery(database, query);
            userDo temp=new userDo();
            if (cursor.moveToFirst()) {
                do {
                    try {
                        temp = new userDo();
                        temp.setUserId(cursor.getString(cursor.getColumnIndexOrThrow("UserId")));
                        temp.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("Password")));
                        temp.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMail")));
                        temp.setName(cursor.getString(cursor.getColumnIndexOrThrow("UserName")));
                        temp.setProfile(cursor.getBlob(cursor.getColumnIndexOrThrow("Profile")));
                        list.add(temp);
                    } catch (Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
                while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        } finally {
            if (cursor != null)
                cursor.close();
            DBManager.getInstance().closeDatabase("login");
        }
        return list;
    }


}
