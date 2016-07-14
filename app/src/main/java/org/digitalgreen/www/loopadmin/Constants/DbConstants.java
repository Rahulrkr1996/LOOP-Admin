package org.digitalgreen.www.loopadmin.Constants;

/**
 * Created by Lokesh on 2016-06-29.
 */
public class DbConstants {
    public static final String IS_VISIBLE_FLAG = " is_visible = 1 ";
    public static final String ACTION = " action <= 1 ";
    public static final String IS_VISIBLE_AND_ACTION = " is_visible = 1 and action <= 1 ";

    public static final String IS_VISIBLE_AND_ONLINE_ID_OR_ACTION = "is_visible = 1 and ( online_id = -1 or action >= 0 )";
    public static final String ONLINE_ID_OR_ACTION = "online_id = -1 or action >= 0";

}

