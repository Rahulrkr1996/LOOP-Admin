package org.digitalgreen.www.loopadmin.Parsers;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.Mandi;

/**
 * Created by Abhishek on 18-Apr-16.
 */
public class ParseGaddidar {
    public void parseGaddidar(JSONObject response) {

        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                int gaddidar_id = obj.optInt(ResponseConstants.ID, -1);
                String gaddidar_name = obj.optString(ResponseConstants.GADDIDAR_NAME);
                String gaddidar_phone = obj.optString(ResponseConstants.GADDIDAR_PHONE);
                double commission = obj.optDouble(ResponseConstants.COMMISSION);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);
                String image = obj.optString(ResponseConstants.GADDIDAR_IMAGE);

                JSONObject mandi = obj.getJSONObject(ResponseConstants.MANDI);
                int mandi_id = mandi.optInt(ResponseConstants.ID, -1);

                Mandi mandiObject = new Select().from(Mandi.class).where("online_id = ? ", mandi_id).executeSingle();

                if (image == null) {
                    image = "default";
                }
                Gaddidar gaddidar = new Gaddidar(gaddidar_id, image, gaddidar_name, gaddidar_phone, commission, mandiObject, is_visible);
                gaddidar.save();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

