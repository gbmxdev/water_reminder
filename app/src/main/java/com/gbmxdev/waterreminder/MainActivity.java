package com.gbmxdev.waterreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.Set;

/*
This is my first application wrote in java for the google play market. It will be opensourced and available on github
and fdroid for free. It will be sold on the play market.

Todo:
    configure alarm
    configure status reminder
    

bugs:
    unkown
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void start(View view) throws IOException {
        EditText editText = (EditText) findViewById(R.id.editTextTime);
        String timeInt = editText.getText().toString();

        // need to gather the config options for timer from the settings activity
        Intent intent = new Intent();
        Context context = this;
        int requestId = 0;
        PendingIntent pendingIntent =  PendingIntent.getService(context, requestId, intent,  PendingIntent.FLAG_NO_CREATE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HALF_HOUR, AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
        if (pendingIntent != null && alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}