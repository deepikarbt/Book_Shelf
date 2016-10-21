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
import android.graphics.Matrix;
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

public class UploadActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private Bitmap filePath=null;
    private TextView txtPercentage, text;
    private ImageView imgPreview, img;
    private Button btnUpload,btnrotate;
    long totalSize = 0;
    public static final String STATUS="status";
    String timestamp;
    String firstname;
    ByteArrayBody bab;
    TextView txtout;
    SessionManager session;
    int statusCode;
    Typeface font;
    //  String responseString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
       /* session= new SessionManager(getApplicationContext());
        session.getUserDetails();
        HashMap<String, String> token = session.getUserDetails();
        msg = token.get(SessionManager.NAME);*/
        //  token.get(NAME);
      /* Bundle bundle = getIntent().getExtras();
        msg = bundle.getString("first_name");*/
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(UploadActivity.this,MainActivity.class);
                startActivity(in);
                // back button pressed
            }
        });*/
        txtPercentage = (TextView) findViewById(R.id.txtPercentage);
        /*txtout = (TextView)findViewById(R.id.textView6);
        font = Typeface.createFromAsset(getAssets(),"fonts/lato.ttf");
        txtout.setTypeface(font);*/
        btnUpload = (Button) findViewById(R.id.btnUpload);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgPreview = (ImageView) findViewById(R.id.imgPreview);
        btnrotate = (Button)findViewById(R.id.btn);
        Intent i = getIntent();
        Log.w("intent","gte");
     filePath = (Bitmap) i.getParcelableExtra("filePath");
        Log.w("gtdty","uhuchh");
     imgPreview.setImageBitmap((filePath));
     //   imgPreview.setRotation((float) 45.0);

        Log.w("hjhj","uii");

        //  filePath = i.getStringExtra("filePath");
       // Log.w("file",filePath);
        System.out.println(filePath);
        timestamp = i.getStringExtra("timestamp");
        firstname = i.getStringExtra("first_name");
        System.out.println(firstname);
//        Log.w("name", firstname);
        boolean isImage = i.getBooleanExtra("isImage", true);
        if (filePath != null) {
            previewMedia(isImage);
            Log.w("btn","rotaet");
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

        btnUpload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new UploadFileToServer().execute();
                Log.w("true", "come");
                Log.w("right", "crct");
            }
        });
    }

  /*  public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mean,menu);
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
*/

    public static Bitmap rotateBitmap(Bitmap source)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }




    /*private Bitmap previewMedia(boolean isImage) {
        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            try {
                InputStream in = new FileInputStream(gh);
                Log.w("in", "out");
                BitmapFactory.decodeStream(in, null, options);
                Log.w("out", "in");
                in.close();
                in = new FileInputStream(gh);
                Log.w("sstre", "fri");
                options.inSampleSize = Math.max(options.outWidth / 320, options.outHeight / 240);
                Log.w("dttt", "uuhh");
                Matrix mat = new Matrix();
                bitmap = BitmapFactory.decodeStream(in, null, options);
                mat.postRotate(90);
                Bitmap rotate = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
                //  mediaStorageDir = new File(String.valueOf(bitmap), Config.IMAGE_DIRECTORY_NAME);
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 75, bos);
                    byte[] data = bos.toByteArray();
                    bab = new ByteArrayBody(data,gh);
                } catch (Exception ex) {
                }
                Log.w("garty", "uuii");
                imgPreview.setImageBitmap((rotate));
                Log.w("huyhu", "juiou");
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } else {
            imgPreview.setVisibility(View.GONE);
        }
        return bitmap;
    }*/


    private void previewMedia(boolean isImage) {
     // String encodedImage;

        if (isImage) {
            imgPreview.setVisibility(View.VISIBLE);
            BitmapFactory.Options options = new BitmapFactory.Options();
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
        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                int pixelColor = src.getPixel(x, y);
                int pixelAlpha = Color.alpha(pixelColor);
                int pixelRed = Color.red(pixelColor);
                int pixelGreen = Color.green(pixelColor);
                int pixelBlue = Color.blue(pixelColor);
                int pixelBW = (pixelRed + pixelGreen + pixelBlue) / 3;
                int newPixel = Color.argb(pixelAlpha, pixelBW, pixelBW, pixelBW);
                dest.setPixel(x, y, newPixel);
            }
        }
        return dest;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(progress[0]);
            txtPercentage.setText(String.valueOf(progress[0]) + "% sending");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            // abt = edit.getText().toString().trim();
            String responseString = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Config.FILE_UPLOAD_URL);
            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new ProgressListener() {
                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });
                //   File sourceFile = new File(filePath);
                // entity.addPart("myfile", new FileBody(sourceFile));
               /* String head = null;
                try {
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("title", abt);
                        Log.w("tit",abt);
                    }
                    catch (Exception e) {
                    }
                    head = obj.toString();
                    Log.w("hae",head);

                } catch (Exception e) {
                }*/
                // Log.d("edit", abt);

                entity.addPart("myfile", bab);
                Log.d("image", String.valueOf(bab));
                entity.addPart("borrower", new StringBody(firstname));
                Log.w("user", firstname);
                entity.addPart("checkouttime", new StringBody(timestamp));
                Log.w("time", timestamp);
                /*entity.addPart("title", new StringBody(head));
                Log.w("title",head);*/
                totalSize = entity.getContentLength();
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();
                statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    Log.w("state", "code");
                    responseString = EntityUtils.toString(r_entity);
                    //showAlerts(responseString);
                } else if (statusCode == 400) {
                    responseString = EntityUtils.toString(r_entity);
                } else if (statusCode == 403) {
                    responseString = EntityUtils.toString(r_entity);
                } else if (statusCode == 404) {
                    responseString = EntityUtils.toString(r_entity);
                } else if (statusCode == 500) {
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
        public void onPostExecute(String result) {
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
                Log.w("dcode","stat");
                showAlerts(result);
            }
            else {
                Log.w("xxxx","xxcs");
                showAlert(result);
            }
        }
    }

    private void showAlerts(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Book Borrowed Successfully").setTitle("").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
                Intent move = new Intent(UploadActivity.this,MainActivity.class);
                startActivity(move);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle("").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
