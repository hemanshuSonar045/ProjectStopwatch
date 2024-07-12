package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay;
    private Button btnStart, btnStop, btnLap, btnReset;
    private ListView lapTimesList;
    private ArrayList<String> lapTimes;
    private LapTimeAdapter lapTimeAdapter;

    private Handler handler = new Handler();
    private long startTime, timeInMillis;
    private boolean isRunning = false;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                timeInMillis = System.currentTimeMillis() - startTime;
                updateDisplay();
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.timeDisplay);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnLap = findViewById(R.id.btnLap);
        btnReset = findViewById(R.id.btnReset);
        lapTimesList = findViewById(R.id.lapTimesList);

        lapTimes = new ArrayList<>();
        lapTimeAdapter = new LapTimeAdapter(this, lapTimes);
        lapTimesList.setAdapter(lapTimeAdapter);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis() - timeInMillis;
                    handler.postDelayed(runnable, 0);
                    isRunning = true;
                    Toast.makeText(MainActivity.this, "Started", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                    Toast.makeText(MainActivity.this, "Stopped", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    String lapTime = formatTime(timeInMillis);
                    lapTimes.add(lapTime);
                    lapTimeAdapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Lap Recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                isRunning = false;
                timeInMillis = 0;
                startTime = 0;
                lapTimes.clear();
                lapTimeAdapter.notifyDataSetChanged();
                updateDisplay();
                Toast.makeText(MainActivity.this, "Reset", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateDisplay() {
        timeDisplay.setText(formatTime(timeInMillis));
    }

    private String formatTime(long millis) {
        int seconds = (int) (millis / 1000) % 60;
        int minutes = (int) ((millis / (1000 * 60)) % 60);
        int hours = (int) ((millis / (1000 * 60 * 60)) % 24);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds);
    }
    void animateButton(View view) {
        view.animate().rotationBy(360).setDuration(500).start();
    }
}

