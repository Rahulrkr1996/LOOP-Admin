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

import org.digitalgreen.www.loopadmin.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 6/3/2016.
 */

@Table(name = "LoopUser")
public class LoopUser extends Model {
    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "image_path")
    public String image_path;

    @Expose
    @Column(name = "user")
    public String user;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "role")
    public String role;

    @Expose
    @Column(name = "assigned_mandi")
    public ArrayList<Mandi> assigned_mandi;

    @Expose
    @Column(name = "assigned_villages")
    public ArrayList<Village> assigned_villages;

    @Expose
    @Column(name = "contact")
    public String contact;

    @Expose
    @Column(name = "village")
    public Village village;

    public LoopUser() {
        super();
    }

    public LoopUser(Bitmap image, String user, String name, String role, ArrayList<Mandi> assigned_mandi, ArrayList<Village> assigned_villages, String contact, Village village) {
        this.saveImage(image);
        this.user = user;
        this.name = name;
        this.role = role;
        this.assigned_mandi = assigned_mandi;
        this.assigned_villages = assigned_villages;
        this.contact = contact;
        this.village = village;
    }

    public LoopUser(String name) {
        super();

        Village dummyVillage1 = new Village("Dummy_Village_Own");
        dummyVillage1.save();

        this.name = name;
        this.user = "Dummy";
        this.role = "Aggregator";
        this.saveImage(null);
        this.contact = "9933938814";
        this.village = dummyVillage1;
        this.online_id = -1;
        this.assigned_villages = new ArrayList<Village>();
        this.assigned_mandi = new ArrayList<Mandi>();
    }

    public LoopUser(Bitmap image, String user, String name, ArrayList<Mandi> assigned_mandi, ArrayList<Village> assigned_villages) {
        this.saveImage(image);
        this.user = user;
        this.role = "Aggregator";
        this.name = name;
        this.assigned_villages = assigned_villages;
        this.assigned_mandi = assigned_mandi;
    }

    public LoopUser(String user, String name, ArrayList<Mandi> assigned_mandi, ArrayList<Village> assigned_villages) {
        this.saveImage(null);
        this.user = user;
        this.name = name;
        this.role = "Aggregator";
        this.assigned_mandi = assigned_mandi;
        this.assigned_villages = assigned_villages;
    }

    public List<LoopUser> getAllAggregators(){
        return new Select().all().from(LoopUser.class).where("role = ?", "Aggregator").execute();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public List<LoopUser> getAggregatorsFromMandi(Long mandiId) {
        Select select = new Select();
        return select.from(LoopUser.class)
                .where("mandi = ?", mandiId)
                .where("role = ?", "Aggregator")
                .execute();
    }


    public void saveImage(Bitmap image) {
        if(image==null){
            this.image_path = "default" ;
            return ;
        }

        File myDir = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/DigitalGreen/LoopUser/");

        if (!myDir.exists())
            myDir.mkdirs();

        String fname = this.user + ".jpg";

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

    ////////////////////////////////////////////////////////////////
    public Bitmap getImage() {
        if(image_path.equals("default")){
            Drawable temp = getContext().getResources().getDrawable(R.mipmap.ic_my_profile);
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
    ///////////////////////////////////////////////////////////////

}
