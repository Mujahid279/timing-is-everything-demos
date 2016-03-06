package com.katfishi.timer_delay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mWrapperLayout;
    private SwitchCompat mSwitchCompat;

    private Timer mTimer;

    private boolean isColored = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWrapperLayout = (LinearLayout) findViewById(R.id.wrapper_layout);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });
        mSwitchCompat = (SwitchCompat) findViewById(R.id.task_switch);
    }

    private void startTimer() {
        mTimer = new Timer();
        TimerTask lengthyTask = new TimerTask() {
            @Override
            public void run() {
                lengthyOperation();
            }
        };

        TimerTask delayedTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        flipBackgroundColor();
                    }
                });
            }
        };

        mTimer.schedule(delayedTask, 1000); // supposed to run in 1 second
        if (mSwitchCompat.isChecked()) {
            mTimer.schedule(lengthyTask, 0);
        }
    }

    private void lengthyOperation() {
        int sum = 0;
        for (int r = 0; r < 8; r++) {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sum += 1;
            }
        }
    }

    private void flipBackgroundColor() {
        if (isColored) {
            mWrapperLayout.setBackgroundColor(getResources().getColor(android.R.color.white));
        } else {
            mWrapperLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        isColored = !isColored;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}
