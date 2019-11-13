package com.example.app_ban_hang.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.ThongtinKhachHang;
import com.example.app_ban_hang.adapter.GioHangAdapter;
import com.example.app_ban_hang.model.GioHang;
import com.example.app_ban_hang.ultil.CheckConnection;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangActivity extends AppCompatActivity {
    ListView listViewGioHang;
    Toolbar toolbarGiaHang;
   public static TextView txtThongBao;
    static TextView txtTongTien;
    Button btnThanhtoan,btnTiepTuc;

    public static  GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhxa();
        ActiontoolbarGiaHang();
        CheckData();
        EventUltil();
        DeleteItemGH();
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){

                    Intent intent = new Intent(GioHangActivity.this, ThongtinKhachHang.class);
                    startActivity(intent);

                }else {
                    CheckConnection.ShowToast_short(GioHangActivity.this,"Gio hàng trống bạn vui lòng mua hàng");

                }
            }
        });

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GioHangActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void DeleteItemGH() {
        listViewGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Toast.makeText(GioHangActivity.this, "ahhaha", Toast.LENGTH_SHORT).show();


                return false;
            }
        });
    }

    public static void EventUltil() {

        int tongtien = 0 ;
        for(int i = 0 ; i <MainActivity.manggiohang.size();i++){

            tongtien = tongtien + MainActivity.manggiohang.get(i).getGiaGH();

        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongTien.setText(""+decimalFormat.format(tongtien)+"đồng");
    }

    private void CheckData() {
        if(MainActivity.manggiohang.size() <=0){
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);// hiện
            listViewGioHang.setVisibility(View.INVISIBLE);
        }else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);// ẩn
            listViewGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void ActiontoolbarGiaHang() {
        setSupportActionBar(toolbarGiaHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGiaHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void anhxa() {
        listViewGioHang = findViewById(R.id.listviewGioHang);
        toolbarGiaHang = findViewById(R.id.toolbarGioHangSP);
        txtThongBao = findViewById(R.id.textviewThongBao);
        txtTongTien =findViewById(R.id.txtTongTien);
        btnThanhtoan =findViewById(R.id.btnThanhtoan);
        btnTiepTuc = findViewById(R.id.btnNext);
       // gọi mảng gio hàng từ mainactivity
        gioHangAdapter = new GioHangAdapter(GioHangActivity.this,MainActivity.manggiohang);
        listViewGioHang.setAdapter(gioHangAdapter);

    }
}
