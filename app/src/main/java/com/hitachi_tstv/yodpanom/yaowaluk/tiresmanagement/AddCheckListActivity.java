package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement.R.id.spinner2;

public class AddCheckListActivity extends AppCompatActivity {

    private TextView tireIdTextView;
    private EditText odoEditText,deepEditText,presureEditText ;
    private String tireIdString,serialString,odoString,deepString,presureString,commentString,
            dateString,url,username,urlReason,reasonIdString,reasonNameString,reasonId2String,reasonName2String;
    private DatePicker checkDatePicker;
    private Spinner reason1Spinner, reason2Spinner;
    private Button btn_save;

    static final int  DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        setContentView(R.layout.activity_add_check_list);

        ConstantUrl constantUrl = new ConstantUrl();

        tireIdTextView = (TextView) findViewById(R.id.textView5);
        odoEditText = (EditText) findViewById(R.id.editText3);
        deepEditText = (EditText) findViewById(R.id.editText5);
        presureEditText = (EditText) findViewById(R.id.editText4);
      //  commentEditText = (EditText) findViewById(R.id.editText7);
        checkDatePicker = (DatePicker) findViewById(R.id.datePicker);
        reason1Spinner = (Spinner) findViewById(R.id.spinner);
        reason2Spinner = (Spinner) findViewById(R.id.spinner2);
        btn_save = (Button) findViewById(R.id.saveAddCheckList);

        setCurrentDateOnView();

        tireIdString = getIntent().getStringExtra("ID");
        serialString = getIntent().getStringExtra("Serial");
        username = getIntent().getStringExtra("username");

        tireIdTextView.setText("Tire Series :  " + serialString);


        url = constantUrl.getUrlAddCheckList();
        urlReason = constantUrl.getUrlJSONReason();

        SyncReason syncReason = new SyncReason(reason1Spinner, this, urlReason);
        syncReason.execute();



    }//main method


    private class SyncReason extends AsyncTask<Void, Void, String> {

    private Spinner mySpinner;
    private Context context;
    private String myURL;
    private String[] reasonStrings,idStrings;

        public SyncReason(Spinner mySpinner, Context context, String myURL) {
            this.mySpinner = mySpinner;
            this.context = context;
            this.myURL = myURL;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
//                OkHttpClient okHttpClient = new OkHttpClient();
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
//                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {

                Log.d("Spinner", "e doInBack ==> " + e.toString());
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Spinner", "e onPostExecute---->" +s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                reasonStrings = new String[jsonArray.length()+1];
                idStrings = new String[jsonArray.length()+1];

                for(int i = 0;i < jsonArray.length();i++){
                    if(i==0){
                        reasonStrings[i] = "--select reason--";
                        idStrings[i] = "";
                    }else {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        reasonStrings[i] = jsonObject.getString("res_name");
                        idStrings[i] = jsonObject.getString("res_id");
                    }
                }

                ArrayAdapter arrayAdapter = new ArrayAdapter(AddCheckListActivity.this, R.layout.support_simple_spinner_dropdown_item, reasonStrings);
                reason1Spinner.setAdapter(arrayAdapter);
                reason2Spinner.setAdapter(arrayAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Spinner", "e onPostExecute+++" + e.toString());
            }
        }
    }



    public void setCurrentDateOnView() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into datepicker
        checkDatePicker.init(year, month, day, null);
    }



    public void clickAddCheckList(View view) {
        odoString = odoEditText.getText().toString().trim();
        deepString = deepEditText.getText().toString().trim();
        presureString = presureEditText.getText().toString().trim();
        //commentString = commentEditText.getText().toString().trim();
        int month = checkDatePicker.getMonth() + 1;
        dateString = checkDatePicker.getYear() + "-" + month + "-" + checkDatePicker.getDayOfMonth();

        reasonNameString = reason1Spinner.getSelectedItem().toString();
        reasonName2String = reason2Spinner.getSelectedItem().toString();

        if(reasonNameString.equals("--select reason--")){

        }else{
            if(reasonNameString.equals(reasonName2String)||reasonName2String.equals(reasonNameString)){
                reasonNameString = reasonNameString.toString();
                reasonName2String = "";
            }else{
                reasonNameString = reasonNameString.toString();
                reasonName2String = reasonName2String.toString();
            }
         }
        if(reasonName2String.equals("--select reason--")){

        }else {
            if(reasonNameString.equals(reasonName2String)||reasonName2String.equals(reasonNameString)){
                reasonNameString = reasonNameString.toString();
                reasonName2String = "";
            }else{
                reasonNameString = reasonNameString.toString();
                reasonName2String = reasonName2String.toString();
            }
        }

        //reasonIdString = reason1Spinner.get
        Log.d("Date", dateString);
        Log.d("Spinner", reasonNameString  +" :: Spinner 2 ::" + reasonName2String);


        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("isAdd", "true")
                .add("odo", odoString)
                .add("deepTread", deepString)
                .add("presure", presureString)
                .add("checkDate",dateString)
                .add("tire_id",tireIdString)
                .add("username",username)
                .add("reason1",reasonNameString)
                .add("reason2",reasonName2String).build();
        Request.Builder builder = new Request.Builder();
        Request request = builder.url(url).post(requestBody).build();

        Call call = okHttpClient.newCall(request);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("Call", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Tag",response.body().string());
                Log.d("Call", "Success");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.setTitle(R.string.alert);
                        dialog.setCancelable(true);
                        dialog.setIcon(R.drawable.warning);
                        dialog.setMessage(R.string.dialog_save);

                        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(AddCheckListActivity.this, "Add Checklist Successful!!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        dialog.show();
                    }


                });

//                finish();
            }

//            @Override
//            public void onFailure(Request request, IOException e) {
//                Log.d("Call", "Failure");
//            }
//
//
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                Log.d("Tag",response.body().string());
//                Log.d("Call", "Success");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        dialog.setTitle(R.string.alert);
//                        dialog.setCancelable(true);
//                        dialog.setIcon(R.drawable.warning);
//                        dialog.setMessage(R.string.dialog_save);
//
//                        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(AddCheckListActivity.this, "Add Checklist Successful!!", Toast.LENGTH_SHORT).show();
//                            finish();
//                            }
//                        });
//
//                        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.cancel();
//                            }
//                        });
//
//                        dialog.show();
//                    }
//
//
//                });
//
////                finish();
//            }
        });


    }

}//main class
