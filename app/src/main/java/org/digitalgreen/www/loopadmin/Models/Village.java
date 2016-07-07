package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Rahul Kumar on 6/6/2016.
 */
@Table(name="Village")
public class Village extends Model {
    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "Lat")
    public double Lat;

    @Expose
    @Column(name = "Long")
    public double Long;

    @Expose
    @Column(name = "block_name")
    public String block_name;

    public Village() {
        super();
    }

    public Village(String name, String block_name) {
        super();
        this.Lat=0;
        this.Long=0;
        this.name = name;
        this.block_name = block_name;
    }

    public Village(String name) {
        super();
        this.Lat=0;
        this.Long=0;
        this.name = name;
        this.block_name = "Morwa";
    }

    public Village(String name, double Lat, double Long, String block_name) {
        super();
        this.name = name;
        this.Lat = Lat;
        this.Long = Long;
        this.block_name = block_name;
    }

    public ArrayList<Village> getVillageFromBlock(String block_name){
        Select select = new Select();
        return select.from(Village.class).where("block_name = ?", block_name).execute();
    }

    public ArrayList<Village> getAllVillages() {
        return new Select().all().from(Village.class).execute();
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public boolean equals(Object o) {
        Village v = (Village) o;
        return this.getId() == v.getId();
    }

}
