package com.hitachi_tstv.yodpanom.yaowaluk.tiresmanagement;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CheckListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private ListView myListView;
    private SearchView mySearchView;
    private String urlJSON, username;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle(R.string.alert);
                dialog.setCancelable(true);
                dialog.setIcon(R.drawable.warning);
                dialog.setMessage(R.string.alert_logout);

                dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        ComponentName name = intent.getComponent();
                        Intent backToMainIntent = IntentCompat.makeRestartActivityTask(name);
                        startActivity(backToMainIntent);
                    }
                });
                dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                dialog.show();
                break;

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        ConstantUrl constantUrl = new ConstantUrl();
        urlJSON = constantUrl.getUrlJSONLicense();


        mySearchView = (SearchView) findViewById(R.id.searchView2);
        myListView = (ListView) findViewById(R.id.listView2);

        username = new String();
        username = getIntent().getStringExtra("username");

        SyncVehicle syncVehicle = new SyncVehicle(this, urlJSON, myListView);
        syncVehicle.execute();


    }//main method



    private class SyncVehicle extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private String myURL;
        private ListView listView;
        private String[] licenseStrings, idStrings;

        public SyncVehicle(Context context, String myURL, ListView listView) {
            this.context = context;
            this.myURL = myURL;
            this.listView = listView;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(myURL).build();
                Response response = okHttpClient.newCall(request).execute();

                return response.body().string();
            } catch (IOException e) {
                Log.d("SPN1", "e doInBack ==> " + e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                Log.d("JSON", "JSON ==> " + jsonArray);


                licenseStrings = new String[jsonArray.length()];
                idStrings = new String[jsonArray.length()];
                final Map<String, String> licenceMap = new HashMap<String, String>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    licenseStrings[i] = jsonObject.getString("veh_license");
                    idStrings[i] = jsonObject.getString("veh_id");
                    licenceMap.put(licenseStrings[i], idStrings[i]);

                }


                final ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, licenseStrings);
                listView.setTextFilterEnabled(true);
                //   filter = arrayAdapter.getFilter();
                listView.setAdapter(arrayAdapter);

                setUpSearchView();

                // Onclick on by item

                myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String[] arrayStrings = getStringArray(arrayAdapter);
                        Intent intent = new Intent(CheckListActivity.this, itemVehicleActivity.class);
//                        intent.putExtra("license", licenseStrings[position]);
                        intent.putExtra("license", arrayStrings[position]);
                        intent.putExtra("id", licenceMap.get(arrayStrings[position]));
                        intent.putExtra("username", username);
                        Log.d("ID", "Map ==> " + licenceMap.get(arrayStrings[position]));
                        startActivity(intent);
                    }


                });


            } catch (JSONException e) {
                Log.d("SPN2", "e doInBack ==> " + e);
            }
        }
    }

    public static String[] getStringArray(ArrayAdapter adapter) {
        String[] a = new String[adapter.getCount()];

        for (int i = 0; i < a.length; i++)
            a[i] = adapter.getItem(i).toString();

        return a;
    }

  /*  public void addItemOnSpinner() {
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);




    }*/


    private void setUpSearchView() {
        mySearchView.setIconifiedByDefault(false);
        mySearchView.setOnQueryTextListener(this);
        mySearchView.setSubmitButtonEnabled(true);

        mySearchView.setQueryHint("Search Vehicle");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            myListView.clearTextFilter();
        } else {
            myListView.setFilterText(newText.toString());

            //filter.filter(newText);
        }
        return true;
    }


}//Main class
