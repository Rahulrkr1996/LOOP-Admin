package org.digitalgreen.www.loopadmin.Parsers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Farmer;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.R;

/**
 * Created by tanmay on 1/11/2016.
 */
public class ParseFarmers {
    public void parseFarmers(Context context, JSONObject response) {

        File theDir = new File(Environment
                .getExternalStorageDirectory().getAbsolutePath() + "/DigitalGreen/Farmers/");
        if (!theDir.exists()) {
            theDir.mkdir();
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_farmer);
            File defaultImage = new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath() + "/DigitalGreen/Farmers/default.png");
            FileOutputStream fos;
            try {
                fos = new FileOutputStream(defaultImage);
                bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONArray objectsArray = response.optJSONArray(ResponseConstants.OBJECTS);
        for (int i = 0; i < objectsArray.length(); i++) {
            try {
                JSONObject obj = objectsArray.getJSONObject(i);
                String image_path = obj.optString(ResponseConstants.IMAGE_PATH, null);
                int farmer_id = obj.optInt(ResponseConstants.ID, -1);
                String farmer_name = obj.optString(ResponseConstants.NAME);
                String farmer_phone = obj.optString(ResponseConstants.PHONE);
                String farmer_gender = obj.optString(ResponseConstants.GENDER);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                JSONObject village = obj.getJSONObject(ResponseConstants.VILLAGE);
                int village_id = village.optInt(ResponseConstants.ID, -1);

                Village villageObject = new Select().from(Village.class).where("online_id = ? ", village_id).executeSingle();

                /* TODO - Use image's absolute path if server responds with image_path other wise use defautl image*/
                File imgFile;
                if (image_path != null) {
                    imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Farmers/" + image_path + ".png");
                    if (!imgFile.exists())
                        imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Farmers/default.png");
                } else
                    imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/Farmers/default.png");

                Farmer farmer = new Farmer(farmer_id, farmer_name, farmer_gender.toUpperCase().charAt(0), farmer_phone,
                        villageObject, imgFile.getAbsolutePath(), is_visible);
                farmer.save();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
