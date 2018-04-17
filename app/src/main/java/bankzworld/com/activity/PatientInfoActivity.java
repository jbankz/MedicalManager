package bankzworld.com.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import bankzworld.com.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PatientInfoActivity extends AppCompatActivity {

    private static final String TAG = "PatientInfoActivity";

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
    TextInputEditText mUsersTemperature;

    private String gender, name, age, location, bloodGroup, weight, temp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialises view
        ButterKnife.bind(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gender_group);

                int id = radioGroup.getCheckedRadioButtonId();

                RadioButton radioButton = (RadioButton) findViewById(id);

                if (mMaleButton.isChecked() == true) {
                    gender = radioButton.getText().toString();
                } else if (mFemaleButton.isChecked() == true) {
                    gender = radioButton.getText().toString();
                } else {
                    gender = "";
                }

                name = mName.getText().toString();
                age = mAge.getText().toString();
                location = mLocation.getText().toString();
                bloodGroup = mUsersBloodGroup.getText().toString();
                weight = mUsersWeight.getText().toString();
                temp = mUsersTemperature.getText().toString();

                // calls the createUsers Method
                createUser(name, gender, age, location, bloodGroup, weight, temp);

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void createUser(String name, String gender, String age, String location, String bloodGroup, String weight, String temp) {
        // stores details to prefs
        saveUserDetails(name, gender, age, location, bloodGroup, weight, temp);
        // calls next activity
        startActivity(new Intent(PatientInfoActivity.this, ProfileActivity.class));
        finish();

    }

    // stores users data's to preferences
    public void saveUserDetails(String name, String gender, String age, String location, String bloodGroup, String weight, String temp) {
        SharedPreferences.Editor editor = getSharedPreferences("myPrefs", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.putString("gender", gender);
        editor.putString("age", age);
        editor.putString("location", location);
        editor.putString("blood_group", bloodGroup);
        editor.putString("weight", weight);
        editor.putString("temperature", temp);
        editor.apply();
    }
}
