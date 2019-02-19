package com.example.hoaitv.linear_acceleromter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.hardware.Sensor.TYPE_ACCELEROMETER;
import static android.hardware.Sensor.TYPE_LINEAR_ACCELERATION;

public class MainActivity extends AppCompatActivity implements SensorEventListener,View.OnClickListener  {



    static public String TAG = "HoaitvApp";

    private SensorManager sensorManager;
    private Sensor 		  accSensors,linearAccSensor;
    private int times = 0;

    float[] accelerometer_data = new float[3];
    float[] linearAcc_data = new float[3];
    float[] gravity = new float[3];
    float linearAccelerometer = 0;
    float linearAccelerometerLA=0;
    float maxX,maxXLA =0;
    float maxY,maxYLA =0;
    float maxZ,maxZLA =0;

    private TextView Total3axis, currentX, currentY, currentZ,textMaxX,textMaxY,textMaxZ;
    private TextView Total3axisLA, currentXLA, currentYLA, currentZLA,textMaxXLA,textMaxYLA,textMaxZLA;
    private Button btnResetMax;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        //		Creat sensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accSensors		 		 = 	sensorManager.getDefaultSensor(TYPE_ACCELEROMETER,false);
        linearAccSensor          = sensorManager.getDefaultSensor(TYPE_LINEAR_ACCELERATION,false);
        //Creat Sensors Listerner
        sensorManager.registerListener(MainActivity.this, accSensors, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this, linearAccSensor, SensorManager.SENSOR_DELAY_NORMAL);




    }

    public synchronized void initializeViews() {
        currentX = (TextView) findViewById(R.id.idcurrentX);
        currentY = (TextView) findViewById(R.id.idcurrentY);
        currentZ = (TextView) findViewById(R.id.idcurrentZ);
        Total3axis =(TextView) findViewById(R.id.idTotal3axis);
        textMaxX = (TextView) findViewById(R.id.idValueMaxX);
        textMaxY = (TextView) findViewById(R.id.idValueMaxY);
        textMaxZ = (TextView) findViewById(R.id.idValueMaxZ);

        currentXLA = (TextView) findViewById(R.id.idcurrentXLA);
        currentYLA = (TextView) findViewById(R.id.idcurrentYLA);
        currentZLA = (TextView) findViewById(R.id.idcurrentZLA);
        Total3axisLA =(TextView) findViewById(R.id.idTotal3axisLA);
        textMaxXLA = (TextView) findViewById(R.id.idValueMaxXLA);
        textMaxYLA = (TextView) findViewById(R.id.idValueMaxYLA);
        textMaxZLA = (TextView) findViewById(R.id.idValueMaxZLA);

        btnResetMax = (Button) findViewById(R.id.idResetMax) ;
        btnResetMax.setOnClickListener(this);


    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        displayCleanValues();
    if(times < 2)
        times ++;
        switch (event.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                final float alpha = 0.8f;

                // Isolate the force of gravity with the low-pass filter.
                gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
                gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
                gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];

                accelerometer_data[0] = event.values[0] - gravity[0];
                accelerometer_data[1] = event.values[1] - gravity[1];
                accelerometer_data[2] = event.values[2] - gravity[2];
                linearAccelerometer = (float) Math.sqrt(accelerometer_data[0]*accelerometer_data[0] + accelerometer_data[1]*accelerometer_data[1]+accelerometer_data[2]*accelerometer_data[2]);
                accValues();
                if (times != 1)
                MaxValue();
                break;

            case Sensor.TYPE_LINEAR_ACCELERATION:

                linearAcc_data[0] = event.values[0];
                linearAcc_data[1] = event.values[1];
                linearAcc_data[2] = event.values[2];
                linearAccelerometerLA = (float) Math.sqrt(linearAcc_data[0]*linearAcc_data[0]+linearAcc_data[1]*linearAcc_data[1]+linearAcc_data[2]*linearAcc_data[2]);
                accValues();
                MaxValue();
            default:
                return;
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public synchronized void accValues() {

        currentX.setText(Float.toString(accelerometer_data[0]));
        currentY.setText(Float.toString(accelerometer_data[1]));
        currentZ.setText(Float.toString(accelerometer_data[2]));
        Total3axis.setText(Float.toString(linearAccelerometer));
        textMaxX.setText(Float.toString(maxX));
        textMaxY.setText(Float.toString(maxY));
        textMaxZ.setText(Float.toString(maxZ));


        currentXLA.setText(Float.toString(linearAcc_data[0]));
        currentYLA.setText(Float.toString(linearAcc_data[1]));
        currentZLA.setText(Float.toString(linearAcc_data[2]));
        Total3axisLA.setText(Float.toString(linearAccelerometerLA));
        textMaxXLA.setText(Float.toString(maxXLA));
        textMaxYLA.setText(Float.toString(maxYLA));
        textMaxZLA.setText(Float.toString(maxZLA));


    }

    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
        Total3axis.setText("0.0");
        textMaxX.setText("0.0");
        textMaxY.setText("0.0");
        textMaxZ.setText("0.0");

        currentXLA.setText("0.0");
        currentYLA.setText("0.0");
        currentZLA.setText("0.0");
        Total3axisLA.setText("0.0");
        textMaxXLA.setText("0.0");
        textMaxYLA.setText("0.0");
        textMaxZLA.setText("0.0");

    }

    public void MaxValue(){
        if(Math.abs(accelerometer_data[0]) >Math.abs(maxX))
            maxX = Math.abs(accelerometer_data[0]);
        if(Math.abs(accelerometer_data[1]) >Math.abs(maxY))
            maxY = Math.abs(accelerometer_data[1]);
        if(Math.abs(accelerometer_data[2]) >Math.abs(maxZ))
            maxZ = Math.abs(accelerometer_data[2]);

        if(Math.abs(linearAcc_data[0]) >Math.abs(maxXLA))
            maxXLA = Math.abs(linearAcc_data[0]);
        if(Math.abs(linearAcc_data[1]) >Math.abs(maxYLA))
            maxYLA = Math.abs(linearAcc_data[1]);
        if(Math.abs(linearAcc_data[2])  >Math.abs(maxZLA))
            maxZLA = Math.abs(linearAcc_data[2]);

    }

    public void resetMax(){
        maxX = 0;
        maxY = 0;
        maxZ = 0;
        maxXLA = 0;
        maxYLA = 0;
        maxZLA = 0;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.idResetMax:
                //TODO
                resetMax();
                break;

        }


    }
}
