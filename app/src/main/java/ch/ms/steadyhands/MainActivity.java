package ch.ms.steadyhands;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get start Button and add listener
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startGame());
    }


    private void startGame() {
        //Load GameActivity
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}