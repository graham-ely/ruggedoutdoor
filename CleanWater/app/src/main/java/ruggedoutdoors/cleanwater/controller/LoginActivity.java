package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.login_username);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button mEmailCancelButton = (Button) findViewById(R.id.email_cancel_button);
        mEmailCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void cancelLogin() {
        Intent nextScreen = new Intent(getApplicationContext(), WelcomeActivity.class);

        startActivity(nextScreen);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // try to login with credentials
        else {
            Model model = new Model(this);
            model.open();
            try {
                if (model.logIn(username, password)) {
                    model.addLog(true, "Login", "Success", "None");
                    model.close();
                    Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
                    nextScreen.putExtra("USERNAME", mUsernameView.getText().toString());
                    startActivity(nextScreen);
                }
            } catch (NoSuchElementException e) {
                model.addLog(false, "Login", "Failure", "username not found");
                model.close();
                mUsernameView.setError("This username was not found");
                focusView = mUsernameView;
                cancel = true;
            } catch (InvalidParameterException e) {
                model.addLog(false, "Login", "Failure", "incorrect password");
                model.close();
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                cancel = true;
                mPasswordView.setText("");
                focusView = mPasswordView;
            }
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}

