package com.application.pgadijkstra;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static int[] COLOR_LINE = {Color.BLUE};
    private static int[] WIDTH_LINE = {20};
    private GoogleMap mMap;
    private LatLng lokasiAwal;
    private LatLng lokasiTujuan;
    private int idNodeAwal;
    private int idNodeTujuan;
    private String algoritma;
    private TextView tvWaktuPencarian, tvJumlahNodeChecked, tvJumlahIterasi;
    private ListView lvHasilPengujian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tvWaktuPencarian = findViewById(R.id.tvWaktuPencarian);
        tvJumlahIterasi = findViewById(R.id.tvJumlahIterasi);
        tvJumlahNodeChecked = findViewById(R.id.tvJumlahNodeChecked);
        lvHasilPengujian = findViewById(R.id.lvHasilPengujian);

        idNodeAwal =  getIntent().getExtras().getInt("idNodeAwal");
        idNodeTujuan = getIntent().getExtras().getInt("idNodeTujuan");
        algoritma = getIntent().getExtras().getString("algoritma");

        final Button MapView = (Button) findViewById(R.id.buttonMapView);
        final Button SatelliteView = (Button) findViewById(R.id.buttonSatelliteView);
        final Button PetaUtama = (Button) findViewById(R.id.buttonMain);

        MapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                MapView.setTag(R.id.buttonMapView);
            }
        });

        SatelliteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                SatelliteView.setTag(R.id.buttonSatelliteView);
            }
        });
        PetaUtama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lokasiAwal = MapManager.nodeMap.get(idNodeAwal).getPosisi();
        lokasiTujuan = MapManager.nodeMap.get(idNodeTujuan).getPosisi();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiAwal,16));
        mMap.addMarker(new MarkerOptions().position(lokasiAwal).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        mMap.addMarker(new MarkerOptions().position(lokasiTujuan).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        //---Pengujian Dengan Graph Biasa---
        HasilKesuluruhanPengujian kesuluruhanPengujian;

        kesuluruhanPengujian = DjikstraAlgorithm.searchPath(MapManager.graphK,idNodeAwal,idNodeTujuan);

        tvWaktuPencarian.setText("Waktu Pencarian: "+kesuluruhanPengujian.getWaktuPencarian());
        tvJumlahIterasi.setText("Jumlah Iterasi: "+kesuluruhanPengujian.getJumlahIterasi());
        tvJumlahNodeChecked.setText("Jumlah Node Checked: "+kesuluruhanPengujian.getJumlahNodeChecked());

        for(int i=0; i<kesuluruhanPengujian.getListHasilPengujian().size(); i++){
            HasilPengujian hasilPengujian = kesuluruhanPengujian.getListHasilPengujian().get(i);
            PolylineManager.drawPolyline(mMap,hasilPengujian.getJalur(),COLOR_LINE[i],WIDTH_LINE[i]);
        }

        ListHasilPengujianAdapter adapter = new ListHasilPengujianAdapter(this,kesuluruhanPengujian.getListHasilPengujian(),R.layout.item_hasil_pengujian,COLOR_LINE, idNodeTujuan, idNodeAwal);
        lvHasilPengujian.setAdapter(adapter);

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
