package org.digitalgreen.www.loopadmin.Parsers;

import android.speech.RecognitionService;

import com.activeandroid.query.Select;

import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.District;
import org.json.JSONArray;
import org.json.JSONObject;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Village;

/**
 * Created by tanmay on 1/11/2016.
 */
public class ParseVillages {
    public void parseVillages(JSONObject response) {
        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String village_name = obj.optString(ResponseConstants.VILLAGE_NAME, null);
                int village_id = obj.optInt(ResponseConstants.ID, -1);
                double latitude = obj.optDouble(ResponseConstants.LATITUDE);
                double longitude = obj.optDouble(ResponseConstants.LONGITUDE);

                JSONObject block = obj.getJSONObject(ResponseConstants.BLOCK);
                int block_id = block.optInt(ResponseConstants.ID, -1);
                Block bloc = new Select().from(Block.class).where("online_id = ? ", block_id).executeSingle();

                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                Village village = new Village(village_id, village_name, latitude, longitude, bloc.name,is_visible);
                village.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
