package com.example.app_ban_hang.model;

public class GioHang {
    public  int idGH;
    public  String tenGH;
    public  int giaGH;
    public  String hinhGH;
    public  int idsoluongGH;

    public int getIdGH() {
        return idGH;
    }

    public void setIdGH(int idGH) {
        this.idGH = idGH;
    }

    public String getTenGH() {
        return tenGH;
    }

    public void setTenGH(String tenGH) {
        this.tenGH = tenGH;
    }

    public int getGiaGH() {
        return giaGH;
    }

    public void setGiaGH(int giaGH) {
        this.giaGH = giaGH;
    }

    public String getHinhGH() {
        return hinhGH;
    }

    public void setHinhGH(String hinhGH) {
        this.hinhGH = hinhGH;
    }

    public int getIdsoluongGH() {
        return idsoluongGH;
    }

    public void setIdsoluongGH(int idsoluongGH) {
        this.idsoluongGH = idsoluongGH;
    }

    public GioHang(int idGH, String tenGH, int giaGH, String hinhGH, int idsoluongGH) {
        this.idGH = idGH;
        this.tenGH = tenGH;
        this.giaGH = giaGH;
        this.hinhGH = hinhGH;
        this.idsoluongGH = idsoluongGH;
    }
}
