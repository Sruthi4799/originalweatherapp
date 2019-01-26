package com.example.tech2k8.weatherapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentListWeatherData extends Fragment {

    private RecyclerView weatherDataInUi;
    private ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_list_weather_data,container,false);
        weatherDataInUi=rootView.findViewById(R.id.list_weather_data);
        progressBar=rootView.findViewById(R.id.progress);
        weatherDataInUi.setLayoutManager(new LinearLayoutManager(getContext()));
        String url="https://samples.openweathermap.org/data/2.5/box/city?bbox=12,32,15,37,10&appid=b6907d289e10d714a6e88b30761fae22";
        Log.i("FragmentListWeatherData","started to load url");

        NetworkCalls calls =new NetworkCalls(new NetworkCallsListener() {
            @Override
            public void onSuccess(ArrayList<WeatherData> data) {
                WeatherDataAdapter adapter =new WeatherDataAdapter(data,getContext());
                weatherDataInUi.setAdapter(adapter);
                Toast.makeText(getContext(), "success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
            }
        });
        calls.loadDataFromNetwork(getContext());
//        StringRequest request =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
//                Log.i("FragmentListWeatherData","success response :"+response);
//                progressBar.setVisibility(View.GONE);
//                extractDataFromJson(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(getContext(), "Failure", Toast.LENGTH_SHORT).show();
//                Log.i("FragmentListWeatherData","error response "+error.getMessage());
//            }
//        });
//        Log.i("FragmentListWeatherData","request is added to queue");
//        Volley.newRequestQueue(getContext()).add(request);

        Log.i("FragmentListWeatherData","req added into queue");
        return rootView;
    }

    private void extractDataFromJson(String urlReqResult)
    {
        ArrayList<WeatherData> weatherDataArrayList =new ArrayList<>();
        try {
            JSONObject rootObject =new JSONObject(urlReqResult);
            JSONArray listArray = rootObject.getJSONArray("list");
            for (int i=0;i<listArray.length();i++)
            {
                JSONObject indivJsonObj=listArray.getJSONObject(i);
                JSONObject coordObj=indivJsonObj.getJSONObject("coord");
                String lattitude = coordObj.getString("lat");
                String longitude =coordObj.getString("lon");

                JSONObject mainObj = indivJsonObj.getJSONObject("main");
                String temperature = mainObj.getString("temp");
                WeatherData data =new WeatherData();
                data.setLatitude(lattitude);
                data.setLongitude(longitude);
                data.setTemperature(temperature);
                weatherDataArrayList.add(data);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
