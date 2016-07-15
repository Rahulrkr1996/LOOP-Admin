package org.digitalgreen.www.loopadmin.Parsers;

import com.activeandroid.query.Select;

import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.json.JSONArray;
import org.json.JSONObject;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Mandi;

/**
 * Created by tanmay on 1/11/2016.
 */
public class ParseMandi {
    public void parseMandis(JSONObject response) {
        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String mandi_name = obj.optString(ResponseConstants.MANDI_NAME, null);
                int mandi_id = obj.optInt(ResponseConstants.ID, -1);
                double latitude = obj.optDouble(ResponseConstants.LATITUDE, 0.0);
                double longitude = obj.optDouble(ResponseConstants.LONGITUDE, 0.0);

                JSONObject district = obj.getJSONObject(ResponseConstants.DISTRICT);
                int district_id = district.optInt(ResponseConstants.ID, -1);

                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                District dist = new Select().from(District.class).where("online_id = ? ", district_id).executeSingle();
                Mandi mandi = new Mandi(mandi_id, mandi_name, latitude, longitude, dist, is_visible);
                mandi.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
