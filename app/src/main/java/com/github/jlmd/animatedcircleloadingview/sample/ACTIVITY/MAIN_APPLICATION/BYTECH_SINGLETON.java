package com.github.jlmd.animatedcircleloadingview.sample.ACTIVITY.MAIN_APPLICATION;


import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.pixplicity.easyprefs.library.Prefs;

/**
 * Created by Mainak Karmakar on 16/09/2015.
 */
public class BYTECH_SINGLETON extends Application {

    public static final String TAG = BYTECH_SINGLETON.class.getSimpleName();
    private static final String PROPERTY_ID = "UA-51830602-8";
    private static BYTECH_SINGLETON mInstance;
 //   HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;

    public static synchronized BYTECH_SINGLETON getInstance() {
        return mInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        //Fabric.with(this, new Crashlytics());
        mInstance = this;
        Prefs.initPrefs(this);


    }

//    public synchronized Tracker getTracker(TrackerName trackerId) {
//        if (!mTrackers.containsKey(trackerId)) {
//
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
//                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
//                    : analytics.newTracker(R.xml.ecommerce_tracker);
//            mTrackers.put(trackerId, t);
//
//        }
//        return mTrackers.get(trackerId);
//    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getInstance());

        }

        return mRequestQueue;
    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new AIE_U_LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(/*Object tag*/) {
        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
            mRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }


    }

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     * <p/>
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER, // Tracker used only in this app.
        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
    }
}

//}
