package com.example.app_ban_hang.model;

public class SanPhamNgangMoiNhat {
    String tenSPNgang;
    String imgSPNgang;

    public SanPhamNgangMoiNhat(String tenSPNgang, String imgSPNgang) {
        this.tenSPNgang = tenSPNgang;
        this.imgSPNgang = imgSPNgang;
    }

    public String getTenSPNgang() {
        return tenSPNgang;
    }

    public void setTenSPNgang(String tenSPNgang) {
        this.tenSPNgang = tenSPNgang;
    }

    public String getImgSPNgang() {
        return imgSPNgang;
    }

    public void setImgSPNgang(String imgSPNgang) {
        this.imgSPNgang = imgSPNgang;
    }
}
