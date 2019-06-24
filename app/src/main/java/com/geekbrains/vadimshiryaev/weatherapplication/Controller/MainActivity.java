package com.geekbrains.vadimshiryaev.weatherapplication.Controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityDetailFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityListFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityWeatherForecastListFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.model.City;
import com.geekbrains.vadimshiryaev.weatherapplication.model.CityLab;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CityListFragment.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentList = fragmentManager.findFragmentById(R.id.fragment_list_container);
        Fragment fragmentDetail = fragmentManager.findFragmentById(R.id.fragment_detail_container);
        Fragment weatherForecast = fragmentManager.findFragmentById(R.id.fragment_weather_forecast_container);



        updateUI(fragmentList, fragmentDetail, weatherForecast);
    }

    private void updateUI(Fragment fragmentList, Fragment fragmentDetail, Fragment weatherForecast) {
        if (fragmentList == null) {
            replaceFragment(R.id.fragment_list_container, CityListFragment.newInstance());
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.fragment_detail_container).setVisibility(View.VISIBLE);
            City firstCity = CityLab.getCityLab(this).getCities().get(0);
            if (fragmentDetail == null) {
                replaceFragment(R.id.fragment_detail_container, CityDetailFragment.newInstance(firstCity.getId()));
            }

            if (weatherForecast == null) {
                replaceFragment(R.id.fragment_weather_forecast_container, CityWeatherForecastListFragment.newInstance(firstCity.getId()));
            }
        }
    }

    private void replaceFragment(@IdRes int container, @NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(container, fragment)
                .commit();
    }


    @Override
    public void onItemClick(UUID id) {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Fragment cityDetailFragment = CityDetailFragment.newInstance(id);
            Fragment cityWeatherForecastListFragment = CityWeatherForecastListFragment.newInstance(id);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_detail_container, cityDetailFragment)
                    .replace(R.id.fragment_weather_forecast_container, cityWeatherForecastListFragment)
                    .commit();

        } else {
            Intent intent = DetailActivity.newIntent(this, id);
            startActivity(intent);
        }
    }
}
