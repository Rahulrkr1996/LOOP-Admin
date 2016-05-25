package org.digitalgreen.www.loopadmin.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 5/20/2016.
 */

@Table(name = "Gaddidar")
public class Gaddidar extends Model {

    @Column(name = "image_path")
    public String image_path;

    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "gaddidar_name")
    public String gaddidar_name;

    @Expose
    @Column(name = "gaddidar_phone")
    public String gaddidar_phone;

    @Expose
    @Column(name = "commission")
    public double commission;

    @Column(name = "action")
    public int action;   // 0 = ADD , 1= EDIT ,  -1 = NO CHANGE

    @Expose
    @Column(name = "mandi", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column
            .ForeignKeyAction.CASCADE)

    public Mandi mandi;

    public Gaddidar() {
        super();
    }

    //Add New Gaddidar
    public Gaddidar(String name, Double commission, String phone, Mandi mandi, Bitmap image) {
        super();
        //this.image = image;
        this.gaddidar_name = name;
        this.gaddidar_phone = phone;
        this.mandi = mandi;
        this.online_id = -1;
        this.action = GeneralConstants.ADD;
        this.commission = commission;
        this.saveImage(image);
    }

    public Gaddidar(int online_id, String name, Double commission, String phone, Mandi mandi, Bitmap image) {
        super();
        //this.image = image;
        this.online_id = online_id;
        this.gaddidar_name = name;
        this.gaddidar_phone = phone;
        this.mandi = mandi;
        this.commission = commission;
        this.action = GeneralConstants.NO_CHANGE;
        this.saveImage(image);
    }

    public Gaddidar(int online_id, Double commission, String name, String phone, Mandi mandi, String image_path) {
        super();
        this.image_path = image_path;
        this.online_id = online_id;
        this.gaddidar_name = name;
        this.gaddidar_phone = phone;
        this.mandi = mandi;
        this.commission = commission;
        this.action = GeneralConstants.NO_CHANGE;
    }


    public int getAction() {
        return action;
    }

    public void saveImage(Bitmap image) {
//        ContextWrapper cw = new ContextWrapper(getContext().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
//        File directory = cw.getDir("images", Context.MODE_PRIVATE);
        // Create imageDir

        if (image != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            File mypath = new File("/sdcard/Gaddidar/" + this.getGaddidarName() + this.getPhone() + ".png");

            try {
                mypath.createNewFile();
                FileOutputStream fos;
                fos = new FileOutputStream(mypath);

                // Use the compress method on the BitMap object to write image to the OutputStream
//            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.write(bytes.toByteArray());
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.image_path = mypath.getAbsolutePath();
        } else {
            File mypath = new File("/sdcard/Gaddidar/default.png");
            this.image_path = mypath.getAbsolutePath();

        }
    }

    public List<Gaddidar> getGaddidars(Long mandiId) {
        Select select = new Select();
        return select.from(Gaddidar.class).where("mandi = ?", mandiId).execute();
    }

    public List<Gaddidar> getAllGaddidars() {
        Select select = new Select();
        ArrayList<Gaddidar> gaddidar_list;
//        Log.i("district id", district.getId()+"");
        gaddidar_list = select.from(Gaddidar.class).execute();
        return gaddidar_list;
    }

    @Override
    public String toString() {
        try {
            return gaddidar_name + " (" + mandi.toString() + ")";
        } catch (Exception e) {
            return gaddidar_name;
        }
    }

    public String getImage_path() {
        return image_path;
    }

    public int getOnline_id() {
        return online_id;
    }

    public void setOnline_id(int online_id) {
        this.online_id = online_id;
    }

    public String getGaddidarName() {
        return gaddidar_name;
    }

    public String getPhone() {
        return gaddidar_phone;
    }

    public void setPhone(String phone) {
        this.gaddidar_phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        Gaddidar f = (Gaddidar) o;
        return this.getId() == f.getId();
    }

    public Bitmap getImage() {
        Bitmap b = null;
        try {
            File f = new File(this.image_path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            return b;
        }
    }
}
