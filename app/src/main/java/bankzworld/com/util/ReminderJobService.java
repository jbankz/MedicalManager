package bankzworld.com.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class ReminderJobService extends JobService {

    private static final String TAG = "ReminderJobService";

    private AsyncTask mBackground;

    @Override
    public boolean onStartJob(final JobParameters job) {
        Log.d(TAG, "onStartJob: called");
        mBackground = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = ReminderJobService.this;
                ReminderTask.issueReminder(context);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);
            }
        };
        mBackground.execute();
        return false;
    }

    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        Log.d(TAG, "onStopJob: called");
        if (mBackground != null) mBackground.cancel(true);
        return false;
    }
}
