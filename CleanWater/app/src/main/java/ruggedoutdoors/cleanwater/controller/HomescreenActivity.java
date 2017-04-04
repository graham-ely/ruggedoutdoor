package ruggedoutdoors.cleanwater.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.AnyDBAdapter;

public class HomescreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        AnyDBAdapter dba = new AnyDBAdapter(this);

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
        dba.open();
        if (dba.canFilePurityReport()) {
            dba.close();
            mFilePurityReportButton.setVisibility(View.VISIBLE);
            mFilePurityReportButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), FilePurityReportActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            dba.close();
            mFilePurityReportButton.setVisibility(View.GONE);
        }


        Button mViewPurityReportsButton = (Button) findViewById(R.id.viewPurityReportsButton);
        dba.open();
        if (dba.canViewPurityReport()) {
            dba.close();
            mViewPurityReportsButton.setVisibility(View.VISIBLE);
            mViewPurityReportsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent nextScreen = new Intent(getApplicationContext(), PurityReportListActivity.class);
                    startActivity(nextScreen);
                }
            });
        } else {
            dba.close();
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
    }
}