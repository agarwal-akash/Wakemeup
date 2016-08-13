package com.example.android.wakemeup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Akash on 12-08-2016.
 */

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Alarm.java","Reached Alarmmmm!!!");
        String fetch_extra=intent.getExtras().getString("alarm on/off");
        Integer fetch_ringtone=intent.getExtras().getInt("ringtone added");
        Log.e("The ringtone id is",String.valueOf(fetch_ringtone));
        Intent intent_to_playRingtone=new Intent(context,PlayRingtone.class);
        intent_to_playRingtone.putExtra("alarm on/off",fetch_extra);
        intent_to_playRingtone.putExtra("ringtone to Play",fetch_ringtone);
        context.startService(intent_to_playRingtone);
    }

}
