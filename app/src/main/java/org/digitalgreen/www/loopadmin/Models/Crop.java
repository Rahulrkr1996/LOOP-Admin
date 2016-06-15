package org.digitalgreen.www.loopadmin.Models;

/**
 * Created by Rahul Kumar on 6/15/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

@Table(name = "Crop")
public class Crop extends Model {

    @Column(name = "image_path")
    public String image_path;

    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "crop_name")
    public String crop_name;

    @Expose
    @Column(name = "measuring_unit")
    public String measuring_unit;

    @Column(name = "action")
    public int action;   // 0 = ADD , 1= EDIT ,  -1 = NO CHANGE

    public Crop() {
        super();
    }

    public Crop(String crop_name, String measuring_unit, Bitmap image) {
        super();
        this.online_id = -1;
        this.crop_name = crop_name;
        this.measuring_unit = measuring_unit;
        this.saveImage(image);
        this.action = GeneralConstants.ADD;
    }

    public Crop(int online_id, String crop_name, String measuring_unit, Bitmap image) {
        super();
        this.online_id = online_id;
        this.crop_name = crop_name;
        this.measuring_unit = measuring_unit;
        this.saveImage(image);
        this.action = GeneralConstants.NO_CHANGE;
    }

    public Crop(int online_id, String crop_name, String measuring_unit, String image_path) {
        super();
        this.online_id = online_id;
        this.crop_name = crop_name;
        this.measuring_unit = measuring_unit;
//        this.saveImage(image);
        this.image_path = image_path;
        this.action = GeneralConstants.NO_CHANGE;
    }

    public void saveImage(Bitmap image) {
        File myDir = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/DigitalGreen/Crops/");

        if (!myDir.exists())
            myDir.mkdirs();

        String fname = this.crop_name + ".jpg";
        File file = new File(myDir, fname);

        //Setting the photo path in ext. hard disk to the current user details
        this.image_path = myDir +"/"+ fname;
        /////////////////////////////////////

        try {
            FileOutputStream out = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        if(image_path.equals("default")){
            Drawable temp = getContext().getResources().getDrawable(R.mipmap.default_crop);
            Bitmap bitmap =((BitmapDrawable)temp).getBitmap();
            return bitmap;
        }

        Bitmap b = null;
        try {
            File f = new File(this.image_path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }

    @Override
    public String toString() {
        return crop_name;
    }

    @Override
    public boolean equals(Object another) {
        if (another.getClass().equals(this.getClass())) {
            return this.getId() == ((Crop) another).getId();
        } else return false;
    }
}

