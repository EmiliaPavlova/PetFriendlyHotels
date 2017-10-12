package com.example.emily.petfriendlyhotels;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.emily.petfriendlyhotels.databinding.ActivityHotelDetailBinding;

public class HotelDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        ActivityHotelDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_hotel_detail);
        Hotel hotel = getIntent().getParcelableExtra("Hotel");
        binding.setHotel(hotel);
    }
}
