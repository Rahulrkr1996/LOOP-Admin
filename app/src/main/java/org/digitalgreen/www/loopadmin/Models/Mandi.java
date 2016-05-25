package org.digitalgreen.www.loopadmin.Models;


/**
 * Created by Rahul Kumar on 5/20/2016.
 */


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

@Table(name = "Mandi")
public class Mandi extends Model {
    //    public static final Creator<Mandi> CREATOR = new Creator<Mandi>() {
//        public Mandi createFromParcel(Parcel source) {
//            return new Mandi(source);
//        }
//
//        public Mandi[] newArray(int size) {
//            return new Mandi[size];
//        }
//    };
    @Expose
    @Column(name = "online_id")
    public int online_id;
    @Expose
    @Column(name = "mandi_name")
    public String mandi_name;
    @Column(name = "latitude")
    public double latitude;
    @Column(name = "longitude")
    public double longitude;
    @Column(name = "action")
    public int action;   // 0 = ADD , 1= EDIT ,  -1 = NO CHANGE

    public Mandi() {
        super();
        this.online_id = -1;
        this.mandi_name = "Dummy Mandi";
        this.latitude = 0;
        this.longitude = 0;
        this.action = -1;
    }

    public Mandi(String mandi_name, double latitude, double longitude) {
        super();
        this.online_id = -1;
        this.mandi_name = mandi_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.action = -1;
    }

    public Mandi(String mandi_name, double latitude, double longitude,int action) {
        super();
        this.online_id = -1;
        this.mandi_name = mandi_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.action = action;
    }

    public Mandi(int online_id, String mandi_name, double latitude, double longitude) {
        super();
        this.online_id = online_id;
        this.mandi_name = mandi_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.action = -1;
    }

    /*protected Mandi(Parcel in) {
        this.online_id = in.readInt();
        this.mandi_name = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
//        this.district = in.readParcelable(District.class.getClassLoader());
    }
*/
/*
    public List<MandiTransaction> MandiTransaction() {
        return getMany(MandiTransaction.class, "Mandi");
    }

    public ArrayList<Mandi> getMandis(District district) {

        Select select = new Select();
        ArrayList<Mandi> mandi_name;
        Log.i("district id", district.getId() + "");
        mandi_name = select.from(Mandi.class).where("district = ?", district.getId()).execute();
        return mandi_name;
    }
*/

    public ArrayList<Mandi> getMandis() {

        Select select = new Select();
        ArrayList<Mandi> mandi_name;
//        Log.i("district id", district.getId()+"");
        mandi_name = select.all().from(Mandi.class).execute();
        return mandi_name;
    }

    @Override
    public String toString() {
        return mandi_name;
    }

/*
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.online_id);
        dest.writeString(this.mandi_name);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
//        dest.writeParcelable(this.district, 0);
    }
*/

    @Override
    public boolean equals(Object o) {
        Mandi m = (Mandi) o;
        return this.getId() == m.getId();
    }
}