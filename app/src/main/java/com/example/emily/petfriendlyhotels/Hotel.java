package com.example.emily.petfriendlyhotels;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Hotel implements Parcelable {
    public String id;
    public String name;
    public String address;
    public String imageUrl;
    public String rating;
    public String[] location;

    public Hotel(String id, String name, String address, String imageUrl, String rating, String[] location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageUrl = imageUrl;
        this.rating = rating;
        this.location = location;
    }

    protected Hotel(Parcel in) {
        id = in.readString();
        name = in.readString();
        address = in.readString();
        imageUrl = in.readString();
        rating = in.readString();
        location = in.createStringArray();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(imageUrl);
        dest.writeString(rating);
        dest.writeStringArray(location);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.hotel_icon)
                .into(view);
    }
}
