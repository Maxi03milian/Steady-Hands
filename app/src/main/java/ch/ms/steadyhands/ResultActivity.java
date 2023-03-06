package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        sharedPreferences = getSharedPreferences("Highscore", MODE_PRIVATE);
        //get score from game activity
        Bundle extras = getIntent().getExtras();
        score = extras.getInt("score");

        //get start Button and add listener
        Button startButton = findViewById(R.id.backToMenuButton);
        startButton.setOnClickListener(v -> backToMenu());

        //get score text view and set score
        TextView scoreText = findViewById(R.id.scoreText);
        scoreText.setText("Score: " + score);
    }

    private void backToMenu() {
        //save highscore if it is higher than the old one
        if(score < sharedPreferences.getInt("highscore", 0)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highscore", score);
            editor.apply();
        }

        //Load GameActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}