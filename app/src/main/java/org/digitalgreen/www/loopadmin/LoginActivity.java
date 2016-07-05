package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.query.Delete;

import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Vehicle;
import org.digitalgreen.www.loopadmin.Models.Village;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private Button Login_addMandiButton,Login_addGaddidar,Login_addVehicle;
    private Button Login_addVillage,Login_addCrop;
    private Button Login_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*
        for(int i=1;i<11;i++){
            District district = new District("District_"+String.valueOf(i));
            district.save();
            Crop crop = new Crop("Crop_"+String.valueOf(i),null);
            crop.save();
            Block block = new Block("Block_"+String.valueOf(i));
            block.save();
            Village village = new Village("Village_"+String.valueOf(i));
            village.save();
            Vehicle vehicle = new Vehicle("Vehicle_"+String.valueOf(i));
            vehicle.save();
//            Mandi mandi = new Mandi("Mandi_" + String.valueOf(i),(double)(i*2.5/9),(double)i/6*1.2,district);
//            mandi.save();
        }
*/

        Login_view = (Button)findViewById(R.id.Login_view);
        Login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,ViewDetails.class);
                startActivity(i);
            }
        });

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
                Intent i = new Intent(LoginActivity.this, AddCrop.class);
                startActivity(i);
            }
        });
    }
    
}
