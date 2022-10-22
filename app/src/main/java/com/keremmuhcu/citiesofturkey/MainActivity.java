package com.keremmuhcu.citiesofturkey;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button normalGameBTN, timedGameBTN, exitBTN;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        normalGameBTN = findViewById(R.id.normalGameBTN);
        timedGameBTN = findViewById(R.id.timedGameBTN);
        exitBTN = findViewById(R.id.exitBTN);

    }



    public void mainPage(View view) {
        if (view.getId() == normalGameBTN.getId()) {
            intent = new Intent(this, NormalGameActivity.class);
            startActivity(intent);
        } else if (view.getId() == timedGameBTN.getId()) {
            intent = new Intent(this, TimedGameActivity.class);
            startActivity(intent);
        } else {
            finish();
        }
    }
}