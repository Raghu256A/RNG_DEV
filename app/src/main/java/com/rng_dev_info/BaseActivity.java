package com.rng_dev_info;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BaseActivity extends Activity implements View.OnClickListener {

    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        context = getMainApplication();
    }

    @Override
    public void onClick(View v)
    {
        // TODO Auto-generated method stub
    }

    public void handleConfirmDailogResponse1(String isOk)
    {

    }

    public void handleConfirmDailogResponse(Boolean isOk)
    {
    }

    public Intent getIntent(Class<?> cname)
    {
        Intent intent = new Intent();
        intent.setClass(this, cname);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    public AppActivity getMainApplication()
    {
        return (AppActivity) getApplication();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    public void onBackPressed(){

        super.onBackPressed();
    }

    @Override
    protected void onResume(){

        super.onResume();

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }



}
