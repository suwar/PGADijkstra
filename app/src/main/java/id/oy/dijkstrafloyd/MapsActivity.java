package id.oy.dijkstrafloyd;

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

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static int[] COLOR_LINE = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.CYAN,Color.DKGRAY, Color.YELLOW,Color.LTGRAY,Color.MAGENTA, Color.BLACK};
    private static int[] WIDTH_LINE = {20,18,16,14,12,10,8,6,4,2};


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

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        lokasiAwal = MapManager.nodeMap.get(idNodeAwal).getPosisi();
        lokasiTujuan = MapManager.nodeMap.get(idNodeTujuan).getPosisi();

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiAwal,12));
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

    }
}
