package com.katfishi.scheduledthreadpoolexecutor_delay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mWrapperLayout;
    private SwitchCompat mSwitchCompat;

    private ScheduledExecutorService mExecutorService;

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

        mExecutorService = Executors.newScheduledThreadPool(2);
        mSwitchCompat = (SwitchCompat) findViewById(R.id.task_switch);
    }

    private void startTimer() {
        Runnable lengthyTask = new Runnable() {
            @Override
            public void run() {
                lengthyOperation();
            }
        };

        Runnable delayedTask = new Runnable() {
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

        mExecutorService.schedule(delayedTask, 1000, TimeUnit.MILLISECONDS);
        if (mSwitchCompat.isChecked()) {
            mExecutorService.schedule(lengthyTask, 0, TimeUnit.MILLISECONDS);
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
        mExecutorService.shutdownNow();
    }
}
