package com.example.pluggedinalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;

public class RingtonePlayingService extends Service {

    public MediaPlayer mediaSong;
    public int startId;
    public boolean isRunning;
    public Vibrator vbrVibration;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String state = intent.getExtras().getString("extra");
        assert state != null;

        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                break;
            default:
                startId = 0;
                break;
        }

        if (!this.isRunning && startId == 1){

            mediaSong = MediaPlayer.create(this, R.raw.ringtone);
            mediaSong.start();
            this.isRunning = true;
            this.startId = 0;

            //notifications
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);
            Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntentMainActivity = PendingIntent.getActivity(this, 0, intentMainActivity, 0);
            Notification notificationPopup = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.small_icon)
                    .setContentTitle("Plug your phone!!!")
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notificationPopup);


            vbrVibration = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vbrVibration.vibrate(1000);



        } else if (this.isRunning && startId == 0){
            mediaSong.stop();
            mediaSong.reset();
            this.isRunning = false;
            this.startId = 0;
        } else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
        } else if (this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 1;
        } else {

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy(){

        super.onDestroy();
        this.isRunning = false;

    }
}

