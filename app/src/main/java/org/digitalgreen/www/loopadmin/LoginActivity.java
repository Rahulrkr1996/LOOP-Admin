package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.query.Delete;

import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Village;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button Login_addMandiButton,Login_addGaddidar,Login_addVehicle;
    private Button Login_addVillage,Login_addCrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_addMandiButton = (Button) findViewById(R.id.Login_addMandiButton);
        Login_addMandiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, AddMandi.class);
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
                Intent i = new Intent(LoginActivity.this, AddVehicle.class);
                startActivity(i);
            }
        });

        Login_addVillage = (Button)findViewById(R.id.Login_addVillage);
        Login_addVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,AddVillage.class);
                startActivity(i);
            }
        });

        Login_addCrop = (Button)findViewById(R.id.Login_addCrop);
        Login_addCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,AddCrop.class);
                startActivity(i);
            }
        });
    }
    
}
