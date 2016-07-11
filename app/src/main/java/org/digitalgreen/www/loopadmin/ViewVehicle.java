package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import org.digitalgreen.www.loopadmin.Adapters.ViewVehicleAdapter;
import org.digitalgreen.www.loopadmin.Models.Vehicle;

import java.util.ArrayList;

public class ViewVehicle extends AppCompatActivity {
    private ListView view_vehicle_list;
    private FloatingActionButton view_vehicle_add;
    private ArrayList<Vehicle> vehicleList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle);
        vehicleList = new Vehicle().getAllVehicles();

        view_vehicle_list = (ListView)findViewById(R.id.view_vehicle_list);
        view_vehicle_add = (FloatingActionButton)findViewById(R.id.view_vehicle_add);

        ViewVehicleAdapter viewVehicleAdapter = new ViewVehicleAdapter(vehicleList, this, new ViewVehicleAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(Vehicle v, int position) {
                Intent intent = new Intent(ViewVehicle.this, AddVehicle.class);
                intent.putExtra("vehicle_id", v.getId());
                startActivity(intent);
                finish();
            }
        });
        view_vehicle_list.setAdapter(viewVehicleAdapter);

        view_vehicle_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewVehicle.this, AddVehicle.class);
                startActivity(i);
                finish();
            }
        });
    }
}
