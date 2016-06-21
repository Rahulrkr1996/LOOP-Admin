package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 6/2/2016.
 */
@Table(name = "District")
public class District extends Model {
    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "latitude")
    public double latitude;

    @Expose
    @Column(name = "longitude")
    public double longitude;

    @Expose
    @Column(name = "state_name")
    public String state_name;

    public District(int online_id, String name, double latitude, double longitude, String state_name) {
        super();
        this.online_id = online_id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state_name = state_name;
    }

    public District() {
        super();
    }

    public District(String name, double latitude, double longitude, String state_name) {
        super();
        this.online_id = -1;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state_name = state_name;
    }

    public District(String name) {
        super();
        this.online_id = -1;
        this.name = name;
        this.latitude = 0;
        this.longitude = 0;
        this.state_name = "Bihar";
    }

    public ArrayList<District> getAllDistricts() {
        Select select = new Select();
        ArrayList<District> allDistrict;
//        Log.i("district id", district.getId()+"");
        allDistrict = select.all().from(District.class).execute();
        return allDistrict;
    }

    public District getFromName(String name){ //,String state_name){
        List<District> temp = new Select().from(District.class)
                .where("name = ?", name)
               // .where("state_name = ?", state_name)
                .execute();

        return temp.get(0) ;
    }

    @Override
    public String toString(){
        return name;
    }
}
