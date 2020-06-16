package com.example.crudtest;

public class Soal {

    private String soal;
    private String jawaba;
    private String jawabb;
    private String jawabc;
    private String jawabd;
    private String jawabbenar;
    private String gambar;

    public Soal(String soal, String jawaba, String jawabb, String jawabc, String jawabd, String jawabbenar, String gambar) {
        this.soal = soal;
        this.jawaba = jawaba;
        this.jawabb = jawabb;
        this.jawabc = jawabc;
        this.jawabd = jawabd;
        this.jawabbenar = jawabbenar;
        this.gambar = gambar;
    }

    public String getSoal() {
        return soal;
    }

    public String getJawaba() {
        return jawaba;
    }

    public String getJawabb() {
        return jawabb;
    }

    public String getJawabc() {
        return jawabc;
    }

    public String getJawabd() {
        return jawabd;
    }

    public String getJawabbenar() {
        return jawabbenar;
    }

    public String getGambar(){
        return gambar;
    }
}
