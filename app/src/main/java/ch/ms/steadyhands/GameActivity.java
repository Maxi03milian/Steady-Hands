package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import javax.xml.transform.Result;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private TextView timerText;
    private TextView angleText;
    private CountDownTimer countDownTimer;

    //Sensor setup
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    //Initialize arrays for sensor data
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float[] orientation = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timerText = findViewById(R.id.timerText);
        angleText = findViewById(R.id.timerText3);

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the countdown text view with the remaining time
                timerText.setText(String.valueOf(millisUntilFinished / 1000 + 1) + " seconds");
            }

            @Override
            public void onFinish() {
                // Start the new activity when the countdown is finished
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                startActivity(intent);
                finish();
            }
        };
        // Start the countdown timer
        countDownTimer.start();

        // Initialize the sensor manager and sensors
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // Check sensor type
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = sensorEvent.values.clone();
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = sensorEvent.values.clone();
        }

        // Get rotation matrix and orientation
        float[] rotationMatrix = new float[9];
        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
            SensorManager.getOrientation(rotationMatrix, orientation);
        }

        // Get angle value in degrees
        float roll = (float) Math.toDegrees(orientation[2]);
        float pitch = (float) Math.toDegrees(orientation[1]);
        float yaw = (float) Math.toDegrees(orientation[0]);

        float combinedEval = Math.abs(roll) + Math.abs(pitch) + Math.abs(yaw);
        angleText.setText(String.valueOf(combinedEval));



    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}