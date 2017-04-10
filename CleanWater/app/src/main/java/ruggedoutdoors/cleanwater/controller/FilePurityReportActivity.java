package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.io.File;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;
import ruggedoutdoors.cleanwater.model.OverallCondition;
import ruggedoutdoors.cleanwater.model.PurityReportManagementFacade;

/**
 * Created by gde on 3/3/17.
 * Modified by Austin Dunn on 3/13/17.
 *
 * Screen that prompts for report details and allows the user to file a water report.
 */

public class FilePurityReportActivity extends AppCompatActivity {

    // UI references.
    private EditText mWaterLocationLatView;
    private EditText mWaterLocationLonView;
    private Spinner mOverallCondition;
    private EditText mVirusPPM;
    private EditText mContaminantPPM;
    private final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_purity_report);

        // Set up the file report form.
        mWaterLocationLatView = (EditText) findViewById(R.id.water_location_latitude);
        mWaterLocationLonView = (EditText) findViewById(R.id.water_location_longitude);
        mOverallCondition = (Spinner) findViewById(R.id.overall_condition);
        mVirusPPM = (EditText) findViewById(R.id.virus_ppm);
        mContaminantPPM = (EditText) findViewById(R.id.contaminant_ppm);

        /*
          Set up the adapter to display the allowable Water Types & Conditions in the spinner
         */
        ArrayAdapter<String> adapter_water_type = new ArrayAdapter(this, android.R.layout.simple_spinner_item, OverallCondition.values());
        adapter_water_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverallCondition.setAdapter(adapter_water_type);

        Button mFileReportButton = (Button) findViewById(R.id.file_purity_report_button);
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
        String overallCondition = mOverallCondition.getSelectedItem().toString();
        String virusPPM = mVirusPPM.getText().toString();
        String contaminantPPM = mContaminantPPM.getText().toString();
        double lat = 0.0;
        double lon = 0.0;
        double vPPM = 0.0;
        double cPPM = 0.0;

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

        try {
            vPPM = Double.valueOf(virusPPM);
            if (vPPM < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mVirusPPM.setError("Invalid Number.");
            focusView = mVirusPPM;
            cancel = true;
        }

        try {
            cPPM = Double.valueOf(contaminantPPM);
            if (cPPM < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mContaminantPPM.setError("Invalid Number.");
            focusView = mContaminantPPM;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();

        } else {
            // Add the report to the system and advance to the homescreen
            model.addPurityReport(lat, lon, overallCondition, vPPM, cPPM);
            model.savePurityReportData(new File(getFilesDir(), PurityReportManagementFacade.PURITY_REPORT_TEXT_FILE_NAME));
            Intent nextScreen = new Intent(getApplicationContext(), HomescreenActivity.class);
            startActivity(nextScreen);
        }
    }
}
