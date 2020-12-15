package com.application.pgadijkstra;

import java.util.ArrayList;

public class HasilPenghitungan {
    private ArrayList<Node> jalur;
    private double jarak;

    public HasilPenghitungan(){}

    public double getJarakRute(){
        return jarak;
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
}
