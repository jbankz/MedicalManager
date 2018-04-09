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

    public static void issueReminder(Context context) {
        Log.d(TAG, "issueReminder: called");
        NotificationUtil.remindUserToTakeMedication(context);
    }


}
