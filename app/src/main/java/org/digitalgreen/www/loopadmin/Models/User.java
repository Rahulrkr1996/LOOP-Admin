package org.digitalgreen.www.loopadmin.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * Created by Rahul Kumar on 7/7/2016.
 */
@Table(name="User")
public class User extends Model{
    @Expose
    @Column(name = "online_id")
    public int online_id;
    @Expose
    @Column(name = "user_name")
    public String user_name;
    @Expose
    @Column(name = "password")
    public String password;

    public User() {
        super();
    }

    public User(int online_id, String user_name, String password) {
        this.online_id = online_id;
        this.user_name = user_name;
        this.password = password;
    }

    public User(String user_name, String password) {
        this.online_id = -1;
        this.user_name = user_name;
        this.password = password;
    }

    public ArrayList<User> getAllUsers() {
        return new Select().from(User.class).execute();
    }

    @Override
    public boolean equals(Object o) {
        User u = (User) o;
        return this.getId() == u.getId();
    }


}
