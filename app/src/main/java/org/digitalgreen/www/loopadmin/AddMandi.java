package org.digitalgreen.www.loopadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Mandi;

import java.util.ArrayList;

public class AddMandi extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Context context = this;
    private GoogleMap mMap;
    private GPSTracker gps;
    private double currentLatitude = 0, currentLongitude = 0;
    private LatLng currentLocation;
    private TextView mandiLatitude, mandiLongitude, mandiName;
    private float zoomLevel = 10;
    private Marker currentLocationMarker = null, mandiLocationMarker = null;
    private FloatingActionButton mandiSaveButton, mandiDiscardButton;
    private Mandi currentMandi = null;
    private static int CURRENT_LOCATION_IS_MANDI = 0;      // 1 = True , 0 = False

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandi);

        mandiName = (TextView) findViewById(R.id.mandiName);
        mandiLatitude = (TextView) findViewById(R.id.mandi_Latitude);
        mandiLongitude = (TextView) findViewById(R.id.mandi_Longitude);
        mandiSaveButton = (FloatingActionButton) findViewById(R.id.mandiSaveButton);
        mandiDiscardButton = (FloatingActionButton) findViewById(R.id.mandiDiscardButton);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mandiMap);
        mapFragment.getMapAsync(this);

        /** GPS initialization starts here */
        gps = new GPSTracker(AddMandi.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {
            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();

            mandiLatitude.setText(String.format("%.8f", currentLatitude));
            mandiLongitude.setText(String.format("%.8f", currentLongitude));

            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + currentLatitude + "\nLong: " + currentLongitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location GPS or Network is not enabled. Ask user to enable GPS/network in settings
            Toast.makeText(getApplicationContext(), "GPS or Network both are not enabled , enable at least one of them.", Toast.LENGTH_LONG).show();
            gps.showSettingsAlert();
        }

        // Setting Save Button
        mandiSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mandi_name = mandiName.getText().toString();
                final double mandi_lati = Double.parseDouble(mandiLatitude.getText().toString());
                final double mandi_long = Double.parseDouble(mandiLongitude.getText().toString());

                if (mandi_name.equals("")) {
                    Toast.makeText(AddMandi.this, "Please add a name for Mandi!!", Toast.LENGTH_SHORT).show();
                } else if (mandiLocationMarker == null) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View alertBox = inflater.inflate(R.layout.mandi_alertbox_layout, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(alertBox);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            CURRENT_LOCATION_IS_MANDI = 1;
                                            Toast.makeText(context, "Your Current Location is selected as a Mandi location", Toast.LENGTH_SHORT).show();

                                            if (currentMandi == null) {
                                                saveMandi(mandi_name, mandi_lati, mandi_long, GeneralConstants.ADD);
                                            } else {
                                                currentMandi.mandi_name = mandi_name;
                                                currentMandi.latitude = mandi_lati;
                                                currentMandi.longitude = mandi_long;

                                                if (currentMandi.online_id != GeneralConstants.NO_CHANGE)
                                                    currentMandi.action = GeneralConstants.EDIT;
                                                currentMandi.save();
                                            }
                                            finish();
                                            Toast.makeText(AddMandi.this,"New Mandi added",Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Toast.makeText(context, "Please select a location from map by clicking on the place!!", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                        }
                                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();

                } else {
                    if (currentMandi == null) {
                        saveMandi(mandi_name, mandi_lati, mandi_long, GeneralConstants.ADD);
                    } else {
                        currentMandi.mandi_name = mandi_name;
                        currentMandi.latitude = mandi_lati;
                        currentMandi.longitude = mandi_long;

                        if (currentMandi.online_id != GeneralConstants.NO_CHANGE)
                            currentMandi.action = GeneralConstants.EDIT;

                        currentMandi.save();
                    }
                    finish();
                }
            }

        });


        mandiDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    public void saveMandi(String mandiName, double mandiLati, double mandiLongi, int action) {
        Mandi mandi = new Mandi(mandiName, mandiLati, mandiLongi, action);
//        ArrayList<Mandi> list = new ArrayList<Mandi>();
//        list = mandi.getMandis();

        mandi.save();
        Toast.makeText(AddMandi.this,"New Mandi added",Toast.LENGTH_SHORT).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Setting Map click listeners
        mMap.setOnMapClickListener(this);

        //Enabling myLocation Layer
        mMap.setMyLocationEnabled(true);

        // Add a marker in the current and move the camera
        currentLocation = new LatLng(currentLatitude, currentLongitude);
        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are presently Here"));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        // Add a marker in the current and move the camera
        if (mandiLocationMarker == null) {
            mandiLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Do you want to Select this place as a Mandi"));
        } else {
            mandiLocationMarker.remove();
            mandiLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Do you want to Select this place as a Mandi"));
        }
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel + 5));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        mandiLatitude.setText(String.format("%.8f", latLng.latitude));
        mandiLongitude.setText(String.format("%.8f", latLng.longitude));
    }

}
