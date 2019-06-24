package com.geekbrains.vadimshiryaev.weatherapplication.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityDetailFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityWeatherForecastListFragment;

import java.util.UUID;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_CITY_ID = "com.geekbrains.vadimshiryaev.weatherapplication.model.City.id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment cityDetailFragment = fragmentManager.findFragmentById(R.id.fragment_detail_container);
        Fragment weatherForecast = fragmentManager.findFragmentById(R.id.fragment_weather_forecast_container);

        if (cityDetailFragment == null) {
            replaceFragment(R.id.fragment_detail_container, CityDetailFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_CITY_ID)));
        }

        if (weatherForecast == null) {
            replaceFragment(R.id.fragment_weather_forecast_container, CityWeatherForecastListFragment.newInstance((UUID) getIntent().getSerializableExtra(EXTRA_CITY_ID)));
        }
    }

    private void replaceFragment(@IdRes int container, @NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }

    public static Intent newIntent(Context context, UUID cityId){
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_CITY_ID, cityId);
        return intent;
    }
}
