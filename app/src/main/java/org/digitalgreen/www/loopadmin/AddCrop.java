package org.digitalgreen.www.loopadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.Vehicle;

import java.io.File;

public class AddCrop extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 16;
    private ImageView crop_image;
    private Bitmap image;
    private FloatingActionButton cropSaveButton, cropDiscardButton;
    private boolean cropImageCaptured = false;
    private Crop currentCrop;
    private EditText crop_name;
    private boolean openedForEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);

        Context context = this;

        crop_name = (EditText) findViewById(R.id.crop_name);
        crop_image = (ImageView) findViewById(R.id.crop_image);
        cropSaveButton = (FloatingActionButton) findViewById(R.id.cropSaveButton);
        cropDiscardButton = (FloatingActionButton) findViewById(R.id.cropDiscardButton);

        // Getting extra intent data
        Bundle CropData = getIntent().getExtras();
        if (CropData != null) {
            long crop_id = CropData.getLong("crop_id");
            if (crop_id >= 0) {
                openedForEdit = true;
                currentCrop = Crop.load(Crop.class, crop_id);

                crop_name.setText(currentCrop.crop_name);
                crop_image.setImageBitmap(currentCrop.getImage());
            }
        }

        crop_image.setOnClickListener(new View.OnClickListener() {
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

        cropSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cropName = crop_name.getText().toString();

                if (cropName.equals("")) {
                    Toast.makeText(AddCrop.this, "Add a name for Crop", Toast.LENGTH_SHORT).show();
                } else {
                    if (cropImageCaptured == false) {
                        image = null;
                    } else {
                        image = ((BitmapDrawable) crop_image.getDrawable()).getBitmap();
                    }

                    if (currentCrop == null) {
                        Crop crop = new Crop(cropName, image);
                        crop.save();
                    } else {
                        currentCrop.crop_name = cropName;
                        currentCrop.action = GeneralConstants.EDIT;
                        currentCrop.save();
                    }

                    if (openedForEdit == false)
                        Toast.makeText(AddCrop.this, "New Crop is saved", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(AddCrop.this, "Crop edited", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(AddCrop.this,ViewCrop.class);
                startActivity(i);

                finish();
            }
        });

        cropDiscardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            image = (Bitmap) extras.get("data");
            crop_image.setImageBitmap(image);
            cropImageCaptured = true;
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(AddCrop.this, "Use the X button", Toast.LENGTH_SHORT).show();
    }

}
