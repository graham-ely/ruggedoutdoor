package ruggedoutdoors.cleanwater.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import ruggedoutdoors.cleanwater.R;
import ruggedoutdoors.cleanwater.model.*;


/**
 * Created by Austin Dunn on 2/13/2017.
 */

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // for ease of testing
        //model.addUser("test", "person", "tester", "12345", "test@gatech.edu", "1234567890", "", "123 test ave", "MANAGER");

        Button mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(nextScreen);
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextScreen = new Intent(getApplicationContext(), RegistrationActivity.class);

                startActivity(nextScreen);
            }
        });
    }

}
