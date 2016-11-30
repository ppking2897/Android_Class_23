package com.example.user.android_class_23;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CameraActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private MyPreview myPreview;
    private Camera camera;
    private File sdroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    123);
        } else {
            init();
        }
    }
    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
    private void init(){

        sdroot = Environment.getExternalStorageDirectory();

        camera = Camera.open();
        frameLayout = (FrameLayout)findViewById(R.id.frameLayout);
        myPreview = new MyPreview(this,camera);
        frameLayout.addView(myPreview);

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera.takePicture(new MyShutter(),null,new MyimgBack());
            }
        });
    }

    private class MyShutter implements Camera.ShutterCallback{

        @Override
        public void onShutter() {

        }
    }

    private class MyimgBack implements Camera.PictureCallback{

        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            try {
                FileOutputStream fout = new FileOutputStream(new File(sdroot, "ppking.jpg"));
                fout.write(bytes);
                fout.flush();
                fout.close();
                Toast.makeText(CameraActivity.this, "Save OK",Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Log.v("ppking", e.toString());
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        init();
    }
}
