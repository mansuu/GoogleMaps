package googlemaps.com.googlemapsintergration.screens;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import googlemaps.com.googlemapsintergration.R;
import googlemaps.com.googlemapsintergration.location.LocationTracker;
import googlemaps.com.googlemapsintergration.map.MapsActivity;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn_show_my_current_location)
    AppCompatButton btn_show_my_current_location;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }



    /*
    this method will get user's current location and show on map
     */
    @OnClick(R.id.btn_show_my_current_location) void getPosition(){
        LocationTracker locationTracker=new LocationTracker(context);

        if(locationTracker.canGetLocation()){
            double lattitude=0;
            double longitude=0;
            Location location=locationTracker.getLocation();
            if(location!=null){
                 lattitude =location.getLatitude();
                 longitude=location.getLongitude();
            }
            else{
                 lattitude=-34;
                 longitude=151;
            }

            Intent mapsIntent=new Intent(context, MapsActivity.class);
            mapsIntent.putExtra("lattitude",lattitude);
            mapsIntent.putExtra("longitude",longitude);
            startActivity(mapsIntent);
        }
    }


    /*
    initialise primary variables
     */
    private void init(){
        context=MainActivity.this;
    }
}
