package ruggedoutdoors.cleanwater.controller;

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

import java.io.File;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.UserManagementFacade;
import ruggedoutdoors.cleanwater.model.UserType;

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
    private Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        // set up the edit form
        mNameTextView = (TextView) findViewById(R.id.editUser_name_label_dynamic);
        mNameTextView.setText(model.getFirstName() + " " + model.getLastName());

        mUsernameTextView = (TextView) findViewById(R.id.editUser_username_label_dynamic);
        mUsernameTextView.setText(model.getUsername());

        mEmailView = (EditText) findViewById(R.id.editUser_email);
        mEmailView.setText(model.getEmail());

        mPhoneView = (EditText) findViewById(R.id.editUser_phone);
        mPhoneView.setText(model.getPhone());

        mAddressView = (EditText) findViewById(R.id.editUser_address);
        mAddressView.setText(model.getAddress());

        mBirthdayView = (EditText) findViewById(R.id.editUser_birthday);
        mBirthdayView.setText(model.getBirthday());

        mUserTypeView = (Spinner) findViewById(R.id.editUser_type);

        /*
          Set up the adapter to display the allowable User Types in the spinner
         */
        ArrayAdapter<String> adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, UserType.values());
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUserTypeView.setAdapter(adapter1);
        mUserTypeView.setSelection(UserType.valueOf(model.getUserType()).ordinal());

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
        String type = mUserTypeView.getSelectedItem().toString();

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
            model.updateUserInfo(birthday, email, phone, address, type);
            model.saveUserData(new File(getFilesDir(), UserManagementFacade.DEFAULT_TEXT_FILE_NAME));
            Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
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

