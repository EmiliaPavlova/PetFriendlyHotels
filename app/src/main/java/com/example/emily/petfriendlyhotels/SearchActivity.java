package com.example.emily.petfriendlyhotels;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etAddress = (EditText) findViewById(R.id.etAddress);
        final EditText etDistance = (EditText) findViewById(R.id.etDistance);
        final Button button = (Button) findViewById(R.id.btnSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                String address = etAddress.getText().toString().trim();
                String distance = etDistance.getText().toString().trim();
                if (name.isEmpty() && address.isEmpty() && distance.isEmpty()) {
                    String message = getString(R.string.no_search_data);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
                else {
                    URL queryURL = ApiUtil.buildUrl(name, address, distance);
                    Context context = getApplicationContext();
                    //sharedPreferences
                    int position = SpUtil.getPreferenceInt(context, SpUtil.POSITION);
                    if (position == 0 || position == 3) {
                        position=1;
                    }
                    else {
                        position++;
                    }
                    String key = SpUtil.QUERY + String.valueOf(position);
                    String value = name + "," +  address +"," + distance;
                    SpUtil.setPreferenceString(context, key, value);
                    SpUtil.setPreferenceInt(context, SpUtil.POSITION, position);
                    Intent intent = new Intent(getApplicationContext(), HotelListActivity.class);
                    intent.putExtra("Query", queryURL);
                    startActivity(intent);
                }
            }
        });
    }
}
