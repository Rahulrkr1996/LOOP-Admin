package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Kumar on 6/6/2016.
 */

@Table(name="Block")
public class Block extends Model {

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "district_name")
    public String district_name;

    public Block() {
        super();
    }

    public Block(String name, String district_name) {
        super();
        this.name = name;
        this.district_name = district_name;
    }

    public Block(String name) {
        super();
        this.name = name;
        this.district_name = "Morwa" ;
    }

    public ArrayList<Block> getAllBlocks() {
        return new Select().all().from(Block.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }
}
