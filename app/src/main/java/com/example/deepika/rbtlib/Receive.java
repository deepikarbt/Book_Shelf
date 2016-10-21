package com.example.deepika.rbtlib;

/**
 * Created by deepika on 29-Aug-16.
 */
import com.example.deepika.rbtlib.AndroidMultiPartEntity.ProgressListener;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Receive extends AppCompatActivity {
    // LogCat tag
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar progressBar;
    private Bitmap filePath = null;
    private TextView txtPercentage;
    private ImageView imgPreview;
    String firstname;
    private Button btnrecv;
    long totalSize = 0;
    public static final String STATUS = "status" ;
    ByteArrayBody bab;
    Button btnrotate;
    int statusCode;
    TextView txtin;
    Typeface font;
    String responseString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolin);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(Receive.this,MainActivity.class);
                startActivity(in);
                // back button pressed
            }
        });*/
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
/*        txtin = (TextView)findViewById(R.id.textcheck);
        font = Typeface.createFromAsset(getAssets(),"fonts/lato.ttf");
        txtin.setTypeface(font);*/
        btnrecv = (Button) findViewById(R.id.btnReceive);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btnrotate = (Button)findViewById(R.id.btnrot);

        Intent i = getIntent();
        //filePath = i.getStringExtra("filePath");
        filePath = (Bitmap) i.getParcelableExtra("filePath");
        imgPreview.setImageBitmap(filePath);

        firstname = i.getStringExtra("first_name");
     //   Log.w("name",firstname);


        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {

            previewMedia(isImage);
            btnrotate.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.w("onclick","view");
                    //  bitscale=rotateBitmap(filePath);
                    imgPreview.setRotation(imgPreview.getRotation() + 90);
                }
            });
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }
        btnrecv.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new UploadFileToServer().execute();
            }
        });

    }

   /* public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id=item.getItemId();
        if(id==R.id.out)
        {
            Intent in= new Intent(this,Login.class);
            startActivity(in);
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
*/


    /**
     * Displaying captured image/video on the screen
     * */
    /*private Bitmap previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
       *//* if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            //  vidPreview.setVisibility(View.GONE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            try {
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 75, out);
                byte[] dat = out.toByteArray();
                bab = new ByteArrayBody(dat, filePath);
                //   int b= (int)(bitmap.getHeight()*512.0/bitmap.getWidth());
                //  Bitmap bi = Bitmap.createScaledBitmap(bitmap,512,b,true);
                // imgPreview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imgPreview.setImageBitmap(bitmap);

            } catch (Exception e) {
            }
        } else {
            imgPreview.setVisibility(View.GONE);
            //  vidPreview.setVisibility(View.VISIBLE);
            // vidPreview.setVideoPath(filePath);
            // start playing
            // vidPreview.start();
        }
        return bitmap;*//*
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            try {
                InputStream in = new FileInputStream(filePath);
                Log.w("in","out");
                BitmapFactory.decodeStream(in,null,options);
                Log.w("out","in");
                in.close();
                in=new FileInputStream(filePath);
                Log.w("sstre","fri");
                options.inSampleSize = Math.max(options.outWidth/320,options.outHeight/240);
                Log.w("dttt","uuhh");
                bitmap = BitmapFactory.decodeStream(in,null,options);
                //  mediaStorageDir = new File(String.valueOf(bitmap), Config.IMAGE_DIRECTORY_NAME);
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos);
                    byte[] data = bos.toByteArray();
                    bab = new ByteArrayBody(data,filePath);
                }
                catch (Exception ex){}
                Log.w("garty","uuii");
                imgPreview.setImageBitmap((bitmap));
                Log.w("huyhu","juiou");
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //  options.inSampleSize =8;
            //bitmap = BitmapFactory.decodeFile(filePath, options);
            Log.w("bit","map");
            //  int a = (int)(bitmap.getHeight()*(512.0/bitmap.getWidth()));
            //Bitmap bit = Bitmap.createScaledBitmap(bitmap,512,a,true);
            Log.w("map","bit");
            //   ByteArrayOutputStream out = new ByteArrayOutputStream();
            // bitmap.compress(Bitmap.CompressFormat.PNG,50,out);
            //byte[] ba = out.toByteArray();
            //String ba1 = Base64.encodeToString(ba,Base64.NO_WRAP);
            //  int a = (int)(bitmap.getHeight()*(512.0/bitmap.getWidth()));
            //Bitmap scale = Bitmap.createScaledBitmap(bitmap,512,a,true);
            Log.w("scale","bitmsp");
            // bitscale = Bitmap.createScaledBitmap(bitmap,320,240,true);
            Log.w("wid","hgt");
            //  imgPreview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // imgPreview.setImageBitmap(Processing(bitscale));
            //    imgPreview.setImageBitmap(Processing(Bitmap.createScaledBitmap(bitmap,320,240,false)));
            //   img = imgPreview;
            Log.w("set","img");
        } else {
            imgPreview.setVisibility(View.GONE);
        }
        return  bitmap;
    }*/


    private void previewMedia(boolean isImage) {
        // String encodedImage;

        // Checking whether captured media is image or video
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            //     Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
            //  Bitmap bitmap = BitmapFactory.;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            filePath.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            //     encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            bab = new ByteArrayBody(imageBytes,filePath.toString());
            imgPreview.setImageBitmap(filePath);
        } else {
            imgPreview.setVisibility(View.GONE);
            // start playing
        }
    }


    private Bitmap Processing(Bitmap src) {
        Bitmap dest = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        for(int x = 0; x < src.getWidth(); x++){
            for(int y = 0; y < src.getHeight(); y++){
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);
                int pixelBW = (pixelRed + pixelGreen + pixelBlue)/3;
                int newPixel = Color.argb(pixelAlpha, pixelBW, pixelBW, pixelBW);
                dest.setPixel(x, y, newPixel);
            }
        }

        return dest;
    }


    /**
     * Uploading the file to server
     * */
    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
            progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
            progressBar.setProgress(progress[0]);

            // updating percentage value
            txtPercentage.setText(String.valueOf(progress[0]) + "% sending");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
           // String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Return.FILE_UPLOAD_URL);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                entity.addPart("myfile", bab);
                entity.addPart("borrower", new StringBody(firstname));
                Log.w("name", firstname);
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: " + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            //  String out = null;
            String put = STATUS;
            // int status = Integer.parseInt(null);
            Log.e(TAG, result);
            super.onPostExecute(result);
            Log.w("exe", "posttt");

/*
               try {
                JSONObject obj = new JSONObject(result);
                String out = obj.getString(STATUS);
                showAlerts(out);
           } catch (JSONException e) {
                e.printStackTrace();
            }
*/
/*            try {
                JSONObject obj = new JSONObject(responseString);
                out = obj.getString(STATUS);
                if (put==out) {
                    Log.w("true", "status");
                    showAlerts(String.valueOf(out));
                } else    {
                    Log.w("status", "coide");
                    showAlert();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            // showAlerts(result);
            if (statusCode == 200) {
                Log.w("dcode", "stat");
                showAlerts(result);
            } else {
                Log.w("xxxx", "xxcs");
               showAlert(result);
              //  responseString = "Error occurred! Http Status Code: " + statusCode;

            }
        }
    }

        private void showAlerts(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Book Returned Successfully").setTitle("").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // do nothing
                    Intent mov = new Intent(Receive.this,MainActivity.class);
                    startActivity(mov);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();

        }


        private void showAlert(String message) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(message).setTitle("").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                        Intent out = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(out);
                   // Toast.makeText(getApplicationContext(), "You have been logged out successfully", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

}