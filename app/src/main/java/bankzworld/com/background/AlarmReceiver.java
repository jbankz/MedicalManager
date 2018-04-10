package bankzworld.com.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import bankzworld.com.util.NotificationUtil;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Alarm is running");
        NotificationUtil.remindUserToTakeMedication(context);
    }



}