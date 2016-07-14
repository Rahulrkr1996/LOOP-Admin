package org.digitalgreen.www.loopadmin;

import org.digitalgreen.www.loopadmin.Models.Mandi;

/**
 * Created by Rahul Kumar on 7/12/2016.
 */
public class MandiCheck {
    private Mandi mandi;
    private boolean check;

    public MandiCheck(Mandi mandi, boolean check) {
        this.mandi = mandi;
        this.check = check;
    }

    public MandiCheck(Mandi mandi) {
        this.mandi = mandi;
        this.check = false;
    }

    public Mandi getMandi() {
        return mandi;
    }

    public boolean isCheck() {
        return check;
    }

    public void setMandi(Mandi mandi) {
        this.mandi = mandi;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
