package com.example.emily.petfriendlyhotels;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtil {
    private ApiUtil() {}

    public static final String BASE_API_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";
    public static final String QUERY_PARAMETER_KEY = "query";
    public static final String KEY = "key";
    public static final String API_KEY = "AIzaSyDJyoiqpcR-Y4uZ0m3_AslxA73K3XuE40k";
//    public static final String TYPE = "type=lodging";
    public static final String NAME = "name=";
    public static final String ADDRESS = "keyword=";
    public static final String DISTANCE = "distance=";


    public static URL buildUrl(String query) {
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
//                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrl(String name, String address, String distance) {
        URL url = null;
        StringBuilder sb = new StringBuilder();
        if (!name.isEmpty()) sb.append(NAME + name + '&');
        if (!address.isEmpty()) sb.append(ADDRESS + address + '&');
        if (!distance.isEmpty()) sb.append(DISTANCE + distance + '&');
//        sb.append(TYPE + '&');
        sb.setLength(sb.length() - 1);
        String query = sb.toString();
        Uri uri = Uri.parse(BASE_API_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAMETER_KEY, query)
                .appendQueryParameter(KEY, API_KEY)
                .build();
        try {
            url = new URL(uri.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(15000 /* milliseconds */);
        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setDoInput(true);

        try {
            InputStream stream = connection.getInputStream();
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            
            boolean hasData = scanner.hasNext();
            if (hasData) {
                return scanner.next();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        }
        finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Hotel> getHotelsFromJson(String json) {
        final String ID = "id";
        final String NAME = "name";
        final String ADDRESS = "formatted_address";
        final String PHOTOS = "photos";
        final String HTML_ATTRIBUTIONS="html_attributions";
        final String RATING = "rating";
        final String LOCATION = "location";
        final String LAT = "lat";
        final String LNG = "lng";
        final String RESULTS = "results";
        final String GEOMETRY = "geometry";

        ArrayList<Hotel> hotels = new ArrayList<Hotel>();

        try {
            JSONObject jsonHotels = new JSONObject(json);
            JSONArray arrayHotels = jsonHotels.getJSONArray(RESULTS);
            String imageUrl = new String();
            int numberOfHotels = arrayHotels.length();
            for (int i = 0; i < numberOfHotels; ++i) {
                JSONObject hotelJSON = arrayHotels.getJSONObject(i);
                JSONArray arrayPhotos = hotelJSON.getJSONArray(PHOTOS);
                int numberOfPhotos = arrayPhotos.length();
                for (int j = 0; j < numberOfPhotos; ++j) {
                    JSONObject photosAttributionsJSON = arrayPhotos.getJSONObject(j);
                    JSONArray arrayHtmlAttributions = photosAttributionsJSON.getJSONArray(HTML_ATTRIBUTIONS);
                    int numberOfHtmlAttributions = arrayHtmlAttributions.length();
                    String[] imageUrls = new String[numberOfHtmlAttributions];
                    imageUrl = photosAttributionsJSON.getJSONArray(HTML_ATTRIBUTIONS).get(j).toString();
                    int index = imageUrl.indexOf("href=\"");
                    imageUrl = imageUrl.substring(index + 6, imageUrl.length());
//                    for (int k = 0; k < numberOfHtmlAttributions; ++k) {
//                        imageUrls[k] = photosAttributionsJSON.getJSONArray(HTML_ATTRIBUTIONS).get(j).toString();
//                        int index = imageUrls[k].indexOf("href=\"");
//                        imageUrls[k] = imageUrls[k].substring(index + 6, imageUrls[k].length());
//                    }
                }

                JSONObject geometryJSON = hotelJSON.getJSONObject(GEOMETRY);
                JSONObject locationJSON = geometryJSON.getJSONObject(LOCATION);
                String[] location = new String[2];
                location[0] = locationJSON.getString(LAT); // latitude
                location[1] = locationJSON.getString(LNG); // longitude

                Hotel hotel = new Hotel(
                        hotelJSON.getString(ID),
                        hotelJSON.getString(NAME),
                        hotelJSON.getString(ADDRESS),
                        imageUrl,
                        (hotelJSON.isNull(RATING) ? "" : hotelJSON.getString(RATING)),
                        location);
                hotels.add(hotel);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return hotels;
    }
}
