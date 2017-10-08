package com.example.emily.petfriendlyhotels;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class HotelListActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvHotels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading);
        rvHotels = (RecyclerView) findViewById(R.id.rv_hotels);
        LinearLayoutManager hotelsLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        rvHotels.setLayoutManager(hotelsLayoutManager);

        try {
            URL hotelUrl = ApiUtil.buildUrl("hotelsofia");
            new HotelsQueryTask().execute(hotelUrl);
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }
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
            }

            ArrayList<Hotel> hotels = ApiUtil.getHotelsFromJson(result);
            String resultString = "";

            HotelsAdapter adapter = new HotelsAdapter(hotels);
            rvHotels.setAdapter(adapter);
        }

        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
