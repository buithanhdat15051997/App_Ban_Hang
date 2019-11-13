package com.example.app_ban_hang.model;

public class LoaiSP {
    public int IdSP;
    public String TenSP;
    public  String HinhanhloaiSP;

    public int getIdSP() {
        return IdSP;
    }

    public void setIdSP(int idSP) {
        IdSP = idSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public String getHinhanhloaiSP() {
        return HinhanhloaiSP;
    }

    public void setHinhanhloaiSP(String hinhanhloaiSP) {
        HinhanhloaiSP = hinhanhloaiSP;
    }

    public LoaiSP(int idSP, String tenSP, String hinhanhloaiSP) {
        IdSP = idSP;
        TenSP = tenSP;
        HinhanhloaiSP = hinhanhloaiSP;
    }
}
