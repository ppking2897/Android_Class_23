package com.example.user.android_class_23;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    private ImageView img;
    private File sdroot , photoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.img);
        sdroot = Environment.getExternalStorageDirectory();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    123);

        }
    }
    public void b1(View v){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it,1);
    }
    public void b2(View v){
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = new File(sdroot, "ppking.jpg");
        Uri photoUri = Uri.fromFile(photoFile);
        it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(it, 2);
    }

    public void b3(View v){
        Intent it = new Intent(this,CameraActivity.class);
        startActivityForResult(it, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode ==RESULT_OK){
            take1(data);
        }else if(requestCode == 2 && resultCode ==RESULT_OK){
            take2(data);
        }
    }

    private void take1(Intent it){
        Bitmap bmp = (Bitmap) it.getExtras().get("data");
        img.setImageBitmap(bmp);
    }
    private void take2(Intent it){
        Bitmap bmp = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        img.setImageBitmap(bmp);
    }
}
