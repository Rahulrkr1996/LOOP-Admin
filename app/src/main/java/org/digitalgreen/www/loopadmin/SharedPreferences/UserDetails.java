package org.digitalgreen.www.loopadmin.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.digitalgreen.www.loopadmin.Constants.GeneralConstants;

/**
 * Created by tanmay on 1/5/2016.
 */
public class UserDetails {
    private final String TOKEN_KEY = "token_key";
    private final String USER_NAME = "Username";
    private final String FULL_NAME = "Fullname";
    private final String LANGUAGE = "Language";
    private final String ROLE = "Role";
    private final String MODE = "Mode";
    private final String PHONE_NUMBER = "PhoneNumber";
    private final String USER_IMAGE_PATH = "UserImagePath";


    private final String TIMESTAMP = "TimeStamp";
    private final String HELPLINE = "helpline";

    private final String NUMBER_OF_DAYS = "numberOfDays";
    private final String USER_DISTRICT_ID = "user_district_id";
    SharedPreferences pref;
    Context _context;
    int PRIVATE_MODE = 0;


    public UserDetails(Context context) {
        this._context = context;
        String PREF_NAME = "MyPrefs";
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public String getTokenKey() {
        return pref.getString(TOKEN_KEY, null);
    }

    public void setTokenKey(String tokenKey) {
        Editor editor = pref.edit();
        editor.putString(TOKEN_KEY, tokenKey);
        editor.apply();
    }

    public String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public void setUserName(String username) {
        Editor editor = pref.edit();
        editor.putString(USER_NAME, username);
        editor.apply();
    }

    public String getFullName() {
        return pref.getString(FULL_NAME, null);
    }

    public void setFullName(String fullname) {
        Editor editor = pref.edit();
        editor.putString(FULL_NAME, fullname);
        editor.apply();
    }

    public String getLanguage() {
        return pref.getString(LANGUAGE, GeneralConstants.HINDI_LANGUAGE);
    }

    public void setLanguage(String language) {
        Editor editor = pref.edit();
        editor.putString(LANGUAGE, language);
        editor.apply();
    }

    public String getRole() {
        return pref.getString(ROLE, "aggregator");
    }

   /* public void setRole(String role) {
        Editor editor = pref.edit();
        editor.putString(ROLE, role);
        editor.apply();
    }

    public int getMode() {
        return pref.getInt(MODE, 1);
    }*/

    public void setMode(int mode) {
        Editor editor = pref.edit();
        editor.putInt(MODE, mode);
        editor.apply();
    }

    public String getPhoneNumber() {
        return pref.getString(PHONE_NUMBER, null);
    }

    public void setPhoneNumber(String phoneNumber) {
        Editor editor = pref.edit();
        editor.putString(PHONE_NUMBER, phoneNumber);
        editor.apply();
    }

    public String getTimestamp() {
        return pref.getString(TIMESTAMP, null);
    }

    public void setTimestamp(String timestamp) {
        Editor editor = pref.edit();
        editor.putString(TIMESTAMP, timestamp);
        editor.apply();
    }

    public String getHelpLine() {
        return pref.getString(HELPLINE, "09891256494");
    }

    public void setHelpLine(String helpline) {
        Editor editor = pref.edit();
        editor.putString(HELPLINE, helpline);
        editor.apply();
    }

    public String getUserImagePath() {
        return pref.getString(USER_IMAGE_PATH, null);
    }

    public void setUserImagePath(String imagePath) {
        Editor editor = pref.edit();
        editor.putString(USER_IMAGE_PATH, imagePath);
        editor.apply();
    }

    public int getNumberOfDays() {
        return pref.getInt(NUMBER_OF_DAYS, 3);
    }

    public void setNumberOfDays(int numberOfDays) {
        Editor editor = pref.edit();
        editor.putInt(NUMBER_OF_DAYS, numberOfDays);
        editor.apply();
    }

    /*public int getUserDistrictId() {
        return pref.getInt(USER_DISTRICT_ID, -1);
    }*/

    public void setUserDistrictId(int userDistrictId) {
        Editor editor = pref.edit();
        editor.putInt(USER_DISTRICT_ID, userDistrictId);
        editor.apply();
    }

    public void clearData() {
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

}
