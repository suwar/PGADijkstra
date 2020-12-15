package com.application.pgadijkstra;

import java.util.ArrayList;

public class AlgoritmaDijkstra {
    private static double jarak;

    private static double[] InitializeCost(int graphLength) {
        double[] cost = new double[graphLength];
        for (int i = 0; i < graphLength; i++) {
            cost[i] = Double.POSITIVE_INFINITY;
        }

        return cost;
    }

    private static int[] InitializeParents(int graphLength) {
        int[] parents = new int[graphLength];
        for (int i = 0; i < graphLength; i++) {
            parents[i] = -1;
        }

        return parents;
    }

    private static boolean[] InitializeVisited(int graphLength) {
        boolean[] isVisited = new boolean[graphLength];
        for (int i = 0; i < graphLength; i++) {
            isVisited[i] = false;
        }

        return isVisited;
    }

    private static int[] Search(double[][] graph, double[] cost, int[] parents, boolean[] isVisited, int idNodeTujuan) {
        //---Searching Path---
        for (int i = 0; i < graph.length; i++) {

            int idNodeTerdekat = -1;
            double costTerkecil = Double.POSITIVE_INFINITY;

            //---Mencari node dengan cost terkecil---   //untuk update posisi sekarang
            for (int j = 0; j < graph.length; j++) {
                if (!isVisited[j] && cost[j] < costTerkecil) { //
                    idNodeTerdekat = j;
                    costTerkecil = cost[j];
                }
            }

            isVisited[idNodeTerdekat] = true; //memberikan tanda bahwa node it pernah dikunjungi

            if (idNodeTerdekat == idNodeTujuan)
                break;

            //---Update Cost---
            for (int j = 0; j < graph.length; j++) {
                if (graph[idNodeTerdekat][j] > 0 && ((costTerkecil + graph[idNodeTerdekat][j]) < cost[j])) {  //if graph terdekat > 0 dan cost terkecil (bisa keselanjutnya) + node selanjutnya
                    //untuk mengetahui bisa tidak ketujuan selanjutnya
                    parents[j] = idNodeTerdekat; //untuk update cost
                    cost[j] = costTerkecil + graph[idNodeTerdekat][j]; //cost terkecil + selanjutnya untuk update

                }
            }
        }

        return parents;
    }

    public static HasilKeseluruhanPenghitungan searchPath(double[][] graph, int idNodeAwal, int idNodeTujuan){
        jarak = 0;
        double[] cost; //Untuk mencatat cost
        boolean[] isVisited; //untuk mencatat node yang sudah dikunjungi
        int[] parents; // untuk mencatat orang tua dari node ke-i

        ArrayList<HasilPenghitungan> listHasilPenghitungan = new ArrayList<>();

            cost = InitializeCost(graph.length);
            isVisited = InitializeVisited(graph.length);
            parents = InitializeParents(graph.length);

            cost[idNodeAwal] = 0;
            parents[idNodeAwal] = -1;

            parents = Search(graph, cost, parents, isVisited, idNodeTujuan);

            //---Buat Jalur---
            ArrayList<Node> jalur = buatJalur(parents, idNodeAwal, idNodeTujuan);

            //---Hasil Penghitungan---
            HasilPenghitungan hasilPenghitungan = new HasilPenghitungan();
            hasilPenghitungan.setJalur(jalur); // untuk membuat jalur
            hasilPenghitungan.setJarakRute(jarak); // jarak tempuh

           listHasilPenghitungan.add(hasilPenghitungan);

        HasilKeseluruhanPenghitungan hasilKeseluruhanPenghitungan = new HasilKeseluruhanPenghitungan(listHasilPenghitungan);

        return hasilKeseluruhanPenghitungan;
    }

    private static ArrayList<Node> buatJalur(int[] parents, int u, int v) {
        jarak=0;
        ArrayList<Node> path = new ArrayList<>();
        path.add(MapManager.nodeMap.get(v));

        while(u != v){  // selama node awal bukan tujuan
            Node asal = MapManager.nodeMap.get(v); // node asalnya v, buat jalurnya dari tujuan ke asal
            v = parents[v];
            Node tujuan = MapManager.nodeMap.get(v);

            jarak += MapManager.distance_in_kilometer(asal,tujuan);

            path.add(tujuan);
        }
        return path;
    }
}
