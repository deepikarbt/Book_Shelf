package com.example.deepika.rbtlib;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 101;
    private static final int CAMERA_CODE = 102;
    private static final int CAM_CODE = 103;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_IMAGE = 2;
    private Uri fileUri;
    private Button btnOut,btnIn,actbut;
    String firstname;
    private static  boolean status = false;
    String timeStamp;
    Bitmap thePic,pic;
    String extra;
    Bitmap bit;
    String email,username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ActionBar actionBar = getActionBar();
        actionBar.setCustomView(R.layout.actionbutton);
        actbut = (Button) actionBar.getCustomView().findViewById(R.id.buttonaction);
        actbut.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                *//*Toast.makeText(MainActivity.this, "Search triggered",
                        Toast.LENGTH_LONG).show();*//*
                Intent mov = new Intent(MainActivity.this,Login.class);
                startActivity(mov);
                return false;
            }
        });
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);*/
        SharedPreferences sp1=this.getSharedPreferences("Login",0);
        username = sp1.getString("first_name",null);
        System.out.println(username);
     //   Log.w("value",username);
       /* username = sp1.getString("first_name",null);
        System.out.println(username);*/
      //  Log.w("value",username);

        Intent i = getIntent();
      //  firstname=i.getStringExtra("first_name");
     //   System.out.println(firstname);
   //     Log.w("name",firstname);
        btnOut = (Button) findViewById(R.id.button2);
        btnIn = (Button)findViewById(R.id.btnCapturePicture);
        btnOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                captureImage(1);

            }
        });
        btnIn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                captureImages(0);
            }
                                  });

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id=item.getItemId();
        if(id==R.id.logout)
        {
            Intent in= new Intent(this,Login.class);
            startActivity(in);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    private void captureImage(int cab ) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAMERA_CODE);
    }

    private void captureImages(int cab ) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
       intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAM_CODE);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_CODE&& resultCode == RESULT_OK) {
            Log.w("req", "crop");
            //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
            try {
                Log.w("crop","image");
                cropCapImage(fileUri);
                Log.w("sad","fasf");
                Log.w("dad","cszfsf");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (requestCode == CAMERA_REQUEST_CODE&&resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            pic = extras.getParcelable("data");

            Intent in = new Intent(MainActivity.this,Receive.class);
                Log.w("1","upload");
                in.putExtra("filePath",pic);
                in.putExtra("first_name",username);
            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            //  in.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                // in.putExtra("isImage",isImage);
                 startActivity(in);

            }
            if (requestCode == CAMERA_CODE&& resultCode == RESULT_OK) {
                Log.w("req", "crop");
                //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                try {
                    Log.w("crop","image");
                    cropCapturedImage(fileUri);
                    Log.w("sad","fasf");
                   // space = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    Log.w("dad","cszfsf");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
               if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
                  // Bitmap bit;
                Log.w("rwew","dwaqdaq");
               Bundle extras = data.getExtras();
                //get the cropped bitmap from extras
                Log.w("etx", "data");
                thePic = extras.getParcelable("data");
                   Log.w("hhjjjkj","mokmmoo");
                  // rotateBitmap(thePic);
                   Log.w("pic","hhj");

                   // extra = thePic.toString();
                Log.w("pic", "ad");
                Intent out = new Intent(MainActivity.this, UploadActivity.class);
                Log.w("code", "out");
                Log.w("2", "receive");
                out.putExtra("filePath", thePic);
                   Log.w("sample","dadad");
                  // Log.w("filePath", thePic.toString());
                out.putExtra("timestamp", timeStamp);
                out.putExtra("first_name", username);
                   out.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                   //  out.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(out);


        }


            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();

            }
            else {
               return;
            }

        }
    public void cropCapturedImage(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);

        startActivityForResult(cropIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


    }

    public void cropCapImage(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);

        startActivityForResult(cropIntent, CAMERA_REQUEST_CODE);


    }




    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private  File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), Config.IMAGE_DIRECTORY_NAME);
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create " + Config.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).format(new Date());
        File mediaFile;
        String option;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "" + timeStamp + ".PNG");
            String value = mediaFile.toString();
            Log.v("true",value);
            option = value.substring(0, 4);
        } else {
            return null;
        }
        return mediaFile;

    }

}