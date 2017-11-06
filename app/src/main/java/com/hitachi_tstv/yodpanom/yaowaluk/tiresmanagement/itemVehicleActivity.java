package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class itemVehicleActivity extends AppCompatActivity {

    private String username;
    private String[] lineString, positionString, positionNoString, tireNoString, tireIdString;
    private TextView licenseTextView, tv11TextView, tv21TextView, tv31TextView, tv41TextView, tv52TextView, tv62TextView,
            tv72TextView, tv82TextView, tv93TextView, tv103TextView, tv113TextView, tv123TextView,tv134TextView,
    tv144TextView,  tv154TextView, tv164TextView, tvspTextView;
    private ImageView imageView11, imageView21, imageView31, imageView41, imageView52,
            imageView62, imageView72, imageView82, imageView93, imageView103,
            imageView113, imageView123,  imageView134, imageView144, imageView154, imageView164, spImageView;

    private String licenseString, idString;
    private String urlJSONFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_vehicle);


        //Bind widget
        licenseTextView = (TextView) findViewById(R.id.textView3);

        tv11TextView = (TextView) findViewById(R.id.tv11);
        tv21TextView = (TextView) findViewById(R.id.tv21);
        tv31TextView = (TextView) findViewById(R.id.tv31);
        tv41TextView = (TextView) findViewById(R.id.tv41);
        tv52TextView = (TextView) findViewById(R.id.tv52);
        tv62TextView = (TextView) findViewById(R.id.tv62);
        tv72TextView = (TextView) findViewById(R.id.tv72);
        tv82TextView = (TextView) findViewById(R.id.tv82);
        tv93TextView = (TextView) findViewById(R.id.tv93);
        tv103TextView = (TextView) findViewById(R.id.tv103);
        tv113TextView = (TextView) findViewById(R.id.tv113);
        tv123TextView = (TextView) findViewById(R.id.tv123);
        tv134TextView = (TextView) findViewById(R.id.tv134);
        tv144TextView = (TextView) findViewById(R.id.tv144);
        tv154TextView = (TextView) findViewById(R.id.tv154);
        tv164TextView = (TextView) findViewById(R.id.tv164);
        tvspTextView = (TextView) findViewById(R.id.tvsp);

        imageView11 = (ImageView) findViewById(R.id.iv11);
        imageView21 = (ImageView) findViewById(R.id.iv21);
        imageView31 = (ImageView) findViewById(R.id.iv31);
        imageView41 = (ImageView) findViewById(R.id.iv41);
        imageView52 = (ImageView) findViewById(R.id.iv52);
        imageView62 = (ImageView) findViewById(R.id.iv62);
        imageView72 = (ImageView) findViewById(R.id.iv72);
        imageView82 = (ImageView) findViewById(R.id.iv82);
        imageView93 = (ImageView) findViewById(R.id.iv93);
        imageView103 = (ImageView) findViewById(R.id.iv103);
        imageView113 = (ImageView) findViewById(R.id.iv113);
        imageView123 = (ImageView) findViewById(R.id.iv123);
        imageView134 = (ImageView) findViewById(R.id.iv134);
        imageView144 = (ImageView) findViewById(R.id.iv144);
        imageView154 = (ImageView) findViewById(R.id.iv154);
        imageView164 = (ImageView) findViewById(R.id.iv164);

        spImageView = (ImageView) findViewById(R.id.ivsp);

        //show view
        licenseString = getIntent().getStringExtra("license");
        idString = getIntent().getStringExtra("id");
        username = getIntent().getStringExtra("username");

        ConstantUrl constantUrl = new ConstantUrl();
        urlJSONFormat = constantUrl.getUrlJSONFormatWhell();

        Log.d("Extra", "license -> " + licenseString);

        licenseTextView.setText("License : " + licenseString);

        SyncFormatWheel syncFormatWheel = new SyncFormatWheel(this, urlJSONFormat, idString);
        syncFormatWheel.execute();
        Log.d("End","End execute");


    } //Main Method


    private class SyncFormatWheel extends AsyncTask<Void, Void, String> {

        private Context context;
        private String formatURL, vehIdString;


        public SyncFormatWheel(Context context, String formatURL, String vehIdString) {
            this.context = context;
            this.formatURL = formatURL;
            this.vehIdString = vehIdString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                RequestBody requestBody = new FormEncodingBuilder().add("isSearch", "true").add("veh_id", vehIdString).build();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(formatURL).post(requestBody).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (Exception e) {

                Log.d("SPN1", "e doInBack ==> " + e);
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);

                tireIdString = new String[jsonArray.length()];
                tireNoString = new String[jsonArray.length()];
                lineString = new String[jsonArray.length()];
                positionNoString = new String[jsonArray.length()];
                positionString = new String[jsonArray.length()];

                Log.d("JSON", "JSON ==> " + jsonArray);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    tireNoString[i] = jsonObject.getString("tire_serial");
                    tireIdString[i] = jsonObject.getString("tire_id");
                    lineString[i] = jsonObject.getString("fmwDtl_lineNo");
                    positionString[i] = jsonObject.getString("fmwDtl_positionLine");
                    positionNoString[i] = jsonObject.getString("fmwDtl_positionNo");
                }

                Map<String, String> arrMap = new HashMap<String, String>();
                arrMap.put("11", "");
                arrMap.put("21", "");
                arrMap.put("31", "");
                arrMap.put("41", "");
                arrMap.put("52", "");
                arrMap.put("62", "");
                arrMap.put("72", "");
                arrMap.put("82", "");
                arrMap.put("93", "");
                arrMap.put("103", "");
                arrMap.put("113", "");
                arrMap.put("123", "");
                arrMap.put("134", "");
                arrMap.put("144", "");
                arrMap.put("154", "");
                arrMap.put("164", "");
                arrMap.put("sp0", "");

                final Map<String, String> seriesMap = new HashMap<String, String>();


                List<HashMap> listTemp = new ArrayList<HashMap>();
                HashMap<String, String> dataMap = new HashMap<String, String>();
                int j = 0;
                for (int i = 0; i < positionNoString.length; i++) {

                    if (!(tireIdString[i].equals("null"))) {
                        seriesMap.put(tireIdString[i], tireNoString[i]);

                    }
                    dataMap.put( positionNoString[i]+lineString[i], tireIdString[i]);
                    // listTemp.add(dataMap);
                }


                Set eSet = dataMap.entrySet();
                Iterator it = eSet.iterator();
        while (it.hasNext()) {
                    Map.Entry e = (Map.Entry) it.next();
                    Log.d("Value", "arrKey ==> " + e.getKey());
                    Log.d("Value", "arrValue ==> " + e.getValue());

            if (e.getKey().equals("11")) {
                // arrMap.put("21", e.getValue().toString());
                if (e.getValue().equals("null")) {
                    imageView21.setImageResource(R.drawable.tire_null);
                    imageView21.setTag(R.id.Tire_ID, "");
                } else {
                    imageView21.setImageResource(R.drawable.tire);
                    tv21TextView.setText(seriesMap.get(e.getValue()));
                    imageView21.setTag(R.id.Tire_ID,e.getValue());
                    imageView21.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }

            }
            if (e.getKey().equals("21")) {
                if (e.getValue().equals("null")) {
                    imageView31.setImageResource(R.drawable.tire_null);
                    imageView31.setTag(R.id.Tire_ID, "");
                } else {
                    imageView31.setImageResource(R.drawable.tire);
                    tv31TextView.setText(seriesMap.get(e.getValue()));
                    imageView31.setTag(R.id.Tire_ID,e.getValue());
                    imageView31.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));

                }
            }
            if (e.getKey().equals("32")) {
                if (e.getValue().equals("null")) {
                    imageView52.setImageResource(R.drawable.tire_null);
                    imageView52.setTag(R.id.Tire_ID, "");
                } else {
                    imageView52.setImageResource(R.drawable.tire);
                    tv52TextView.setText(seriesMap.get(e.getValue()));
                    imageView52.setTag(R.id.Tire_ID,e.getValue());
                    imageView52.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("42")) {
                if (e.getValue().equals("null")) {
                    imageView62.setImageResource(R.drawable.tire_null);
                    imageView62.setTag(R.id.Tire_ID, "");
                } else {
                    imageView62.setImageResource(R.drawable.tire);
                    tv62TextView.setText(seriesMap.get(e.getValue()));
                    imageView62.setTag(R.id.Tire_ID,e.getValue());
                    imageView62.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("52")) {
                if (e.getValue().equals("null")) {
                    imageView72.setImageResource(R.drawable.tire_null);
                    imageView72.setTag(R.id.Tire_ID, "");
                } else {
                    imageView72.setImageResource(R.drawable.tire);
                    tv72TextView.setText(seriesMap.get(e.getValue()));
                    imageView72.setTag(R.id.Tire_ID,e.getValue());
                    imageView72.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("62")) {
                if (e.getValue().equals("null")) {
                    imageView82.setImageResource(R.drawable.tire_null);
                    imageView82.setTag(R.id.Tire_ID, "");
                } else {
                    imageView82.setImageResource(R.drawable.tire);
                    tv82TextView.setText(seriesMap.get(e.getValue()));
                    imageView82.setTag(R.id.Tire_ID,e.getValue());
                    imageView82.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("73")) {
                if (e.getValue().equals("null")) {
                    imageView93.setImageResource(R.drawable.tire_null);
                    imageView93.setTag(R.id.Tire_ID, "");
                } else {
                    imageView93.setImageResource(R.drawable.tire);
                    tv93TextView.setText(seriesMap.get(e.getValue()));
                    imageView93.setTag(R.id.Tire_ID,e.getValue());
                    imageView93.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("83")) {
                if (e.getValue().equals("null")) {
                    imageView103.setImageResource(R.drawable.tire_null);
                    imageView103.setTag(R.id.Tire_ID, "");
                } else {
                    imageView103.setImageResource(R.drawable.tire);
                    tv103TextView.setText(seriesMap.get(e.getValue()));
                    imageView103.setTag(R.id.Tire_ID,e.getValue());
                    imageView103.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("93")) {
                if (e.getValue().equals("null")) {
                    imageView113.setImageResource(R.drawable.tire_null);
                    imageView113.setTag(R.id.Tire_ID, "");
                } else {
                    imageView113.setImageResource(R.drawable.tire);
                    tv113TextView.setText(seriesMap.get(e.getValue()));
                    imageView113.setTag(R.id.Tire_ID,e.getValue());
                    imageView113.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("103")) {
                if (e.getValue().equals("null")) {
                    imageView123.setImageResource(R.drawable.tire_null);
                    imageView123.setTag(R.id.Tire_ID, "");
                } else {
                    imageView123.setImageResource(R.drawable.tire);
                    tv123TextView.setText(seriesMap.get(e.getValue()));
                    imageView123.setTag(R.id.Tire_ID,e.getValue());
                    imageView123.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("114")) {
                if (e.getValue().equals("null")) {
                    imageView134.setImageResource(R.drawable.tire_null);
                    imageView134.setTag(R.id.Tire_ID, "");
                } else {
                    imageView134.setImageResource(R.drawable.tire);
                    tv134TextView.setText(seriesMap.get(e.getValue()));
                    imageView134.setTag(R.id.Tire_ID,e.getValue());
                    imageView134.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("124")) {
                if (e.getValue().equals("null")) {
                    imageView144.setImageResource(R.drawable.tire_null);
                    imageView144.setTag(R.id.Tire_ID, "");
                } else {
                    imageView144.setImageResource(R.drawable.tire);
                    tv144TextView.setText(seriesMap.get(e.getValue()));
                    imageView144.setTag(R.id.Tire_ID,e.getValue());
                    imageView144.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("134")) {
                if (e.getValue().equals("null")) {
                    imageView154.setImageResource(R.drawable.tire_null);
                    imageView154.setTag(R.id.Tire_ID, "");
                } else {
                    imageView154.setImageResource(R.drawable.tire);
                    tv154TextView.setText(seriesMap.get(e.getValue()));
                    imageView154.setTag(R.id.Tire_ID,e.getValue());
                    imageView154.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("144")) {
                if (e.getValue().equals("null")) {
                    imageView164.setImageResource(R.drawable.tire_null);
                    imageView164.setTag(R.id.Tire_ID, "");
                } else {
                    imageView164.setImageResource(R.drawable.tire);
                    tv164TextView.setText(seriesMap.get(e.getValue()));
                    imageView164.setTag(R.id.Tire_ID,e.getValue());
                    imageView164.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("SP0")) {
                if (e.getValue().equals("null")) {
                    spImageView.setImageResource(R.drawable.tire_null);
                    spImageView.setTag(R.id.Tire_ID, "");
                } else {
                    spImageView.setImageResource(R.drawable.tire);
                    tvspTextView.setText(seriesMap.get(e.getValue()));
                    spImageView.setTag(R.id.Tire_ID,e.getValue());
                    spImageView.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("111")) {
                String position = Integer.toString(Integer.parseInt(e.getKey().toString().substring(0, 2)) - 10) + e.getKey().toString().substring(2, 3);
                ;
                if (e.getValue().equals("null")) {
                    imageView11.setImageResource(R.drawable.tire_null);
                    imageView11.setTag(R.id.Tire_ID, "");
                } else {
                    imageView11.setImageResource(R.drawable.tire);
                    tv11TextView.setText(seriesMap.get(e.getValue()));
                    imageView11.setTag(R.id.Tire_ID,e.getValue());
                    imageView11.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("121")) {
                if (e.getValue().equals("null")) {
                    imageView21.setImageResource(R.drawable.tire_null);
                    imageView21.setTag(R.id.Tire_ID, "");
                } else {
                    imageView21.setImageResource(R.drawable.tire);
                    tv21TextView.setText(seriesMap.get(e.getValue()));
                    imageView21.setTag(R.id.Tire_ID,e.getValue());
                    imageView21.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("131")) {
                if (e.getValue().equals("null")) {
                    imageView31.setImageResource(R.drawable.tire_null);
                    imageView31.setTag(R.id.Tire_ID, "");
                } else {
                    imageView31.setImageResource(R.drawable.tire);
                    tv31TextView.setText(seriesMap.get(e.getValue()));
                    imageView31.setTag(R.id.Tire_ID,e.getValue());
                    imageView31.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("141") && (!e.getValue().equals("null"))) {
                if (e.getValue().equals("null")) {
                    imageView41.setImageResource(R.drawable.tire_null);
                    imageView41.setTag(R.id.Tire_ID, "");
                } else {
                    imageView41.setImageResource(R.drawable.tire);
                    tv41TextView.setText(seriesMap.get(e.getValue()));
                    imageView41.setTag(R.id.Tire_ID,e.getValue());
                    imageView41.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("152")) {
                if (e.getValue().equals("null")) {
                    imageView52.setImageResource(R.drawable.tire_null);
                    imageView52.setTag(R.id.Tire_ID, "");
                } else {
                    imageView52.setImageResource(R.drawable.tire);
                    tv52TextView.setText(seriesMap.get(e.getValue()));
                    imageView52.setTag(R.id.Tire_ID,e.getValue());
                    imageView52.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("162")) {
                if (e.getValue().equals("null")) {
                    imageView62.setImageResource(R.drawable.tire_null);
                    imageView62.setTag(R.id.Tire_ID, "");
                } else {
                    imageView62.setImageResource(R.drawable.tire);
                    tv62TextView.setText(seriesMap.get(e.getValue()));
                    imageView62.setTag(R.id.Tire_ID,e.getValue());
                    imageView62.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("172")) {
                if (e.getValue().equals("null")) {
                    imageView72.setImageResource(R.drawable.tire_null);
                    imageView72.setTag(R.id.Tire_ID, "");
                } else {
                    imageView72.setImageResource(R.drawable.tire);
                    tv72TextView.setText(seriesMap.get(e.getValue()));
                    imageView72.setTag(R.id.Tire_ID,e.getValue());
                    imageView72.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("182")) {
                if (e.getValue().equals("null")) {
                    imageView82.setImageResource(R.drawable.tire_null);
                    imageView82.setTag(R.id.Tire_ID, "");
                } else {
                    imageView82.setImageResource(R.drawable.tire);
                    tv82TextView.setText(seriesMap.get(e.getValue()));
                    imageView82.setTag(R.id.Tire_ID,e.getValue());
                    imageView82.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("193")) {
                if (e.getValue().equals("null")) {
                    imageView93.setImageResource(R.drawable.tire_null);
                    imageView93.setTag(R.id.Tire_ID, "");
                } else {
                    imageView93.setImageResource(R.drawable.tire);
                    tv93TextView.setText(seriesMap.get(e.getValue()));
                    imageView93.setTag(R.id.Tire_ID,e.getValue());
                    imageView93.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("203")) {
                if (e.getValue().equals("null")) {
                    imageView103.setImageResource(R.drawable.tire_null);
                    imageView103.setTag(R.id.Tire_ID, "");
              } else {
                    imageView103.setImageResource(R.drawable.tire);
                    tv103TextView.setText(seriesMap.get(e.getValue()));
                    imageView103.setTag(R.id.Tire_ID,e.getValue());
                    imageView103.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
               }
            }
            if (e.getKey().equals("213") ) {
                if (e.getValue().equals("null")) {
                    imageView113.setImageResource(R.drawable.tire_null);
                    imageView113.setTag(R.id.Tire_ID, "");
                } else {
                    imageView113.setImageResource(R.drawable.tire);
                    tv113TextView.setText(seriesMap.get(e.getValue()));
                    imageView113.setTag(R.id.Tire_ID,e.getValue());
                    imageView113.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("223") ) {
                if (e.getValue().equals("null")) {
                    imageView123.setImageResource(R.drawable.tire_null);
                    imageView123.setTag(R.id.Tire_ID, "");
                } else {
                    imageView123.setImageResource(R.drawable.tire);
                    tv123TextView.setText(seriesMap.get(e.getValue()));
                    imageView123.setTag(R.id.Tire_ID,e.getValue());
                    imageView123.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("234") ) {
                if (e.getValue().equals("null")) {
                    imageView134.setImageResource(R.drawable.tire_null);
                    imageView134.setTag(R.id.Tire_ID, "");
                } else {
                    imageView134.setImageResource(R.drawable.tire);
                    tv134TextView.setText(seriesMap.get(e.getValue()));
                    imageView134.setTag(R.id.Tire_ID,e.getValue());
                    imageView134.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("244")) {
                if (e.getValue().equals("null")) {
                    imageView144.setImageResource(R.drawable.tire_null);
                    imageView144.setTag(R.id.Tire_ID, "");
                } else {
                    imageView144.setImageResource(R.drawable.tire);
                    tv144TextView.setText(seriesMap.get(e.getValue()));
                    imageView144.setTag(R.id.Tire_ID,e.getValue());
                    imageView144.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("254")) {
                if (e.getValue().equals("null")) {
                    imageView154.setImageResource(R.drawable.tire_null);
                    imageView154.setTag(R.id.Tire_ID, "");
                } else {
                    imageView154.setImageResource(R.drawable.tire);
                    tv154TextView.setText(seriesMap.get(e.getValue()));
                    imageView154.setTag(R.id.Tire_ID,e.getValue());
                    imageView154.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
            if (e.getKey().equals("264")) {
                if (e.getValue().equals("null")) {
                    imageView164.setImageResource(R.drawable.tire_null);
                    imageView164.setTag(R.id.Tire_ID, "");
                } else {
                    imageView164.setImageResource(R.drawable.tire);
                    tv164TextView.setText(seriesMap.get(e.getValue()));
                    imageView164.setTag(R.id.Tire_ID,e.getValue());
                    imageView164.setTag(R.id.Tire_Serial,seriesMap.get(e.getValue()));
                }
            }
        }
                imageView11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView21.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView31.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }



                    }
                });

                imageView41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView52.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView62.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView72.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView82.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView93.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView103.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView113.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView123.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView134.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView144.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView154.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });

                imageView164.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("Click","TAG ID ==> " + v.getTag(R.id.Tire_ID));
                        Log.d("Click", "TAG Serial ==> " + v.getTag(R.id.Tire_Serial));

                        if (v.getTag(R.id.Tire_ID).equals(""))
                        {
                            Log.d("Tag", "Tire ID is Null");
                        }else {
                            Intent intent = new Intent(itemVehicleActivity.this, AddCheckListActivity.class);
                            intent.putExtra("ID", (String) v.getTag(R.id.Tire_ID));
                            intent.putExtra("Serial", (String) v.getTag(R.id.Tire_Serial));
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                    }
                });




            } catch (Exception e) {
                Log.d("ItemVeh", "e doInBack ==> " + e);
            }


        }
    }
//
//    public void clickWheelImage(View view) {
//        Log.d("TAG", "In Click Wheel");
//        if(!(view.getTag(1).equals(""))){
//           Intent intent = new Intent(itemVehicleActivity.this,AddCheckListActivity.class);
//            intent.putExtra("tireId", view.getTag(1).toString());
//            intent.putExtra("series", view.getTag(2).toString());
//            startActivity(intent);
//
//        }
//
//    }

}//Main Class
