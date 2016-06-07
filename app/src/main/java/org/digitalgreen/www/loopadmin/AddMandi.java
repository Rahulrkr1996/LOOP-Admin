package org.digitalgreen.www.loopadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Adapters.MandiDistrictAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Mandi;

import java.util.ArrayList;
import java.util.List;

public class AddMandi extends FragmentActivity {

    private Context context = this;
    private Mandi currentMandi;
    private District currentDistrict;
    private int textlength = 0;
    private boolean latLongCheck = false;
    private double mandiLatitude = 0.0, mandiLongitude = 0.0;
    private EditText mandi_name, mandi_select_gaddidar_name, mandi_select_gaddidar_commission, mandi_select_gaddidar_contact;
    private TextView mandi_select_district, mandi_select_aggregators;
    private Button mandi_get_location;
    private ListView mandi_gaddidar_list;
    private ImageButton mandi_gaddidar_save_button;
    private FloatingActionButton mandi_save_button, mandi_discard_button;
    private Dialog dialog;
    private List<District> filteredDistrictList = new ArrayList<District>();
    private List<District> districtList = new ArrayList<District>();
    private MandiDistrictAdapter adapter;
    private District selectedDistrict;
    private TextView dialog1_titleText;
    private EditText dialog1_editText;
    private ListView dialog1_listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandi);

        mandi_name = (EditText) findViewById(R.id.mandi_name);
        mandi_select_gaddidar_name = (EditText) findViewById(R.id.mandi_select_gaddidar_name);
        mandi_select_gaddidar_contact = (EditText) findViewById(R.id.mandi_select_gaddidar_contact);
        mandi_select_gaddidar_commission = (EditText) findViewById(R.id.mandi_select_gaddidar_commision);
        mandi_get_location = (Button) findViewById(R.id.mandi_get_location);
        mandi_select_district = (TextView) findViewById(R.id.mandi_select_district);
        mandi_select_aggregators = (TextView) findViewById(R.id.mandi_select_aggregators);
        mandi_save_button = (FloatingActionButton) findViewById(R.id.mandi_save_button);
        mandi_discard_button = (FloatingActionButton) findViewById(R.id.mandi_discard_button);

        for (int i = 0; i < 10; i++) {
            currentDistrict = new District("District_" + String.valueOf(i));
            currentDistrict.save();
        }

        districtList = new District().getAllDistricts();

        /* Drop down functionality*/
        mandi_select_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralConstants.FLAG_IS_MANDI_CHANGE_ALLOWED) {
                    dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog1);

                    dialog1_titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                    dialog1_editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                    dialog1_listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                    dialog1_titleText.setText("Select District Name");

                    filteredDistrictList.clear();
                    filteredDistrictList.addAll(districtList);

                    adapter = new MandiDistrictAdapter(filteredDistrictList, context);
                    dialog1_listView.setAdapter(adapter);

                    dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                            selectedDistrict = (District) adapter.getItem(position);
                            mandi_select_district.setText(selectedDistrict.toString());
                        }
                    });

                    dialog1_editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dialog1_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            textlength = dialog1_editText.getText().length();
                            filteredDistrictList.clear();

                            // SearchBar Functionality
                            for (int i = 0; i < districtList.size(); i++) {
                                if (textlength <= districtList.get(i).name.length()) {
                                    if (districtList.get(i).name.toLowerCase().startsWith(dialog1_editText.getText().toString().toLowerCase().trim())) {
                                        filteredDistrictList.add(districtList.get(i));
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    dialog.setCancelable(true);
                    dialog.show();
                }
            }
        });
        /* End of drop down click listener*/

        // Setting Save Button
        mandi_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mandiName = mandi_name.getText().toString();
                final double mandiLati = mandiLatitude;
                final double mandiLong = mandiLongitude;
                final String districtName = mandi_select_district.getText().toString();

                if (mandi_name.equals("")) {
                    Toast.makeText(AddMandi.this, "Please add a name for Mandi!!", Toast.LENGTH_SHORT).show();
                } else if (mandi_select_district.equals("")) {
                    Toast.makeText(AddMandi.this, "Please add the district of the Mandi!!", Toast.LENGTH_SHORT).show();
                } else if (latLongCheck == false) {
                    Toast.makeText(AddMandi.this, "Please Select the location of the Mandi!!", Toast.LENGTH_SHORT).show();
                } else if (mandi_select_district.equals("")) {
                    Toast.makeText(AddMandi.this, "Please Select a district for the Mandi!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentMandi == null) {
                        saveMandi(mandiName, mandiLati, mandiLong, GeneralConstants.ADD, districtName);
                    } else {
                        currentMandi.mandi_name = mandiName;
                        currentMandi.latitude = mandiLati;
                        currentMandi.longitude = mandiLong;
                        currentMandi.district_name = districtName;
                        currentMandi.action = GeneralConstants.EDIT;
                        currentMandi.save();
                    }
                    finish();
                }
            }

        });

        mandi_discard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mandi_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mandi_select_district.equals("")) {
//                    Toast.makeText(AddMandi.this, "Please select a district first!!", Toast.LENGTH_SHORT).show();
//                } else {
                Intent i = new Intent(AddMandi.this, SelectLocation.class);
                i.putExtra("SearchBar", mandi_select_district.getText().toString());
                i.putExtra("Activity", "AddMandi");
                startActivityForResult(i, GeneralConstants.MAPCALL_FROM_ADDMANDI);
//                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GeneralConstants.MAPCALL_FROM_ADDMANDI) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //  Getting the location data from intent
                mandiLatitude = (double) data.getDoubleExtra("location_data_lat", 0);
                mandiLongitude = (double) data.getDoubleExtra("location_data_long", 0);
                latLongCheck = (boolean) data.getBooleanExtra("addMandiCheck", false);

                if (latLongCheck == true) {
                    Toast.makeText(AddMandi.this, "Location Data Captured!!", Toast.LENGTH_SHORT).show();
                    mandi_get_location.setTextColor(Color.GREEN);
                    mandi_get_location.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.mipmap.ic_place_green), null);
                }
            }
        }
    }

    public void saveMandi(String mandiName, double mandiLati, double mandiLongi, int action, String districtName) {
        Mandi mandi = new Mandi(mandiName, mandiLati, mandiLongi, action, districtName);
//        ArrayList<Mandi> list = new ArrayList<Mandi>();
//        list = mandi.getMandis();

        mandi.save();
        Toast.makeText(AddMandi.this, "New Mandi added", Toast.LENGTH_SHORT).show();
    }
}
