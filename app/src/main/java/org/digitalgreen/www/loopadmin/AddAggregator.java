package org.digitalgreen.www.loopadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.digitalgreen.www.loopadmin.Adapters.AggregatorAssignMandiAdapter;
import org.digitalgreen.www.loopadmin.Adapters.AggregatorAssignVillageAdapter;
import org.digitalgreen.www.loopadmin.Adapters.AggregatorUserNameAdapter;
import org.digitalgreen.www.loopadmin.Adapters.AggregatorVillageAdapter;
import org.digitalgreen.www.loopadmin.Adapters.MandiDistrictAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.User;
import org.digitalgreen.www.loopadmin.Models.Village;

import java.util.ArrayList;
import java.util.List;

public class AddAggregator extends AppCompatActivity {

    private TextView aggregatorUserName, aggregatorVillage, aggregatorAssignVillages, aggregatorAssignMandis;
    private EditText aggregatorName, aggregatorContact;
    private ImageView aggregatorPic;
    private ArrayList<Mandi> mandiList, assignedMandis;
    private ArrayList<Mandi> selectedAssignedMandis;
    private ArrayList<Mandi> filteredMandiList;
    private ArrayList<Village> selectedAssignedVillages = null;
    private ArrayList<Village> villageList, assignedVillages;
    private ArrayList<Village> filteredVillageList;
    private ArrayList<User> userList;
    private ArrayList<User> filteredUserNameList;
    private FloatingActionButton aggregatorDiscardButton, aggregatorSaveButton;
    private boolean activityOpenedForResult = false;
    private int textlength = 0;
    private Dialog dialog;
    private TextView dialog1_titleText;
    private EditText dialog1_editText;
    private ListView dialog1_listView;
    private TextView dialog2_titleText;
    private EditText dialog2_editText;
    private ListView dialog2_listView;
    private AggregatorUserNameAdapter aggregatorUserNameAdapter;
    private User selectedUser = null;
    private AggregatorVillageAdapter aggregatorVillageAdapter;
    private Village selectedVillage = null;
    private Village aggregatorsVillage = null;
    private AggregatorAssignVillageAdapter aggregatorAssignVillageAdapter;
    private FloatingActionButton dialog2_save, dialog2_discard;
    private static final int REQUEST_IMAGE_CAPTURE = 14;
    private boolean aggregatorImageCaptured = false;
    private AggregatorAssignMandiAdapter aggregatorAssignMandiAdapter;
    private Mandi selectedMandi=null;
    private LoopUser currentAggregator=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aggregator);

        final Context context = this;

        aggregatorUserName = (TextView) findViewById(R.id.aggregatorUserName);
        aggregatorName = (EditText) findViewById(R.id.aggregatorName);
        aggregatorVillage = (TextView) findViewById(R.id.aggregatorVillage);
        aggregatorContact = (EditText) findViewById(R.id.aggregatorContact);
        aggregatorAssignVillages = (TextView) findViewById(R.id.aggregatorAssignVillages);
        aggregatorAssignMandis = (TextView) findViewById(R.id.aggregatorAssignMandis);
        aggregatorPic = (ImageView) findViewById(R.id.aggregatorPic);
        aggregatorSaveButton = (FloatingActionButton) findViewById(R.id.aggregatorSaveButton);
        aggregatorDiscardButton = (FloatingActionButton) findViewById(R.id.aggregatorDiscardButton);

        mandiList = new Mandi().getAllMandis();
        villageList = new Village().getAllVillages();
        userList = new User().getAllUsers();
        assignedVillages = new ArrayList<Village>();

        // Drop down UserName functionality
        aggregatorUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textlength = 0;
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog1);

                dialog1_titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                dialog1_editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                dialog1_listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                dialog1_titleText.setText("Select UserName");

                filteredUserNameList.clear();
                filteredUserNameList.addAll(userList);


                aggregatorUserNameAdapter = new AggregatorUserNameAdapter(filteredUserNameList, context);
                dialog1_listView.setAdapter(aggregatorUserNameAdapter);

                dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        selectedUser = (User) aggregatorUserNameAdapter.getItem(position);
                        aggregatorUserName.setText(selectedUser.toString());
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
                        filteredUserNameList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < userList.size(); i++) {
                            if (textlength <= userList.get(i).user_name.length()) {
                                if (userList.get(i).user_name.toLowerCase().startsWith(dialog1_editText.getText().toString().toLowerCase().trim())) {
                                    filteredUserNameList.add(userList.get(i));
                                }
                            }
                        }
                        aggregatorUserNameAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        /* End of drop down click listener*/

        // Drop down Village functionality
        aggregatorVillage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textlength = 0;
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog1);

                dialog1_titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                dialog1_editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                dialog1_listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                dialog1_titleText.setText("Select Village");

                filteredVillageList.clear();
                filteredVillageList.addAll(villageList);

                aggregatorVillageAdapter = new AggregatorVillageAdapter(filteredVillageList, context);
                dialog1_listView.setAdapter(aggregatorVillageAdapter);

                dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        selectedVillage = (Village) aggregatorVillageAdapter.getItem(position);
                        aggregatorVillage.setText(selectedVillage.toString());
                        aggregatorsVillage = selectedVillage; // To save the aggregator
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
                        filteredVillageList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < villageList.size(); i++) {
                            if (textlength <= villageList.get(i).name.length()) {
                                if (villageList.get(i).name.toLowerCase().startsWith(dialog1_editText.getText().toString().toLowerCase().trim())) {
                                    filteredVillageList.add(villageList.get(i));
                                }
                            }
                        }
                        aggregatorVillageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        /* End of drop down click listener*/

        // Drop down Assign Village functionality
        aggregatorAssignVillages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textlength = 0;
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog2);

                dialog2_titleText = (TextView) dialog.findViewById(R.id.dialog2_titleText);
                dialog2_editText = (EditText) dialog.findViewById(R.id.dialog2_editText);
                dialog2_listView = (ListView) dialog.findViewById(R.id.dialog2_listView);
                dialog2_save = (FloatingActionButton) dialog.findViewById(R.id.dialog2_save);
                dialog2_discard = (FloatingActionButton) dialog.findViewById(R.id.dialog2_discard);

                dialog2_titleText.setText("Select Village");

                filteredVillageList.clear();
                filteredVillageList.addAll(villageList);

                aggregatorAssignVillageAdapter = new AggregatorAssignVillageAdapter(filteredVillageList, context);
                dialog2_listView.setAdapter(aggregatorAssignVillageAdapter);

                dialog2_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        boolean selected = false;
                        //dialog.dismiss();
                        selectedVillage = aggregatorAssignVillageAdapter.getItem(position);
                        for (int i = 0; i < selectedAssignedVillages.size(); i++) {
                            if (selectedVillage.getId() == selectedAssignedVillages.get(i).getId()) {
                                selected = true;
                                selectedAssignedVillages.remove(i);
                                break;
                            }
                        }
                        if (selected == false) {
                            selectedAssignedVillages.add(selectedVillage);
                        }
                    }
                });

                dialog2_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aggregatorAssignVillages.setText(String.valueOf(selectedAssignedVillages.size()) + " Village selected");
                        dialog.dismiss();
                    }
                });

                dialog2_discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedAssignedVillages.clear();
                        dialog.dismiss();
                    }
                });

                dialog2_editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        dialog2_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        textlength = dialog2_editText.getText().length();
                        filteredVillageList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < villageList.size(); i++) {
                            if (textlength <= villageList.get(i).name.length()) {
                                if (villageList.get(i).name.toLowerCase().startsWith(dialog2_editText.getText().toString().toLowerCase().trim())) {
                                    filteredVillageList.add(villageList.get(i));
                                }
                            }
                        }
                        aggregatorAssignVillageAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        /* End of drop down click listener*/

        // Drop down Assign Village functionality
        aggregatorAssignMandis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textlength = 0;
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog2);

                dialog2_titleText = (TextView) dialog.findViewById(R.id.dialog2_titleText);
                dialog2_editText = (EditText) dialog.findViewById(R.id.dialog2_editText);
                dialog2_listView = (ListView) dialog.findViewById(R.id.dialog2_listView);
                dialog2_save = (FloatingActionButton) dialog.findViewById(R.id.dialog2_save);
                dialog2_discard = (FloatingActionButton) dialog.findViewById(R.id.dialog2_discard);

                dialog2_titleText.setText("Select Mandis");

                filteredMandiList.clear();
                filteredMandiList.addAll(mandiList);

                aggregatorAssignMandiAdapter = new AggregatorAssignMandiAdapter(filteredMandiList, context);
                dialog2_listView.setAdapter(aggregatorAssignMandiAdapter);

                dialog2_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        boolean selected = false;
                        //dialog.dismiss();
                        selectedMandi = aggregatorAssignMandiAdapter.getItem(position);
                        for (int i = 0; i < selectedAssignedMandis.size(); i++) {
                            if (selectedMandi.getId() == selectedAssignedMandis.get(i).getId()) {
                                selected = true;
                                selectedAssignedMandis.remove(i);
                                break;
                            }
                        }
                        if (selected == false) {
                            selectedAssignedMandis.add(selectedMandi);
                        }
                    }
                });

                dialog2_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aggregatorAssignMandis.setText(String.valueOf(selectedAssignedMandis.size()) + " Mandi selected");
                        dialog.dismiss();
                    }
                });

                dialog2_discard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedAssignedMandis.clear();
                        dialog.dismiss();
                    }
                });

                dialog2_editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        dialog2_editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        textlength = dialog2_editText.getText().length();
                        filteredMandiList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < mandiList.size(); i++) {
                            if (textlength <= mandiList.get(i).mandi_name.length()) {
                                if (mandiList.get(i).mandi_name.toLowerCase().startsWith(dialog2_editText.getText().toString().toLowerCase().trim())) {
                                    filteredMandiList.add(mandiList.get(i));
                                }
                            }
                        }
                        aggregatorAssignMandiAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                dialog.setCancelable(true);
                dialog.show();
            }
        });
        /* End of drop down click listener*/

        aggregatorPic.setOnClickListener(new View.OnClickListener() {
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


        aggregatorSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = aggregatorName.getText().toString();
                String userName = aggregatorUserName.getText().toString();
                Village village = aggregatorsVillage;
                String contact = aggregatorContact.getText().toString();

                Bitmap image;
                if (name.equals("")) {
                    Toast.makeText(AddAggregator.this, "Add a name for Aggregator", Toast.LENGTH_SHORT).show();
                } else if (userName.equals("")) {
                    Toast.makeText(AddAggregator.this, "Select Username for Aggregator", Toast.LENGTH_SHORT).show();
                } else if (village.equals("")) {
                    Toast.makeText(AddAggregator.this, "Select Village of Aggregator", Toast.LENGTH_SHORT).show();
                } else {
                    if (aggregatorImageCaptured == false) {
                        image = null;
                    } else {
                        image = ((BitmapDrawable) aggregatorPic.getDrawable()).getBitmap();
                    }

                    if (currentAggregator == null) {
                        LoopUser loopUser = new LoopUser(image,userName,name,"Aggregator",selectedAssignedMandis,selectedAssignedVillages,contact,aggregatorsVillage);
                        loopUser.save();
                    } else {
                        currentAggregator.name = name;
                        currentAggregator.user = userName;
                        currentAggregator.village = aggregatorsVillage;
                        currentAggregator.assigned_villages = selectedAssignedVillages;
                        currentAggregator.assigned_mandi = selectedAssignedMandis;
                        currentAggregator.saveImage(image);
                        currentAggregator.contact = contact;
                        currentAggregator.save();
                    }

                    Toast.makeText(AddAggregator.this, "New Aggregator is saved", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        aggregatorDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityOpenedForResult == true) {
                    Toast.makeText(AddAggregator.this, "Discarding the changes !!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap fImage = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            fImage = (Bitmap) extras.get("data");
            aggregatorPic.setImageBitmap(fImage);
            aggregatorImageCaptured = true;
        }
    }
}
