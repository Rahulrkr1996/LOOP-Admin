package org.digitalgreen.www.loopadmin;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnTouchListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SelectLocation extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private Context context = this;
    private GoogleMap mMap;
    private GPSTracker gps;
    private LatLng selectLocation = null;
    private double currentLatitude = 0.0, currentLongitude = 0.0;
    private EditText SL_searchBar;
    private float zoomLevel = 10;
    private Marker currentLocationMarker = null, selectedLocationMarker = null;
    private FloatingActionButton SL_saveButton, SL_discardButton;
    private String activity = null, searchBar = null;
    private Intent intent;
    private boolean Check = false;
    private MarkerOptions markerOptions;
    private LatLng latLng;
    private boolean IS_CURRENT_LOCATION_SELECTED = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        // Getting views
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        SL_searchBar = (EditText) findViewById(R.id.SL_searchBar);
        SL_saveButton = (FloatingActionButton) findViewById(R.id.SL_save_button);
        SL_discardButton = (FloatingActionButton) findViewById(R.id.SL_discard_button);

        // Getting extra intent data
        Bundle MapData = getIntent().getExtras();
        if (MapData == null) {
            return;
        }
        activity = MapData.getString("Activity");
        searchBar = MapData.getString("SearchBar");

        SL_searchBar.setText(searchBar);
        // GPS initialization starts here
        gps = new GPSTracker(SelectLocation.this);

        if (gps.canGetLocation()) {
            currentLatitude = gps.getLatitude();
            currentLongitude = gps.getLongitude();

            IS_CURRENT_LOCATION_SELECTED = true;
            // \n is for new line
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + currentLatitude + "\nLong: " + currentLongitude, Toast.LENGTH_LONG).show();
        } else {
            // Can't get location GPS or Network is not enabled. Ask user to enable GPS/network in settings
            Toast.makeText(getApplicationContext(), "GPS or Network both are not enabled , enable at least one of them.", Toast.LENGTH_LONG).show();
            gps.showSettingsAlert();
        }

        SL_searchBar.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (SL_searchBar.getRight() - SL_searchBar.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Getting user input location
                        String location = SL_searchBar.getText().toString();

                        if (location != null && !location.equals("")) {
                            new GeocoderTask().execute(location);
                        } else {
                            Toast.makeText(SelectLocation.this, "Add a location to search", Toast.LENGTH_SHORT).show();
                        }

                        View view = SelectLocation.this.getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        return true;
                    }
                }
                return false;
            }
        });

        SL_saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IS_CURRENT_LOCATION_SELECTED == true) {
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View alertBox = inflater.inflate(R.layout.custom_alertbox1, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    alertDialogBuilder.setView(alertBox);

                    // set dialog message
                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            IS_CURRENT_LOCATION_SELECTED = true;
                                            Check = true;
                                            sendData(activity, Check);
                                        }
                                    })
                            .setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Toast.makeText(context, "Please select a location from map by clicking on the place!!", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                            IS_CURRENT_LOCATION_SELECTED = false;
                                        }
                                    });
                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
                } else {
                    Check = true;
                    sendData(activity, Check);
                }
            }
        });

        SL_discardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void sendData(String activity, boolean Check) {
        if (activity.equals("AddMandi")) {
            intent = new Intent();
            intent.putExtra("addMandiCheck", Check);    // For changing the color of get Location
            intent.putExtra("location_data_lat", currentLatitude);
            intent.putExtra("location_data_long", currentLongitude);
            setResult(RESULT_OK, intent);
        } else {
            intent = new Intent();
            intent.putExtra("addVillageCheck", Check);
            intent.putExtra("location_data_lat", currentLatitude);
            intent.putExtra("location_data_long", currentLongitude);
            setResult(RESULT_OK, intent);
        }
        finish();
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
        LatLng currentLocation = new LatLng(currentLatitude, currentLongitude);
        currentLocationMarker = mMap.addMarker(new MarkerOptions().position(currentLocation).title("You are presently Here").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        zoomLevel = 12;
        IS_CURRENT_LOCATION_SELECTED = false;
        // Add a marker in the current and move the camera
        if (selectedLocationMarker == null) {
            selectedLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Do you want to Select this place ?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        } else {
            selectedLocationMarker.remove();
            selectedLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("Do you want to Select this place ?").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        selectLocation = latLng;

        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoomLevel + 5));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    // An AsyncTask class for accessing the GeoCoding Web Service
    private class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }


        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            // mMap.clear();

            // Adding Markers on Google Map for each matching address
            for (int i = 0; i < addresses.size(); i++) {

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                mMap.addMarker(markerOptions);

                // Locate the first location
                if (i == 0) {
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                }
            }
        }
    }
}
