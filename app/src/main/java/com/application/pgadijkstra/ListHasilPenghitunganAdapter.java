package com.application.pgadijkstra;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.List;

public class ListHasilPenghitunganAdapter extends ArrayAdapter<HasilPenghitungan> {
    private int color;
    private int idNodeTujuan;
    private int idNodeAwal;
    private Context context;

    public ListHasilPenghitunganAdapter(Context context, List<HasilPenghitungan> data, int resLayout, int color, int idNodeTujuan, int idNodeAwal){
        super(context,resLayout,data);
        this.context = context;
        this.color = color;
        this.idNodeTujuan = idNodeTujuan;
        this.idNodeAwal = idNodeAwal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hasil_pengujian,parent,false);
        }
        HasilPenghitungan hasilPenghitungan = getItem(position);

        DecimalFormat df = new DecimalFormat("#.##");
        String s = df.format(hasilPenghitungan.getJarakRute());

        convertView.setBackgroundColor(color);
        ((TextView)convertView.findViewById(R.id.tvNamaAsal)).setText(MapManager.getNamaLokasiAwal(idNodeAwal));
        ((TextView)convertView.findViewById(R.id.tvNamaTujuan)).setText(MapManager.getNamaLokasiTujuan(idNodeTujuan));
        ((TextView)convertView.findViewById(R.id.tvJarakRute)).setText(String.valueOf(s));

        return convertView;
    }
}
