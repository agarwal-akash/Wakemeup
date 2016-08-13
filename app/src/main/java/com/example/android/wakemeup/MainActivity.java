package com.example.android.wakemeup;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import static android.os.Build.VERSION_CODES.M;
import static com.example.android.wakemeup.R.id.on;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AlarmManager mAlarmManager;
    TimePicker mTimePicker;
    TextView update_view;
    PendingIntent mPendingIntent;
    Context context;
    int ringtone_choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context=this;
        mAlarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
        mTimePicker=(TimePicker)findViewById(R.id.time_picker);
        update_view=(TextView)findViewById(R.id.setAlarm);
        final Calendar calendar=Calendar.getInstance();
        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.sound_string,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        final Intent intent=new Intent(this.context,Alarm.class);


        Button setOn=(Button)findViewById(on);
        //Set OnClick listener for On button
        setOn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(M)
            @Override
            public void onClick(View v) {
                calendar.set(calendar.HOUR_OF_DAY,mTimePicker.getCurrentHour());
                calendar.set(calendar.MINUTE,mTimePicker.getCurrentMinute());
                int hour=mTimePicker.getCurrentHour();
                String display_hour=String.valueOf(hour);
                int minute=mTimePicker.getCurrentMinute();
                String display_minute=String.valueOf(minute);
                if(hour>12)display_hour=String.valueOf(hour-12);
                if(minute<10)display_minute="0"+String.valueOf(minute);
                update_view.setText("Alarm set to"+display_hour+":"+display_minute);
                intent.putExtra("alarm on/off","alarm_set_on");
                intent.putExtra("ringtone added",ringtone_choice);
                mPendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                mAlarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),mPendingIntent);
            }
        });

        Button setOff=(Button)findViewById(R.id.off);
        //Set OnClick listener for Off button
        setOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_view.setText("Alarm set off!");
                intent.putExtra("alarm on/off","alarm_set_off");
                mAlarmManager.cancel(mPendingIntent);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ringtone_choice=(int)id;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
