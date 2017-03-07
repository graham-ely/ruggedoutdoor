package ruggedoutdoors.cleanwater;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomescreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homescreen);
        final String username = getIntent().getStringExtra("USERNAME");
        User me = Users.getUser(username);

        Button mFileReportButton = (Button) findViewById(R.id.fileReportButton);
        mFileReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), FileReportActivity.class);
                //TODO: optionally pass in user from this screen and skip "username" text field in file report form
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
                nextScreen.putExtra("USERNAME", username);
                startActivity(nextScreen);
            }
        });

        Button mViewReportsButton = (Button) findViewById(R.id.viewReportsButton);
        mEditUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), ReportListActivity.class);
                startActivity(nextScreen);
            }
        });
    }
}