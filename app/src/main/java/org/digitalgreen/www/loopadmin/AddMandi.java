package org.digitalgreen.www.loopadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Adapters.MandiDistrictAdapter;
import org.digitalgreen.www.loopadmin.Adapters.MandiGaddidarAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Mandi;

import java.util.ArrayList;
import java.util.List;

public class AddMandi extends FragmentActivity implements MandiGaddidarAdapter.OnEditClickListener {

    private Context context = this;
    private Mandi currentMandi = null;
    private Gaddidar currentGaddidar = null;
    private District currentDistrict;
    private int textlength = 0;
    private boolean latLongCheck = false;
    private double mandiLatitude = 0.0, mandiLongitude = 0.0;
    private ListView mandi_gaddidar_list;
    private ImageButton mandi_gaddidar_save_button;
    private EditText mandi_name, mandi_select_gaddidar_name, mandi_select_gaddidar_commission, mandi_select_gaddidar_contact;
    private ImageView mandi_select_gaddidar_photo;
    private TextView mandi_select_district;
    private ImageView mandi_get_location;
    private FloatingActionButton mandi_save_button, mandi_discard_button;
    private Dialog dialog;
    private List<District> filteredDistrictList = new ArrayList<District>();
    private List<District> districtList = new ArrayList<District>();
    private MandiDistrictAdapter mandiDistrictAdapter;
    private MandiGaddidarAdapter mandiGaddidarAdapter;
    private District selectedDistrict;
    private TextView dialog1_titleText;
    private EditText dialog1_editText;
    private ListView dialog1_listView;
    private List<Gaddidar> gaddidarList = new ArrayList<Gaddidar>();
    private static final int REQUEST_IMAGE_CAPTURE = 14;
    private boolean gaddidarImageCaptured = false;
    private Bitmap gaddidarPhoto;
    private static final int CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mandi);

        mandi_name = (EditText) findViewById(R.id.mandi_name);
        mandi_select_gaddidar_name = (EditText) findViewById(R.id.mandi_select_gaddidar_name);
        mandi_select_gaddidar_contact = (EditText) findViewById(R.id.mandi_select_gaddidar_contact);
        mandi_select_gaddidar_commission = (EditText) findViewById(R.id.mandi_select_gaddidar_commision);
        mandi_select_gaddidar_photo = (ImageView) findViewById(R.id.mandi_select_gaddidar_photo);
        mandi_gaddidar_save_button = (ImageButton) findViewById(R.id.mandi_gaddidar_save_button);
        mandi_get_location = (ImageView) findViewById(R.id.mandi_get_location);
        mandi_select_district = (TextView) findViewById(R.id.mandi_select_district);
        mandi_save_button = (FloatingActionButton) findViewById(R.id.mandi_save_button);
        mandi_discard_button = (FloatingActionButton) findViewById(R.id.mandi_discard_button);
        mandi_gaddidar_list = (ListView) findViewById(R.id.mandi_gaddidar_list);

        for(int i=1;i<=10;i++){
            District dis = new District("District_"+String.valueOf(i));
            dis.save();
        }
        districtList = new District().getAllDistricts();

        mandiGaddidarAdapter = new MandiGaddidarAdapter(gaddidarList, this, context);
        mandi_gaddidar_list.setAdapter(mandiGaddidarAdapter);

        mandi_select_gaddidar_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        mandi_gaddidar_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                if (mandi_name.getText().toString().equals("")) {
                    Toast.makeText(AddMandi.this, "Please add a mandi name!!", Toast.LENGTH_SHORT).show();
                } else if (mandi_select_district.getText().toString().equals("")) {
                    Toast.makeText(AddMandi.this, "Please select a district for mandi!!", Toast.LENGTH_SHORT).show();
                } else if (latLongCheck == false) {
                    Toast.makeText(AddMandi.this, "Please select a location for Mandi!!", Toast.LENGTH_SHORT).show();
                } else {

                    String gaddidar_name = mandi_select_gaddidar_name.getText().toString();
                    String gaddidar_contact = mandi_select_gaddidar_contact.getText().toString();
                    String gaddidar_commision_ = mandi_select_gaddidar_commission.getText().toString();

                    double gaddidar_commission;

                    if (gaddidar_name.equals("")) {
                        mandi_select_gaddidar_name.setText("");
                        Toast.makeText(AddMandi.this, "Please add a name for Gaddidar!!", Toast.LENGTH_SHORT).show();
                    } else if (gaddidar_contact.equals("") || gaddidar_contact.length() != 10) {
                        mandi_select_gaddidar_contact.setText("");
                        Toast.makeText(AddMandi.this, "Please add a proper Phone no. of the Gaddidar!!", Toast.LENGTH_SHORT).show();
                    } else if (gaddidar_commision_.equals("")) {
                        mandi_select_gaddidar_commission.setText("");
                        Toast.makeText(AddMandi.this, "Please enter the commission of Gaddidar!", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            gaddidar_commission = Double.parseDouble(mandi_select_gaddidar_commission.getText().toString());
                        } catch (final NumberFormatException e) {
                            gaddidar_commission = 0.0;
                        }

                        if (gaddidarImageCaptured == false) {
                            gaddidarPhoto = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_my_profile);
                        } else {
                            gaddidarPhoto = ((BitmapDrawable) mandi_select_gaddidar_photo.getDrawable()).getBitmap();
                        }

                        gaddidarList.add(createNewGaddidar(gaddidar_name, gaddidar_contact, gaddidar_commission, gaddidarPhoto, null));
                        gaddidarList.size();
                        mandiGaddidarAdapter.notifyDataSetChanged();

                        Toast.makeText(AddMandi.this, "New Gaddidar Added to the list", Toast.LENGTH_SHORT).show();
                        mandi_select_gaddidar_name.setText("");
                        mandi_select_gaddidar_contact.setText("");
                        mandi_select_gaddidar_commission.setText("");
                        mandi_select_gaddidar_photo.setImageResource(R.drawable.ic_default_camera);
                        gaddidarImageCaptured = false;
                    }
                }
            }
        });


        // Drop down District functionality
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


                    mandiDistrictAdapter = new MandiDistrictAdapter(filteredDistrictList, context);
                    dialog1_listView.setAdapter(mandiDistrictAdapter);

                    dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                            selectedDistrict = (District) mandiDistrictAdapter.getItem(position);
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
                            mandiDistrictAdapter.notifyDataSetChanged();
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

                    for(int i=0;i<gaddidarList.size();i++){
                        currentGaddidar = createNewGaddidar(gaddidarList.get(i).name,gaddidarList.get(i).contact,gaddidarList.get(i).commission,gaddidarList.get(i).getImage(),currentMandi);
                        currentGaddidar.save();
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
                if (mandi_select_district.equals("")) {
                    Toast.makeText(AddMandi.this, "Please select a district first!!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(AddMandi.this, SelectLocation.class);
                    i.putExtra("SearchBar", mandi_select_district.getText().toString());
                    i.putExtra("Activity", "AddMandi");
                    startActivityForResult(i, GeneralConstants.MAPCALL_FROM_ADDMANDI);
                }
            }
        });
    }

    private Gaddidar createNewGaddidar(String gaddidar_name, String gaddidar_contact, double gaddidar_commission, Bitmap gaddidarPhoto, Mandi mandi) {
        Gaddidar gaddidar = new Gaddidar(gaddidar_name, gaddidar_contact, gaddidar_commission, gaddidarPhoto, mandi);
        return gaddidar;
    }

    private void editGaddidar() {
    }

    public void saveGaddidar(String gaddidarName, double commision, String phone, Mandi mandi, Bitmap image) {

        Gaddidar gaddidar = new Gaddidar(gaddidarName, phone, commision, image, mandi);
        //   List<Gaddidar> list = new ArrayList<Gaddidar>();
        //   list = gaddidar.getAllGaddidars();

        gaddidar.save();
        Toast.makeText(AddMandi.this, "Gaddidar is saved", Toast.LENGTH_SHORT).show();
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
                    mandi_get_location.setImageResource(R.mipmap.get_location_green);
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mandi_select_gaddidar_photo.setImageBitmap(imageBitmap);
            gaddidarImageCaptured = true;
        }
    }

    public void saveMandi(String mandiName, double mandiLati, double mandiLongi, int action, String districtName) {
        Mandi mandi = new Mandi(mandiName, mandiLati, mandiLongi, action, districtName);
//        ArrayList<Mandi> list = new ArrayList<Mandi>();
//        list = mandi.getMandis();

        mandi.save();
        Toast.makeText(AddMandi.this, "New Mandi added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(Gaddidar f, int position) {
        mandi_select_gaddidar_name.setText(f.name);
        mandi_select_gaddidar_contact.setText(f.contact);
        mandi_select_gaddidar_commission.setText(String.valueOf(f.commission));
        mandi_select_gaddidar_photo.setImageBitmap(f.getImage());
        gaddidarList.remove(position);
        mandiGaddidarAdapter.notifyDataSetChanged();
    }
}
