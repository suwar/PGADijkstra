package com.application.pgadijkstra;

import java.util.ArrayList;

public class HasilPengujian {
    private ArrayList<Node> jalur;
    private double jarakRute;

    public HasilPengujian(){}

    public double getJarakRute(){
        return jarakRute;
    }

    public void setJarakRute(double jarakRute){
        this.jarakRute = jarakRute;
    }

    public ArrayList<Node> getJalur() {
        return jalur;
    }

    public void setJalur(ArrayList<Node> jalur) {
        this.jalur = jalur;
    }
}
