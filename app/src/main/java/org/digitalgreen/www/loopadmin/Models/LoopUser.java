package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by Rahul Kumar on 6/3/2016.
 */

@Table(name = "LoopUser")
public class LoopUser extends Model {
    @Expose
    @Column(name = "image_path")
    public String image_path;

    @Expose
    @Column(name = "user")
    public String user;         // Mobile No.

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

    public LoopUser(String image_path, String user, String name, List<Village> assigned_villages, List<Mandi> assigned_mandi) {
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

}
