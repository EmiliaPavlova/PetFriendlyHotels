package com.example.emily.petfriendlyhotels;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HotelsAdapter extends RecyclerView.Adapter<HotelsAdapter.HotelViewHolder> {

    ArrayList<Hotel> hotels;
    public HotelsAdapter(ArrayList<Hotel> hotels) {
        this.hotels = hotels;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.hotel_list_item, parent, false);
        return new HotelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel hotel = hotels.get(position);
        holder.bind(hotel);
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvAddress;

        public HotelViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            itemView.setOnClickListener(this);
        }

        public void bind (Hotel hotel) {
            tvName.setText(hotel.name);
            tvAddress.setText(hotel.address);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Hotel selectedHotel = hotels.get(position);
            Intent intent = new Intent(view.getContext(), HotelDetail.class);
            intent.putExtra("Hotel", selectedHotel);
            view.getContext().startActivity(intent);
        }
    }
}
