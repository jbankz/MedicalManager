package bankzworld.com.util;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.Calendar;

import bankzworld.com.activity.MainActivity;
import bankzworld.com.R;
import bankzworld.com.background.MyBroadcastReceiver;

import static android.content.Context.ALARM_SERVICE;

public class NotificationUtil {

    private static final int MEDICATION_REMINDER_PENDING_INTENT = 10;
    private static final int NOTIFICATION_ID = 11;
    private static PendingIntent pendingIntent;
    private static AlarmManager alarmManager;
    private static Intent intent;

    /**
     * create a contentIntent helper method
     **/
    private static PendingIntent contentItent(Context context) {

        // create an intent that opens up the MainActivity
        Intent startMainActivityIntent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(
                context,
                MEDICATION_REMINDER_PENDING_INTENT,
                startMainActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * helper function for decoding image
     **/
    private static Bitmap largeIcon(Context context) {
        Resources resources = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(resources, R.drawable.notification_icon);
        return largeIcon;
    }


    /**
     * helper method for building the notification
     *
     * @param context
     */
    public static void remindUserToTakeMedication(Context context) {
        // set a notification builder
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.notification_icon)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentItent(context))
                .setSound(Uri.parse("android.resource://bankzworld.com/" + R.raw.tone))
                .setAutoCancel(true);

        // if build version is greater than jellybean, set its priority to high
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    // this method is used to set the alarm
    public static void setAlarm(Context context, String time) {
        if (!time.equals("")) {
            int i = Integer.parseInt(time);
            intent = new Intent(context, MyBroadcastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 280192, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + (i * 1000), 10000
                    , pendingIntent);
            Toast.makeText(context, "Alarm will set in " + i + " seconds", Toast.LENGTH_LONG).show();
        } else {
            if (alarmManager != null) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(context, "Alarm Disabled !!", Toast.LENGTH_LONG).show();

            }
        }
    }

}
