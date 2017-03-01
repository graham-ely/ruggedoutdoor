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

        Button mLogoutButton = (Button) findViewById(R.id.logoutButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(nextScreen);
            }
        });
        Button mEditUserButton = (Button) findViewById(R.id.editUserButton);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), EditUserActivity.class);
                nextScreen.putExtra("USERNAME", username);
                startActivity(nextScreen);
            }
        });
    }
}
