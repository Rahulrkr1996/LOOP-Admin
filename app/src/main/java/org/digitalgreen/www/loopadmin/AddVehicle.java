package org.digitalgreen.www.loopadmin;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class AddVehicle extends AppCompatActivity {

    private EditText vehicleName;
    private FloatingActionButton vehicleSaveButton;
    private FloatingActionButton vehicleDiscardButton;
    private Vehicle currentVehicle = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        vehicleName = (EditText)findViewById(R.id.vehicleName);
        vehicleSaveButton = (FloatingActionButton)findViewById(R.id.vehicleSaveButton);
        vehicleDiscardButton = (FloatingActionButton)findViewById(R.id.vehicleDiscardButton);

        vehicleDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vehicleSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String vehicle_name = vehicleName.getText().toString();

                if(vehicle_name.equals("")){
                    Toast.makeText(AddVehicle.this, "Please enter a Vehicle name", Toast.LENGTH_SHORT).show();
                }else{
                    if(currentVehicle==null){
                        saveVehicle(vehicle_name);
                    }else{
                        currentVehicle.vehicle_name = vehicle_name ;

                        if (currentVehicle.online_id != GeneralConstants.NO_CHANGE)
                            currentVehicle.action = GeneralConstants.EDIT;

                        currentVehicle.save();
                        Toast.makeText(AddVehicle.this, "New Vehicle is saved", Toast.LENGTH_SHORT).show();
                    }
                finish();
                }
            }
        });
    }

    public void saveVehicle(String vehicle_name){
        Vehicle vehicle = new Vehicle(vehicle_name);
        List<Vehicle> list = new ArrayList<Vehicle>();
        list = vehicle.getAllVehicles();

        vehicle.save();
        Toast.makeText(AddVehicle.this, "Vehicle is saved", Toast.LENGTH_SHORT).show();

    }

}
