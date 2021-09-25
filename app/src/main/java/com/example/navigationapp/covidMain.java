package com.example.navigationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class covidMain extends AppCompatActivity {
    TextView tvCases, tvRecover, tvCritical, tvActive, tvToday, tvTodaycases, tvTodaydeaths, tvTotaldeaths,tvAffect;
    //    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_main);
        tvCases=findViewById(R.id.tvCases);
        tvRecover=findViewById(R.id.tvRecover);
        tvCritical=findViewById(R.id.tvCritical);
        tvActive=findViewById(R.id.tvActive);
        tvToday=findViewById(R.id.tvToday);
        tvTodaycases=findViewById(R.id.tvTodaycases);
        tvTodaydeaths=findViewById(R.id.tvTodaydeaths);
        tvTotaldeaths=findViewById(R.id.tvTotaldeaths);
        tvAffect=findViewById(R.id.tvAffect);
//        simpleArcLoader=findViewById(R.id.loader);
        scrollView=findViewById(R.id.scrollstats);
        pieChart=findViewById(R.id.piechart);

        fetchData();
    }
    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all/";
//        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecover.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvToday.setText(jsonObject.getString("todayRecovered"));
                    tvTodaycases.setText(jsonObject.getString("todayCases"));
                    tvTodaydeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTotaldeaths.setText(jsonObject.getString("deaths"));
                    tvAffect.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvRecover.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotaldeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

//                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
//                    simpleArcLoader.stop();
//                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                simpleArcLoader.stop();
//                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(covidMain.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }

}