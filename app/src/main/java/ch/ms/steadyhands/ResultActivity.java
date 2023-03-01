package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //get start Button and add listener
        Button startButton = findViewById(R.id.backToMenuButton);
        startButton.setOnClickListener(v -> backToMenu());
    }

    private void backToMenu() {
        //Load GameActivity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}