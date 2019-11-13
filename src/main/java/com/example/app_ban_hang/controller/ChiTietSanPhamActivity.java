package com.example.app_ban_hang.controller;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.GioHang;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    TextView txtTenSPChitiet, txtGiaSPChitiet, txtMotaSPchitiet;
    Button btnSPadd;
    Spinner spinner;
    ImageView imgSPChitiet;
    int iddienthoai = 0;
    Toolbar toolbarChitietSP;

    int idchitiet = 0;
    String Tenchitiet = "";
    String imgChitiet = "";
    int giachitiet = 0 ;
    String Motachitiet = "";
    int idloaichitiet = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        anhxa();
        getIDSANPHAM();
        ToolBarAction();
        GetInformation();
        CatchEvent();
        EventButton();

    }

    private void EventButton() {
        btnSPadd.setOnClickListener(new View.OnClickListener() {
            // nếu như mảng có dữ liệu thì  kiểm tra các giá trị bên trong và Update lại Số lượng giỏ hàng
            // ngược lại không có dữ liệu thì có up dữ liệu mới cho chúng ta

            @Override
            public void onClick(View v) {

                Log.d("AAAA","hihi");

                if (MainActivity.manggiohang.size() > 0){

                    Log.d("AAAA","vào");

                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());

                    boolean exit = false;
                    // chạy duyệt mảng. nếu sản phẩm trùng ID thì
                    for(int i = 0 ;i<MainActivity.manggiohang.size();i++){

                        if(MainActivity.manggiohang.get(i).getIdGH() == idchitiet ){

                            // gán số lượng sản phẩm mới = Số lượng sản phẩm hiện tại  + sl
                            MainActivity.manggiohang.get(i).setIdsoluongGH(MainActivity.manggiohang.get(i).getIdsoluongGH()+sl);
                            // nếu số lượng lớn hơn 10 thì set lại
                            if(MainActivity.manggiohang.get(i).getIdsoluongGH() >= 10 ){

                                MainActivity.manggiohang.get(i).setIdsoluongGH(10);}
                            // set lại giá của sản phẩm
                            MainActivity.manggiohang.get(i).setGiaGH(giachitiet * MainActivity.manggiohang.get(i).getIdsoluongGH());

                            exit = true;

                        }

                        }
                    // nếu không trùng ID thì ta thêm mới
                    if(exit == false){

                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

                        int Giamoi = soluong * giachitiet;

                        MainActivity.manggiohang.add(new GioHang(idchitiet,Tenchitiet,Giamoi,imgChitiet,soluong));


                    }
                }else {
                    // lấy số lượng từ Spiner
                    Log.d("AAAA","thêm ");
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

                    int Giamoi = soluong * giachitiet;

                    MainActivity.manggiohang.add(new GioHang(idchitiet,Tenchitiet,Giamoi,imgChitiet,soluong));

                }
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
                Toast.makeText(ChiTietSanPhamActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


    private void CatchEvent() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(getApplicationContext()
                ,android.R.layout.simple_spinner_dropdown_item
                ,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {

        SanPhamMoiNhat sanPhamMoiNhat = (SanPhamMoiNhat) getIntent().getSerializableExtra("thongtinchitietSP");
        idchitiet = sanPhamMoiNhat.getIDSPNew();
        Tenchitiet = sanPhamMoiNhat.getTenSPNew();
        imgChitiet = sanPhamMoiNhat.getHinhanhSPNew();
        giachitiet = sanPhamMoiNhat.getGiaSPNew();
        Motachitiet = sanPhamMoiNhat.getMotaSPNew();
        idloaichitiet = sanPhamMoiNhat.getIDloaiSPNew();
        txtTenSPChitiet.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGiaSPChitiet.setText("Giá: "+decimalFormat.format(giachitiet)+" Đồng");
        txtMotaSPchitiet.setText(Motachitiet);
        Picasso.with(getApplicationContext()).load(imgChitiet).placeholder(R.drawable.noimage).error(R.drawable.imgerror)
                .into(imgSPChitiet);
    }

    private void anhxa() {
        imgSPChitiet = (ImageView) findViewById(R.id.imgchitiet);
        btnSPadd = (Button) findViewById(R.id.btnthem);
        txtTenSPChitiet = (TextView) findViewById(R.id.txttenchitiet);
        txtGiaSPChitiet = (TextView) findViewById(R.id.txtgiachitiet);
        txtMotaSPchitiet = (TextView) findViewById(R.id.txtmotachitiet) ;
        toolbarChitietSP =(Toolbar) findViewById(R.id.toolbarChitietSP);
        spinner = (Spinner) findViewById(R.id.spinner);
    }

    private void ToolBarAction() {
    setSupportActionBar(toolbarChitietSP);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbarChitietSP.setNavigationOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v){
            finish();
        }
    });
    }

    private void getIDSANPHAM() {
        Intent intent = getIntent();
        iddienthoai = intent.getIntExtra("thongtinchitietSP",-1);
        Log.d("thongtinchitietSP",""+iddienthoai);

    }
}
