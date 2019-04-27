package com.example.geoquester;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(getApplicationContext(), " - onCreate()", Toast.LENGTH_SHORT).show();

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

        cityTextView = findViewById(R.id.cityTextView);
        Button searchButton = findViewById(R.id.search_button);

        final CheckBox temperature = findViewById(R.id.temperature);
        final CheckBox fellingTemperature = findViewById(R.id.fellingTemperature);
        final CheckBox pressure = findViewById(R.id.pressure);
        final CheckBox humidity = findViewById(R.id.humidity);
        final CheckBox rainfall = findViewById(R.id.rainfall);
        final CheckBox windSpeed = findViewById(R.id.windSpeed);
        final CheckBox windDirection = findViewById(R.id.windDirection);

        String[] cities = getResources().getStringArray(R.array.cities);
        List<String> catList = Arrays.asList(cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, catList);
        cityTextView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logger.log(Level.FINE, "Search button pressed");
                Intent intent = new Intent(MainActivity.this, WeatherResultActivity.class);
                Parcel parcel = new Parcel(cityTextView.getText().toString());

                if (temperature.isChecked()) parcel.setTemperature(true);
                if (fellingTemperature.isChecked()) parcel.setFeelingTemperature(true);
                if (pressure.isChecked()) parcel.setPressure(true);
                if (humidity.isChecked()) parcel.setHumidity(true);
                if (rainfall.isChecked()) parcel.setRainfall(true);
                if (windSpeed.isChecked()) parcel.setWindSpeed(true);
                if (windDirection.isChecked()) parcel.setWindDirection(true);

                intent.putExtra("CITY", parcel);
                cityTextView.setText(R.string.rainfall);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentInfo("onStart()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle saveInstanceState){
        super.onRestoreInstanceState(saveInstanceState);
        currentInfo("Повторный запуск!! - onRestoreInstanceState()");
        cityTextView.setText(saveInstanceState.getString("City"));
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
        saveInstanceState.putString("City", cityTextView.toString());
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

    protected void currentInfo(String loggerText){
        Toast.makeText(getApplicationContext(), loggerText, Toast.LENGTH_SHORT).show();
        logger.log(Level.INFO, loggerText);
    }

}
