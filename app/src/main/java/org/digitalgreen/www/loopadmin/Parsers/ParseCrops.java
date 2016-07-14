package org.digitalgreen.www.loopadmin.Parsers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.R;

/**
 * Created by tanmay on 1/11/2016.
 */
public class ParseCrops {

    public ParseCrops() {
        super();
    }

    public void parseCrops(Context context, JSONObject response) {

        File theDir = new File(Environment.getExternalStorageDirectory().getPath() + "/DigitalGreen/Crops/");
        if (!theDir.exists()) {
            theDir.mkdir();
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.default_crop);
            File defaultImage = new File(Environment.getExternalStorageDirectory().getPath() + "/DigitalGreen/Crops/default.png");
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
                String crop_name = obj.optString(ResponseConstants.CROP_NAME, null);
                int crop_id = obj.optInt(ResponseConstants.ID, -1);
                String measuring_unit = obj.optString(ResponseConstants.MEASURING_UNIT, null);
                Boolean is_visible = obj.optBoolean(ResponseConstants.IS_VISIBLE, true);

                /* TODO - Use image's absolute path if server responds with image_path other wise use default image
                * TODO _ or set local path for the image */

                File imgFile;
                if (image_path != null) {
                    imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DigitalGreen/Crops/" + image_path + ".png");
                    if (!imgFile.exists())
                        imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DigitalGreen/Crops/default.png");
                } else
                    imgFile = new File(Environment.getExternalStorageDirectory().getPath() + "/DigitalGreen/Crops/default.png");


                Crop crop = new Crop(crop_id, crop_name, measuring_unit, imgFile.getAbsolutePath(), is_visible);
                crop.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
