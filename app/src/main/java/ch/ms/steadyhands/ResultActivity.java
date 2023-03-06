package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
        //Load GameActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}