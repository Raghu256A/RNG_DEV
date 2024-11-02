package com.rng_dev_info.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.rng_dev_info.Common.Constants;
import com.rng_dev_info.Common.DBManager;
import com.rng_dev_info.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE_CAMERA = 100;
    private static final int REQUEST_CODE_GALLERY = 101;



    TextInputEditText et_Name = null, et_email = null, et_password = null;
    TextInputLayout tv_Name, tv_email, tv_password;
    TextView signIn_tv;
    Button bn_signUp;
    ImageButton bnt_Update_pic;
    ConstraintLayout cl_image;
    ImageView img_profile;
    String name = "", email = "", password = "", imageFile = "";
    Uri imgUri = null;
    Bitmap bitmap=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        updateXML();
    }

    private void updateXML() {
        try {
            et_email = findViewById(R.id.et_email);
            tv_email = findViewById(R.id.tv_email);
            et_password = findViewById(R.id.et_password);
            tv_password = findViewById(R.id.tv_password);
            bnt_Update_pic = findViewById(R.id.bnt_Update_pic);
            et_Name = findViewById(R.id.et_Name);
            tv_Name = findViewById(R.id.tv_Name);
            img_profile = findViewById(R.id.img_profile);
            signIn_tv = findViewById(R.id.signIn_tv);
            signIn_tv.setOnClickListener(this);
            bn_signUp = findViewById(R.id.bn_signUp);
            bn_signUp.setOnClickListener(this);
            cl_image = findViewById(R.id.cl_image);
            cl_image.setOnClickListener(this);
            bnt_Update_pic.setOnClickListener(this);

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.bn_signUp:
                    email = et_email.getText().toString().trim();
                    name = et_Name.getText().toString().trim();
                    password = et_password.getText().toString().trim();
                    if (bitmap==null && name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                        tv_Name.setError("Full Name cannot be empty");
                        tv_email.setError("EMail cannot be empty");
                        tv_password.setError("Password cannot be empty");
                        Toast.makeText(this, "Please Capture the Profile ", Toast.LENGTH_LONG).show();
                    } else if (bitmap==null) {
                        Toast.makeText(this, "Please Capture the Profile ", Toast.LENGTH_LONG).show();
                    } else if (name.isEmpty()) {
                        tv_Name.setError(null);
                        tv_Name.setError("Full Name cannot be empty");
                    } else if (email.isEmpty()) {
                        tv_Name.setError(null);
                        tv_email.setError("EMail cannot be empty");
                    } else if (password.isEmpty()) {
                        tv_email.setError(null);
                        tv_password.setError("Password cannot be empty");
                    } else {
                        boolean check=true;

                        if (password.length()<6) {
                            tv_password.setError("password mim length of 6 ");
                            check=false;
                        }
                        if (check){
                            tv_Name.setError(null);
                            tv_email.setError(null);
                            tv_password.setError(null);
                            saveData();
                        }

                    }

                    break;
                case R.id.signIn_tv:
                    Intent in = new Intent(this, LoginActivity.class);
                    startActivity(in);
                    break;
                case R.id.cl_image:
                case R.id.bnt_Update_pic:
                    email = et_email.getText().toString().trim();
                    {
                        tv_email.setError(null);
                        selectImageSource();
                    }


                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private void selectImageSource() {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Image Source")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openCamera(); // Open camera method
                                break;
                            case 1:
                                openGallery(); // Open gallery method
                                break;
                        }
                    }
                })
                .show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK) {
                bitmap  = (Bitmap) data.getExtras().get("data");
                img_profile.setImageBitmap(bitmap);
            } else if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
                imgUri = data.getData();
                bitmap =uriToBitmap(imgUri);
                img_profile.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());

        }
    }

    private void openCamera() {
        Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }



    private Bitmap uriToBitmap(Uri uri) {
        try {
            ContentResolver contentResolver = getContentResolver();
            InputStream inputStream = contentResolver.openInputStream(uri);
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle the error as needed
        }
    }
    public void saveData(){
        try{
            ByteArrayOutputStream bos= new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bos);
            byte[] imageBytes= bos.toByteArray();

            long  res = DBManager.getInstance().insertUserDetails(email,name,password,imageBytes);

            if (res>=0){
                Toast.makeText(this,"Register Successfully..!",Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                finish();
            }else {
                Toast.makeText(this,"Register Fail..!",Toast.LENGTH_LONG).show();

            }
        }catch (Exception e){
            Log.e(TAG,e.getMessage());
        }


    }

}