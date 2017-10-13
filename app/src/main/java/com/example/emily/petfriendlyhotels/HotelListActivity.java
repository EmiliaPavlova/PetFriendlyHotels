package com.example.emily.petfriendlyhotels;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class HotelListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        rvHotels = (RecyclerView) findViewById(R.id.rv_hotels);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);
        Intent intent = getIntent();
        String query = intent.getStringExtra("Query");
        URL hotelUrl;

        try {
            if (query == null || query.isEmpty()) {
                hotelUrl = ApiUtil.buildUrl("lodging");
            }
            else {
                hotelUrl = new URL(query);
            }
            new HotelsQueryTask().execute(hotelUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        LinearLayoutManager hotelsLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvHotels.setLayoutManager(hotelsLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.hotel_list_menu, menu);
        final MenuItem searchItem=menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);

        ArrayList<String> recentList = SpUtil.getQueryList(getApplicationContext());
        int itemNumber = recentList.size();
        MenuItem recentMenu;
        for (int i = 0; i < itemNumber; ++i) {
            recentMenu = menu.add(Menu.NONE, i, Menu.NONE, recentList.get(i));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_advanced_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;
            default:
                int position = item.getItemId() + 1;
                String preferenceName = SpUtil.QUERY + String.valueOf(position);
                String query = SpUtil.getPreferenceString(getApplicationContext(), preferenceName);
                String[] prefParams = query.split("\\,");
                String[] queryParams = new String[3];
                for (int i = 0; i < prefParams.length; ++i) {
                    queryParams[i] = prefParams[i];
                }
                URL hotelUrl = ApiUtil.buildUrl(
                        (queryParams[0] == null) ? "" : queryParams[0],
                        (queryParams[1] == null) ? "" : queryParams[1],
                        (queryParams[2] == null) ? "" : queryParams[2]);

                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            URL hotelUrl = ApiUtil.buildUrl(query);
            new HotelsQueryTask().execute(hotelUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    public class HotelsQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL searchURL = params[0];
            String result = null;

            try {
                result = ApiUtil.getJson(searchURL);
            }
            catch (IOException e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView tvError = (TextView) findViewById(R.id.tv_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvHotels.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }
            else {
                rvHotels.setVisibility(View.VISIBLE);
                tvError.setVisibility(View.INVISIBLE);

                ArrayList<Hotel> hotels = ApiUtil.getHotelsFromJson(result);
                String resultString = "";

                HotelsAdapter adapter = new HotelsAdapter(hotels);
                rvHotels.setAdapter(adapter);
            }

        }

        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
