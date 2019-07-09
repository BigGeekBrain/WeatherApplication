package com.geekbrains.vadimshiryaev.weatherapplication.Controller;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.geekbrains.vadimshiryaev.weatherapplication.R;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityDetailFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityListFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.fragments.CityWeatherForecastListFragment;
import com.geekbrains.vadimshiryaev.weatherapplication.model.City;
import com.geekbrains.vadimshiryaev.weatherapplication.model.CityLab;

import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CityListFragment.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private EditText cityEditText;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ImageButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragmentList = fragmentManager.findFragmentById(R.id.fragment_list_container);
        Fragment fragmentDetail = fragmentManager.findFragmentById(R.id.fragment_detail_container);
        Fragment weatherForecast = fragmentManager.findFragmentById(R.id.fragment_weather_forecast_container);

        initParam();

        setSupportActionBar(toolbar);
        initFloatingActionBtn();
        initSideMenu(toolbar);

        updateUI(fragmentList, fragmentDetail, weatherForecast);
    }

    private void initParam() {
        toolbar = findViewById(R.id.toolbar);
        cityEditText = findViewById(R.id.cityEditText);
        addButton = findViewById(R.id.edit_button);
    }

    private void initSideMenu(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        handleMenuItemClick(item);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_settings:
                Snackbar.make(addButton, R.string.settings, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.nav_language:
                Snackbar.make(addButton, R.string.language, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.nav_rate_app:
                Snackbar.make(addButton, R.string.rate_app, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.nav_more_apps:
                Snackbar.make(addButton, R.string.more_apps, Snackbar.LENGTH_LONG).show();
                break;
            case R.id.nav_about_us:
                Snackbar.make(addButton, R.string.about_us, Snackbar.LENGTH_LONG).show();
                break;
                default: break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFloatingActionBtn() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                popupMenu.getMenu().findItem(R.id.menu_search).setVisible(false);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        handleMenuItemClick(menuItem);
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    private void updateUI(Fragment fragmentList, Fragment fragmentDetail, Fragment weatherForecast) {
        if (fragmentList == null) {
            replaceFragment(R.id.fragment_list_container, CityListFragment.newInstance());
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.fragment_detail_container).setVisibility(View.VISIBLE);
            City firstCity = CityLab.getCityLab(this).getCities().get(0);
            addButton.setVisibility(View.GONE);
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

    private void handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        CityLab cityLab = CityLab.getCityLab(this);
        CityListFragment fragment = (CityListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_list_container);
        CityListFragment.Adapter adapter = fragment.getAdapter();

        switch (id) {
            case R.id.menu_add: {
                adapter.notifyItemInserted(cityLab.addCity());
                break;
            }
            case R.id.menu_search: {
                Objects.requireNonNull(getSupportActionBar()).setTitle("");
                cityEditText.setVisibility(View.VISIBLE);
                cityEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                break;
            }

            case R.id.menu_remove: {
                adapter.notifyItemRemoved(cityLab.removeCity());
                break;
            }

            default: {
                Toast.makeText(getApplicationContext(), "Action not found", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }


}
