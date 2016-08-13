package com.example.android.wakemeup;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

import static android.media.MediaPlayer.create;

/**
 * Created by Akash on 12-08-2016.
 */

public class PlayRingtone extends Service {
    MediaPlayer mMediaPlayer;
    int alarm_id;
    boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id" + startId + ":" + intent);
        Log.e("Playringtone", "Reached here!");
        String get_alarm_state = intent.getExtras().getString("alarm on/off");
        Integer get_ringtone = intent.getExtras().getInt("ringtone to Play");
        Log.e("Alarm ringtone is", String.valueOf(get_ringtone));
        assert get_alarm_state != null;
        switch (get_alarm_state) {
            case "alarm_set_on":
                alarm_id = 1;
                break;
            case "alarm_set_off":
                alarm_id = 0;
                break;
            default:
                alarm_id = 0;
                break;
        }

        if (!this.isRunning && alarm_id == 1) {
            Log.e("Music going to play", "pressed start");
            this.isRunning = true;
            alarm_id = 0;
            //Setting up notification display
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent to_main = new Intent(this.getApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, to_main, 0);
            Notification notifs = new Notification.Builder(this)
                    .setContentTitle("An Alarm is going on,wake up!")
                    .setContentText("Click to set off")
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    //.setColor(getResources().getColor(R.color.notifsColor))-->produces error in API-17 TODO-?
                    .setSmallIcon(R.mipmap.ic_clock)
                    //.setVibrate(new long[]{0,1000,1000,1000,1000})-->produces error in API-17 TODO-?
                    .build();
            notificationManager.notify(0, notifs);
            if (get_ringtone == 0) {
                Random random = new Random();
                int value = random.nextInt(5) + 1;
                Log.e("Playing random value",String.valueOf(value));
                switch (value) {
                    case 1:
                        mMediaPlayer = create(this, R.raw.wake_up_lol);
                        mMediaPlayer.start();
                        break;
                    case 2:
                        mMediaPlayer = create(this, R.raw.lg_good_morning);
                        mMediaPlayer.start();
                        break;
                    case 3:
                        mMediaPlayer = create(this, R.raw.simple_office_tone);
                        mMediaPlayer.start();
                        break;
                    case 4:
                        create(this, R.raw.vampire_call);
                        mMediaPlayer.start();
                        break;
                    case 5:
                        mMediaPlayer = create(this, R.raw.alarm);
                        mMediaPlayer.start();
                        break;
                }
            }
            else if (get_ringtone==1) {
                    mMediaPlayer = create(this, R.raw.wake_up_lol);
                    mMediaPlayer.start();

                }
            else if (get_ringtone==2) {
                    mMediaPlayer = create(this, R.raw.lg_good_morning);
                    mMediaPlayer.start();

                }
            else if (get_ringtone==3) {
                    mMediaPlayer = create(this, R.raw.simple_office_tone);
                    mMediaPlayer.start();

                }
            else if (get_ringtone==4) {
                    mMediaPlayer=create(this, R.raw.vampire_call);
                    mMediaPlayer.start();

                }
            else if (get_ringtone==5) {
                    mMediaPlayer = create(this, R.raw.alarm);
                    mMediaPlayer.start();

                }


        } else if (this.isRunning && alarm_id == 0) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            this.isRunning = false;
            alarm_id = 0;

        } else if (this.isRunning && alarm_id == 1) {
            this.isRunning = true;
            alarm_id = 1;
        } else if (!this.isRunning && alarm_id == 0) {
            this.isRunning = false;
            alarm_id = 0;
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
