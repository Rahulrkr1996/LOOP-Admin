package org.digitalgreen.www.loopadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    private Button home_view_details,home_view_crops,home_view_vehicle;
    private Button home_add_aggregators;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_view_crops = (Button)findViewById(R.id.home_view_crops);
        home_view_crops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ViewCrop.class);
                startActivity(intent);
            }
        });

        home_view_details = (Button)findViewById(R.id.home_view_details);
        home_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ViewDetails.class);
                startActivity(intent);
            }
        });

        home_view_vehicle = (Button)findViewById(R.id.home_view_vehicle);
        home_view_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,ViewVehicle.class);
                startActivity(intent);
            }
        });

        home_add_aggregators = (Button)findViewById(R.id.home_add_aggregators);
        home_add_aggregators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AddAggregator.class);
                startActivity(intent);
            }
        });
    }
}
