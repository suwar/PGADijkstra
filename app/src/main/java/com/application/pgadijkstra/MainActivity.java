package com.application.pgadijkstra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PagarAlam, DEFAULT_ZOOM));
            //getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }

        // Add a marker in Pagar Alam and move the camera
        LatLng AirTerjunLematang = new LatLng(-4.072735, 103.321704);
        LatLng Curup7Kenangan = new LatLng(-4.015061, 103.183256);
        LatLng CurupAlapAlap = new LatLng(-4.015494, 103.181303);
        LatLng CurupAirKarang = new LatLng( -4.063054, 103.333644);
        LatLng CurupBesemah = new LatLng( -3.988306, 103.399167);
        LatLng CurupEmbun = new LatLng(-4.015820, 103.194081);
        LatLng CurupMangkok = new LatLng(-4.013589, 103.188362);
        LatLng CurupMaung = new LatLng( -3.989848, 103.392476);
        LatLng CurupNapalKuning = new LatLng(  -4.134278, 103.311583);
        LatLng CurupPintuLangit = new LatLng(-4.098696, 103.213349);
        LatLng CurupSendangDerajat = new LatLng(-4.014518, 103.184703);
        LatLng GreenParadise = new LatLng(-4.068537, 103.190882);
        LatLng HutanBambu = new LatLng(-4.011008, 103.195478);
        LatLng LandingParalayang = new LatLng(-4.024241, 103.167892);
        LatLng LimestoneAyekBesemah = new LatLng( -4.038278, 103.388472);
        LatLng Tangga2001 = new LatLng(-4.037824, 103.190280);
        LatLng TebatReban = new LatLng(-4.016713, 103.263158);
        LatLng TuguRimau = new LatLng(-4.024452, 103.154490);

        mMap.addMarker(new MarkerOptions().position(AirTerjunLematang).title("Air Terjun Lematang"));
        mMap.addMarker(new MarkerOptions().position(Curup7Kenangan).title("Curup 7 Kenangan"));
        mMap.addMarker(new MarkerOptions().position(CurupAirKarang).title("Curup Air Karang"));
        mMap.addMarker(new MarkerOptions().position(CurupAlapAlap).title("Curup Alap-Alap"));
        mMap.addMarker(new MarkerOptions().position(CurupBesemah).title("Curup Besemah"));
        mMap.addMarker(new MarkerOptions().position(CurupEmbun).title("Curup Embun"));
        mMap.addMarker(new MarkerOptions().position(CurupMaung).title("Curup Maung"));
        mMap.addMarker(new MarkerOptions().position(CurupMangkok).title("Curup Mangkok"));
        mMap.addMarker(new MarkerOptions().position(CurupNapalKuning).title("Curup Napal Kuning"));
        mMap.addMarker(new MarkerOptions().position(CurupPintuLangit).title("Curup Pintu Langit"));
        mMap.addMarker(new MarkerOptions().position(CurupSendangDerajat).title("Curup Sendang Derajat"));
        mMap.addMarker(new MarkerOptions().position(GreenParadise).title("Green Paradise"));
        mMap.addMarker(new MarkerOptions().position(HutanBambu).title("Hutan Bambu"));
        mMap.addMarker(new MarkerOptions().position(LandingParalayang).title("Landing Paralayang"));
        mMap.addMarker(new MarkerOptions().position(LimestoneAyekBesemah).title("Limestone Ayek Besemah"));
        mMap.addMarker(new MarkerOptions().position(Tangga2001).title("Tangga 2001"));
        mMap.addMarker(new MarkerOptions().position(TebatReban).title("Tebat Reban"));
        mMap.addMarker(new MarkerOptions().position(TuguRimau).title("Tugu Rimau"));
    }

    private String TAG = "MainActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int DEFAULT_ZOOM = 10;
    private static final float MY_LOCATION_ZOOM = 15f;
    private boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final LatLng PagarAlam = new LatLng( -4.066010, 103.268494);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button pga = (Button) findViewById(R.id.pga);
        final Button normalView = (Button) findViewById(R.id.btnNormalView);
        final Button satelitView = (Button) findViewById(R.id.btnSatelitView);
        final Button prosesCariRute = (Button) findViewById(R.id.btnProsesCariRute);
        final Button btnLoc = (Button) findViewById(R.id.btnLoc);

        pga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PagarAlam, DEFAULT_ZOOM));
           }
        });

        normalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                normalView.setTag(R.id.btnNormalView);
                satelitView.setVisibility(View.VISIBLE);
                normalView.setVisibility(View.GONE);
            }
        });

        satelitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                satelitView.setTag(R.id.btnSatelitView);
                normalView.setVisibility(View.VISIBLE);
                satelitView.setVisibility(View.GONE);
            }
        });
        prosesCariRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked L");
                getDeviceLocation();
            }
        });

        getLocationPermission();
    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), MY_LOCATION_ZOOM,
                                    "My Location");
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MainActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float myLocationZoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, myLocationZoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();

            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();

                }
            }
        }
    }
}