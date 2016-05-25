package org.digitalgreen.www.loopadmin.Models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 5/25/2016.
 */

@Table(name = "Vehicle")
public class Vehicle extends Model {
    @Expose
    @Column(name = "vehicle_name")
    public String vehicle_name;

    @Expose
    @Column(name = "online_id")
    public int online_id;

    @Expose
    @Column(name = "action")
    public int action;   // 0 = ADD , 1= EDIT ,  -1 = NO CHANGE

    public Vehicle(int online_id,String vehicle_name) {
        super();
        this.online_id = online_id;
        this.vehicle_name = vehicle_name;
        this.action = GeneralConstants.NO_CHANGE;
    }


    public Vehicle(String vehicle_name) {
        super();
        this.vehicle_name = vehicle_name;
        this.online_id = -1;
        this.action = GeneralConstants.NO_CHANGE;
    }


    public List<Vehicle> getAllVehicles() {
        Select select = new Select();
        ArrayList<Vehicle> vehicle_list;
        vehicle_list = select.from(Gaddidar.class).execute();
        return vehicle_list;
    }

    @Override
    public String toString() {
        return vehicle_name;
    }
}
