package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button Login_addMandiButton,Login_addGaddidar,Login_addVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_addMandiButton = (Button) findViewById(R.id.Login_addMandiButton);
        Login_addMandiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,AddMandi.class);
                startActivity(i);
            }
        });

        Login_addGaddidar = (Button) findViewById(R.id.Login_addGaddidar);
        Login_addGaddidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,AddGaddidar.class);
                startActivity(i);
            }
        });

        Login_addVehicle = (Button)findViewById(R.id.Login_addVehicle);
        Login_addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,AddVehicle.class);
                startActivity(i);
            }
        });
    }
    
}
