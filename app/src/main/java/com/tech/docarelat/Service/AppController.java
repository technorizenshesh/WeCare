package com.tech.docarelat.Service;

import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class AppController extends MultiDexApplication {

    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        AppController appController;
        synchronized (AppController.class) {
            appController = mInstance;
        }
        return appController;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (this.mImageLoader == null) {
            this.mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String str) {
        if (TextUtils.isEmpty(str)) {
            str = TAG;
        }
        request.setTag(str);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request) {
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object obj) {
        RequestQueue requestQueue = this.mRequestQueue;
        if (requestQueue != null) {
            requestQueue.cancelAll(obj);
        }
    }
}
