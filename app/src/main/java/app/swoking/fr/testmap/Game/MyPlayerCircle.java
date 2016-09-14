package app.swoking.fr.testmap.Game;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rgab7 on 09/09/2016.
 */
public class MyPlayerCircle {

    private Circle playerCircle;
    private Circle playerSondeCircle;
    private int sondeRadiusMax;
    private int sondeRarius;
    private int sondeAlpha;

    private LatLng    latLng;
    private GoogleMap mMap;

    public MyPlayerCircle(GoogleMap mMap) {
        this.sondeRarius = 0;
        this.sondeAlpha  = 250;
        this.sondeRadiusMax = 40;
        this.mMap = mMap;
    }

    public void updatePlayerCircle() {

        if(latLng            == null) return;
        if(mMap              == null) return;
        if(playerCircle      != null) playerCircle.remove();
        if(playerSondeCircle != null) playerSondeCircle.remove();

        if(this.sondeRarius++ >= 40) this.sondeRarius = 0;
        if(this.sondeRarius    > 30) this.sondeAlpha -= 25;
        if(this.sondeRarius   == 0 ) this.sondeAlpha  = 250;
        if(this.sondeAlpha     < 0 ) this.sondeAlpha  = 0;

        playerCircle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(30)
                .fillColor(Color.argb(0,0,0,0))
                .strokeColor(Color.argb(200,101,29,235)));

        playerSondeCircle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(this.sondeRarius)
                .fillColor(Color.argb(0,0,0,0))
                .strokeColor(Color.argb(this.sondeAlpha,26,238,121)));

    }

    public void updateCircleLocation(LatLng latLng){
        this.latLng = latLng;
    }

}
