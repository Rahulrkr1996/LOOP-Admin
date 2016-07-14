package org.digitalgreen.www.loopadmin.Constants;

/**
 * Created by Rahul Kumar on 7/13/2016.
 */
public class UrlConstants {
    private static final String BASE_URL = "http://www.digitalgreen.org/loop/";

    public static final String SYNC_CROPS_WITH_SERVER = BASE_URL + "api/v1/crop/";
    public static final String SYNC_FARMERS_WITH_SERVER = BASE_URL + "api/v1/farmer/";
    public static final String SYNC_TRANSACTIONS_WITH_SERVER = BASE_URL + "api/v1/combinedtransaction/";
    public static final String SYNC_TRANSPORTERS_WITH_SERVER = BASE_URL + "api/v1/transporter/";
    public static final String SYNC_TRANSPORTATION_VEHICLE_WITH_SERVER = BASE_URL + "api/v1/transportationvehicle/";
    public static final String SYNC_DAY_TRANSPORTATION_WITH_SERVER = BASE_URL + "api/v1/daytransportation/";


    public static final String GET_LOG = BASE_URL + "get_log/";

    public static final String LOGIN_URL = BASE_URL + "login/";
    public static final String GET_CROP_URL = BASE_URL + "api/v1/crop/?format=json";
    public static final String GET_TRANSACTIONS_URL = BASE_URL + "api/v1/combinedtransaction/?format=json";
    public static final String GET_FARMERS_URL = BASE_URL + "api/v1/farmer/?format=json";
    public static final String GET_MANDIS_URL = BASE_URL + "api/v1/mandi/?format=json";
    public static final String GET_VILLAGES_URL = BASE_URL + "api/v1/village/?format=json";

    public static final String GET_VEHICLES_URL = BASE_URL + "api/v1/vehicle/?format=json";
    public static final String GET_TRANSPORTERS_URL = BASE_URL + "api/v1/transporter/?format=json";
    public static final String GET_TRANSPORTATION_VEHICLE_URL = BASE_URL + "api/v1/transportationvehicle/?format=json";
    public static final String GET_DAY_TRANSPORTATION_URL = BASE_URL + "api/v1/daytransportation/?format=json";
    public static final String GET_BLOCKS_URL = BASE_URL + "api/v1/block/?format=json";
    public static final String GET_DISTRICT_URL = BASE_URL + "api/v1/district/?format=json";
    public static final String GET_GADDIDAR_URL = BASE_URL + "api/v1/gaddidar/?format=json";
}
