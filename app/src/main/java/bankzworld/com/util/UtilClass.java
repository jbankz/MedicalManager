package bankzworld.com.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bankzworld.com.R;
import bankzworld.com.activity.LoginActivity;
import bankzworld.com.activity.MainActivity;
import bankzworld.com.activity.SignupActivity;
import bankzworld.com.fragment.MainActivityFragment;

public class UtilClass {
    private static final String TAG = "UtilClass";

    private static FirebaseAuth auth = FirebaseAuth.getInstance();

    public static void getAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Info");
        builder.setCancelable(false);
        builder.setMessage(context.getString(R.string.help_one) + context.getString(R.string.help_two) +
                context.getString(R.string.help_three));
        builder.setIcon(R.drawable.ic_about);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //signs user out
    public static void signOut(Context context) {
        auth.signOut();
        context.startActivity(new Intent(context, LoginActivity.class));
        MainActivity mainActivity = new MainActivity();
        mainActivity.finish();
    }

    /**
     * Deletes users account
     **/
    public static void deleteAccount(final Context context) {
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                context.startActivity(new Intent(context, SignupActivity.class));
                                MainActivity mainActivity = new MainActivity();
                                mainActivity.finish();
                            } else {
                                Toast.makeText(context, "An error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    // checks if user is already logged in
    public static void testForUsersAuthentication(final Context context) {

        final MainActivityFragment fragment = new MainActivityFragment();

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Log.d(TAG, "testForUsersAuthentication: " + user);

        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    fragment.startActivity(new Intent(context, LoginActivity.class));
                    fragment.getActivity().finish();
                }
            }
        };
    }

}
