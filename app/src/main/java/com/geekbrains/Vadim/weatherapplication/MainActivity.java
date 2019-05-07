package com.geekbrains.Vadim.weatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private final Logger logger = Logger.getLogger(MainActivity.class.getName());
    private Handler logHandler;
    private final Date dat = new Date();

    private AutoCompleteTextView cityTextView;
    private Button searchButton;

    private TextView temperature;
    private TextView fellingTemperature;
    private TextView pressure;
    private TextView humidity;
    private TextView rainfall;
    private TextView windSpeed;
    private TextView windDirection;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentInfo("onCreate()");

        addLoggerAndHandler();
        initialization();
    }

    protected void initialization(){
        cityTextView = findViewById(R.id.cityTextView);
        searchButton = findViewById(R.id.search_button);

        temperature = findViewById(R.id.temperature);
        fellingTemperature = findViewById(R.id.fellingTemperature);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);
        rainfall = findViewById(R.id.rainfall);
        windSpeed = findViewById(R.id.windSpeed);
        windDirection = findViewById(R.id.windDirection);

        String[] cities = getResources().getStringArray(R.array.cities);
        List<String> catList = Arrays.asList(cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, catList);
        cityTextView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.log(Level.FINE, "Search button pressed");
            }
        });
    }

    protected void addLoggerAndHandler(){
        logger.setLevel(Level.ALL);
        logger.setUseParentHandlers(false);
        logHandler = new ConsoleHandler();
        logHandler.setLevel(Level.ALL);
        logHandler.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                dat.setTime(record.getMillis());
                return dat + "\n ----- "
                        + record.getMessage() + "\n";
            }
        });

        logger.addHandler(logHandler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentInfo("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        currentInfo("onRestoreInstanceState()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentInfo("onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentInfo("onPause()");
    }

    @Override
    protected void onSaveInstanceState(Bundle saveInstanceState){
        super.onSaveInstanceState(saveInstanceState);
        currentInfo("onSaveInstanceState()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        currentInfo("onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        currentInfo("onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currentInfo("onDestroy()");
    }

    protected void currentInfo(String text){
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        logger.log(Level.INFO, text);
    }

}
