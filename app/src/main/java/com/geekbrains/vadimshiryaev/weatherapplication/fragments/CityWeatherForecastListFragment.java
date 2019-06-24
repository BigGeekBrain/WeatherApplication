package com.geekbrains.vadimshiryaev.weatherapplication.fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.vadimshiryaev.weatherapplication.Controller.DetailActivity;
import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.model.City;
import com.geekbrains.vadimshiryaev.weatherapplication.model.CityLab;
import com.geekbrains.vadimshiryaev.weatherapplication.model.Day;

import java.util.List;
import java.util.UUID;

public class CityWeatherForecastListFragment extends Fragment {

    private static final String ARG_CITY_ID = "com.geekbrains.vadimshiryaev.weatherapplication.model.City.id";

    private RecyclerView recyclerView;
    private WeatherForecastAdapter adapter;
    private UUID cityId;

    public static CityWeatherForecastListFragment newInstance(UUID id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CITY_ID, id);
        CityWeatherForecastListFragment fragment = new CityWeatherForecastListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weather_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            cityId = (UUID) getArguments().getSerializable(ARG_CITY_ID);
        }
        recyclerView = view.findViewById(R.id.weather_forecast_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CityLab cityLab = CityLab.getCityLab(getActivity());
        City city = cityLab.getCity(cityId);

        if (adapter == null) {
            adapter = new WeatherForecastAdapter(city);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastHolder> {

        private City city;
        private List<Day> days;

        public WeatherForecastAdapter(City city) {
            this.city = city;
            this.days = city.getWeek();
        }

        @NonNull
        @Override
        public WeatherForecastHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.fragment_weather_list_item, viewGroup, false);
            return new WeatherForecastHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WeatherForecastHolder weatherForecastHolder, int i) {
            weatherForecastHolder.bind(days.get(i), city);
        }

        @Override
        public int getItemCount() {
            return days.size();
        }
    }

    class WeatherForecastHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView dataView;
        private TextView degreeView;
        private ImageView weatherLabelView;
        private City city;
        private Day day;

        public WeatherForecastHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.dataView = itemView.findViewById(R.id.data);
            this.degreeView = itemView.findViewById(R.id.degree);
            this.weatherLabelView = itemView.findViewById(R.id.weather_label);
        }

        public void bind(Day day, City city){
            dataView.setText(day.getStringDate());
            degreeView.setText(day.getTemperatureDegree());
            weatherLabelView.setImageDrawable(day.getWeatherLabel());
            this.city = city;
            this.day = day;

        }

        @Override
        public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                CityDetailFragment detailFragment = (CityDetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail_container);

                detailFragment.goToDay(day);

        }

    }
}
