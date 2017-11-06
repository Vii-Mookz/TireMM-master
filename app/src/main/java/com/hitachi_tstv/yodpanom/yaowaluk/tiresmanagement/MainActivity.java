package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private EditText username, password;
    private String usernameString, passwordString,url;
    Button signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.edit_username);
        password = (EditText) findViewById(R.id.edit_password);
        signInButton = (Button) findViewById(R.id.button);
//
//
//        SpannableString s = new SpannableString("My Title");
//        s.setSpan(new TypefaceSpan(this, "MyTypeface.otf"), 0, s.length(),
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        // Update the action bar title with the TypefaceSpan instance

//        ActionBar actionBar=getSupportActionBar();
//        actionBar.setTitle(R.string.app_name);
//actionBar.

        ConstantUrl constantUrl = new ConstantUrl();
        url = constantUrl.getUrlJSONuser();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameString = username.getText().toString();
                passwordString = password.getText().toString();

                Log.d("Tag", "User ==> " + usernameString + " :: Pass ==> " + passwordString);
                SyncUser syncUser = new SyncUser(MainActivity.this, url, usernameString, passwordString);
                syncUser.execute();
            }
        });


    }// Main Method

    private class SyncUser extends AsyncTask<Void, Void, String> {
        //Explicit

        private Context context;
        private String myURL,username,password;
        private boolean aBoolean;

        public SyncUser(Context context, String myURL, String username, String password) {
            this.context = context;
            this.myURL = myURL;
            this.username = username;
            this.password = password;
        }


        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormBody.Builder()
                        .add("username", username)
                        .add("password", password).build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(url).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();

            } catch (IOException e) {
                Log.d("Tag", "e doInBackground ==> " + e);
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            Log.d("Tag", "JSON ==> " + s);

            try {
                JSONObject obj = new JSONObject(s);
                aBoolean = obj.getBoolean("flag");

//                for (int i = 0;i < jsonArray.length();i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    aBoolean = new Boolean(jsonObject.getString("flag"));
//                }
                Log.d("Tag", "Boolean ==> " + aBoolean);
                if (aBoolean) {
                    Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
                    intent.putExtra("username", username);
                    Toast.makeText(context, R.string.login_success, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(context, R.string.login_fail, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                Log.d("Tag", "e onPost ==> " + e);
                e.printStackTrace();
            }


        }
    }

    public void clickSignIn(View view) {

//        Intent intent = new Intent(MainActivity.this, CheckListActivity.class);
//        startActivity(intent);
//        finish();
    }

}//Main Class
