package app.swoking.fr.testmap.Activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Timer;
import java.util.TimerTask;

import app.swoking.fr.testmap.Game.Character;
import app.swoking.fr.testmap.Game.MyPlayerCircle;
import app.swoking.fr.testmap.Game.Skin.Skin;
import app.swoking.fr.testmap.R;
import app.swoking.fr.testmap.User;

public class MapsActivity extends FragmentActivity
        implements OnMapReadyCallback {

    private static GoogleMap mMap;
    private User user;
    private Character character;
    private Skin skin;

    GoogleApiClient mGoogleApiClient;

    // private MyLocation myLocation;
    // private MyMarker   myMarker;

    Location mLastLocation;
    Location mActuLocation;
    LatLng mLastLatLng;
    LatLng mActuLatLng;

    Marker mCurrLocationMarker;
    Marker mLastLocationMarker;
    Marker myMarker;

    MyPlayerCircle myPlayerCircle;

    LocationManager locationManager;
    LocationListener locationListener = new android.location.LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if(mCurrLocationMarker != null) mCurrLocationMarker.remove();
            if(mLastLocationMarker != null) mLastLocationMarker.remove();
            if(myMarker != null)            myMarker.remove();

            mLastLocation = mActuLocation;
            mActuLocation = location;
            if (mLastLocation == null) mLastLocation = location;
            mLastLatLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            mActuLatLng = new LatLng(mActuLocation.getLatitude(), mActuLocation.getLongitude());






            myMarker = mMap.addMarker(new MarkerOptions()
                    .position(mLastLatLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title("Moving Position"));

            final LatLng startPosition = myMarker.getPosition();
            final LatLng finalPosition = mActuLatLng;
            final Handler handler = new Handler();
            final long start = SystemClock.uptimeMillis();
            final Interpolator interpolator = new AccelerateDecelerateInterpolator();
            final float durationInMs = 1000;
            final boolean hideMarker = false;
            handler.post(new Runnable() {
                long elapsed;
                float t;
                float v;

                @Override
                public void run() {
                    // Calculate progress using interpolator
                    elapsed = SystemClock.uptimeMillis() - start;
                    t = elapsed / durationInMs;
                    v = interpolator.getInterpolation(t);

                    LatLng currentPositionMoving = new LatLng(
                            startPosition.latitude*(1-t)+finalPosition.latitude*t,
                            startPosition.longitude*(1-t)+finalPosition.longitude*t);

                    myMarker.setPosition(currentPositionMoving);

                    myPlayerCircle.updateCircleLocation(currentPositionMoving);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPositionMoving));

                    // Repeat till progress is complete.
                    if (t < 1) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });











            MarkerOptions actuMarkerOptions = new MarkerOptions();
            actuMarkerOptions.position(mActuLatLng);
            actuMarkerOptions.title("Current Position");
            actuMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMap.addMarker(actuMarkerOptions);
            MarkerOptions LastMarkerOptions = new MarkerOptions();
            LastMarkerOptions.position(mLastLatLng);
            LastMarkerOptions.title("Last Position");
            LastMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
            mLastLocationMarker = mMap.addMarker(LastMarkerOptions);


        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            if (i == 0) Log.e("onStatusChanged", "OUT_OF_SERVICE");
            if (i == 1) Log.e("onStatusChanged", "TEMPORARILY_UNAVAILABLE");
            if (i == 2) Log.e("onStatusChanged", "AVAILABLE");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.e("onProviderEnabled", "ProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.e("onProviderDisabled", "ProviderDisabled");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);

        user      = (User)      getIntent().getSerializableExtra("user");
        character = (Character) getIntent().getSerializableExtra("character");
        skin      = (Skin)      getIntent().getSerializableExtra("skin");

        // myLocation = new MyLocation();
        // myMarker   = new MyMarker();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMaxZoomPreference(19);
        mMap.setMinZoomPreference(18);

        myPlayerCircle = new MyPlayerCircle(mMap);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        myPlayerCircle.updatePlayerCircle();
                    }
                });
            }
        }, 0, 100);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location mobileLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //Log.e("mobileLocation", String.valueOf(mobileLocation.getLatitude()));
        //Log.e("mobileLocation", String.valueOf(mobileLocation.getLongitude()));


        // MyGame GAME = new MyGame(mMap, myLocation, myMarker);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.e("onResume", "onResume");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 0, locationListener);
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("onPause", "onPause");
        if(mGoogleApiClient != null) LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) locationListener);
    }
}
