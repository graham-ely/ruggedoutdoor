package ruggedoutdoors.cleanwater;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by gde on 3/3/17.
 *
 * Screen that prompts for report details and allows the user to file a water report.
 */

public class FileReportActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    // UI references.
    private EditText mWaterLocationView;
    private Spinner mWaterTypeView;
    private Spinner mWaterConditionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the file report form.
        mWaterLocationView = (EditText) findViewById(R.id.water_location);
        mWaterTypeView = (Spinner) findViewById(R.id.water_type);
        mWaterConditionView = (Spinner) findViewById(R.id.water_condition);

        /*
          Set up the adapter to display the allowable Water Types & Conditions in the spinner
         */
        ArrayAdapter<String> adapter_water_type = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterType.values());
        adapter_water_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterTypeView.setAdapter(adapter_water_type);

        ArrayAdapter<String> adapter_water_condition = new ArrayAdapter(this, android.R.layout.simple_spinner_item, WaterCondition.values());
        adapter_water_condition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWaterTypeView.setAdapter(adapter_water_condition);

        //TODO: Check if we need this, if so implement for file report
        /*Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });*/

        Button mCancelButton = (Button) findViewById(R.id.register_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
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

        // Store values at the time of the login attempt.
        //TODO: get current user & name
        String waterLocation = mWaterLocationView.getText().toString();
        WaterType waterType = (WaterType) mWaterTypeView.getSelectedItem();
        WaterCondition waterCondition = (WaterCondition) mWaterConditionView.getSelectedItem();

        boolean cancel = false;
        View focusView = null;

        //TODO: Do we want any validation?
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // Add the user to the system and advance to the login screen
            //TODO: Create Reports.java class to store reports and add new report there
            //Users.add(new User(firstName, lastName, username, password, email, phone, birthday, address, type));

            Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);

            startActivity(nextScreen);
        }
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
