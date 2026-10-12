package com.pngelectoral;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static android.app.Activity.RESULT_OK;


public class LocationHelper2 {

    /**
     * The constant LATITUDE.
     */
    public static double LATITUDE = 0, /**
     * The Longitude.
     */
    LONGITUDE = 0;

    /**
     * Is location available boolean.
     *
     * @return the boolean
     */
    public static boolean isLocationAvailable() {
        return !(LATITUDE == 0 && LONGITUDE == 0);
    }

    private static final String TAG = "LocationHelper2";
    /**
     * The M activity.
     */
    Activity mActivity;

    /**
     * Instantiates a new Location helper 2.
     *
     * @param activity the activity
     */
    public LocationHelper2(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * The M location callback.
     */
    LocationCallback1 mLocationCallback;
    private boolean isLocationUpdates;

    /**
     * Fetch single location.
     *
     * @param mLocationCallback the m location callback
     */
    public void fetchSingleLocation(LocationCallback1 mLocationCallback) {
        isLocationUpdates = false;
        this.mLocationCallback = mLocationCallback;

        checkLocationSettings();
    }


    /**
     * Fetch multiple location.
     *
     * @param mLocationCallback the m location callback
     */
    public void fetchMultipleLocation(LocationCallback1 mLocationCallback) {
        isLocationUpdates = true;
        this.mLocationCallback = mLocationCallback;
        checkLocationSettings();
    }

    /**
     * The constant REQUEST_CHECK_SETTINGS.
     */
//===========
    //Linked with Settings- START
    //===========
    public static final int REQUEST_CHECK_SETTINGS = 101;
    private static final int REQUEST_GPS_SETTINGS = 102;

    private void checkLocationSettings() {

        Task<LocationSettingsResponse> result = getSettingsClient().checkLocationSettings(getLocationSettingsRequest());
        result.addOnSuccessListener(mActivity, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationUpdates();
            }
        });
        result.addOnFailureListener(mActivity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                ResolvableApiException rae = (ResolvableApiException) e;
                                rae.startResolutionForResult(mActivity, REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException sendIntentException) {
                                // Ignore the error.
                            }
                        } else {
                            buildAlertMessageNoGps();
                        }
                        Log.d(TAG, "onResult() called with: locationSettingsResult = RESOLUTION_REQUIRED");
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        Log.d(TAG, "onResult() called with: locationSettingsResult = SETTINGS_CHANGE_UNAVAILABLE");
                        break;
                }

            }
        });
    }

    /**
     * Is location enabled boolean.
     *
     * @param context the context
     * @return the boolean
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        mActivity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_GPS_SETTINGS);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CHECK_SETTINGS) {
            if (mActivity != null && !mActivity.isFinishing())
                Toast.makeText(mActivity, "Checking location...", Toast.LENGTH_SHORT).show();
            checkLocationSettings();
        } else if (requestCode == REQUEST_GPS_SETTINGS) {
            if (mActivity != null && !mActivity.isFinishing())
                Toast.makeText(mActivity, "Checking location...", Toast.LENGTH_SHORT).show();
            checkLocationSettings();
        }
    }

    /**
     * The M location request.
     */
//Location Request
    LocationRequest mLocationRequest;


    private LocationRequest getLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(30000);
            mLocationRequest.setFastestInterval(20000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    //            mLocationRequest.setSmallestDisplacement(500);
        }
        return mLocationRequest;
    }


    //LocationSettings
    private LocationSettingsRequest mLocationSettingsRequest;

    private LocationSettingsRequest getLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(getLocationRequest());
        mLocationSettingsRequest = builder.build();
        return mLocationSettingsRequest;
    }

    /**
     * The Settings client.
     */
//Settings client
    SettingsClient settingsClient;

    private SettingsClient getSettingsClient() {
        if (settingsClient == null) {
            settingsClient = LocationServices.getSettingsClient(mActivity);
        }
        return settingsClient;
    }

    /**
     * The M fused location client.
     */
//Fused Location client
    FusedLocationProviderClient mFusedLocationClient;

    private FusedLocationProviderClient getFusedLocationClient() {
        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity);
        }
        return mFusedLocationClient;
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        getFusedLocationClient().requestLocationUpdates(getLocationRequest(), locationListener, Looper.myLooper());
    }

    /**
     * Stop location updates.
     */
    public void stopLocationUpdates() {
        if (mFusedLocationClient != null)
            mFusedLocationClient.removeLocationUpdates(locationListener);
    }

    /**
     * The Location listener.
     */
    LocationCallback locationListener = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location location = locationResult.getLastLocation();
            if (location != null) {
                LATITUDE = location.getLatitude();
                LONGITUDE = location.getLongitude();
            }
            Log.d(TAG, "onLocationChanged() called with: location = [" + location + "]");
            if (mLocationCallback != null) {
                mLocationCallback.onLocationChanged(location);
            }
            if (isLocationUpdates == false) {
                stopLocationUpdates();
            }
        }
    };

    /**
     * The interface Location callback 1.
     */
    public interface LocationCallback1 {
        /**
         * On location changed.
         *
         * @param location the location
         */
        void onLocationChanged(Location location);
    }
}
