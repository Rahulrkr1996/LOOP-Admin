package org.digitalgreen.www.loopadmin.Parsers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.R;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Rahul Kumar on 7/14/2016.
 */
public class ParseDistrict {
    public ParseDistrict() {
        super();
    }

    public void parseDistrict(Context context, JSONObject response) {
        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String district_name = obj.optString(ResponseConstants.DISTRICT_NAME, null);
                String state_name = obj.optString(ResponseConstants.STATE_NAME, null);
                double lat = obj.optDouble(ResponseConstants.LATITUDE, 0.0);
                double lng = obj.optDouble(ResponseConstants.LONGITUDE, 0.0);
                int district_id = obj.optInt(ResponseConstants.ID, -1);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                District district = new District(district_id,district_name,lat,lng,state_name, is_visible);
                district.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
