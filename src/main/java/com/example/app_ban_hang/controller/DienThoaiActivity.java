package com.example.app_ban_hang.controller;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.DienThoaiAdapter;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.example.app_ban_hang.ultil.CheckConnection;
import com.example.app_ban_hang.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DienThoaiActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbarDT;
    ListView listViewDT;
    ArrayList<SanPhamMoiNhat> arrayListdienthoai;
    DienThoaiAdapter dienThoaiAdapter;
    int iddt = 0;
    int page = 1;
    View footerview;//view progressbar
    boolean isLoading = false;// Load xong, biến ngăn chặn kéo liên lục và crack app
    mHandler mHandler;
    boolean limitdata = false;// xác nhận chưa hết dữ liệu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dien_thoai);
        anhxa();
        ActionToolbarDT();
        getIDloaisp();
        GetdataDienThoai(page);
        LoadMoreData();
    }
    //MENU Cart giỏ hàng
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

    private void LoadMoreData() {
        //thong tin chi tiet
        listViewDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DienThoaiActivity.this,ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinchitietSP",arrayListdienthoai.get(position));
                startActivity(intent);


            }
        });


        // sự kiện kéo của listview
        listViewDT.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int FirstVisibleItem, int VisibleItemCount, int TotalItemCount) {
                // if để bắt giá trị cuối cho listview
                // tổng view  = view đầu cộng các view nhìn thấy => đứng ở vị trí cuối cùng
                // Total khác 0
                if(FirstVisibleItem + VisibleItemCount == TotalItemCount && TotalItemCount != 0 && isLoading == false &&  limitdata==false){
                    // load dữ liệu
                    isLoading = true; // bật load
                    ThreadData threadDATA = new ThreadData();
                    threadDATA.start();

                }
            }
        });

    }
    private void GetdataDienThoai(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Service.DuongDanDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String Tenspty = "";
                int Giaspty = 0;
                String Hinhanhspty = "";
                String Motaspty = "";
                int Idspty = 0;
                if (response != null && response.length()>0){
                    listViewDT.removeFooterView(footerview); // remove progressbar
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Id = jsonObject.getInt("id");
                            Tenspty = jsonObject.getString("tensp");
                            Giaspty = jsonObject.getInt("giasp");
                            Hinhanhspty = jsonObject.getString("hinhanhsp");
                            Motaspty = jsonObject.getString("motasp");
                            Idspty = jsonObject.getInt("idsanpham");
                            arrayListdienthoai.add(new SanPhamMoiNhat(Id,Tenspty,Giaspty,Hinhanhspty,Motaspty,Idspty));
                            dienThoaiAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    // Loaddata hết thì đóng progressbar if response.length()>0
                    limitdata = true;// xác nhận hết dữ liệu
                    listViewDT.removeFooterView(footerview);
                    Toast.makeText(getApplicationContext(),"Đã hết dữ liệu",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<String,String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbarDT() {
        setSupportActionBar(toolbarDT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getIDloaisp() {
    Intent intent = getIntent();
    iddt = intent.getIntExtra("idloaisp", -1);
        Log.d("giatrisanpham",iddt +"");
    }


    private void anhxa() {
        listViewDT = (ListView) findViewById(R.id.listviewDienthoai);
        toolbarDT = (Toolbar) findViewById(R.id.toolbardienthoai);
        arrayListdienthoai = new ArrayList<>();
        dienThoaiAdapter = new DienThoaiAdapter(getApplicationContext(),arrayListdienthoai);
        listViewDT.setAdapter(dienThoaiAdapter);
        // gắn layout progressbar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();


    }
    // khai báo class handler

    public  class  mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {  //dùng quản lí Thread gửi lên
            switch (msg.what){
                case 0: // nếu thread gửi lên biến bằng 0
                    listViewDT.addFooterView(footerview);
                    break;
                case 1: // nếu thread bằng 1 thì nó cập nhật đỗ dữ liệu lên listview
                    GetdataDienThoai(++page);// ++ page khi nhảy vào fucntion nó cộng page trước khi vào function
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    // Luồng Thread
    public  class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);// gửi lên case 0
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //  Khởi tạo một message liên kết với handler trên
            Message message  = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
