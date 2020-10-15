package com.application.pgadijkstra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final LatLng PagarAlam = new LatLng(-4.125814, 103.271983);
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button pga = (Button) findViewById(R.id.pga);
        final Button normalView = (Button) findViewById(R.id.btnNormalView);
        final Button satelitView = (Button) findViewById(R.id.btnSatelitView);
        final Button prosesCariRute = (Button) findViewById(R.id.btnProsesCariRute);


        pga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PagarAlam, 12));
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



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Pagar Alam and move the camera
        LatLng AirTerjunLematang = new LatLng(-4.072735, 103.321704);
        LatLng Curup7Kenangan = new LatLng(-4.015061, 103.183256);
        LatLng CurupAlapAlap = new LatLng(-4.015494, 103.181303);
        LatLng CurupEmbun = new LatLng(-4.015820, 103.194081);
        LatLng CurupMangkok = new LatLng(-4.013589, 103.188362);
        LatLng CurupPintuLangit = new LatLng(-4.098696, 103.213349);
        LatLng CurupSendangDerajat = new LatLng(-4.014518, 103.184703);
        LatLng GreenParadise = new LatLng(-4.068537, 103.190882);
        LatLng HutanBambu = new LatLng(-4.011008, 103.195478);
        LatLng LandingParalayang = new LatLng(-4.024241, 103.167892);
        LatLng Tangga2001 = new LatLng(-4.037824, 103.190280);
        LatLng TebatReban = new LatLng(-4.016713, 103.263158);
        LatLng TuguRimau = new LatLng(-4.024452, 103.154490);

        mMap.addMarker(new MarkerOptions().position(AirTerjunLematang).title("Air Terjun Lematang"));
        mMap.addMarker(new MarkerOptions().position(Curup7Kenangan).title("Curup 7 Kenangan"));
        mMap.addMarker(new MarkerOptions().position(CurupAlapAlap).title("Curup Alap-Alap"));
        mMap.addMarker(new MarkerOptions().position(CurupEmbun).title("Curup Embun"));
        mMap.addMarker(new MarkerOptions().position(CurupMangkok).title("Curup Mangkok"));
        mMap.addMarker(new MarkerOptions().position(CurupPintuLangit).title("Curup Pintu Langit"));
        mMap.addMarker(new MarkerOptions().position(CurupSendangDerajat).title("Curup Sendang Derajat"));
        mMap.addMarker(new MarkerOptions().position(GreenParadise).title("Green Paradise"));
        mMap.addMarker(new MarkerOptions().position(HutanBambu).title("Hutan Bambu"));
        mMap.addMarker(new MarkerOptions().position(LandingParalayang).title("Landing Paralayang"));
        mMap.addMarker(new MarkerOptions().position(Tangga2001).title("Tangga 2001"));
        mMap.addMarker(new MarkerOptions().position(TebatReban).title("Tebat Reban"));
        mMap.addMarker(new MarkerOptions().position(TuguRimau).title("Tugu Rimau"));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(PagarAlam, 12));




    }
}