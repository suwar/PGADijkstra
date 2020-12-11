package com.application.pgadijkstra;

import java.util.ArrayList;

public class DijkstraAlgorithm {
    private static double jarakRute;

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

            //---Mencari Node Dengan Cost Terkecil---   //untuk update posisi skrng
            for (int j = 0; j < graph.length; j++) {
                if (!isVisited[j] && cost[j] < costTerkecil) { //
                    idNodeTerdekat = j;
                    costTerkecil = cost[j];
                }
            }

            isVisited[idNodeTerdekat] = true; //memberikan tanda bahwa node itu pernah dikunjungi

            if (idNodeTerdekat == idNodeTujuan)
                break;

            //---Update Cost---
            for (int j = 0; j < graph.length; j++) {
                if (graph[idNodeTerdekat][j] > 0 && ((costTerkecil + graph[idNodeTerdekat][j]) < cost[j])) {  //if graph terdekat > 0 dan cost terkecil (bisa keselanjutnya) + node selanjutnya
                   //untuk mengetahui bisa tidak ketujuan selanjutnya      //cost terkecil + selanjutnya untuk update
                    parents[j] = idNodeTerdekat;
                    cost[j] = costTerkecil + graph[idNodeTerdekat][j]; // untuk update cost
                }
            }
        }

        return parents;
    }

    public static HasilKeseluruhanPengujian searchPath(double[][] graph, int idNodeAwal, int idNodeTujuan){
        jarakRute = 0;
        double[] cost; //Untuk mencatat cost
        boolean[] isVisited; //untuk mencatat node yang sudah dikunjungi
        int[] parents; // untuk mencatat orang tua dari node ke-i

        ArrayList<HasilPengujian> listHasilPengujian = new ArrayList<>();

            cost = InitializeCost(graph.length);
            isVisited = InitializeVisited(graph.length);
            parents = InitializeParents(graph.length);

            cost[idNodeAwal] = 0;
            parents[idNodeAwal] = -1;

            parents = Search(graph, cost, parents, isVisited, idNodeTujuan);

            //---Buat Jalur---
            ArrayList<Node> jalur = buatJalur(parents, idNodeAwal, idNodeTujuan);

            //---Hasil Pengujian---
            HasilPengujian hasilPengujian = new HasilPengujian();
            hasilPengujian.setJalur(jalur); // untuk bikin jalur

            hasilPengujian.setJarakRute(jarakRute); // jarak tempuh

            listHasilPengujian.add(hasilPengujian);

        HasilKeseluruhanPengujian hasilKeseluruhanPengujian = new HasilKeseluruhanPengujian(listHasilPengujian);

        return hasilKeseluruhanPengujian;
    }

    private static ArrayList<Node> buatJalur(int[] parents, int u, int v) {
        jarakRute = 0;
        ArrayList<Node> path = new ArrayList<>();
        path.add(MapManager.nodeMap.get(v));

        while(u != v){  // jika node awal bukan tujuan
            Node asal = MapManager.nodeMap.get(v); // node asalnyo v , buat jalurnya dari tujuan ke asal

            v = parents[v];
            Node tujuan = MapManager.nodeMap.get(v);

            jarakRute += MapManager.distance_in_kilometer(asal,tujuan);

            path.add(tujuan);
        }
        return path;
    }
}
