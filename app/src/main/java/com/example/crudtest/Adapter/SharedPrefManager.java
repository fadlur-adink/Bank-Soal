package com.example.crudtest.Adapter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.crudtest.Soal;

import java.util.List;

public class SharedPrefManager {
    private static SharedPrefManager instance;
    private static Context ctx;

    private static final String SHARED_PREF_NAME = "mysharedpref12";
    private static final String KEY_USER_ID = "userid";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_USER_FULLNAME = "userfullname";
    private static final String KEY_USER_KELAS = "userkelas";
    private static final String KEY_BINDONESIA = "userbindo";
    private static final String KEY_MATEMATIKA = "usermatematika";
    private static final String KEY_PKN = "userpkn";
    private static final String KEY_IPA = "useripa";
    private static final String KEY_IPS = "userips";
    private static final String KEY_SBDP = "usersbdp";
    private static final String KEY_PJOK = "userpjok";
    private static final String KEY_SOAL_COUNT = "jumlahsoal";
    private static final String KEY_MAPEL_ACTIVE = "namamapel";
    private static final String KEY_SOAL = "soal";
    private static final String KEY_BEST_SCORE = "bestscore";
    private static final String KEY_SOAL_KELAS = "soalkelas";

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public boolean userLogin(int id, String username, String fullname, int kelas, int bindo, int matematika, int pkn, int ipa, int ips, int sbdp, int pjok){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_FULLNAME, fullname);
        editor.putInt(KEY_USER_KELAS, kelas);
        editor.putInt(KEY_BINDONESIA, bindo);
        editor.putInt(KEY_MATEMATIKA, matematika);
        editor.putInt(KEY_PKN, pkn);
        editor.putInt(KEY_IPA, ipa);
        editor.putInt(KEY_IPS, ips);
        editor.putInt(KEY_SBDP, sbdp);
        editor.putInt(KEY_PJOK, pjok);

        editor.apply();
        return true;
    }

    public boolean resetStatusMapel(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_BINDONESIA, 0);
        editor.putInt(KEY_MATEMATIKA, 0);
        editor.putInt(KEY_PKN, 0);
        editor.putInt(KEY_IPA, 0);
        editor.putInt(KEY_IPS, 0);
        editor.putInt(KEY_SBDP, 0);
        editor.putInt(KEY_PJOK, 0);

        editor.apply();
        return true;
    }

    public boolean updateStatusMapel(int newstatusmapel, String namamapel){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(namamapel.equals("B Indonesia")){
            editor.putInt(KEY_BINDONESIA, newstatusmapel);
        }else if(namamapel.equals("Matematika")){
            editor.putInt(KEY_MATEMATIKA, newstatusmapel);
        }else if(namamapel.equals("PKN")){
            editor.putInt(KEY_PKN, newstatusmapel);
        }else if(namamapel.equals("IPA")){
            editor.putInt(KEY_IPA, newstatusmapel);
        }else if(namamapel.equals("IPS")){
            editor.putInt(KEY_IPS, newstatusmapel);
        }else if(namamapel.equals("SBdP")){
            editor.putInt(KEY_SBDP, newstatusmapel);
        }else if(namamapel.equals("PJOK")){
            editor.putInt(KEY_PJOK, newstatusmapel);
        }

        editor.apply();
        return true;
    }

    public boolean updateKelasSiswa(int kelas){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_KELAS, kelas);

        editor.apply();
        return true;
    }

    public boolean refreshInfo(int id, String username, String fullname, int kelas){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_USER_ID, id);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_USER_FULLNAME, fullname);
        editor.putInt(KEY_USER_KELAS, kelas);

        editor.apply();
        return true;
    }

    public boolean setJumlahSoal(int jumlahSoal){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SOAL_COUNT, jumlahSoal);

        editor.apply();
        return true;
    }

    public boolean setSoalKelas(int soalkelas){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SOAL_KELAS, soalkelas);

        editor.apply();
        return true;
    }

    public boolean setMapelActive(String namamapel){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_MAPEL_ACTIVE, namamapel);

        editor.apply();
        return true;
    }

    public boolean setBestScore(int bestscore){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_BEST_SCORE, bestscore);

        editor.apply();
        return true;
    }

    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(sharedPreferences.getString(KEY_USER_FULLNAME, null) != null){
            return true;
        }
        return false;
    }

    public boolean logOut(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;
    }

    public void setMapelOnStartQuiz(){

    }

    public int getBestScore(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_BEST_SCORE, 0);
    }

    public int getSoalKelas(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SOAL_KELAS, 0);
    }

    public int getJumlahSoal(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SOAL_COUNT, 0);
    }

    public String getMapelActive(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_MAPEL_ACTIVE, null);
    }

    public int getId(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_ID, 0);
    }

    public String getUsername(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null);
    }

    public String getUserFullname(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USER_FULLNAME, null);
    }

    public int getUserKelas(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_USER_KELAS, 1);
    }

    public int getBindo(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_BINDONESIA, 0);
    }

    public int getMatematika(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_MATEMATIKA, 0);
    }

    public int getPkn(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_PKN, 0);
    }

    public int getIpa(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_IPA, 0);
    }

    public int getIps(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_IPS, 0);
    }

    public int getSbdp(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SBDP, 0);
    }

    public int getPjok(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_PJOK, 0);
    }

}
