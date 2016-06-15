package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

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
    public List<Mandi> assigned_mandi;

    @Expose
    @Column(name = "assigned_villages")
    public List<Village> assigned_villages;

    @Expose
    @Column(name = "mobile")
    public String mobile;

    @Expose
    @Column(name = "village")
    public Village village;

    public LoopUser() {
        super();
    }

    public LoopUser(String name) {
        Village dummyVillage1 = new Village("Dummy_Village_Own");
        dummyVillage1.save();

        this.name = name;
        this.user = "Dummy";
        this.role = "Aggregator";
        this.image_path = null;
        this.mobile = "9933938814";
        this.village = dummyVillage1;
        this.online_id = -1;
        this.assigned_villages = new ArrayList<Village>();
        this.assigned_mandi = new ArrayList<Mandi>();
    }

    public LoopUser(String image_path, String user, String name, List<Mandi> assigned_mandi, List<Village> assigned_villages) {
        this.image_path = image_path;
        this.user = user;
        this.role = "Aggregator";
        this.name = name;
        this.assigned_villages = assigned_villages;
        this.assigned_mandi = assigned_mandi;
    }

    public LoopUser(String user, String name, List<Mandi> assigned_mandi, List<Village> assigned_villages) {
        this.image_path = null;
        this.user = user;
        this.name = name;
        this.role = "Aggregator";
        this.assigned_mandi = assigned_mandi;
        this.assigned_villages = assigned_villages;
    }

    public List<LoopUser> getAllAggregators(){
        return new Select().all().from(LoopUser.class).execute();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
