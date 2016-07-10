package org.digitalgreen.www.loopadmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.digitalgreen.www.loopadmin.Adapters.VillageAggregatorAdapter;
import org.digitalgreen.www.loopadmin.Adapters.VillageBlockAdapter;
import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Village;

import java.util.ArrayList;
import java.util.List;

public class AddVillage extends AppCompatActivity {

    private double villageLatitude = 0;
    private double villageLongitude = 0;
    private boolean latLongCheck = false;
    private ImageView village_get_location;
    private TextView village_select_aggregator;
    private TextView village_select_block;
    private Dialog dialog;
    private Context context;
    private TextView dialog1_titleText;
    private ListView dialog1_listView;
    private TextView dialog1_editText;
    private List<Block> filteredBlockList = new ArrayList<Block>();
    private List<Block> blockList= new ArrayList<Block>();
    private List<LoopUser> filteredAggregatorList = new ArrayList<LoopUser>();
    private List<LoopUser> aggregatorList = new ArrayList<LoopUser>();
    private Block currentBlock = null;
    private VillageBlockAdapter blockAdapter;
    private VillageAggregatorAdapter aggregatorAdapter;
    private Block selectedBlock;
    private int textlength = 0;
    private FloatingActionButton village_discard_button;
    private TextView village_name;
    private FloatingActionButton village_save_button;
    private Village currentVillage;
    private LoopUser selectedAggregator;
    private boolean activityOpenedForResult=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_village);

        context = this;

        village_save_button = (FloatingActionButton) findViewById(R.id.village_save_button);
        village_discard_button = (FloatingActionButton) findViewById(R.id.village_discard_button);
        village_name = (TextView) findViewById(R.id.village_name);
        village_get_location = (ImageView) findViewById(R.id.village_get_location);
        village_select_block = (TextView) findViewById(R.id.village_select_block);
        village_select_aggregator = (TextView) findViewById(R.id.village_select_aggregator);

        // Getting extra intent data
        Bundle VillageData = getIntent().getExtras();
        if (VillageData != null) {
            long village_id = VillageData.getLong("village_id");
            if (village_id != 0) {
                currentVillage = Village.load(Village.class, village_id);
                village_name.setText(currentVillage.name);
                village_select_block.setText(currentVillage.block_name);
                latLongCheck = true;
                activityOpenedForResult = true;
                village_get_location.setImageResource(R.mipmap.get_location_green);
            }
        }

        blockList = new Block().getAllBlocks();
        aggregatorList = new LoopUser().getAllAggregators();

                /* Drop down functionality*/
        village_select_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog1);

                dialog1_titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                dialog1_editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                dialog1_listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                dialog1_titleText.setText("Select Block Name");

                filteredBlockList.clear();
                filteredBlockList.addAll(blockList);

                blockAdapter = new VillageBlockAdapter(filteredBlockList, context);
                dialog1_listView.setAdapter(blockAdapter);

                dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        selectedBlock = (Block) blockAdapter.getItem(position);
                        village_select_block.setText(selectedBlock.toString());
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
                        filteredBlockList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < blockList.size(); i++) {
                            if (textlength <= blockList.get(i).name.length()) {
                                if (blockList.get(i).name.toLowerCase().startsWith(dialog1_editText.getText().toString().toLowerCase().trim())) {
                                    filteredBlockList.add(blockList.get(i));
                                }
                            }
                        }
                        blockAdapter.notifyDataSetChanged();
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

        village_select_aggregator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog1);

                dialog1_titleText = (TextView) dialog.findViewById(R.id.dialog1_titleText);
                dialog1_editText = (EditText) dialog.findViewById(R.id.dialog1_editText);
                dialog1_listView = (ListView) dialog.findViewById(R.id.dialog1_listView);

                dialog1_titleText.setText("Select Aggregator Name");

                filteredAggregatorList.clear();
                filteredAggregatorList.addAll(aggregatorList);

                aggregatorAdapter = new VillageAggregatorAdapter(filteredAggregatorList, context);
                dialog1_listView.setAdapter(aggregatorAdapter);

                dialog1_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        dialog.dismiss();
                        selectedAggregator = (LoopUser) aggregatorAdapter.getItem(position);
                        village_select_aggregator.setText(selectedAggregator.toString());
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
                        filteredAggregatorList.clear();

                        // SearchBar Functionality
                        for (int i = 0; i < aggregatorList.size(); i++) {
                            if (textlength <= aggregatorList.get(i).name.length()) {
                                if (aggregatorList.get(i).name.toLowerCase().startsWith(dialog1_editText.getText().toString().toLowerCase().trim())) {
                                    filteredAggregatorList.add(aggregatorList.get(i));
                                }
                            }
                        }
                        aggregatorAdapter.notifyDataSetChanged();
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


        // Setting Save Button
        village_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String villageName = village_name.getText().toString();
                final double villageLat = villageLatitude;
                final double villageLong = villageLongitude;
                final String blockName = village_select_block.getText().toString();

                if (village_name.equals("")) {
                    Toast.makeText(AddVillage.this, "Please add a name for Village!!", Toast.LENGTH_SHORT).show();
                } else if (village_select_block.equals("")) {
                    Toast.makeText(AddVillage.this, "Please add the district of the Village!!", Toast.LENGTH_SHORT).show();
                } else if (latLongCheck == false) {
                    Toast.makeText(AddVillage.this, "Please Select the location of the Village!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (currentVillage == null) {
                        saveVillage(villageName, villageLat, villageLong, blockName);
                    } else {
                        currentVillage.name = villageName;
                        currentVillage.Lat = villageLat;
                        currentVillage.Long = villageLong;
                        currentVillage.block_name = blockName;
                        currentVillage.save();
                    }

                    if (activityOpenedForResult == true) {
                        Toast.makeText(AddVillage.this, "Applied the changes ...", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                    }
                    Toast.makeText(AddVillage.this, "New Village Added!!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        });

        village_discard_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activityOpenedForResult == true) {
                    Toast.makeText(AddVillage.this, "Discarding the changes...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                }
                finish();
            }
        });

        village_get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (village_select_block.equals("")) {
                    Toast.makeText(AddVillage.this, "Please select a block first!!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(AddVillage.this, SelectLocation.class);
                    i.putExtra("SearchBar", village_select_block.getText().toString());
                    i.putExtra("Activity", "AddVillage");
                    startActivityForResult(i, GeneralConstants.MAPCALL_FROM_ADDVILLAGE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GeneralConstants.MAPCALL_FROM_ADDVILLAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //  Getting the location data from intent
                villageLatitude = data.getDoubleExtra("location_data_lat", 0);
                villageLongitude = data.getDoubleExtra("location_data_long", 0);
                latLongCheck = data.getBooleanExtra("addVillageCheck", false);

                if (latLongCheck == true) {
                    village_get_location.setImageResource(R.mipmap.get_location_green);
                    Toast.makeText(AddVillage.this, "Location data Captured", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void saveVillage(String villageName, double villageLat, double villageLong, String blockName) {
        Village village = new Village(villageName, villageLat, villageLong, blockName);
//        ArrayList<Village> list = new ArrayList<Village>();
//        list = list.getVillages();

        village.save();
        Toast.makeText(AddVillage.this, "New Village added", Toast.LENGTH_SHORT).show();

    }

    public void onBackPressed() {
        Toast.makeText(AddVillage.this, "Use the X button", Toast.LENGTH_SHORT).show();
    }


}
