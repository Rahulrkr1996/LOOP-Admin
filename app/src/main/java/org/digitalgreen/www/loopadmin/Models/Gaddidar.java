package org.digitalgreen.www.loopadmin.Models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;
import org.digitalgreen.www.loopadmin.Constants.ImageSavingConstants;

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
    public String name;

    @Expose
    @Column(name = "gaddidar_contact")
    public String contact;

    @Expose
    @Column(name = "commission")
    public double commission;

    @Expose
    @Column(name = "mandi", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column
            .ForeignKeyAction.CASCADE)
    public Mandi mandi;

    public Gaddidar() {
        super();
    }

    //Add New Gaddidar
    public Gaddidar(String name, String contact,Double commission, Bitmap image, Mandi mandi) {
        super();
        //this.image = image;
        this.name = name;
        this.contact = contact;
        this.mandi = mandi;
        this.online_id = -1;
        this.commission = commission;
        this.saveImage(image);
    }

    public Gaddidar(int online_id, String name, Double commission, String contact, Mandi mandi, Bitmap image) {
        super();
        //this.image = image;
        this.online_id = online_id;
        this.name = name;
        this.contact = contact;
        this.mandi = mandi;
        this.commission = commission;
        this.saveImage(image);
    }

    public Gaddidar(int online_id, Double commission, String name, String contact, Mandi mandi, String image_path) {
        super();
        this.image_path = image_path;
        this.online_id = online_id;
        this.name = name;
        this.contact = contact;
        this.mandi = mandi;
        this.commission = commission;
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
            return name + " (" + mandi.toString() + ")";
        } catch (Exception e) {
            return name;
        }
    }


    @Override
    public boolean equals(Object o) {
        Gaddidar f = (Gaddidar) o;
        return this.getId() == f.getId();
    }

    public void saveImage(Bitmap image) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/DigitalGreen/"+ ImageSavingConstants.SAVE_GADDIDAR_IMG);
        if (image != null) {

            myDir.mkdirs();
            String fname =  this.name + "_" + this.contact +".jpg";
            File file = new File(myDir, fname);

            //Setting the photo path in ext. hard disk to the current user details
            this.image_path = myDir + fname;
            /////////////////////////////////////

            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream out = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getImage() {
        Bitmap b = null;
        try {
            File f = new File(this.image_path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            return b;
        }
    }
}
