package com.mycompany.infentoryapp.model;

public class Barang {
    private int idBarang;
    private String kodeBarang;
    private String namaBarang;
    private int idKategori;
    private String namaKategori; // hasil join, untuk ditampilkan di tabel
    private int stok;
    private double harga;

    public Barang() {}

    public Barang(int idBarang, String kodeBarang, String namaBarang, int idKategori,
                   String namaKategori, int stok, double harga) {
        this.idBarang = idBarang;
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.stok = stok;
        this.harga = harga;
    }

    public int getIdBarang() { return idBarang; }
    public void setIdBarang(int idBarang) { this.idBarang = idBarang; }

    public String getKodeBarang() { return kodeBarang; }
    public void setKodeBarang(String kodeBarang) { this.kodeBarang = kodeBarang; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }

    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
}
