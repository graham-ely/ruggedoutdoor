package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.Model;

public class HomescreenActivity extends AppCompatActivity {

    Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);

        Button mFileReportButton = (Button) findViewById(R.id.fileReportButton);
        mFileReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), FileReportActivity.class);
                startActivity(nextScreen);
            }
        });

        Button mLogoutButton = (Button) findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);
                model = model.logOut();
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
        mViewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), ReportListActivity.class);
                startActivity(nextScreen);
            }
        });

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
            mViewPurityReportsButton.setVisibility(View.VISIBLE);
            mViewPurityReportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), PurityReportListActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            mViewPurityReportsButton.setVisibility(View.GONE);
        }

        Button mViewMapButton = (Button) findViewById(R.id.viewMapButton);
        mViewMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(nextScreen);
            }
        });

        Button mHistoryGraphButton = (Button) findViewById(R.id.historyGraphButton);
        mHistoryGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), GraphActivity.class);
                startActivity(nextScreen);
            }
        });
    }
}