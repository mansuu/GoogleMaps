package googlemaps.com.googlemapsintergration.location;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import googlemaps.com.googlemapsintergration.utils.GlobalValues;

import static googlemaps.com.googlemapsintergration.utils.GlobalValues.MIN_TIME_FOR_LOCATION_UPDATE;

/**
 * Created by Himanshu on 11/25/2016.
 */

public class LocationTracker extends Service implements LocationListener {
    private Context context;
    private LocationManager locationManager;
    private Location location;
    private boolean isGpsEnabled=false;
    private boolean isNetworkEnabled=false;
    private boolean canGetLocation=false;


    /*
    constructor
     */
    public LocationTracker(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        GlobalValues.MIN_DISTANCE_FOR_LOCATION_UPDATE=10;
        GlobalValues.MIN_TIME_FOR_LOCATION_UPDATE=30*1000;

    }


    //gets location using enabled provider

    public Location getLocation() {
        //Checking gps provider status
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Checking Network provider status
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.e("neywork and gps",isGpsEnabled+"\t"+isNetworkEnabled);
        if (!isNetworkEnabled && !isGpsEnabled) {

            //No provider is enabled
        } else {
            canGetLocation=true;
            if (locationManager != null) {
                if (isGpsEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, GlobalValues.MIN_TIME_FOR_LOCATION_UPDATE, GlobalValues.MIN_DISTANCE_FOR_LOCATION_UPDATE, this);
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                else if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,GlobalValues.MIN_TIME_FOR_LOCATION_UPDATE,GlobalValues.MIN_DISTANCE_FOR_LOCATION_UPDATE,this);
                    location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                }
            }
        }
return location;
    }

    //tells location can be found or not
    public boolean canGetLocation(){
        return canGetLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
    this.location=location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * Function to show settings alert dialog
     * */
    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("Settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS/Network is not enabled. Do you want to go to settings menu?");

        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.delete);

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
}
