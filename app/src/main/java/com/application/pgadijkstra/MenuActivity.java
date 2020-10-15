package com.application.pgadijkstra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    private Button btnCariRute;
    private Spinner spLokasiAwal, spLokasiTujuan;

    @Override //
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);//fungsi manggil layout

        btnCariRute = (Button) findViewById(R.id.btnCariRute); //untuk id
        spLokasiAwal = (Spinner) findViewById(R.id.spLokasiAwal);
        spLokasiTujuan = (Spinner) findViewById(R.id.spLokasiTujuan);

        settingSpinner();
        MapManager.init(MenuActivity.this);

        btnCariRute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //jika user mengclick button cari rute
                Intent intent = new Intent(MenuActivity.this, MapsActivity.class);

                int posisiLokasiAwalDipilih = spLokasiAwal.getSelectedItemPosition();
                Node nodeAwalDipilih = MapManager.getListLokasiAwal().get(posisiLokasiAwalDipilih);

                int posisiLokasiTujuanDipilih = spLokasiTujuan.getSelectedItemPosition();
                Node nodeTujuanDipilih = MapManager.getListLokasiTujuan().get(posisiLokasiTujuanDipilih);

                String algoritma = "djikstra";

                if (posisiLokasiAwalDipilih == posisiLokasiTujuanDipilih){
                    Toast.makeText(MenuActivity.this, "Tidak ada tujuan atau lokasi tujuan sama dengan lokasi awal", Toast.LENGTH_SHORT).show();
                }
                else{
                    intent.putExtra("idNodeAwal", nodeAwalDipilih.getId());
                    intent.putExtra("idNodeTujuan", nodeTujuanDipilih.getId());
                    intent.putExtra("algoritma", algoritma);
                    startActivity(intent);
                }

            }
        });
    }

    private void settingSpinner(){
        //---Setting Spinner Lokasi Awal---
        ArrayList<Node>listLokasiAwal = MapManager.getListLokasiAwal(); //variabe penampung untuk listlokasi awal
        ArrayList<String> listNamaLokasiAwal = new ArrayList<>(); // untuk memunculkan nama di spiner

        for(int i=0; i<listLokasiAwal.size(); i++){ // untuk mengbil atribut nama di class node awal
            Node nodeI = listLokasiAwal.get(i);
            listNamaLokasiAwal.add(nodeI.getNama());
        }
        spLokasiAwal.setAdapter(new ArrayAdapter<String>(MenuActivity.this,R.layout.support_simple_spinner_dropdown_item,listNamaLokasiAwal)); // untuk menampilkan di spinner

        ArrayList<Node>listLokasiTujuan = MapManager.getListLokasiTujuan(); //variabe penampung untuk listlokasi tujuan
        ArrayList<String> listNamaLokasiTujuan = new ArrayList<>(); // untuk memunculkan nama di spiner

        for(int i=0; i<listLokasiTujuan.size(); i++){ // untuk mengbil atribut nama di class node awal
            Node nodea = listLokasiTujuan.get(i);
            listNamaLokasiTujuan.add(nodea.getNama());
        }
        spLokasiTujuan.setAdapter(new ArrayAdapter<String>(MenuActivity.this,R.layout.support_simple_spinner_dropdown_item,listNamaLokasiTujuan)); // untuk menampilkan di spinner

    }

}
