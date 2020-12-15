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
import android.widget.ImageView;
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


public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng PagarAlam = new LatLng( -4.066010, 103.268494);
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private String TAG = "MainActivity";
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 10.8f;
    private static final float MY_LOCATION_ZOOM = 15f;
    private GoogleMap mMap;
    private ImageView mCurrentLoc;
    private ImageView mCariRute;
    private ImageView mPetaPga;
    private TextView mMapView;
    private TextView mSatelliteView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentLoc = (ImageView) findViewById(R.id.ic_current_location);
        mPetaPga = (ImageView) findViewById(R.id.peta_pga);
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
        mPetaPga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PagarAlam, DEFAULT_ZOOM));
           }
        });
        mCariRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
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

        // Tourism Object in Pagar Alam and move the camera
        LatLng AirTerjunLematang = new LatLng( -4.072817,103.321776);
        LatLng Curup7Kenangan = new LatLng( -4.015061,103.183256);
        LatLng CurupAlapAlap = new LatLng( -4.014861,103.179250);
        LatLng CurupAirKarang = new LatLng(  -4.063054,103.333644);
        LatLng CurupBesemah = new LatLng(  -3.988306,103.399167);
        LatLng CurupEmbun = new LatLng( -4.015820,103.194081);
        LatLng CurupMangkok = new LatLng( -4.013589,103.188362);
        LatLng CurupMaung = new LatLng(  -3.989848,103.392476);
        LatLng CurupNapalKuning = new LatLng(   -4.134278,103.311583);
        LatLng CurupPintuLangit = new LatLng( -4.098629,103.212690);
        LatLng CurupSendangDerajat = new LatLng( -4.014518,103.184703);
        LatLng GreenParadise = new LatLng( -4.068691,103.191128);
        LatLng HutanBambu = new LatLng( -4.012652,103.196071);
        LatLng LandingParalayang = new LatLng( -4.024241,103.167892);
        LatLng LimestoneAyekBesemah = new LatLng(  -4.038278,103.388472);
        LatLng Tangga2001 = new LatLng( -4.037824,103.190280);
        LatLng TebatReban = new LatLng( -4.016713,103.263158);
        LatLng TuguRimau = new LatLng( -4.024452,103.154490);
        LatLng HotelDharmaKarya =  new LatLng( -4.003182, 103.241757);
        LatLng HotelFavour = new LatLng( -4.036518, 103.255588);
        LatLng HotelGrandZZ = new LatLng( -4.012484, 103.249421);
        LatLng HotelMirasa = new LatLng( -4.007235, 103.245288);
        LatLng HotelTelagaBiru = new LatLng( -4.019886, 103.251040);
        LatLng KanawaGuestHouse = new LatLng( -4.022591, 103.252921);
        LatLng PutriSriwijayaResort = new LatLng( -4.024217, 103.180213);
        LatLng VillaAldeoz = new LatLng( -4.084155,103.351733);
        LatLng VillaDempoFlower = new LatLng( -4.035536,103.196132);
        LatLng VillaexMTQPagarAlam = new LatLng( -4.038575,103.193711);
        LatLng VillaGunungGare = new LatLng( -4.037847,103.192724);
        LatLng WismaBara = new LatLng( -4.038814,103.219562);
        LatLng BandarUdaraAtungBungsu = new LatLng( -4.025745,103.380013);
        LatLng CVDharmaKaryaTravel = new LatLng( -4.022023,103.253632);
        LatLng CVDimasTravel = new LatLng( -4.028729,103.258782);
        LatLng POAnugerahWisataBusTravel = new LatLng( -4.023341,103.254264);
        LatLng POMelatiIndahBusTravel = new LatLng( -4.023272,103.254246);
        LatLng POSinarDempoBus = new LatLng( -4.022944,103.253444);
        LatLng POTelagaBiruBusTravel = new LatLng( -4.019416,103.251782);
        LatLng POTelagaIndahArmadaBus = new LatLng( -4.020615,103.248625);
        LatLng TerminalBusNendagung = new LatLng( -4.026174,103.238405);

        mMap.addMarker(new MarkerOptions().position(AirTerjunLematang).title("Air Terjun Lematang"));
        mMap.addMarker(new MarkerOptions().position(Curup7Kenangan).title("Curup Tujuh Kenangan"));
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
        mMap.addMarker(new MarkerOptions().position(HotelDharmaKarya).title("Hotel Dharma Karya").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(HotelFavour).title("Hotel Favour").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(HotelGrandZZ).title("Hotel Grand ZZ").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(HotelMirasa).title("Hotel Mirasa").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(HotelTelagaBiru).title("Hotel Telaga Biru").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(KanawaGuestHouse).title("Kanawa Guest House").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(PutriSriwijayaResort).title("Putri Sriwijaya Resort").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(VillaAldeoz).title("Villa Aldeoz").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(VillaDempoFlower).title("VillaDempoFlower").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(VillaexMTQPagarAlam).title("VillaexMTQPagarAlam").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(VillaGunungGare).title("Villa Gunung Gare").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(WismaBara).title("Wisma Bara").icon(BitmapDescriptorFactory.fromResource(R.drawable.lodging)));
        mMap.addMarker(new MarkerOptions().position(BandarUdaraAtungBungsu).title("Bandar Udara Atung Bungsu").icon(BitmapDescriptorFactory.fromResource(R.drawable.airports)));
        mMap.addMarker(new MarkerOptions().position(CVDharmaKaryaTravel).title("CV Dharma Karya Travel").icon(BitmapDescriptorFactory.fromResource(R.drawable.cabs)));
        mMap.addMarker(new MarkerOptions().position(CVDimasTravel).title("CV Dimas Travel").icon(BitmapDescriptorFactory.fromResource(R.drawable.cabs)));
        mMap.addMarker(new MarkerOptions().position(POAnugerahWisataBusTravel).title("PO Anugerah Wisata Bus & Travel").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        mMap.addMarker(new MarkerOptions().position(POMelatiIndahBusTravel).title("PO Melati Indah Bus & Travel").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        mMap.addMarker(new MarkerOptions().position(POSinarDempoBus).title("PO Sinar Dempo Bus").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        mMap.addMarker(new MarkerOptions().position(POTelagaBiruBusTravel).title("PO Telaga Biru Bus & Travel").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        mMap.addMarker(new MarkerOptions().position(POTelagaIndahArmadaBus).title("PO Telaga Indah Armada Bus").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
        mMap.addMarker(new MarkerOptions().position(TerminalBusNendagung).title("Terminal Bus Nendagung").icon(BitmapDescriptorFactory.fromResource(R.drawable.bus)));
    }
}