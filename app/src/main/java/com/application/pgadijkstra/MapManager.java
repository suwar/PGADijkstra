package com.application.pgadijkstra;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MapManager {
    private static ArrayList<Node> listLokasiAwal;
    private static ArrayList<Node> listLokasiTujuan;
    public static HashMap<Integer,Node> nodeMap;
    public static double[][] graph;
    public static double[][] graphK;

    private static void initLokasiAwal(){
        listLokasiAwal = new ArrayList<>();
        listLokasiAwal.add(new Node(1,"Air Terjun Lematang", new LatLng(-4.072744734797606,103.3229034574862)));
        listLokasiAwal.add(new Node(267,"Curup Air Karang", new LatLng(-4.069573816416701,103.33119591685698)));
        listLokasiAwal.add(new Node(2343,"Curup Alap-Alap",new LatLng(-4.017991530979126,103.18422074739635)));
        listLokasiAwal.add(new Node(525,"Curup Besemah",new LatLng(-4.001420873448631,103.4084314893522)));
        listLokasiAwal.add(new Node(2385,"Curup Embun",new LatLng(-4.015592857864531,103.1955000312617)));
        listLokasiAwal.add(new Node(2374,"Curup Mangkok",new LatLng(-4.013560860251633,103.18866779929489)));
        listLokasiAwal.add(new Node(1118,"Curup Maung",new LatLng(-3.9837461309820963,103.39055575317083)));
        listLokasiAwal.add(new Node(221,"Curup Napal Kuning", new LatLng(-4.126056731391195,103.31130729937365)));
        listLokasiAwal.add(new Node(1650,"Curup Pintu Langit",new LatLng(-4.099385663403846,103.2139344657773)));
        listLokasiAwal.add(new Node(2347,"Curup Sendang Derajad",new LatLng(-4.017261349619523,103.18553672087039)));
        listLokasiAwal.add(new Node(2344,"Curup Tujuh Kenangan",new LatLng(-4.017755045469879,103.1844593750468)));
        listLokasiAwal.add(new Node(1297,"Green Paradise",new LatLng(-4.068562050885773,103.19210576579518)));
        listLokasiAwal.add(new Node(2391,"Hutan Bambu",new LatLng(-4.01243897214042,103.19595089013986)));
        listLokasiAwal.add(new Node(2083,"Landing Paralayang",new LatLng(-4.0242016828256,103.16818989724402)));
        listLokasiAwal.add(new Node(716,"Limestone Ayek Besemah", new LatLng(-4.038275673024914,103.38867477808034)));
        listLokasiAwal.add(new Node(1697,"Tangga 2001",new LatLng(-4.038702709902436,103.19039914471335)));
        listLokasiAwal.add(new Node(2734,"Tebat Reban",new LatLng(-4.01646193148431,103.26170828438205)));
        listLokasiAwal.add(new Node(2285,"Tugu Rimau",new LatLng(-4.024529592657282,103.15448677699503)));

        listLokasiAwal.add(new Node(2719, "Hotel Dharma Karya", new LatLng(-4.003008199952546,103.2419765736004)));
        listLokasiAwal.add(new Node(1195, "Hotel Favour", new LatLng( -4.036654,103.255625)));
        listLokasiAwal.add(new Node(2711, "Hotel Grand ZZ", new LatLng(-4.012662841404377,103.24929362580322)));
        listLokasiAwal.add(new Node(2718, "Hotel Mirasa", new LatLng(-4.007352171809295,103.24516948530946)));
        listLokasiAwal.add(new Node(2704, "Hotel Telaga Biru", new LatLng( -4.019739,103.251013)));
        listLokasiAwal.add(new Node(2745, "Kanawa Guest House", new LatLng( -4.022624,103.252826)));
        listLokasiAwal.add(new Node(2307, "Putri Sriwijaya Resort", new LatLng( -4.024872,103.180353)));
        listLokasiAwal.add(new Node(353, "Villa Aldeoz",new LatLng(-4.084310590286288,103.35108612013312)));
        listLokasiAwal.add(new Node(2562, "Villa Dempo Flower", new LatLng(-4.03559097720569,103.19539055229461)));
        listLokasiAwal.add(new Node(1692, "Villa ex-MTQ", new LatLng( -4.039375,103.194157)));
        listLokasiAwal.add(new Node(1695, "Villa Gunung Gare", new LatLng( -4.037933,103.193063)));
        listLokasiAwal.add(new Node(2602, "Wisma Bara", new LatLng(-4.038917,103.219354)));

        listLokasiAwal.add(new Node(732, "Bandara Atung Bungsu", new LatLng( -4.02643925357983,103.38226789824428)));
        listLokasiAwal.add(new Node(2741, "CV Dharma Karya Travel", new LatLng( -4.022093,103.253513)));
        listLokasiAwal.add(new Node(2752, "CV Dimas Travel", new LatLng( -4.028595,103.258882)));
        listLokasiAwal.add(new Node(2748, "PO Anugerah Wisata Bus & Travel", new LatLng( -4.023356,103.254206)));
        listLokasiAwal.add(new Node(2747, "PO Melati Indah Bus & Travel", new LatLng( -4.023308,103.254182)));
        listLokasiAwal.add(new Node(2743, "PO Sinar Dempo Bus", new LatLng( -4.023012,103.253471)));
        listLokasiAwal.add(new Node(2705, "PO Telaga Biru Bus & Travel", new LatLng( -4.019236,103.251697)));
        listLokasiAwal.add(new Node(2701, "PO Telaga Indah Armada", new LatLng( -4.020615,103.248625)));
        listLokasiAwal.add(new Node(2683, "Terminal Bus Nendagung", new LatLng(  -4.026174,103.238405)));
    }

    private static void initLokasiTujuan(){ //inisialisasi data lokasi tujuan
        listLokasiTujuan = new ArrayList<>();
        listLokasiTujuan.add(new Node(1,"Air Terjun Lematang", new LatLng(-4.072744734797606,103.3229034574862)));
        listLokasiTujuan.add(new Node(267,"Curup Air Karang", new LatLng(-4.069573816416701,103.33119591685698)));
        listLokasiTujuan.add(new Node(2343,"Curup Alap-Alap",new LatLng(-4.017991530979126,103.18422074739635)));
        listLokasiTujuan.add(new Node(525,"Curup Besemah",new LatLng(-4.001420873448631,103.4084314893522)));
        listLokasiTujuan.add(new Node(2385,"Curup Embun",new LatLng(-4.015592857864531,103.1955000312617)));
        listLokasiTujuan.add(new Node(2374,"Curup Mangkok",new LatLng(-4.013560860251633,103.18866779929489)));
        listLokasiTujuan.add(new Node(1118,"Curup Maung",new LatLng(-3.9837461309820963,103.39055575317083)));
        listLokasiTujuan.add(new Node(221,"Curup Napal Kuning", new LatLng(-4.126056731391195,103.31130729937365)));
        listLokasiTujuan.add(new Node(1650,"Curup Pintu Langit",new LatLng(-4.099385663403846,103.2139344657773)));
        listLokasiTujuan.add(new Node(2347,"Curup Sendang Derajad",new LatLng(-4.017261349619523,103.18553672087039)));
        listLokasiTujuan.add(new Node(2344,"Curup Tujuh Kenangan",new LatLng(-4.017755045469879,103.1844593750468)));
        listLokasiTujuan.add(new Node(1297,"Green Paradise",new LatLng(-4.068562050885773,103.19210576579518)));
        listLokasiTujuan.add(new Node(2391,"Hutan Bambu",new LatLng(-4.01243897214042,103.19595089013986)));
        listLokasiTujuan.add(new Node(2083,"Landing Paralayang",new LatLng(-4.0242016828256,103.16818989724402)));
        listLokasiTujuan.add(new Node(716,"Limestone Ayek Besemah", new LatLng(-4.038275673024914,103.38867477808034)));
        listLokasiTujuan.add(new Node(1697,"Tangga 2001",new LatLng(-4.038702709902436,103.19039914471335)));
        listLokasiTujuan.add(new Node(2734,"Tebat Reban",new LatLng(-4.01646193148431,103.26170828438205)));
        listLokasiTujuan.add(new Node(2285,"Tugu Rimau",new LatLng(-4.024529592657282,103.15448677699503)));

        listLokasiTujuan.add(new Node(2719, "Hotel Dharma Karya", new LatLng(-4.003008199952546,103.2419765736004)));
        listLokasiTujuan.add(new Node(1195, "Hotel Favour", new LatLng( -4.036654,103.255625)));
        listLokasiTujuan.add(new Node(2711, "Hotel Grand ZZ", new LatLng(-4.012662841404377,103.24929362580322)));
        listLokasiTujuan.add(new Node(2718, "Hotel Mirasa", new LatLng(-4.007352171809295,103.24516948530946)));
        listLokasiTujuan.add(new Node(2704, "Hotel Telaga Biru", new LatLng( -4.019739,103.251013)));
        listLokasiTujuan.add(new Node(2745, "Kanawa Guest House", new LatLng( -4.022624,103.252826)));
        listLokasiTujuan.add(new Node(2307, "Putri Sriwijaya Resort", new LatLng( -4.024872,103.180353)));
        listLokasiTujuan.add(new Node(353, "Villa Aldeoz",new LatLng(-4.084310590286288,103.35108612013312)));
        listLokasiTujuan.add(new Node(2562, "Villa Dempo Flower", new LatLng(-4.03559097720569,103.19539055229461)));
        listLokasiTujuan.add(new Node(1692, "Villa ex-MTQ", new LatLng( -4.039375,103.194157)));
        listLokasiTujuan.add(new Node(1695, "Villa Gunung Gare", new LatLng( -4.037933,103.193063)));
        listLokasiTujuan.add(new Node(2602, "Wisma Bara", new LatLng(-4.038917,103.219354)));

        listLokasiTujuan.add(new Node(732, "Bandara Atung Bungsu", new LatLng( -4.02643925357983,103.38226789824428)));
        listLokasiTujuan.add(new Node(2741, "CV Dharma Karya Travel", new LatLng( -4.022093,103.253513)));
        listLokasiTujuan.add(new Node(2752, "CV Dimas Travel", new LatLng( -4.028595,103.258882)));
        listLokasiTujuan.add(new Node(2748, "PO Anugerah Wisata Bus & Travel", new LatLng( -4.023356,103.254206)));
        listLokasiTujuan.add(new Node(2747, "PO Melati Indah Bus & Travel", new LatLng( -4.023308,103.254182)));
        listLokasiTujuan.add(new Node(2743, "PO Sinar Dempo Bus", new LatLng( -4.023012,103.253471)));
        listLokasiTujuan.add(new Node(2705, "PO Telaga Biru Bus & Travel", new LatLng( -4.019236,103.251697)));
        listLokasiTujuan.add(new Node(2701, "PO Telaga Indah Armada", new LatLng( -4.020615,103.248625)));
        listLokasiTujuan.add(new Node(2683, "Terminal Bus Nendagung", new LatLng(  -4.026174,103.238405)));
    }

    public static ArrayList<Node> getListLokasiAwal(){ // untuk membuat ListLokasiAwal pertama
        if(listLokasiAwal == null){ // kalu null -> bikin dulu
            initLokasiAwal();
        }
        return listLokasiAwal;
    }

    public static ArrayList<Node> getListLokasiTujuan(){
        if(listLokasiTujuan == null) {
            initLokasiTujuan();
        }
        return listLokasiTujuan;
    }

    public static String getNamaLokasiAwal(int id){
        if(listLokasiAwal == null) {
            initLokasiTujuan();
        }

        for(int i=0; i<listLokasiAwal.size(); i++){
            Node nodeTujuan = listLokasiAwal.get(i);
            if(nodeTujuan.getId() == id)
                return nodeTujuan.getNama();
        }

        return "";
    }

    public static String getNamaLokasiTujuan(int id){
        if(listLokasiTujuan == null) {
            initLokasiTujuan();
        }

        for(int i=0; i<listLokasiTujuan.size(); i++){
            Node nodeTujuan = listLokasiTujuan.get(i);
            if(nodeTujuan.getId() == id)
                return nodeTujuan.getNama();
        }
        return "";
    }

    public static void init(Context context){
        settingNodeMap(context); // untuk seting node
        settingGraph(context);// untuk seting graph
    }

    private static void settingNodeMap(Context context){ //untuk setting node map dari database untuk listing node map
        nodeMap = new HashMap<>(); //buat variabel map baru
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            Cursor cursor = databaseHelper.getAllNode();

            if (cursor.getCount()!=0) { //
                Log.v("NodeMap","Cursor "+cursor.getCount());
                graph = new double[cursor.getCount()+1][cursor.getCount()+1];
                graphK = new double[cursor.getCount()+1][cursor.getCount()+1];

                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0); //i = kolom
                        double lat = cursor.getDouble(1);
                        double lng = cursor.getDouble(2);
                        String name = cursor.getString(3);
                        Node nodeBaru = new Node(id,name, new LatLng(lat,lng));
                        nodeMap.put(id, nodeBaru);
                    } while(cursor.moveToNext());
                }
            } else {
                Log.v("NodeMap","Cursor 0");
            }
            databaseHelper.close();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void settingGraph(Context context) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);

        try {
            databaseHelper.createDataBase();
            databaseHelper.openDataBase();
            Cursor cursor = databaseHelper.getAllEdge();
            if (cursor.getCount()!=0) {
                Log.v("Graph","Cursor "+cursor.getCount());

                if (cursor.moveToFirst()) {
                    do {
                        int id = cursor.getInt(0);
                        int idNodeAwal = cursor.getInt(1);
                        int idNodeTujuan = cursor.getInt(2);
                        int status = cursor.getInt(3);
                        int konstanta = cursor.getInt(4);

                        Log.v("Graph-"+id,"Node ke-"+idNodeAwal+" ->" +idNodeTujuan + " Dengan Status: "+status + " Konstanta "+konstanta);

                        Node nodeAwal = nodeMap.get(idNodeAwal);
                        Node nodeTujuan = nodeMap.get(idNodeTujuan);

                        double jarak = distance_in_kilometer(nodeAwal,nodeTujuan); // untuk menghitung jarak antar node

                    }while(cursor.moveToNext());
                }
            }else{
                Log.v("Graph","Cursor 0");
            }
            databaseHelper.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static double distance_in_kilometer(Node awal, Node tujuan) {

        double lat1 = awal.getPosisi().latitude;
        double lon1 = awal.getPosisi().longitude;

        double lat2 = tujuan.getPosisi().latitude;
        double lon2 = tujuan.getPosisi().longitude;

        double R = 6371f; // Radius of the earth in km
        double dLat = (lat1 - lat2) * Math.PI / 180f;
        double dLon = (lon1 - lon2) * Math.PI / 180f;
        double a = Math.sin(dLat / 2f) * Math.sin(dLat / 2f) +
                Math.cos(lat1 * Math.PI / 180f) * Math.cos(lat2 * Math.PI / 180f) *
                        Math.sin(dLon / 2f) * Math.sin(dLon / 2f);
        double c = 2f * Math.atan2(Math.sqrt(a), Math.sqrt(1f - a));
        double d = R * c;
        return d;
    }
}
