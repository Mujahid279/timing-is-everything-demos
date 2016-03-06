package com.katfishi.alarm_manager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mButton;

    private AlarmManager mAlarmManager;
    private PendingIntent mPendingIntent;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFiveSecondAlarm();
            }
        });

        registerAlarm();
    }

    private void startFiveSecondAlarm() {
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, mPendingIntent);
    }

    public static class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent launchIntent = new Intent(context, AlertActivity.class);
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(launchIntent);
        }
    }

    private void registerAlarm() {
        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        mPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    private void unregisterAlarm() {
        mAlarmManager.cancel(mPendingIntent);
    }

    @Override
    protected void onDestroy() {
        unregisterAlarm();
        super.onDestroy();
    }
}
