package org.digitalgreen.www.loopadmin.Parsers;

import android.content.Context;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Block;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Rahul Kumar on 7/14/2016.
 */
public class ParseBlocks {
    public ParseBlocks() {
        super();
    }

    public void parseBlocks(Context context, JSONObject response) {
        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String block_name = obj.optString(ResponseConstants.BLOCK_NAME, null);
                String district_name = obj.optString(ResponseConstants.DISTRICT_NAME, null);
                int block_id = obj.optInt(ResponseConstants.ID, -1);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                Block block = new Block(block_id,block_name,district_name, is_visible);
                block.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
