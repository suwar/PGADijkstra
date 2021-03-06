package com.application.pgadijkstra;

import java.util.ArrayList;

public class HasilPenghitungan {
    private ArrayList<Node> jalur;
    private double jarak;
    private long waktuPencarian;

    public HasilPenghitungan(){
        this.waktuPencarian = getWaktuPencarian();
    }

    public void setJarakRute(double jarakRute){
        this.jarak = jarakRute;
    }

    public ArrayList<Node> getJalur() {
        return jalur;
    }

    public void setJalur(ArrayList<Node> jalur) {
        this.jalur = jalur;
    }

    public long getWaktuPencarian(){
        return waktuPencarian;
    }

    public void setWaktuPencarian(long waktuPencarian){
        this.waktuPencarian = waktuPencarian;
    }
}
