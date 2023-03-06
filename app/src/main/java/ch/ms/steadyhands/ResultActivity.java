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

        TextView scoreDescText = findViewById(R.id.scoreDescText);
        if(score < 2) {
            scoreDescText.setText("WOW! Your skill to put your phone down is amazing!");
        } else if(score < 40) {
            scoreDescText.setText("Okay... You have my respect! Not bad.");
        } else if(score < 100) {
            scoreDescText.setText("Average...");
        } else if(score < 200) {
            scoreDescText.setText("Getting shaky...");
        } else {
            scoreDescText.setText("Stop doing drugs!");
        }
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