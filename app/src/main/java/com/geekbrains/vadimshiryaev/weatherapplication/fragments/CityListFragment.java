package com.geekbrains.vadimshiryaev.weatherapplication.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.model.City;
import com.geekbrains.vadimshiryaev.weatherapplication.model.CityLab;
import com.geekbrains.vadimshiryaev.weatherapplication.model.Day;

import java.util.List;
import java.util.UUID;

public class CityListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private final int TYPE_LARGE = 0;
    private final int TYPE_SMALL = 1;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Adapter getAdapter() {
        return adapter;
    }

    private Adapter adapter;
    private OnItemClickListener listener;

    public static CityListFragment newInstance() {
        return new CityListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            listener = (OnItemClickListener) context;
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.list);
        swipeRefreshLayout = view.findViewById(R.id.refresher);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI() {
        CityLab cityLab = CityLab.getCityLab(getActivity());
        List<City> cities = cityLab.getCities();

        if (adapter == null) {
            adapter = new Adapter(cities);
            adapter.setListener(listener);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<City> cities = CityLab.getNewCityLab(getActivity()).getCities();
                adapter = new Adapter(cities);
                adapter.setListener(listener);
                recyclerView.setAdapter(adapter);

                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){

                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.fragment_detail_container, CityDetailFragment.newInstance(cities.get(0).getId()))
                        .replace(R.id.fragment_weather_forecast_container, CityWeatherForecastListFragment.newInstance(cities.get(0).getId()))
                        .commit();

                }

                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 2000);

    }

    public class CityHolder extends RecyclerView.ViewHolder{

        private TextView cityView;
        private TextView countryView;
        private TextView detailsView;
        private TextView degreeView;
        private TextView windSpeedView;
        private ImageView weatherView;
        private int viewType;
        private City city;
        private UUID cityId;


        public CityHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
//            itemView.setOnClickListener(this);
            cityView = itemView.findViewById(R.id.city);

            switch (viewType) {
                case TYPE_LARGE:
                    countryView = itemView.findViewById(R.id.country);
                    detailsView = itemView.findViewById(R.id.details);
                    degreeView = itemView.findViewById(R.id.degree);
                    windSpeedView = itemView.findViewById(R.id.wind_speed);
                    weatherView = itemView.findViewById(R.id.weather_label);
            }

        }

        public void bind(City city, int i) {
            this.city = city;
            this.cityId = city.getId();

            Day currentDay = city.getWeek().get(0);
            cityView.setText(city.getName());

            switch (viewType) {
                case TYPE_LARGE:
                    countryView.setText(city.getCountry());
                    detailsView.setText(getResources().getString(R.string.Humidity) + " " + currentDay.getHumidity() + "% " + currentDay.getWindDirection());
                    degreeView.setText(String.valueOf(currentDay.getTemperatureDegree()));
                    windSpeedView.setText(currentDay.getWindSpeed() + getResources().getString(R.string.m_s));
                    weatherView.setImageDrawable(currentDay.getWeatherLabel());
            }

        }

        public void setListener(final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cityId = cityId;
                    listener.onItemClick(cityId);
                }
            });
        }

//        @Override
//        public void onClick(View v) {
//            if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//
//                Fragment fragment = CityDetailFragment.newInstance(city.getId());
//
//                fragmentManager.beginTransaction()
//                        .replace(R.id.fragment_detail_container, fragment)
//                        .commit();
//                v.setBackground(getActivity().getResources().getDrawable(R.drawable.list_item_style_small_checked));
//
//            } else {
//                Intent intent = DetailActivity.newIntent(getActivity(), city.getId());
//                startActivity(intent);
//            }
//        }
    }

    public class Adapter extends RecyclerView.Adapter<CityHolder>{

        private List<City> cities;
        private OnItemClickListener listener;

        public Adapter(List<City> cities) {
            this.cities = cities;
        }

        @Override
        public int getItemViewType(int position) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                return TYPE_SMALL;
            }
            return TYPE_LARGE;
        }

        @NonNull
        @Override
        public CityHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = null;
            switch (getItemViewType(i)) {
                case TYPE_SMALL:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_item_small, viewGroup, false);
                    break;
                case TYPE_LARGE:
                    view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_list_item, viewGroup, false);
            }
            return new CityHolder(view, getItemViewType(i));
        }

        @Override
        public void onBindViewHolder(@NonNull CityHolder cityHolder, int i) {
            cityHolder.bind(cities.get(i), i);
            cityHolder.setListener(listener);
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }


        public void setListener(OnItemClickListener listener) {
            this.listener = listener;
        }
    }


    public interface OnItemClickListener{
        void onItemClick(UUID id);
    }
}
