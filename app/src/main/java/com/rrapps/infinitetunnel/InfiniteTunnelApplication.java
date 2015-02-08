package com.rrapps.infinitetunnel;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

/**
 * Created by abhishek on 21/12/14.
 */


public class InfiniteTunnelApplication extends android.app.Application {

    /**
     * Enum used to identify the tracker that needs to be used for tracking.
     *
     * A single tracker is usually enough for most purposes. In case you do need multiple trackers,
     * storing them all in Application object helps ensure that they are created only once per
     * application instance.
     */
    public enum TrackerName {
        APP_TRACKER
    }
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public static final String PROPERTY_ID = "UA-57889999-1";

    public static final String LogTag = "InfiniteTunnel_Log";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    synchronized public Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = analytics.newTracker(PROPERTY_ID);
            mTrackers.put(trackerId, t);

        }
        return mTrackers.get(trackerId);
    }

}
