package com.example.aplikacja;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ActualDataFragment extends Fragment implements SensorEventListener {

    private static final String TAG = "ActualDataFragment";

    public SensorManager sensorManager;
    Sensor Accel, AmbTemp, Grav, Gyro, Light, LinAcc, MagFie, Orie, Pres, Prox, RelHum, RotVec, Temp;

    TextView xAccel, yAccel, zAccel;
    TextView xAmbTemp;
    TextView xGrav, yGrav, zGrav;
    TextView xGyro, yGyro, zGyro;
    TextView xLight;
    TextView xLinAcc, yLinAcc, zLinAcc;
    TextView xMagFie, yMagFie, zMagFie;
    TextView xOrie, yOrie, zOrie;
    TextView xPres;
    TextView xProx;
    TextView xRelHum;
    TextView xRotVec, yRotVec, zRotVec;
    TextView xTemp;
    Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actualdata,container,false);

        xAccel = (TextView) view.findViewById(R.id.xAccel);
        yAccel = (TextView) view.findViewById(R.id.yAccel);
        zAccel = (TextView) view.findViewById(R.id.zAccel);

        xAmbTemp = (TextView) view.findViewById(R.id.xAmbTemp);

        xGrav = (TextView) view.findViewById(R.id.xGrav);
        yGrav = (TextView) view.findViewById(R.id.yGrav);
        zGrav = (TextView) view.findViewById(R.id.zGrav);

        xGyro = (TextView) view.findViewById(R.id.xGyro);
        yGyro = (TextView) view.findViewById(R.id.yGyro);
        zGyro = (TextView) view.findViewById(R.id.zGyro);

        xLight = (TextView) view.findViewById(R.id.xLight);

        xLinAcc = (TextView) view.findViewById(R.id.xLinAcc);
        yLinAcc = (TextView) view.findViewById(R.id.yLinAcc);
        zLinAcc = (TextView) view.findViewById(R.id.zLinAcc);

        xMagFie = (TextView) view.findViewById(R.id.xMagFie);
        yMagFie = (TextView) view.findViewById(R.id.yMagFie);
        zMagFie = (TextView) view.findViewById(R.id.zMagFie);

        xOrie = (TextView) view.findViewById(R.id.xOrie);
        yOrie = (TextView) view.findViewById(R.id.yOrie);
        zOrie = (TextView) view.findViewById(R.id.zOrie);

        xPres = (TextView) view.findViewById(R.id.xPres);

        xProx = (TextView) view.findViewById(R.id.xProx);

        xRelHum = (TextView) view.findViewById(R.id.xRelHum);

        xRotVec = (TextView) view.findViewById(R.id.xRotVec);
        yRotVec = (TextView) view.findViewById(R.id.yRotVec);
        zRotVec = (TextView) view.findViewById(R.id.zRotVec);

        xTemp = (TextView) view.findViewById(R.id.xTemp);

        saveButton = view.findViewById(R.id.SaveButton);

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                WriteData();
            }
        });

        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);

        Accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener( ActualDataFragment.this, Accel, SensorManager.SENSOR_DELAY_NORMAL);

        AmbTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener( ActualDataFragment.this, AmbTemp, SensorManager.SENSOR_DELAY_NORMAL);

        Grav = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener( ActualDataFragment.this, Grav, SensorManager.SENSOR_DELAY_NORMAL);

        Gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener( ActualDataFragment.this, Gyro, SensorManager.SENSOR_DELAY_NORMAL);

        Light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(ActualDataFragment.this, Light, SensorManager.SENSOR_DELAY_NORMAL);

        LinAcc = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(ActualDataFragment.this, LinAcc, SensorManager.SENSOR_DELAY_NORMAL);

        MagFie = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(ActualDataFragment.this, MagFie, SensorManager.SENSOR_DELAY_NORMAL);

        Orie = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(ActualDataFragment.this, Orie, SensorManager.SENSOR_DELAY_NORMAL);

        Pres = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(ActualDataFragment.this, Pres, SensorManager.SENSOR_DELAY_NORMAL);

        Prox = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(ActualDataFragment.this, Prox, SensorManager.SENSOR_DELAY_NORMAL);

        RelHum = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        sensorManager.registerListener(ActualDataFragment.this, RelHum, SensorManager.SENSOR_DELAY_NORMAL);

        RotVec = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorManager.registerListener(ActualDataFragment.this, RotVec, SensorManager.SENSOR_DELAY_NORMAL);

        Temp = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        sensorManager.registerListener(ActualDataFragment.this, Temp, SensorManager.SENSOR_DELAY_NORMAL);

        return view;
    }

    public void WriteData(){
        checkFolder();

        String currentData = ReadCurrentData();

        SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date todayDate = new Date();
        String thisDate = currentDate.format(todayDate);

        File folder = getContext().getFilesDir();
        File f = new File(folder, "DataMeasure");

        String fileName = thisDate + ".txt";

        File destinationDir = new File(f, fileName);

        try {

            FileWriter writer = new FileWriter(destinationDir);
            writer.append(currentData);
            writer.flush();
            writer.close();

            Toast.makeText(getContext().getApplicationContext(), "File Saved", Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String ReadCurrentData(){
        StringBuffer sb = new StringBuffer();

        sb.append(xAccel.getText().toString() + "\n");
        sb.append(yAccel.getText().toString() + "\n");
        sb.append(zAccel.getText().toString() + "\n");

        sb.append(xAmbTemp.getText().toString() + "\n");

        sb.append(xGrav.getText().toString() + "\n");
        sb.append(yGrav.getText().toString() + "\n");
        sb.append(zGrav.getText().toString() + "\n");

        sb.append(xGyro.getText().toString() + "\n");
        sb.append(yGyro.getText().toString() + "\n");
        sb.append(zGyro.getText().toString() + "\n");

        sb.append(xLight.getText().toString() + "\n");

        sb.append(xLinAcc.getText().toString() + "\n");
        sb.append(yLinAcc.getText().toString() + "\n");
        sb.append(zLinAcc.getText().toString() + "\n");

        sb.append(xMagFie.getText().toString() + "\n");
        sb.append(yMagFie.getText().toString() + "\n");
        sb.append(zMagFie.getText().toString() + "\n");

        sb.append(xOrie.getText().toString() + "\n");
        sb.append(yOrie.getText().toString() + "\n");
        sb.append(zOrie.getText().toString() + "\n");

        sb.append(xPres.getText().toString() + "\n");

        sb.append(xProx.getText().toString() + "\n");

        sb.append(xRelHum.getText().toString() + "\n");

        sb.append(xRotVec.getText().toString() + "\n");
        sb.append(yRotVec.getText().toString() + "\n");
        sb.append(zRotVec.getText().toString() + "\n");

        sb.append(xTemp.getText().toString() + "\n");

        return sb.toString();
    }

    public void checkFolder() {
        File folder = getContext().getFilesDir();
        File f= new File(folder, "DataMeasure");

        boolean isDirectoryCreated = f.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = f.mkdir();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if (sensor.getType()==Sensor.TYPE_ACCELEROMETER)
        {
            xAccel.setText("xAccel " + sensorEvent.values[0]);
            yAccel.setText("yAccel " + sensorEvent.values[1]);
            zAccel.setText("zAccel " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE)
        {
            xAmbTemp.setText("xAmbTemp " + sensorEvent.values[0]);
        }
        else if (sensor.getType()==Sensor.TYPE_GRAVITY)
        {
            xGrav.setText("xGrav " + sensorEvent.values[0]);
            yGrav.setText("yGrav " + sensorEvent.values[1]);
            zGrav.setText("zGrav " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_GYROSCOPE)
        {
            xGyro.setText("xGyro " + sensorEvent.values[0]);
            yGyro.setText("yGyro " + sensorEvent.values[1]);
            zGyro.setText("zGyro " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_LIGHT)
        {
            xLight.setText("xLight " + sensorEvent.values[0]);
        }
        else if (sensor.getType()==Sensor.TYPE_LINEAR_ACCELERATION)
        {
            xLinAcc.setText("xLinAcc " + sensorEvent.values[0]);
            yLinAcc.setText("yLinAcc " + sensorEvent.values[1]);
            zLinAcc.setText("zLinAcc " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD)
        {
            xMagFie.setText("xMagFie " + sensorEvent.values[0]);
            yMagFie.setText("yMagFie " + sensorEvent.values[1]);
            zMagFie.setText("zMagFie " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_ORIENTATION)
        {
            xOrie.setText("xOrie " + sensorEvent.values[0]);
            yOrie.setText("yOrie " + sensorEvent.values[1]);
            zOrie.setText("zOrie " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_PRESSURE)
        {
            xPres.setText("xPres " + sensorEvent.values[0]);
        }
        else if (sensor.getType()==Sensor.TYPE_PROXIMITY)
        {
            xProx.setText("xProx " + sensorEvent.values[0]);
        }
        else if (sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY)
        {
            xRelHum.setText("xRelHum " + sensorEvent.values[0]);
        }
        else if (sensor.getType()==Sensor.TYPE_ROTATION_VECTOR)
        {
            xRotVec.setText("xRotVec " + sensorEvent.values[0]);
            yRotVec.setText("yRotVec " + sensorEvent.values[1]);
            zRotVec.setText("zRotVec " + sensorEvent.values[2]);
        }
        else if (sensor.getType()==Sensor.TYPE_TEMPERATURE)
        {
            xTemp.setText("xTemp " + sensorEvent.values[0]);
        }
    }
}
