package com.example.stopwatch;

// MainActivity.java
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timerTextView;
    private Button startButton, pauseButton, resetButton;
    private boolean isTimerRunning;
    private long startTime, elapsedTime;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        startButton = findViewById(R.id.startButton);
        pauseButton = findViewById(R.id.pauseButton);
        resetButton = findViewById(R.id.resetButton);

        handler = new Handler();
    }

    public void startTimer(View view) {
        if (!isTimerRunning) {
            isTimerRunning = true;
            startTime = System.currentTimeMillis();
            handler.postDelayed(runnable, 0);
        }
    }

    public void pauseTimer(View view) {
        if (isTimerRunning) {
            isTimerRunning = false;
            handler.removeCallbacks(runnable);
            elapsedTime += System.currentTimeMillis() - startTime;
        }
    }

    public void resetTimer(View view) {
        isTimerRunning = false;
        handler.removeCallbacks(runnable);
        elapsedTime = 0;
        timerTextView.setText("00:00:00");
    }

    private void updateTimer() {
        long currentTime = System.currentTimeMillis() - startTime + elapsedTime;
        int minutes = (int) (currentTime / 60000);
        int seconds = (int) ((currentTime / 1000) % 60);
        int milliseconds = (int) (currentTime % 1000);

        String timeString = String.format("%02d:%02d:%03d", minutes, seconds, milliseconds);
        timerTextView.setText(timeString);

        handler.postDelayed(runnable, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateTimer();
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }
}