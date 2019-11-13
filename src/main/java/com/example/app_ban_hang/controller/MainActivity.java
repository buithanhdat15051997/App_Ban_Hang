package com.example.app_ban_hang.controller;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_ban_hang.DiaChiActivity;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.LoaiSPAdapter;
import com.example.app_ban_hang.adapter.SanPhamNewAdapter;
import com.example.app_ban_hang.adapter.SanPhamNgangAdapter;
import com.example.app_ban_hang.model.GioHang;
import com.example.app_ban_hang.model.LoaiSP;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.example.app_ban_hang.model.SanPhamNgangMoiNhat;
import com.example.app_ban_hang.ultil.CheckConnection;
import com.example.app_ban_hang.ultil.Service;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView,recyclerView_hor;
    NavigationView navigationView;
    ListView listView;
    ArrayList<LoaiSP> mangloaisp;
    LoaiSPAdapter loaiSPAdapter;

    SanPhamNgangAdapter sanPhamNgangAdapter;
    LinearLayoutManager layoutManager;

    // để lưu giá trị khi nhấn tiếp tục mua hàng, khai báo để truy cập được các activity khác
    public  static  ArrayList<GioHang> manggiohang;
    int id=0;
    String TenloaiSp= "";
    String HinhanhSp= "";
    public  static  ArrayList<SanPhamMoiNhat> sanPhamMoiNhatArrayList;
    private ArrayList<SanPhamNgangMoiNhat> sanPhamMoiNhatsNgang;
    SanPhamNewAdapter sanPhamNewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            ActionBar(); // function bắt sự kiện Toolbar
            ActionViewFlipper();//fuction sự kiện ViewFlipper
            GetDuLieuLoaiSP();
            GetDuLieuSanPhamMoiNhat();
            GetDataproductNew();
            ClickItemListViewNavi();// bắt sự kiện listview cho navigative
            ClickItemListViewChitietsp();

        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"Check internet");

        }

    }

    private void GetDataproductNew() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest =new JsonArrayRequest(Service.DuongDanSanPhamNgangNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("Dat",response.toString());

                if(response!=null){

                    for(int i = 0; i<response.length();i++){

                        try {

                            JSONObject jsonObject = response.getJSONObject(i);

                            sanPhamMoiNhatsNgang.add( new SanPhamNgangMoiNhat(jsonObject.getString("tenSP"),jsonObject.getString("hinhanhSP")));

                            sanPhamNgangAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }




                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("Dat",error.toString());

            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    // tạo cart giỏ hàng
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

    private void ClickItemListViewChitietsp() {


    }

    private void ClickItemListViewNavi() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        drawerLayout.closeDrawers();
                        break;
                    case 1:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intentPHONE = new Intent(MainActivity.this, DienThoaiActivity.class);
                            // truyền qua Phoneactivity id loai sản phẩm
                            // case trùng với i của mảng
                            intentPHONE.putExtra("idloaisp",mangloaisp.get(position).getIdSP());
                            startActivity(intentPHONE);
                        }else{ CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra lại kết nối");}
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intentLaptop = new Intent(MainActivity.this,LaptopActivity.class);
                            intentLaptop.putExtra("idloaisp",mangloaisp.get(position).getIdSP());
                            startActivity(intentLaptop);
                        }else{CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra lại kết nối");}

                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case 3:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intentPhukien = new Intent(MainActivity.this,PhuKienActivity.class);
                            intentPhukien.putExtra("idloaisp",mangloaisp.get(position).getIdSP());
                            startActivity(intentPhukien);
                        }else{CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra lại kết nối");}
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Toast.makeText(MainActivity.this, "By: Bùi Thành Đạt", Toast.LENGTH_SHORT).show();
                        }else{CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra lại kết nối");}
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
                            Intent intentDiachi = new Intent(MainActivity.this, DiaChiActivity.class);
                            startActivity(intentDiachi);
                        }else{CheckConnection.ShowToast_short(getApplicationContext(),"Hãy kiểm tra lại kết nối");}
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }
    private void GetDuLieuSanPhamMoiNhat() {

        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Service.DuongDanSanPhamNew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("response", "onResponse: "+response);

                if(response != null) {
                    int ID= 0;
                    String Ten = "";
                    int Gia = 0;
                    String Hinhanh ="";
                    String Mota ="";
                    int idLoaiSp = 0;
                    for(int i = 0; i<response.length() ;i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            ID = object.getInt("id");
                            Ten = object.getString("tenSP");
                            Gia = object.getInt("giaSP");
                            Hinhanh = object.getString("hinhanhSP");
                            Mota = object.getString("motaSP");
                            idLoaiSp = object.getInt("idloaiSP");

                            SanPhamMoiNhat sanPhamMoiNhat = new SanPhamMoiNhat(ID,Ten,Gia,Hinhanh,Mota,idLoaiSp);
                            sanPhamMoiNhatArrayList.add(sanPhamMoiNhat);

                            sanPhamNewAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();

            }
        });

        requestQueue.add(arrayRequest);
    }
    private void GetDuLieuLoaiSP() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Service.DuongDanLoaiSp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("loaisanpham", "onResponse: "+response);
                if(response != null){
                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject object = response.getJSONObject(i);
                            id = object.getInt("id");
                            TenloaiSp = object.getString("tenloaisp");
                            HinhanhSp =object.getString("hinhanhloaisp");
                            LoaiSP loaiSP = new LoaiSP(id,TenloaiSp,HinhanhSp);
                            mangloaisp.add(loaiSP);
                            loaiSPAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(4, new LoaiSP(0,"Liên hệ","http://www.sclance.com/pngs/contact-icon-png/contact_icon_png_315326.png"));
                    mangloaisp.add(5, new LoaiSP(0,"Thông tin","https://img.icons8.com/clouds/2x/google-maps.png"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("cccccc", "onErrorResponse: ");
                Toast.makeText(getApplicationContext(),"Thất bại"+error,Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionViewFlipper() {
        ArrayList<String> arrayListFlipper = new ArrayList<>();
        arrayListFlipper.add("https://cdn.tgdd.vn/2019/05/banner/Vivo-V15-Y93-800-300-800x300-(1).png");
        arrayListFlipper.add("https://cdn.tgdd.vn/2019/06/banner/mi7-800-300-800x300.png");
        arrayListFlipper.add("https://cdn.tgdd.vn/2019/05/banner/800-300-800x300-(9).png");
        // gán vào ImageView rồi gắn vào mảng sau vì mảng k nhận Url
        for(int i = 0; i< arrayListFlipper.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            // dùng thư viện Picasso load vào ImageView. lấy possition từ mảng . đổ vào ImageView
            Picasso.with(getApplicationContext()).load(arrayListFlipper.get(i)).into(imageView);
            //sét imageview bằng XY kích thước ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //Đổ vào ViewLipper
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);// chạy trong 5s
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);


    }

    private  void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// đặt hiển thị khi bặt toolbar
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void anhxa() {

        drawerLayout =(DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar =(Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        navigationView= (NavigationView) findViewById(R.id.navigationView);
        listView =(ListView) findViewById(R.id.listviewManhinhchinh);

        recyclerView_hor = findViewById(R.id.recyclerView_hor);
        //Mảng sp Ngang
        sanPhamMoiNhatsNgang = new ArrayList<>();
        sanPhamNgangAdapter = new SanPhamNgangAdapter(sanPhamMoiNhatsNgang,getApplicationContext());

        recyclerView_hor.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);

        recyclerView_hor.setLayoutManager(layoutManager);

        recyclerView_hor.setAdapter(sanPhamNgangAdapter);


        //Mảng Loại SẢN PHẨM
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new LoaiSP(0,"Trang chính","http://icons.iconarchive.com/icons/graphicloads/colorful-long-shadow/256/Home-icon.png"));
        loaiSPAdapter = new LoaiSPAdapter(getApplicationContext(),mangloaisp);
        listView.setAdapter(loaiSPAdapter);

        // Mảng Sản phẩm Mới
        sanPhamMoiNhatArrayList = new ArrayList<>();
        sanPhamNewAdapter = new SanPhamNewAdapter(getApplicationContext(),sanPhamMoiNhatArrayList);

        // set recyclerview lại 2 cột , giống GridView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
        recyclerView.setAdapter(sanPhamNewAdapter);

        // kiểm tra mảng khi tiếp tục mua hàng có tồn tại
        if(manggiohang != null){

        }else{
            manggiohang= new ArrayList<>();
        }
    }

    private void CustomSanPhamNgang() {




    }
}
