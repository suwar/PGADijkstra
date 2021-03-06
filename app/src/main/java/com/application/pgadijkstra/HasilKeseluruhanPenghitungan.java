package com.application.pgadijkstra;

import java.util.List;

public class HasilKeseluruhanPenghitungan {
    private List<HasilPenghitungan> listHasilPenghitungan;
    long waktuPencarian;
    double jarak;

    public HasilKeseluruhanPenghitungan(List<HasilPenghitungan> listHasilPenghitungan, long waktuPencarian, double jarak) {
        this.listHasilPenghitungan = listHasilPenghitungan;
        this.waktuPencarian = waktuPencarian;
        this.jarak = jarak;
    }

    public List<HasilPenghitungan> getListHasilPenghitungan() {
        return listHasilPenghitungan;
    }

    public long getWaktuPencarian() {
        return waktuPencarian;
    }

    public double getJarak(){return jarak;}
}
