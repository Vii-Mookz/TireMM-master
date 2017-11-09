package com.hitachi_tstv.mist.it.tiresmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class CheckList2Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView mListView ;
    private SearchView mSearchView;
    private final String[] mString = Cheeses.sCheeseStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_check_list2);

        mSearchView = (SearchView) findViewById(R.id.searchView);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mString));
        mListView.setTextFilterEnabled(true);
        setupSearchView();


    }// Main Method

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("search here");
    }

    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText.toString());
        }
          return true;
    }

    public boolean onQueryTextSubmit(String query) {
        return false;
    }

}// Main Class
