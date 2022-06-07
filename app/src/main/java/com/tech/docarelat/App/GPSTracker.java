package com.tech.docarelat.App;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import com.google.firebase.analytics.FirebaseAnalytics;

public class GPSTracker extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    /* access modifiers changed from: private */
    public final Context mContext;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            LocationManager locationManager2 = (LocationManager) this.mContext.getSystemService(FirebaseAnalytics.Param.LOCATION);
            this.locationManager = locationManager2;
            this.isGPSEnabled = locationManager2.isProviderEnabled("gps");
            boolean isProviderEnabled = this.locationManager.isProviderEnabled("network");
            this.isNetworkEnabled = isProviderEnabled;
            if (this.isGPSEnabled || isProviderEnabled) {
                this.canGetLocation = true;
                if (this.isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") != 0) {
                        return null;
                    }
                    this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 2.0f, this);
                    Log.d("Network", "Network");
                    if (this.locationManager != null) {
                        Location lastKnownLocation = this.locationManager.getLastKnownLocation("network");
                        this.location = lastKnownLocation;
                        if (lastKnownLocation != null) {
                            this.latitude = lastKnownLocation.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
                if (this.isGPSEnabled && this.location == null) {
                    this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 2.0f, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (this.locationManager != null) {
                        Location lastKnownLocation2 = this.locationManager.getLastKnownLocation("gps");
                        this.location = lastKnownLocation2;
                        if (lastKnownLocation2 != null) {
                            this.latitude = lastKnownLocation2.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.location;
    }

    public void stopUsingGPS() {
        if (this.locationManager == null) {
            return;
        }
        if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
            this.locationManager.removeUpdates(this);
        }
    }

    public double getLatitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.latitude = location2.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.longitude = location2.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("GPS settings");
        builder.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                GPSTracker.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void onLocationChanged(Location location2) {
        this.location = location2;
        getLatitude();
        getLongitude();
    }
}
