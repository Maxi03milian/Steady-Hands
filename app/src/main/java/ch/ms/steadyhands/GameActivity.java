package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.transform.Result;

import ch.ms.steadyhands.helper.ScoreHelper;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private TextView timerText;
    private TextView angleText;
    private CountDownTimer countDownTimer;
    private Vibrator vibrator;

    //Sensor setup
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;

    //Initialize arrays for sensor data
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float[] orientation = new float[3];
    float combinedEval = 500;

    //Score evaluation
    private Timer scoreCheckTimer;
    private ArrayList<Float> rotationValues = new ArrayList<>();
    ScoreHelper scoreHelper = new ScoreHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timerText = findViewById(R.id.timerText);
        angleText = findViewById(R.id.timerText3);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        // create timer and check rotation values every 100ms
        scoreCheckTimer = new Timer();
        scoreCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //add rotation values to array
                addRotationValues();
            }
        }, 200, 100);

        // Create timer for game countdown
        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                // Update the countdown text view with the remaining time
                timerText.setText(String.valueOf(millisUntilFinished / 1000 + 1) + " seconds");
            }
            @Override
            public void onFinish() {
                loadResultActivity();
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

        combinedEval = Math.abs(roll) + Math.abs(pitch) + Math.abs(yaw);
        angleText.setText(String.valueOf(combinedEval));
    }


    private void addRotationValues() {
        if(combinedEval == 500){
            return;
        }
        rotationValues.add(combinedEval);

    }

    private void loadResultActivity(){
        //vibrate phone
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        // Start the new activity when the countdown is finished
        int result = scoreHelper.calculateScore(rotationValues);
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("score", result);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}