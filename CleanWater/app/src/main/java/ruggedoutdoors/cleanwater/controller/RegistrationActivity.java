package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.User;
import ruggedoutdoors.cleanwater.model.UserType;

/**
 * A login screen that offers login via email/password.
 */
public class RegistrationActivity extends AppCompatActivity {

    // UI references.
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private EditText mAddressView;
    private EditText mBirthdayView;
    private Spinner  mUserTypeView;

    //set up firebase
    private FirebaseDatabase database;
    private DatabaseReference mDatabase = database.getInstance().getReference("users");


    private Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the login form.
        mFirstNameView = (EditText) findViewById(R.id.register_first_name);
        mLastNameView = (EditText) findViewById(R.id.register_last_name);
        mUsernameView = (EditText) findViewById(R.id.register_username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordConfirmView = (EditText) findViewById(R.id.passwordConfirm);
        mEmailView = (EditText) findViewById(R.id.register_email);
        mPhoneView = (EditText) findViewById(R.id.register_phone);
        mAddressView = (EditText) findViewById(R.id.register_address);
        mBirthdayView = (EditText) findViewById(R.id.register_birthday);
        mUserTypeView = (Spinner) findViewById(R.id.register_type);

        /*
          Set up the adapter to display the allowable User Types in the spinner
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UserType.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserTypeView.setAdapter(adapter1);

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.register_cancel_button);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(nextScreen);
            }
        });
    }

    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Store values at the time of the login attempt.
                String firstName = mFirstNameView.getText().toString();
                String lastName = mLastNameView.getText().toString();
                final String username = mUsernameView.getText().toString();
                String password = mPasswordView.getText().toString();
                String passwordConfirm = mPasswordConfirmView.getText().toString();
                String email = mEmailView.getText().toString();
                String phone = mPhoneView.getText().toString();
                String address = mAddressView.getText().toString();
                String birthday = mBirthdayView.getText().toString();
                String type = mUserTypeView.getSelectedItem().toString();

                boolean cancel = false;
                View focusView = null;

                // Check for a valid first name.
                if (TextUtils.isEmpty(firstName)) {
                    mFirstNameView.setError(getString(R.string.error_field_required));
                    focusView = mFirstNameView;
                    cancel = true;
                }

                // Check for a valid last name.
                else if (TextUtils.isEmpty(lastName)) {
                    mLastNameView.setError(getString(R.string.error_field_required));
                    focusView = mLastNameView;
                    cancel = true;
                }

                // Check for valid username
                else if (TextUtils.isEmpty(username)) {
                    mUsernameView.setError(getString(R.string.error_field_required));
                    focusView = mUsernameView;
                    cancel = true;
                } else if (dataSnapshot.hasChild(username)) {
                    mUsernameView.setError(getString(R.string.error_duplicate_username));
                    focusView = mUsernameView;
                    cancel = true;
                }

                // Check for a valid password, if the user entered one.
                else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
                    mPasswordView.setError(getString(R.string.error_invalid_password));
                    focusView = mPasswordView;
                    cancel = true;
                }

                // Checks that passwords are the same
                else if (!password.equals(passwordConfirm)) {
                    mPasswordView.setError("Passwords must match!");
                    mPasswordConfirmView.setText("");
                    mPasswordView.setText("");
                    focusView = mPasswordConfirmView;
                    cancel = true;
                }

                // Check for a valid email address.
                else if (TextUtils.isEmpty(email)) {
                    mEmailView.setError(getString(R.string.error_field_required));
                    focusView = mEmailView;
                    cancel = true;
                } else if (!isEmailValid(email)) {
                    mEmailView.setError(getString(R.string.error_invalid_email));
                    focusView = mEmailView;
                    cancel = true;
                }

                // Check for valid phone number
                else if (TextUtils.isEmpty(phone)) {
                    mPhoneView.setError(getString(R.string.error_field_required));
                    focusView = mPhoneView;
                    cancel = true;
                } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phone)) {
                    mPhoneView.setError("Invalid phone number");
                    focusView = mPhoneView;
                    cancel = true;
                }

                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();

                } else {
                    // Add the user to the system and advance to the login screen
                    mDatabase.child(username).setValue(new User(firstName, lastName, username, password, email, phone, birthday, address, UserType.valueOf(type)));
                    model.addUser(firstName, lastName, username, password, email, phone, birthday, address,
                            type);

                    Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(nextScreen);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * checks for valid email
     *
     * @param email to check
     * @return whether string is a valid email
     */
    private boolean isEmailValid(String email) {
        //TODO: Update validation logic
        return email.contains("@");
    }

    /**
     * checks for valid password
     *
     * @param password to check
     * @return whether string is a valid password
     */
    private boolean isPasswordValid(String password) {
        //TODO: Update validation logic
        return password.length() > 4;
    }

}