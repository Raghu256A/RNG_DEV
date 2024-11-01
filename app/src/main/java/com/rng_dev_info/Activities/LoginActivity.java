package com.rng_dev_info.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rng_dev_info.Common.DBManager;
import com.rng_dev_info.Common.RuntimePermissions;
import com.rng_dev_info.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
 TextInputEditText et_email,et_password;
    TextInputLayout  tv_email,tv_password;
 TextView PasswordRecovery,signUp_tv;
 Button bn_login;
 String email="",password="";
 private  static final int PERMISSIONS_WRITE_SETTING=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        DBManager.initializeInstance(this);
        SettingPermissions();
        updateXML();
        RuntimePermissions.checkAllPermisions(this);
    }
    private void updateXML(){
        try {
            et_email = findViewById(R.id.et_email);
            et_email.setText("");
            tv_email = findViewById(R.id.tv_email);
            et_password = findViewById(R.id.et_password);
            et_password.setText("");
            tv_password = findViewById(R.id.tv_password);
            PasswordRecovery = findViewById(R.id.PasswordRecovery);
            signUp_tv = findViewById(R.id.signUp_tv);
            bn_login = findViewById(R.id.bn_login);
            bn_login.setOnClickListener(this);
            signUp_tv.setOnClickListener(this);

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()){
                case R.id.bn_login:
                    email = et_email.getText().toString().trim();
                    password= et_password.getText().toString().trim();
                    if (email.isEmpty()){
                        tv_email.setError("EMail cannot be empty");
                    } else if (password.isEmpty()) {
                        tv_email.setError(null);
                        tv_password.setError("Password cannot be empty");
                    }else {
                        tv_email.setError(null);
                        tv_password.setError(null);
                        if (Settings.System.canWrite(this)){
                            boolean res = DBManager.loginVerification(email,password);
                            if (res){
                                Toast.makeText(this,"Login Successfully..!",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(this,HomeActivity.class);
                                i.putExtra("email",email);
                                startActivity(i);
                            }else {
                                Toast.makeText(this,"Login Fail, Please check once your credentials..!",Toast.LENGTH_LONG).show();

                            }

                        }else {
                            SettingPermissions();
                        }


                    }

                    break;
                case R.id.signUp_tv:
                    if (Settings.System.canWrite(this)){
                        Intent in = new Intent(this,SignUpActivity.class);
                        startActivity(in);
                    }else {
                        SettingPermissions();
                    }

                    break;
                default:
                    break;
            }

        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
    }

    public void SettingPermissions(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (!Settings.System.canWrite(this)){
                Intent i = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:"+getPackageName()));
               startActivityForResult(i,PERMISSIONS_WRITE_SETTING);
            }
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        updateXML();
    }
}