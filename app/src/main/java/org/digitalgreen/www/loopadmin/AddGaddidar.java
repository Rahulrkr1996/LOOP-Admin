package org.digitalgreen.www.loopadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Adapters.GaddidarMandiAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Mandi;

import java.util.ArrayList;
import java.util.List;

public class AddGaddidar extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 14;
    private int textlength = 0;
    private EditText gaddidarName, gaddidarPhoneNo, gaddidarCommission;
    private ImageView gaddidarPic;
    private TextView gaddidarPhotoText, gaddidarSelectMandi;
    private Gaddidar currentGaddidar = null;
    private Mandi selectedMandi;
    private Dialog dialog;
    private TextView titleText;
    private EditText editText;
    private ListView listView;
    private List<Mandi> filteredMandisList = new ArrayList<Mandi>();
    private List<Mandi> mandisList = new ArrayList<Mandi>();
    private GaddidarMandiAdapter adapter;
    private FloatingActionButton gaddidarSaveButtton, gaddidarDiscardButton;
    private Bitmap fImage = null;
    private boolean gaddidarImageCaptured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gaddidar);

        final Context context = this;

        gaddidarName = (EditText) findViewById(R.id.gaddidarName);
        gaddidarPhoneNo = (EditText) findViewById(R.id.gaddidarPhoneNo);
        gaddidarCommission = (EditText) findViewById(R.id.gaddidarCommission);
        gaddidarPic = (ImageView) findViewById(R.id.gaddidarPic);
        gaddidarPhotoText = (TextView) findViewById(R.id.gaddidarPhotoText);
        gaddidarSelectMandi = (TextView) findViewById(R.id.gaddidarSelectMandi);
        gaddidarSaveButtton = (FloatingActionButton) findViewById(R.id.gaddidarSaveButton);
        gaddidarDiscardButton = (FloatingActionButton) findViewById(R.id.gaddidarDiscardButton);

/*
        for(int i=0;i<10;i++){
            Mandi mandi=new Mandi("Mandi_"+String.valueOf(i+1));
            mandi.save();
        }
*/
        /* Initializing the drop down to Mandi List*/
        mandisList = new Mandi().getAllMandis();

        fImage = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_my_profile);
        /* Drop down functionality*/
        gaddidarSelectMandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GeneralConstants.FLAG_IS_MANDI_CHANGE_ALLOWED) {
                    dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.custom_dialog1);

                    titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                    editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                    listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                    titleText.setText(getString(R.string.select_mandi_name_toast));

                    filteredMandisList.clear();
                    filteredMandisList.addAll(mandisList);

                    adapter = new GaddidarMandiAdapter(filteredMandisList, context);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            dialog.dismiss();
                            selectedMandi = (Mandi) adapter.getItem(position);
                            gaddidarSelectMandi.setText(selectedMandi.toString());
                        }
                    });

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            textlength = editText.getText().length();
                            filteredMandisList.clear();

                            // SearchBar Functionality
                            for (int i = 0; i < mandisList.size(); i++) {
                                if (textlength <= mandisList.get(i).mandi_name.length()) {
                                    if (mandisList.get(i).mandi_name.toLowerCase().startsWith(editText.getText().toString().toLowerCase().trim())) {
                                        filteredMandisList.add(mandisList.get(i));
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

        gaddidarSaveButtton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String gaddidar_name = gaddidarName.getText().toString();
                String gaddidar_phone = gaddidarPhoneNo.getText().toString();
                double gaddidar_commission;
                try {
                    gaddidar_commission = Double.parseDouble(gaddidarCommission.getText().toString());
                } catch (final NumberFormatException e) {
                    gaddidar_commission = 0.0;
                }
                Mandi gaddidar_mandi = selectedMandi;
                Bitmap image = fImage;

                if (gaddidar_name.equals("")) {
                    gaddidarName.setText("");
                    Toast.makeText(AddGaddidar.this, "Please add a name for Gaddidar!!", Toast.LENGTH_SHORT).show();
                } else if (gaddidar_phone.equals("")||gaddidar_phone.length()!=10) {
                    gaddidarPhoneNo.setText("");
                    Toast.makeText(AddGaddidar.this, "Please add a proper Phone no. of the Gaddidar!!", Toast.LENGTH_SHORT).show();
                } else if (gaddidar_commission == 0) {
                    gaddidarCommission.setText("");
                    Toast.makeText(AddGaddidar.this, "Please enter the commission of Gaddidar!", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentGaddidar == null) {
                        saveGaddidar(gaddidar_name, gaddidar_commission, gaddidar_phone, gaddidar_mandi, image);
                    } else {
                        currentGaddidar.name = gaddidar_name;
                        currentGaddidar.commission = gaddidar_commission;
                        currentGaddidar.contact = gaddidar_phone;
                        currentGaddidar.mandi = gaddidar_mandi;
                        currentGaddidar.saveImage(image);

                        currentGaddidar.save();
                        Toast.makeText(AddGaddidar.this, "Gaddidar is saved", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });

        gaddidarDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        gaddidarPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra("outputX", 400);
                takePictureIntent.putExtra("outputY", 400);
                takePictureIntent.putExtra("aspectX", 1);
                takePictureIntent.putExtra("aspectY", 1);
                takePictureIntent.putExtra("scale", true);
                takePictureIntent.putExtra("return-data", true);

                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }

            }
        });

    }

    public void saveGaddidar(String gaddidarName, double commision, String phone, Mandi mandi, Bitmap image) {
        if (!gaddidarImageCaptured)
            image = null;

        Gaddidar gaddidar = new Gaddidar(gaddidarName,phone,commision, image, mandi);
        List<Gaddidar> list = new ArrayList<Gaddidar>();
        list = gaddidar.getAllGaddidars();

        gaddidar.save();
        Toast.makeText(AddGaddidar.this, "Gaddidar is saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            fImage = (Bitmap) extras.get("data");
            gaddidarPic.setImageBitmap(fImage);
            gaddidarImageCaptured = true;
        }
    }
}
