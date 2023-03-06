package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int highscore;
    private SharedPreferences sharedPreferences;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("Highscore", MODE_PRIVATE);

        //get highscore and display it
        highscore = sharedPreferences.getInt("highscore", 0);
        TextView highscoreText = findViewById(R.id.highscoreText);
        highscoreText.setText("Current highscore: " + highscore);

        //get start Button and add listener
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startGame());

        //get vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }


    private void startGame() {
        //vibrate phone
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));

        //Load GameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}