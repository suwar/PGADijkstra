package com.application.pgadijkstra;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static int COLOR_LINE = Color.BLUE;
    private static int WIDTH_LINE = 20;
    private GoogleMap mMap;
    private LatLng lokasiAwal;
    private LatLng lokasiTujuan;
    private int idNodeAwal;
    private int idNodeTujuan;
    private String namaLokasiAwal;
    private String namaLokasiTujuan;
    private ListView lvHasilPengujian;

    private static final LatLng PagarAlam = new LatLng( -4.066010, 103.268494);
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private String TAG = "MainActivity";

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final int DEFAULT_ZOOM = 10;
    private static final float MY_LOCATION_ZOOM = 15f;

    private ImageView mCurrentLoc;
    private ImageView mCariRute;
    private TextView mMapView;
    private TextView mSatelliteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mCurrentLoc = (ImageView) findViewById(R.id.ic_current_location);
        mCariRute = (ImageView) findViewById(R.id.cari_rute);
        mMapView = (TextView) findViewById(R.id.map_view);
        mSatelliteView = (TextView) findViewById(R.id.satellite_view);

        mCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Clicked Current Location ");
                getDeviceLocation();
            }
        });

        mCariRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
        mMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMapView.setTag(R.id.map_view);
                mSatelliteView.setVisibility(View.VISIBLE);
                mMapView.setVisibility(View.GONE);
            }
        });
        mSatelliteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                mSatelliteView.setTag(R.id.satellite_view);
                mMapView.setVisibility(View.VISIBLE);
                mSatelliteView.setVisibility(View.GONE);
            }
        });

        getLocationPermission();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        lvHasilPengujian = findViewById(R.id.lvHasilPengujian);

        idNodeAwal =  getIntent().getExtras().getInt("idNodeAwal");
        idNodeTujuan = getIntent().getExtras().getInt("idNodeTujuan");
        namaLokasiAwal = getIntent().getExtras().getString("namaLokasiAwal");
        namaLokasiTujuan = getIntent().getExtras().getString("namaLokasiTujuan");
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
                            Toast.makeText(MapsActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
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

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

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

        lokasiAwal = MapManager.nodeMap.get(idNodeAwal).getPosisi();
        lokasiTujuan = MapManager.nodeMap.get(idNodeTujuan).getPosisi();
        namaLokasiAwal = MapManager.nodeMap.get(idNodeAwal).getNama();
        namaLokasiTujuan = MapManager.nodeMap.get(idNodeTujuan).getNama();


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiTujuan,14));
        mMap.addMarker(new MarkerOptions().position(lokasiAwal).title(namaLokasiAwal).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(lokasiTujuan).title(namaLokasiTujuan).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        //---Pengujian Dengan Graph Biasa---
        HasilKeseluruhanPengujian keseluruhanPengujian;

        keseluruhanPengujian = DijkstraAlgorithm.searchPath(MapManager.graphK,idNodeAwal,idNodeTujuan);

        for(int i=0; i<keseluruhanPengujian.getListHasilPengujian().size(); i++){
            HasilPengujian hasilPengujian = keseluruhanPengujian.getListHasilPengujian().get(i);
            PolylineManager.drawPolyline(mMap,hasilPengujian.getJalur(),COLOR_LINE,WIDTH_LINE);
        }

        ListHasilPengujianAdapter adapter = new ListHasilPengujianAdapter(this,keseluruhanPengujian.getListHasilPengujian(),R.layout.item_hasil_pengujian,COLOR_LINE, idNodeTujuan, idNodeAwal);
        lvHasilPengujian.setAdapter(adapter);
        }
}
