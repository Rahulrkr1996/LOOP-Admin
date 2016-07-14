package org.digitalgreen.www.loopadmin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.digitalgreen.www.loopadmin.Constants.NetworkRequestConstants;
import org.digitalgreen.www.loopadmin.Constants.ResponseConstants;
import org.digitalgreen.www.loopadmin.Constants.UrlConstants;
import org.digitalgreen.www.loopadmin.Models.Block;
import org.digitalgreen.www.loopadmin.Models.Crop;
import org.digitalgreen.www.loopadmin.Models.District;
import org.digitalgreen.www.loopadmin.Models.Farmer;
import org.digitalgreen.www.loopadmin.Models.Gaddidar;
import org.digitalgreen.www.loopadmin.Models.LoopUser;
import org.digitalgreen.www.loopadmin.Models.Mandi;
import org.digitalgreen.www.loopadmin.Models.User;
import org.digitalgreen.www.loopadmin.Models.Vehicle;
import org.digitalgreen.www.loopadmin.Models.Village;
import org.digitalgreen.www.loopadmin.Parsers.ParseBlocks;
import org.digitalgreen.www.loopadmin.Parsers.ParseCrops;
import org.digitalgreen.www.loopadmin.Parsers.ParseDistrict;
import org.digitalgreen.www.loopadmin.Parsers.ParseFarmers;
import org.digitalgreen.www.loopadmin.Parsers.ParseGaddidar;
import org.digitalgreen.www.loopadmin.Parsers.ParseMandi;
import org.digitalgreen.www.loopadmin.Parsers.ParseVehicles;
import org.digitalgreen.www.loopadmin.Parsers.ParseVillages;
import org.digitalgreen.www.loopadmin.SharedPreferences.UserDetails;
import org.digitalgreen.www.loopadmin.Utils.DisplayDialog;
import org.digitalgreen.www.loopadmin.Utils.MyApplication;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Context context;
    private EditText login_username;
    private EditText login_password;
    private ImageButton login_button;
    private UserDetails userDetails;
    private ProgressDialog progressDialog;
    private boolean villagesDataReceived = false, farmersDataReceived = false,
            cropsDataReceived = false, mandisDataReceived = false, globalflag = true, transactionsDataReceived = false;

    private String token = null;
    private String userNameText;
    private int FARMER_API_CALL = 1;
    private int GADDIDAR_API_CALL = 2;
    private boolean vehicleDataReceived = false, transporterDataReceived = false, transportationVehicleDataReceived = false,
            dayTransportationDataReceived = false, gaddidarDataReceived = false, blocksDataReceived = false, districtDataReceived=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        userDetails = new UserDetails(context);


/*
        for(int i=1;i<11;i++){
            User user = new User("Username_"+String.valueOf(i),"Password_"+String.valueOf(i));
            user.save();
            District district = new District("District_"+String.valueOf(i));
            district.save();
            Crop crop = new Crop("Crop_"+String.valueOf(i),null);
            crop.save();
            Block block = new Block("Block_"+String.valueOf(i));
            block.save();
            Village village = new Village("Village_"+String.valueOf(i),(double)(i*2.5/9),(double)i/6*1.2,block.name);
            village.save();
            Vehicle vehicle = new Vehicle("Vehicle_"+String.valueOf(i));
            vehicle.save();
            Mandi mandi = new Mandi("Mandi_" + String.valueOf(i),(double)(i*2.5/9),(double)i/6*1.2,district);
            mandi.save();
            for(int j=0;j<i;j++){
                Gaddidar gaddidar = createNewGaddidar("Gaddidar_"+String.valueOf(i)+"_"+String.valueOf(j),"993399403"+String.valueOf(j),(double)((j+1)/10),null,mandi);
                gaddidar.save();
            }
            Farmer farmer = new Farmer("Farmer_"+String.valueOf(i), GeneralConstants.MALE,"993393881"+String.valueOf(i-1),village,null);
            farmer.save();
        }
*/

        login_username = (EditText) findViewById(R.id.login_username);
        login_password = (EditText) findViewById(R.id.login_password);
        login_button = (ImageButton) findViewById(R.id.login_button);

        proceedToNextScreen();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(context, getString(R.string.pleaseWait), getString(R.string.downloadingData));
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        globalflag = false;
                    }
                });
                globalflag = true;
                userNameText = login_username.getText().toString().trim();
                clearDB();
                loginUser();
            }
        });
    }

    void loginUser() {

        StringRequest request = new StringRequest(Request.Method.POST, UrlConstants
                .LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject responseObject = new JSONObject(response);


                            token = responseObject.optString(ResponseConstants.KEY, null);
                            userDetails.setTimestamp(responseObject.optString(ResponseConstants.TIMESTAMP,
                                    null));
                            //MODE = 1 means Direct Sell
                            //MODE = 2 means Aggregator mode
                            userDetails.setMode(responseObject.optInt(ResponseConstants.MODE));
                            userDetails.setFullName(responseObject.optString(ResponseConstants.FULL_NAME));
                            userDetails.setUserName(userNameText);
                            userDetails.setHelpLine(responseObject.optString(ResponseConstants.HELPLINE));
                            userDetails.setPhoneNumber(responseObject.optString(ResponseConstants.PHONE_NUMBER));
                            userDetails.setUserDistrictId(responseObject.optInt(ResponseConstants.DISTRICT, -1));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getAllDetails();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userDetails.clearData();
                if (error instanceof NoConnectionError) {
                    Toast.makeText(context, getString(R.string.checkInternetConnection), Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(context, getString(R.string.checkInternetConnection), Toast.LENGTH_LONG).show();
                } else {
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && (networkResponse.statusCode == 401 || networkResponse.statusCode == 403)) {
                        try {
                            new DisplayDialog().displayDialog(context, getString(R.string.authentication_error));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(NetworkRequestConstants.USER_NAME, userNameText);
                params.put(NetworkRequestConstants.PASSWORD, login_password.getText().toString().trim());
                return params;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(
                10000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().getRequestQueue().add(request);

    }

    void getAllDetails() {
        callDistrictApi();
        callBlocksApi();
        callCropsApi();
        callVehicles();
        callMandisApi();
        callVillagesApi();
    }

    void callVillagesApi() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_VILLAGES_URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new ParseVillages().parseVillages(response);
                            villagesDataReceived = true;
                            callFarmersOrGaddidarApi(FARMER_API_CALL);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callDistrictApi() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_DISTRICT_URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new ParseDistrict().parseDistrict(context, response);
                            districtDataReceived = true;
                            checkAllDataReceivedAndProceed();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callBlocksApi() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_BLOCKS_URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new ParseBlocks().parseBlocks(context, response);
                            blocksDataReceived = true;
                            checkAllDataReceivedAndProceed();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callFarmersOrGaddidarApi(final int apiChooser) {
        try {
            String URL = null;
            if (apiChooser == FARMER_API_CALL)
                URL = UrlConstants.GET_FARMERS_URL;
            else if (apiChooser == GADDIDAR_API_CALL)
                URL = UrlConstants.GET_GADDIDAR_URL;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // handle response
                            if (apiChooser == FARMER_API_CALL) {
                                new ParseFarmers().parseFarmers(context, response);
                                farmersDataReceived = true;
                            } else if (apiChooser == GADDIDAR_API_CALL) {
                                new ParseGaddidar().parseGaddidar(response);
                                gaddidarDataReceived = true;
                            }
                            checkAllDataReceivedAndProceed();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callCropsApi() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_CROP_URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new ParseCrops().parseCrops(context, response);
                            cropsDataReceived = true;
                            checkAllDataReceivedAndProceed();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callMandisApi() {
        try {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_MANDIS_URL,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            new ParseMandi().parseMandis(response);
                            mandisDataReceived = true;
                            //CALLING GADDIDAR API
                            callFarmersOrGaddidarApi(GADDIDAR_API_CALL);
//                            checkAllDataReceivedAndProceed();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    NetworkResponse netResponse = error.networkResponse;
                    if (netResponse != null && netResponse.data != null) {
                        switch (netResponse.statusCode) {
                            case 401:
                                Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                    if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put(NetworkRequestConstants.AUTHORIZATION,
                            NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                    return headers;
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000, 0,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void callVehicles() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, UrlConstants.GET_VEHICLES_URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        new ParseVehicles().parseVehicles(response);
                        vehicleDataReceived = true;
                        checkAllDataReceivedAndProceed();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse netResponse = error.networkResponse;
                if (netResponse != null && netResponse.data != null) {
                    switch (netResponse.statusCode) {
                        case 401:
                            Toast.makeText(context, getString(R.string.authentication_error), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, getString(R.string.unableToFetchData), Toast.LENGTH_SHORT).show();
                }
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put(NetworkRequestConstants.AUTHORIZATION,
                        NetworkRequestConstants.APIKEY + " " + userNameText + ":" + token);
                return headers;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MyApplication.getInstance().getRequestQueue().add(request);
    }

    void checkAllDataReceivedAndProceed() {

        if (villagesDataReceived && farmersDataReceived && cropsDataReceived && mandisDataReceived
                && globalflag && vehicleDataReceived && gaddidarDataReceived) {

            userDetails.setTokenKey(token);
            progressDialog.dismiss();
            proceedToNextScreen();
        }
    }

    private void proceedToNextScreen() {
        String user = userDetails.getUserName();
        String role = userDetails.getRole();
        String language = userDetails.getLanguage();
        String tokenKey = userDetails.getTokenKey();

        if (user != null && tokenKey != null && role != null && language != null) {
//            Crashlytics.setUserName(user);
            Intent intent = new Intent(LoginActivity.this, Home.class);
            LoginActivity.this.startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    void clearDB() {
        Delete delete = new Delete();
        delete.from(Block.class).execute();
        delete.from(Crop.class).execute();
        delete.from(District.class).execute();
        delete.from(Farmer.class).execute();
        delete.from(Gaddidar.class).execute();
        //delete.from(LoopUser.class).execute();
        delete.from(Mandi.class).execute();
        delete.from(User.class).execute();
        delete.from(Vehicle.class).execute();
        delete.from(Village.class).execute();
    }

}
