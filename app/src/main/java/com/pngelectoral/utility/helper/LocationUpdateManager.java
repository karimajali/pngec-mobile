package com.pngelectoral.utility.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.Location;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsRequest.Builder;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;


public final class LocationUpdateManager {

    public static final int REQUEST_CHECK_SETTINGS = 14;
    public static final long MIN_DISTANCE = 200;

    private static final int LOCATION_REFRESH_INTERVAL_MILLI = 10000;
    private static final int LOCATION_REFRESH_FASTEST_INTERVAL_MILLI = 5000;
    private static final int SMALLEST_DISPLACEMENT = 50;

    private final Activity mContext;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private final LocationCallback locationUpdateListener;
    private LocationChangeListener locationCallBack;

    public static boolean isLocationEnabled(Context context) {
        int locationMode;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

    public interface LocationChangeListener {
        void onLocationChange(Location location);
    }

    public LocationUpdateManager(@NotNull Activity context, @NotNull LocationChangeListener locationCallBack) {
        super();
        this.mContext = context;
        this.locationCallBack = locationCallBack;
        this.locationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        this.locationRequest = new LocationRequest();
        this.locationUpdateListener = new LocationCallback() {
            public void onLocationResult(@Nullable LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    sendLocalBroadcast(context, location);
                    if (LocationUpdateManager.this.locationCallBack != null)
                        LocationUpdateManager.this.locationCallBack.onLocationChange(location);
                }

            }
        };
        this.locationRequest.setInterval(LOCATION_REFRESH_INTERVAL_MILLI)
                .setFastestInterval(LOCATION_REFRESH_FASTEST_INTERVAL_MILLI)
                .setSmallestDisplacement(SMALLEST_DISPLACEMENT)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

//        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Builder builder = new Builder();
        builder.addLocationRequest(this.locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        settingsClient.checkLocationSettings(locationSettingsRequest);
    }

    @SuppressLint({"MissingPermission"})
    public final void registerUpdateListener() {
        this.locationProviderClient.requestLocationUpdates(this.locationRequest, this.locationUpdateListener, Looper.myLooper());
    }

    public final void unRegisterUpdateListener() {
        this.locationProviderClient.removeLocationUpdates(this.locationUpdateListener);
    }

    public void setLocationCallback(LocationChangeListener locationCallBack) {
        this.locationCallBack = locationCallBack;
    }

    @SuppressLint({"MissingPermission"})
    private void getLastKnownLocation() {
        this.locationProviderClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    sendLocalBroadcast(mContext, location);
                    if (this.locationCallBack != null)
                        this.locationCallBack.onLocationChange(location);
                });
    }

    private void sendLocalBroadcast(@NotNull Context context, Location location) {
//        Intent intent = new Intent(LOCATION_UPDATE_ACTION);
//        intent.putExtra("location_update", location);
//        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void displayLocationSettingsRequest(Activity activity) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        Builder builder = new Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(activity)
                .checkLocationSettings(builder.build());

        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.
            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(activity, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException | ClassCastException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }
}
