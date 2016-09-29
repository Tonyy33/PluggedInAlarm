package com.example.pluggedinalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;

public class RingtonePlayingService extends Service {

    private static final String TAG = "RingtonePlayingService";
    public MediaPlayer mediaSong;
    public Vibrator vbrVibration;
    public RingtoneServiceBatteryInfoReceiver receiver = new RingtoneServiceBatteryInfoReceiver();

    public int startId;
    public boolean isRunning;
    public boolean isCharging;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " Service on create");

        registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

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
        updateChargingState(registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED)));

        if (!this.isRunning && startId == 1 && !isCharging) {

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
                    .setContentTitle("Monitor State")
                    .setContentIntent(pendingIntentMainActivity)
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(0, notificationPopup);


            vbrVibration = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            vbrVibration.vibrate(1000);


        } else if (this.isRunning && startId == 0) {
            mediaSong.stop();
            mediaSong.reset();
            this.isRunning = false;
            this.startId = 0;
        } else if (!this.isRunning && startId == 0) {
            this.isRunning = false;
            this.startId = 0;
        } else if (this.isRunning && startId == 1) {
            this.isRunning = true;
            this.startId = 1;
        } else {

        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, " Service on destroy ");
        super.onDestroy();
        this.isRunning = false;
        if (mediaSong != null) {
            mediaSong.release();
        }
        unregisterReceiver(receiver);
    }

    public class RingtoneServiceBatteryInfoReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, " Service on receive ");
            updateChargingState(intent);
            Log.d(TAG, " Is charging? " + isCharging);
            if (isCharging) {
                stopSelf();
            }
        }
    }

    private void updateChargingState(Intent intent) {
        if (intent != null) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            isCharging = plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
        }
    }
}

