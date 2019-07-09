package com.geekbrains.vadimshiryaev.weatherapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.model.City;
import com.geekbrains.vadimshiryaev.weatherapplication.model.CityLab;
import com.geekbrains.vadimshiryaev.weatherapplication.model.Day;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class CityDetailFragment extends Fragment {

    private static final String ARG_CITY_ID = "com.geekbrains.vadimshiryaev.weatherapplication.model.City.id";
    private static final String ARG_DAY_ID = "com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityDetailFragment.day";

    private UUID cityId;
    private TextView cityView;
    private TextView dataView;
    private TextView detailView;
    private TextView weatherDescriptionView;
    private TextView degreeView;
    private ImageView weatherView;
    private UUID dayId;

    public static CityDetailFragment newInstance(UUID cityId){
        CityDetailFragment fragment = new CityDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY_ID, cityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_city_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            cityId = (UUID) getArguments().getSerializable(ARG_CITY_ID);
        }

        if (savedInstanceState != null) {
            cityId = (UUID) savedInstanceState.getSerializable(ARG_CITY_ID);
            dayId = (UUID) savedInstanceState.getSerializable(ARG_DAY_ID);
        }

        cityView = view.findViewById(R.id.city);
        dataView = view.findViewById(R.id.data);
        detailView = view.findViewById(R.id.details);
        weatherDescriptionView = view.findViewById(R.id.weather_description);
        degreeView = view.findViewById(R.id.degree);
        weatherView = view.findViewById(R.id.weather_label);

    }

    @Override
    public void onStart() {
        super.onStart();
        updateUI();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_CITY_ID, cityId);
        outState.putSerializable(ARG_DAY_ID, dayId);
    }

    private void updateUI(){
        City city = CityLab.getCityLab(getActivity()).getCity(cityId);
        Day currentDay = city.getWeek().get(0);

        cityView.setText(city.getName());

        if (dayId != null) {
            goToDay(city.getWeekMap().get(dayId));
            return;
        }

        dataView.setText(formatData(currentDay.getDate()));
        weatherDescriptionView.setText(currentDay.getWeather());
        detailView.setText(getResources().getString(R.string.Humidity) + " " + currentDay.getHumidity() + "% " + currentDay.getWindDirection() + " " + currentDay.getWindSpeed() + getResources().getString(R.string.m_s));
        degreeView.setText(currentDay.getTemperatureDegree());
        if (currentDay.getWeather().equals(getResources().getString(R.string.weather_cloudy))) {
            weatherView.setImageResource(R.drawable.ic_cloudy_large);
        } else {
            weatherView.setImageResource(R.drawable.ic_sunny_large);
        }
    }

    private String formatData(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, d MMMM, H:m", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }

    public void goToDay(Day day){
        this.dayId = day.getId();
        dataView.setText(day.getStringDate());
        weatherDescriptionView.setText(day.getWeather());
        detailView.setText(getResources().getString(R.string.Humidity) + " " + day.getHumidity() + "% " + day.getWindDirection() + " " + day.getWindSpeed() + getResources().getString(R.string.m_s));
        degreeView.setText(day.getTemperatureDegree());
        if (day.getWeather().equals(getResources().getString(R.string.weather_cloudy))) {
            weatherView.setImageResource(R.drawable.ic_cloudy_large);
        } else {
            weatherView.setImageResource(R.drawable.ic_sunny_large);
        }
    }
}
