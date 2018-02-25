package com.stektpotet.lab02;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Created by halvor on 24.02.18.
 */

public class FeedFetcherClock {

    public static final String TAG = FeedFetcherClock.class.getName();

    private AlarmManager mAlarmManager;
    private PendingIntent mAlarmIntent;

    private Context mContext;

    public FeedFetcherClock(Context context) {
        mContext = context;
        mAlarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(Intent intent, long frequency) {
        if(mAlarmIntent != null) {
            cancelAlarm(); //cancel previous alarm
        }

        mAlarmIntent = PendingIntent.getService(mContext,0,intent,0);
        Log.d(TAG +".setService", intent.toString());

        mAlarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis()+1000,
                1000*10, //TODO set this to the actual wanted refresh rate.
                mAlarmIntent);
    }

    public void cancelAlarm() {
        mAlarmManager.cancel(mAlarmIntent);
    }
}
