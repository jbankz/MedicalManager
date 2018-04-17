package bankzworld.com.activity;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import bankzworld.com.R;
import bankzworld.com.data.Medication;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";

    @BindView(R.id.users_name)
    TextView mUserName;
    @BindView(R.id.users_gender)
    TextView mUserGender;
    @BindView(R.id.users_age)
    TextView mUserAge;
    @BindView(R.id.users_location)
    TextView mUserLocation;
    @BindView(R.id.users_blood_group)
    TextView mUserBloodGroup;
    @BindView(R.id.users_weight)
    TextView mUsersWeight;
    @BindView(R.id.users_tempereture)
    TextView mUsersTemperature;
    @BindView(R.id.fab)
    FloatingActionButton fb;
    @BindView(R.id.image_profile)
    ImageView mProfileImage;

    private ContextWrapper cw;
    private File directory;
    private ByteArrayOutputStream stream;

    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // injects views
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Medication.getUserName(this) != null) {
            this.setTitle(Medication.getUserName(this));
        } else {
            this.setTitle("Profile");
        }


        cw = new ContextWrapper(getApplicationContext());
        directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        stream = new ByteArrayOutputStream();

        // gets image and loads it up
        getImage();

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, PatientInfoActivity.class));
            }
        });

        // calls the method
        retrieveUserDetails();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_camera:
                cameraIntent();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            mProfileImage.setImageBitmap(photo);

//            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
//
//            Glide.with(this)
//                    .load(stream.toByteArray())
//                    .asBitmap()
//                    .error(R.drawable.image_profile_pic)
//                    .into(mProfileImage);

            // saves the image
            saveImage(photo);
        }
    }

    private String saveImage(Bitmap imageBitmap) {
        File myPath = new File(directory, "profile.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Error: saving image " + e.getMessage());
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error: closing");
            }
        }
        return directory.getAbsolutePath();
    }

    private void getImage() {
        try {
            File f = new File(directory, "profile.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(f));

            mProfileImage.setImageBitmap(bitmap);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e(TAG, "getImage: " + e.getMessage());
            // since its unable to decode the image or unable to get file of image,it then sets a default image
            mProfileImage.setImageResource(R.drawable.image_profile_pic);
        }
    }

    /**
     * Note: when this method is called the first time during the execution of the activity
     * it will return a null value from the preference but when the activity is been
     * called from the PatientInfoActivity, it will then return a stored value because a value will be
     * stored in preferences before calling this activity.
     **/
    private void retrieveUserDetails() {
        SharedPreferences prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        mUserName.setText((prefs.getString("name", null)));
        mUserGender.setText(prefs.getString("gender", null));
        mUserAge.setText(prefs.getString("age", null));
        mUserLocation.setText(prefs.getString("location", null));
        mUserBloodGroup.setText(prefs.getString("blood_group", null));
        mUsersWeight.setText(prefs.getString("weight", null));
        mUsersTemperature.setText(prefs.getString("temperature", null));
    }
}
