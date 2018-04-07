package bankzworld.com.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import bankzworld.com.R;
import bankzworld.com.pojo.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class EditInfoActivity extends AppCompatActivity {

    private static final String TAG = "EditInfoActivity";

    @BindView(R.id.patient_name)
    TextInputEditText mName;
    @BindView(R.id.rdb_male)
    RadioButton mMaleButton;
    @BindView(R.id.rdb_female)
    RadioButton mFemaleButton;
    @BindView(R.id.patient_age)
    TextInputEditText mAge;
    @BindView(R.id.patient_location)
    TextInputEditText mLocation;
    @BindView(R.id.users_blood_group)
    TextInputEditText mUsersBloodGroup;
    @BindView(R.id.users_weight)
    TextInputEditText mUsersWeight;
    @BindView(R.id.users_tempereture)
    TextInputEditText mUsersTempereture;
    private ProgressDialog progressDialog;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    public String userId, gender, name, age, location, bloodGroup, weight, temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialises view
        ButterKnife.bind(this);

        // set progressBar
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gender_group);

                int id = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton) findViewById(id);

                gender = radioButton.getText().toString();
                name = mName.getText().toString();
                age = mAge.getText().toString();
                location = mLocation.getText().toString();
                bloodGroup = mUsersBloodGroup.getText().toString();
                weight = mUsersWeight.getText().toString();
                temp = mUsersTempereture.getText().toString();

                createUser(name, gender, age, location, bloodGroup, weight, temp);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialises firebase
        mFirebaseInstance = FirebaseDatabase.getInstance();

        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("title").setValue("Database");

        // app_title change listener
        mFirebaseInstance.getReference("title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // update toolbar title
                getSupportActionBar().setTitle("Edit Profile");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

    }

    private void createUser(String name, String gender, String age, String location, String bloodGroup, String weight, String temp) {
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }

        // displays progressBar
        progressDialog.show();

        User user = new User(name, gender, age, location, bloodGroup, weight, temp);

        mFirebaseDatabase.child(userId).setValue(user);

        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });

        // hides progressBar
        progressDialog.hide();

        // stores details to prefs
        saveUserDetails(name, gender, age, location, bloodGroup, weight, temp);

        // calls next activity
        startActivity(new Intent(EditInfoActivity.this, SettingsActivity.class));

        // ends activity
        finish();
    }

    // stores users data's to preferences
    private void saveUserDetails(String name, String gender, String age, String location, String bloodGroup, String weight, String temp) {
        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("gender", gender);
        editor.putString("age", age);
        editor.putString("location", location);
        editor.putString("blood_group", bloodGroup);
        editor.putString("weight", weight);
        editor.putString("tempereture", temp);
        editor.apply();
    }
}
