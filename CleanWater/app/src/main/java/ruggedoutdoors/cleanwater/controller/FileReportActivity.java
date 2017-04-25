package ruggedoutdoors.cleanwater.controller;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.Report;
import ruggedoutdoors.cleanwater.model.Reports;
import ruggedoutdoors.cleanwater.model.User;
import ruggedoutdoors.cleanwater.model.Users;
import ruggedoutdoors.cleanwater.model.WaterCondition;
import ruggedoutdoors.cleanwater.model.WaterType;
import ruggedoutdoors.cleanwater.model.Location;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn on 3/13/17.
 *
 * Screen that prompts for report details and allows the user to file a water report.
 */

public class FileReportActivity extends AppCompatActivity {

    // UI references.
    private EditText mWaterLocationLatView;
    private EditText mWaterLocationLonView;
    private Spinner mWaterTypeView;
    private Spinner mWaterConditionView;

    private Model model = new Model(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_report);

        // Set up the file report form.
        mWaterLocationLatView = (EditText) findViewById(R.id.water_location_latitude);
        mWaterLocationLonView = (EditText) findViewById(R.id.water_location_longitude);
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
        mWaterConditionView.setAdapter(adapter_water_condition);

        Button mFileReportButton = (Button) findViewById(R.id.file_report_button);
        mFileReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptReport();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.file_report_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
                startActivity(nextScreen);
            }
        });
    }

    /**
     * Attempts to file a water source report.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptReport() {

        //check for valid

        // Store values at the time of the login attempt.
        String waterLocationLat = mWaterLocationLatView.getText().toString();
        String waterLocationLon = mWaterLocationLonView.getText().toString();
        WaterType waterType = (WaterType) mWaterTypeView.getSelectedItem();
        WaterCondition waterCondition = (WaterCondition) mWaterConditionView.getSelectedItem();
        double lat = 0.0;
        double lon = 0.0;

        boolean cancel = false;
        View focusView = null;

        try {
            lat = Double.valueOf(waterLocationLat);
            if (lat < -90.0 || lat > 90.0) {
                mWaterLocationLatView.setError(getString(R.string.error_invalid_latitude));
                focusView = mWaterLocationLatView;
                cancel = true;
            }
        } catch (NumberFormatException e) {
            mWaterLocationLatView.setError(getString(R.string.error_invalid_latitude));
            focusView = mWaterLocationLatView;
            cancel = true;
        }


        try {
            lon = Double.valueOf(waterLocationLon);
            if (lon < -180.0 || lon > 180.0) {
                mWaterLocationLonView.setError(getString(R.string.error_invalid_longitude));
                focusView = mWaterLocationLonView;
                cancel = true;
            }
        } catch (NumberFormatException e) {
            mWaterLocationLonView.setError(getString(R.string.error_invalid_longitude));
            focusView = mWaterLocationLonView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // Add the report to the system and advance to the homescreen
            model.open();
            model.addSourceReport(String.valueOf(lat), String.valueOf(lon), waterType.toString(), waterCondition.toString());
            model.addLog(true, "Added Source Report", "Success", "None");
            model.close();
            Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
            startActivity(nextScreen);
        }
    }
}
