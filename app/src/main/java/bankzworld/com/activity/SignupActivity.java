package bankzworld.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import bankzworld.com.R;
import bankzworld.com.network.NetworkClass;
import bankzworld.com.pojo.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SignupActivity";

    private FirebaseAuth auth;

    @BindView(R.id.sign_in_button)
    Button btnSignIn;
    @BindView(R.id.sign_up_button)
    Button btnSignUp;
    @BindView(R.id.patient_email)
    TextInputEditText inputEmail;
    @BindView(R.id.patient_password)
    TextInputEditText inputPassword;
    @BindView(R.id.btn_reset_password)
    Button btnResetPassword;
    @BindView(R.id.patient_user_name)
    TextInputEditText mUserName;
    @BindView(R.id.pb_loading)
    ProgressBar mPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ButterKnife.bind(this);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        // set clickListeners
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPb.setVisibility(View.INVISIBLE);
    }

    // test for network
    private void isNetworkAvailable() {
        if (!NetworkClass.isConnected(this)) {
            Toast.makeText(SignupActivity.this, R.string.network_error_message, Toast.LENGTH_LONG).show();
        } else {
            isValidated();
        }
    }

    // validate user inputs
    private boolean isValidated() {
        String userName = mUserName.getText().toString();
        String userEmail = inputEmail.getText().toString();
        String userPassword = inputPassword.getText().toString();

        if (TextUtils.isEmpty(userName)) {
            mUserName.setError(getString(R.string.name_required));
            return false;
        }
        if (TextUtils.isEmpty(userEmail) || !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            inputEmail.setError(getString(R.string.error_valid_email));
            return false;
        }
        if (TextUtils.isEmpty(userPassword) || userPassword.length() < 6) {
            inputPassword.setError(getString(R.string.error_valid_password));
            return false;
        }

        performSignup(userName, userEmail, userPassword);

        return true;
    }

    // performs signup
    private void performSignup(String userName, String userEmail, String userPassword) {
        // show progress
        mPb.setVisibility(View.VISIBLE);
        // save name to preference
        User.saveUserToken(this, userName);

        //create user
        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "onComplete: " + task.isSuccessful());
                        mPb.setVisibility(View.INVISIBLE);
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "onComplete: failure: " + task.getException());
                            Toast.makeText(SignupActivity.this, getString(R.string.network_error_message), Toast.LENGTH_LONG).show();
                        } else {
                            Log.d(TAG, "onComplete: Successful");

                            /**Note: REMEMBER TO COMMENT THIS INTENT LINE IN OTHER TO HAVE AN ACCURATE ESPRESSO TEST RESULT OF THIS ACTIVITY/FILE**/
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.sign_up_button:
                isNetworkAvailable();
                break;
            case R.id.btn_reset_password:
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
                break;
            case R.id.sign_in_button:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
}