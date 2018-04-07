package bankzworld.com.util;

import android.content.Context;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderTask {

    private static final String TAG = "ReminderTask";

    public static final int REMINDER_INTERVAL_MINUTE = 1;
    public static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTE));
    public static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    public static final String REMINDER_JOB_TAG = "reminder_tag";

    public static boolean sInitialized;

    public static void issueReminder(Context context) {
        Log.d(TAG, "issueReminder: called");
        NotificationUtil.remindUserToTakeMedication(context);
    }

    synchronized public static void scheduleReminder(Context context) {
        Log.d(TAG, "scheduleReminder: called");
        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);
        Job reminderJob = dispatcher.newJobBuilder()
                .setService(ReminderJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(reminderJob);
    }

}
