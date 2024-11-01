package com.rng_dev_info.Activities;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rng_dev_info.BaseActivity;
import com.rng_dev_info.Common.DBManager;
import com.rng_dev_info.Pojo.userDo;
import com.rng_dev_info.R;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends BaseActivity {

    TextView tv_Day_title, tv_Name, tv_until, tv_per, tv_dis;
    ImageView img_profile, img_fev, img_acc, img_logo_banner;
    LinearLayout ll_btn_Inbox, ll_btn_Maps, ll_btn_Chat, ll_btn_Report, ll_btn_Calender,
            ll_btn_Tips, ll_btn_Settings, ll_btn_Share, ll_btn_More;
    String email="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        updateXML();
        display();
    }

    private void updateXML() {
        try {
            tv_Day_title = findViewById(R.id.tv_Day_title);
            tv_Name = findViewById(R.id.tv_Name);
            tv_until = findViewById(R.id.tv_until);
            tv_per = findViewById(R.id.tv_per);
            tv_dis = findViewById(R.id.tv_dis);
            img_profile = findViewById(R.id.img_profile);
            img_logo_banner = findViewById(R.id.img_logo_banner);
            img_fev = findViewById(R.id.img_fev);
            img_fev.setOnClickListener(this);
            img_acc = findViewById(R.id.img_acc);
            img_acc.setOnClickListener(this);
            ll_btn_Inbox = findViewById(R.id.ll_btn_Inbox);
            ll_btn_Inbox.setOnClickListener(this);
            ll_btn_Maps = findViewById(R.id.ll_btn_Maps);
            ll_btn_Maps.setOnClickListener(this);
            ll_btn_Chat = findViewById(R.id.ll_btn_Chat);
            ll_btn_Chat.setOnClickListener(this);
            ll_btn_Report = findViewById(R.id.ll_btn_Report);
            ll_btn_Report.setOnClickListener(this);
            ll_btn_Calender = findViewById(R.id.ll_btn_Calender);
            ll_btn_Calender.setOnClickListener(this);
            ll_btn_Tips = findViewById(R.id.ll_btn_Tips);
            ll_btn_Tips.setOnClickListener(this);
            ll_btn_Settings = findViewById(R.id.ll_btn_Settings);
            ll_btn_Settings.setOnClickListener(this);
            ll_btn_Share = findViewById(R.id.ll_btn_Share);
            ll_btn_Share.setOnClickListener(this);
            ll_btn_More = findViewById(R.id.ll_btn_More);
            ll_btn_More.setOnClickListener(this);
            email= getIntent().getStringExtra("email");

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.img_fev:
                    Toast.makeText(this, "your favorites", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.img_acc:
                    Toast.makeText(this, "your account", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Inbox:
                    Toast.makeText(this, "your inbox is empty ", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Maps:
                    Toast.makeText(this, "your maps", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Chat:
                    Toast.makeText(this, "your Chat details is empty ", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Report:
                    Toast.makeText(this, "your reports", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Calender:
                    Toast.makeText(this, "Calender", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Tips:
                    Toast.makeText(this, "your tips", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Settings:
                    Toast.makeText(this, "your settings", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_Share:
                    Toast.makeText(this, "to share your Profile", Toast.LENGTH_SHORT).show();

                    break;
                case R.id.ll_btn_More:
                    Toast.makeText(this, "More Details", Toast.LENGTH_SHORT).show();

                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public void display(){
        try{
            ArrayList<userDo> list = DBManager.getUserData(email);
            LocalTime currentTime = null;
            String day="";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                currentTime = LocalTime.now();
                if(currentTime.getHour()>=0&&currentTime.getHour()<12){
                    day=" Good Morning";
                }else
                if(currentTime.getHour()>=12&&currentTime.getHour()<18){
                    day=" Good Afternoon";
                }else
                if(currentTime.getHour()>=6){
                    day=" Good Evening";
                }
                tv_Day_title.setText(day);
            }

            System.out.println("Current Time: " + currentTime);
            if (list.size()>0){
                tv_Name.setText(list.get(0).getName());
                byte[] imageByte =list.get(0).getProfile();
                Bitmap bitmap= BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);
                if (bitmap!=null){
                    img_profile.setImageBitmap(bitmap);
                }
            }

        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }
    }

}