package bankzworld.com.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import bankzworld.com.R;
import bankzworld.com.fragment.MainActivityFragment;
import bankzworld.com.network.NetworkClass;
import bankzworld.com.util.NotificationUtil;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialises views
        ButterKnife.bind(this);

        // initialises the auth
        auth = FirebaseAuth.getInstance();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // new MainActivityFragment
        MainActivityFragment fragment = new MainActivityFragment();
        // gets am instance of the getSupportFragmentManager class
        FragmentManager fragmentManager = getSupportFragmentManager();
        // perform fragment transaction
        fragmentManager.beginTransaction()
                .add(R.id.container, fragment)
                .commit();

    }

    /**
     * Deletes users account
     **/
    private void deleteAccount() {
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(MainActivity.this, SignupActivity.class));
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //signs user out
    public void signOut() {
        auth.signOut();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_set_alarm) {
            // disables the alarm
            getAlarmDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
        builder.setCancelable(false);
        builder.setMessage((CharSequence) getString(R.string.help_one) +
                getString(R.string.help_two) +
                getString(R.string.help_three));
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


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            getAlertDialog();
        } else if (id == R.id.nav_log_out) {
            // logs user out
            if (!NetworkClass.isConnected(this)) {
                Toast.makeText(MainActivity.this, R.string.network_error_message, Toast.LENGTH_LONG).show();
            } else {
                signOut();
            }

        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_delete) {
            if (!NetworkClass.isConnected(this)) {
                Toast.makeText(MainActivity.this, R.string.network_error_message, Toast.LENGTH_LONG).show();
            } else {
                deleteAccount();
            }
        } else if (id == R.id.nav_alarm) {
            NotificationUtil.setAlarm(MainActivity.this, "");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void getAlarmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final TextInputEditText editText = new TextInputEditText(this);
        editText.setHint("Time interval");
        editText.setInputType(2);
        builder.setTitle("Alarm");
        builder.setCancelable(false);
        builder.setView(editText);
        builder.setIcon(R.drawable.ic_get_dark_alarm);
        builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String getAlarm = editText.getText().toString();
                NotificationUtil.setAlarm(MainActivity.this, getAlarm);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Abort", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}