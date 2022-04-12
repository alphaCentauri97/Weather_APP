package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = binding.etcity.getText().toString().trim();
                String url ="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=2a347a67185f3f7322414a8c0e40c1a3&units=metric";


                if(city.isEmpty()){
                    binding.etcity.requestFocus();
                    binding.etcity.setError("Please Enter City name");
                    return;
                }

                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //find City
                            String object = jsonObject.getString("name");
                             binding.tvcity.setText(object);

                             JSONObject object2 = jsonObject.getJSONObject("main");
                             int temp = object2.getInt("temp");
                             binding.tvtemp.setText(String.valueOf(temp)+ "\u2103");

                             JSONArray jsonArray = jsonObject.getJSONArray("weather");
                             JSONObject object1 = jsonArray.getJSONObject(0);
                             String weather = object1.getString("main");
                             binding.tvweather.setText(weather);

                            if (weather.equals("Clear"))
                             {
                                 binding.imageView.setAnimation(R.raw.sunny);
                                 binding.imageView.playAnimation();
                             }
                             else if (weather.equals("Rain"))
                             {
                                 binding.imageView.setAnimation(R.raw.rain);
                                 binding.imageView.playAnimation();
                             }
                             else if (weather.equals("Clouds"))
                             {
                                 binding.imageView.setAnimation(R.raw.cloud);
                                 binding.imageView.playAnimation();
                             }
                             else if (weather.equals("Haze"))
                             {
                                 binding.imageView.setAnimation(R.raw.haze);
                                 binding.imageView.playAnimation();
                             }
                             else if (weather.equals("Mist"))
                             {
                                 binding.imageView.setAnimation(R.raw.mist);
                                 binding.imageView.playAnimation();
                             }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });
    }
}