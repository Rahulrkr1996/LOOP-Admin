package org.digitalgreen.www.loopadmin.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 7/5/2016.
 */

@Table(name = "Farmer")
public class Farmer extends Model {

    @Column(name = "image_path")
    public String image_path;

    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "gender")
    public Character gender;

    @Expose
    @Column(name = "phone")
    public String phone;

    @Column(name = "action")
    public int action;   // 0 = ADD , 1= EDIT ,  -1 = NO CHANGE

    @Expose
    @Column(name = "village", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column
            .ForeignKeyAction.CASCADE)
    public Village village;

    @Expose
    @Column(name = "is_visible")
    public Boolean is_visible;

    public Farmer() {
        super();
    }

    //Add New Farmer
    public Farmer(String name, Character gender, String phone, Village village, Bitmap image) {
        super();
        //this.image = image;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.village = village;
        this.online_id = -1;
        this.action = GeneralConstants.ADD;
        this.saveImage(image);
    }

    public Farmer(int online_id, String name, Character gender, String phone, Village village, Bitmap image,boolean is_visible) {
        super();
        //this.image = image;
        this.online_id = online_id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.village = village;
        this.action = GeneralConstants.NO_CHANGE;
        this.saveImage(image);
        this.is_visible = is_visible;
    }

    public Farmer(int online_id, String name, Character gender, String phone, Village village, String image_path,boolean is_visible) {
        super();
        //this.image = image;
        //this.saveImage(image);
        this.image_path = image_path;
        this.online_id = online_id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.village = village;
        this.action = GeneralConstants.NO_CHANGE;
        this.is_visible = is_visible;
    }

    public int getAction() {
        return action;
    }

//    public List<VillageTransaction> VillageTransaction() {
//        return getMany(VillageTransaction.class, "Farmer");
//    }

    public List<Farmer> getFarmers(Long villageId) {
        Select select = new Select();
//        Log.i("village id is", villageId.toString());
        return select.from(Farmer.class).where("village = ?", villageId).execute();
    }

    public ArrayList<Farmer> getFarmerFromVillage(long villageId) {
        Select select = new Select();
        return select.from(Farmer.class).where("village = ?", villageId).execute();
    }

    @Override
    public String toString() {
        return name + " (" + village.toString() + ")";
    }

    public String getFarmerName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void saveImage(Bitmap image) {
        if (image == null) {
            this.image_path = "default";
            return;
        }

        File myDir = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/DigitalGreen/Farmers/");

        if (!myDir.exists())
            myDir.mkdirs();

        String fname = this.name + "_" + this.phone + ".jpg";
        File file = new File(myDir, fname);

        //Setting the photo path in ext. hard disk to the current user details
        this.image_path = myDir + "/" + fname;
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

    @Override
    public boolean equals(Object o) {
        Farmer f = (Farmer) o;
        return this.getId() == f.getId();
    }

    public Bitmap getImage() {
        if(image_path.equals("default")){
            Drawable temp = getContext().getResources().getDrawable(R.mipmap.ic_farmer_icon);
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
}
