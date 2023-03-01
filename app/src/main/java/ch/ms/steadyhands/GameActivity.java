package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import javax.xml.transform.Result;

public class GameActivity extends AppCompatActivity {

    private TextView timerText;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        timerText = findViewById(R.id.timerText);

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
    }
}