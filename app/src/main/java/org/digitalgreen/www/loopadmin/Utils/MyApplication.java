package org.digitalgreen.www.loopadmin.Utils;

/**
 * Created by Rahul Kumar on 5/20/2016.
 */

import android.app.Application;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication sInstance;
    private RequestQueue mRequestQueue;
//    private RefWatcher refWatcher;

    public synchronized static MyApplication getInstance() {
        return sInstance;
    }

   /* public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }*/


    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
//        refWatcher = LeakCanary.install(this);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
