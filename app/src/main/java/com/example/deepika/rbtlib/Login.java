package com.example.deepika.rbtlib;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("NewApi")
public class Login extends AppCompatActivity {
    EditText emai, pass;
    Button btn;
    TextView txt;
    Typeface font;
    String email, password;
    String mailid, firstname;
    SessionManager manager;
    SharedPreferences sp;
    SharedPreferences.Editor Ed;
    private static int SPLASH_TIME_OUT = 3000;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sp = getApplicationContext().getSharedPreferences("Login", 0);
        Ed = sp.edit();
    /* manager = new SessionManager(getApplicationContext());
      manager.checkLogin();*/
        // manager.getUserDetails();
        // manager.logoutUser();
      /*  if (manager.isLoggedIn()) {
            finish();
        }*/
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);*/
        /*txt = (TextView)findViewById(R.id.textView);
        font = Typeface.createFromAsset(getAssets(),"fonts/Lato-Regular.ttf");
        txt.setTypeface(font);*/
        btn = (Button) findViewById(R.id.button3);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emai = (EditText) findViewById(R.id.emi);
                email = emai.getText().toString().trim();
                pass = (EditText) findViewById(R.id.pwd);
                password = pass.getText().toString().trim();
                // Ed.putString("email",email );
                //   Ed.putString("email",email);
                //   Ed.putString("first_name",firstname);
                //   Ed.commit();
                //  Ed.putString("Psw",password);
                //  Ed.commit();
                Log.d("eroor", email);
                if (!(email.endsWith("@redblacktree.com"))) {
                    Log.w("ema", "text");
                    emai.setError("Invalid Email");
                    pass.requestFocus();
                } else if (!loginPassword(pass.getText().toString())) {
                    Log.w("pass", "logi");
                    pass.setError("Invalid Password");
                    pass.requestFocus();
                } else {
                    Log.w("aut", "task");
                    AuthenticationTask atask = new AuthenticationTask();
                    Log.w("autttt", "tassss");
                    atask.execute();
                    Log.w("final", "ext");
                   /* Intent in = new Intent(getApplicationContext(), MainActivity.class);
                   // in.putExtra("first_name",name);
                    Log.w("next", "tem");
                    startActivity(in);*/
                    Log.w("false", "ext");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // manager.checkLogin();
    }

    protected boolean loginEmail(String ei) {
        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[_A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(ei);
        return matcher.matches();
    }

    protected boolean loginPassword(String pw) {
        Log.w("true", "pass");
        //   String pwdPattern = "((?=.*\\\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{3,15})";
       /* String pwdPattern = "/^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20}$/";
        Log.w("true","false");
        Pattern pattern = Pattern.compile(pwdPattern);
        Log.w("true","pat");
        Matcher matcher = pattern.matcher(pw);
        Log.w("true","match");
        // System.out.println(matcher);
       *//* String out = String.valueOf(matcher);
        Log.d("mat",out);*//*
        return matcher.matches();*/

        return pw != null && pw.length() > 3;
    }

    class AuthenticationTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... urls) {
            Log.w("in", "false");
            DefaultHttpClient httpClient = new DefaultHttpClient();
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            Log.w("1", "true");
            HttpPost postMethod = new HttpPost("http://192.168.244.115/rbt_auth_service/authenticate");
            Log.w("2", "true");
            String response = null;
            try {
                Log.d("eroo", email);
                JSONObject objt = new JSONObject();
                try {
                    objt.put("email", email);
                    Log.d("name", email);
                    objt.put("password", password);
                    Log.d("word", password);
                } catch (Exception e) {
                }
                String postvalue = objt.toString();
                Log.d("var", postvalue);
                postMethod.setEntity(new StringEntity(postvalue));
                Log.w("3", "hello");
                postMethod.setHeader("Content-Type", "application/json");
                Log.w("hiii", "world");
                response = httpClient.execute(postMethod, responseHandler);
                Log.w("world", "hiii");
                System.out.println("response :" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String res;
            try {
                JSONObject obj = new JSONObject(response);
               /* res = obj.getString("failure");
                if(res.equals("status")){
                    showAlerts(response);
                }*/
                    //   mailid = obj.getString("email");
                    firstname = obj.getString("first_name");
                    //  Ed.putString("email",email );
                    Ed.putString("first_name", firstname);
                    Ed.commit();
                    Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);


            } catch (JSONException e) {
                e.printStackTrace();
            }
          /*  if(response.equals("failure")){
                showAlerts(response);
            }*/

          showAlerts(response);
        }


    }

    private void showAlerts(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid email or password").setTitle("").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // do nothing
               /* Intent move = new Intent(getApplicationContext(),Login.class);
                startActivity(move);*/
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

}






































