package org.digitalgreen.www.loopadmin.Parsers;

import org.json.JSONArray;
import org.json.JSONObject;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Vehicle;

/**
 * Created by Abhishek on 11-Feb-16.
 */
public class ParseVehicles {
    public void parseVehicles(JSONObject response) {
        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String vehicle_name = obj.optString(ResponseConstants.VEHICLE_NAME, null);
                int online_id = obj.optInt(ResponseConstants.ONLINE_ID, -1);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);
                Vehicle vehicle = new Vehicle(vehicle_name, online_id, is_visible);
                vehicle.save();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
