package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;

public class HomescreenActivity extends AppCompatActivity {

    Spinner mUnblockUserSpinner;
    Spinner mBlockUserSpinner;
    Model model = new Model(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        model.open();

        Button mFileReportButton = (Button) findViewById(R.id.fileReportButton);
        if (model.canFileSourceReport()) {
            mFileReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), FileReportActivity.class);
                    startActivity(nextScreen);
                }
            });
            mFileReportButton.setVisibility(View.VISIBLE);
        } else {
            mFileReportButton.setVisibility(View.GONE);
        }


        Button mLogoutButton = (Button) findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(nextScreen);
            }
        });

        Button mEditUserButton = (Button) findViewById(R.id.editUserButton);
        mEditUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), EditUserActivity.class);
                startActivity(nextScreen);
            }
        });

        Button mViewReportsButton = (Button) findViewById(R.id.viewReportsButton);
        if (model.canViewSourceReports()) {
            mViewReportsButton.setVisibility(View.VISIBLE);
            mViewReportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), ReportListActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mViewReportsButton.setVisibility(View.GONE);
        }

        Button mFilePurityReportButton = (Button) findViewById(R.id.filePurityReportButton);
        if (model.canFilePurityReport()) {
            mFilePurityReportButton.setVisibility(View.VISIBLE);
            mFilePurityReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), FilePurityReportActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mFilePurityReportButton.setVisibility(View.GONE);
        }


        Button mViewPurityReportsButton = (Button) findViewById(R.id.viewPurityReportsButton);
        if (model.canViewPurityReport()) {
            model.close();
            mViewPurityReportsButton.setVisibility(View.VISIBLE);
            mViewPurityReportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), PurityReportListActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            model.close();
            mViewPurityReportsButton.setVisibility(View.GONE);
        }

        Button mViewMapButton = (Button) findViewById(R.id.viewMapButton);
        if (model.canViewMap()) {
            mViewMapButton.setVisibility(View.VISIBLE);
            mViewMapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), MapActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mViewMapButton.setVisibility(View.GONE);
        }

        Button mHistoryGraphButton = (Button) findViewById(R.id.historyGraphButton);
        if (model.canViewGraph()) {
            mHistoryGraphButton.setVisibility(View.VISIBLE);
            mHistoryGraphButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), GraphActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mHistoryGraphButton.setVisibility(View.GONE);
        }

        Button mUnblockUserButton = (Button) findViewById(R.id.unblockUserButton);
        mUnblockUserSpinner = (Spinner) findViewById(R.id.unblockUserSpinner);
        if (model.canUnblockUser()) {
            mUnblockUserButton.setVisibility(View.VISIBLE);
            mUnblockUserSpinner.setVisibility(View.VISIBLE);
            populateUnblockUserSpinner();

            final Model modelFinal = model;

            mUnblockUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelFinal.unblockUser(mUnblockUserSpinner.getSelectedItem().toString());
                    populateUnblockUserSpinner();
                    populateBlockUserSpinner();
                }
            });
        } else {
            mUnblockUserButton.setVisibility(View.GONE);
            mUnblockUserSpinner.setVisibility(View.GONE);
        }

        Button mBlockUserButton = (Button) findViewById(R.id.blockUserButton);
        mBlockUserSpinner = (Spinner) findViewById(R.id.blockUserSpinner);
        if (model.canUnblockUser()) {
            mBlockUserButton.setVisibility(View.VISIBLE);
            mBlockUserSpinner.setVisibility(View.VISIBLE);
            populateBlockUserSpinner();

            final Model modelFinal = model;

            mBlockUserButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modelFinal.blockUser(mBlockUserSpinner.getSelectedItem().toString());
                    populateBlockUserSpinner();
                    populateUnblockUserSpinner();
                }
            });
        } else {
            mBlockUserButton.setVisibility(View.GONE);
            mBlockUserSpinner.setVisibility(View.GONE);
        }

        Button mLogButton = (Button) findViewById(R.id.logButton);
        if (false) {
            mLogButton.setVisibility(View.VISIBLE);

            mLogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextScreen = new Intent(getApplicationContext(), LogListActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mLogButton.setVisibility(View.GONE);
        }

        model.close();
    }

    private void populateUnblockUserSpinner() {
        // you need to have a list of data that you want the spinner to display

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.addAll(model.getBlockedUserList());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mUnblockUserSpinner.setAdapter(adapter);
    }

    private void populateBlockUserSpinner() {
        // you need to have a list of data that you want the spinner to display

        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.addAll(model.getUnblockedUserList());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBlockUserSpinner.setAdapter(adapter);
    }
}