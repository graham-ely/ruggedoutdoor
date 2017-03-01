package ruggedoutdoors.cleanwater;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * An edit screen that allows the user to update his/her details
 */
public class EditUserActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    // UI references.
    private TextView mNameTextView;
    private TextView mUsernameTextView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private EditText mAddressView;
    private EditText mBirthdayView;
    private Spinner mUserTypeView;
    private User me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // Set up the edit form.
        String username = getIntent().getStringExtra("USERNAME");
        me = Users.getUser(username);

        mNameTextView = (TextView) findViewById(R.id.editUser_name_label_dynamic);
        mNameTextView.setText(me.getFirstName() + " " + me.getLastName());

        mUsernameTextView = (TextView) findViewById(R.id.editUser_username_label_dynamic);
        mUsernameTextView.setText(me.getUsername());

        mEmailView = (EditText) findViewById(R.id.editUser_email);
        mEmailView.setText(me.getEmail());

        mPhoneView = (EditText) findViewById(R.id.editUser_phone);
        mPhoneView.setText(me.getPhone());

        mAddressView = (EditText) findViewById(R.id.editUser_address);
        mAddressView.setText(me.getAddress());

        mBirthdayView = (EditText) findViewById(R.id.editUser_birthday);
        mBirthdayView.setText(me.getBirthday());

        mUserTypeView = (Spinner) findViewById(R.id.editUser_type);

        /*
          Set up the adapter to display the allowable User Types in the spinner
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UserType.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserTypeView.setAdapter(adapter1);
        mUserTypeView.setSelection(me.getUserType().ordinal());

        Button mSubmitButton = (Button) findViewById(R.id.editUser_submit_button);
        mSubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptEdit();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.editUser_cancel_button);
        mCancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
                nextScreen.putExtra("USERNAME", me.getUsername());
                startActivity(nextScreen);
            }
        });

    }

    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptEdit() {
        // Store values at the time of the edit attempt.
        String email = mEmailView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String address = mAddressView.getText().toString();
        String birthday = mBirthdayView.getText().toString();
        UserType type = (UserType) mUserTypeView.getSelectedItem();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
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
            // There was an error; don't attempt edit and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // Update the fields changed, then progress to the next screen
            me.setAddress(address);
            me.setBirthday(birthday);
            me.setPhone(phone);
            me.setEmail(email);
            me.setUserType(type);

            Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
            nextScreen.putExtra("USERNAME", me.getUsername());
            startActivity(nextScreen);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Update validation logic
        return email.contains("@");
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {}

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {}
}

