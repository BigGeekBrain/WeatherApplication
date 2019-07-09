package com.geekbrains.vadimshiryaev.weatherapplication.fragments;

import android.app.Dialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.geekbrains.vadimshiryaev.weatherapplication.R;

public class SensorsDialogFragment extends DialogFragment {


    private SensorManager sensorManager;
    private Sensor sensorHumidity;
    private Sensor sensorTemperature;

    private TextView temperature;
    private TextView humidity;
    private Button ok;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        initParams();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.sensor_dialog, null);

        initDialogView(dialogView);

        setOkBtnClickBehavior();

        registerSensor(sensorTemperature,
                getResourcesString(R.string.temperature),
                temperature,
                getResourcesString(R.string.temperature_sensor_description));

        registerSensor(sensorHumidity,
                getResourcesString(R.string.humidity),
                humidity,
                getResourcesString(R.string.humidity_sensor_description));

        builder.setView(dialogView);
        return builder.create();
    }

    private void initParams() {
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
    }


    private void initDialogView(View dialogView) {
        temperature = dialogView.findViewById(R.id.temperature);
        humidity = dialogView.findViewById(R.id.humidity);
        ok = dialogView.findViewById(R.id.ok);

    }

    private void setOkBtnClickBehavior() {
        ok.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SensorsDialogFragment.this.getDialog().cancel();
            }
        });
    }

    private void registerSensor(Sensor sensor, String sensorName, final TextView sensorView, final String sensorDescription) {
        if (sensor == null) {
            sensorView.setText(getResourcesString(R.string.absent_sensor) + " "
                    + sensorName + " "
                    + getResourcesString(R.string.sensor));
        }

        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(sensorDescription).append(" ").append(event.values[0]);
                sensorView.setText(stringBuilder);
            }
        };

        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private String getResourcesString(int id) {
        return getActivity().getResources().getString(id);
    }
}
