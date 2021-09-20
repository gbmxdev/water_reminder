package com.gbmxdev.waterreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    configure alarm for reminder
    configure status reminder pop up


bugs:
    unkown
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int i = intent.getIntExtra("your_condition", 0);
        if(i>0){
            this.createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Water Reminder")
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Water Reminder!")
                    .setContentText("Please have a glass of water.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, builder.build());
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void start(View view) throws IOException {
        EditText editText = (EditText) findViewById(R.id.editTextTime);
        String timeInt = editText.getText().toString();

        // need to gather the config options for timer from the settings activity
//this will be the intent passed to set the notification
        Intent intent = new Intent();
        intent.putExtra("your_condition", 1);
        //startActivity(intent);
        this.onNewIntent(intent);
//this creates the notification
//this is the alarm to set the notification
        this.alarmSetter(intent);
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Water Reminder", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    private void alarmSetter(Intent intent){
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