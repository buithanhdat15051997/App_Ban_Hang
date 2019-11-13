package com.example.app_ban_hang.model;

import android.support.v7.widget.RecyclerView;

import java.io.Serializable;

public class SanPhamMoiNhat implements Serializable {
    public  int IDSPNew;
    public  String TenSPNew;
    public  int GiaSPNew;
    public  String HinhanhSPNew;
    public  String MotaSPNew;
    public int IDloaiSPNew;

    public int getIDSPNew() {
        return IDSPNew;
    }

    public void setIDSPNew(int IDSPNew) {
        this.IDSPNew = IDSPNew;
    }

    public String getTenSPNew() {
        return TenSPNew;
    }

    public void setTenSPNew(String tenSPNew) {
        TenSPNew = tenSPNew;
    }

    public int getGiaSPNew() {
        return GiaSPNew;
    }

    public void setGiaSPNew(int giaSPNew) {
        GiaSPNew = giaSPNew;
    }

    public String getHinhanhSPNew() {
        return HinhanhSPNew;
    }

    public void setHinhanhSPNew(String hinhanhSPNew) {
        HinhanhSPNew = hinhanhSPNew;
    }

    public String getMotaSPNew() {
        return MotaSPNew;
    }

    public void setMotaSPNew(String motaSPNew) {
        MotaSPNew = motaSPNew;
    }

    public int getIDloaiSPNew() {
        return IDloaiSPNew;
    }

    public void setIDloaiSPNew(int IDloaiSPNew) {
        this.IDloaiSPNew = IDloaiSPNew;
    }

    public SanPhamMoiNhat(int IDSPNew, String tenSPNew, int giaSPNew, String hinhanhSPNew, String motaSPNew, int IDloaiSPNew) {
        this.IDSPNew = IDSPNew;
        TenSPNew = tenSPNew;
        GiaSPNew = giaSPNew;
        HinhanhSPNew = hinhanhSPNew;
        MotaSPNew = motaSPNew;
        this.IDloaiSPNew = IDloaiSPNew;
    }
}
