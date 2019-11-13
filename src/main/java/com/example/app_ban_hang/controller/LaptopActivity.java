package com.example.app_ban_hang.controller;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.LaptopAdapter;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.example.app_ban_hang.ultil.CheckConnection;
import com.example.app_ban_hang.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    ListView listViewlaptop;
    View footerView;
    Toolbar toolbarLaptop;
    ArrayList<SanPhamMoiNhat> arrayListLaptop;
    LaptopAdapter adapterlaptop;
    int page = 1;
    int IDLAPTOP = 0;
    mHandlerPK mHandlerPK;
    boolean isLoadingPK = false;
    boolean LimitdataPK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            anhxa();
            getIDlaptop();
            actiontoolbarLaptop();
            GetDataLaptop(page);
            LoadMoreDataPK();
        }else {
            CheckConnection.ShowToast_short(getApplicationContext(),"Kiễm tra wifi");

        }
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
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

    private void LoadMoreDataPK() {
        listViewlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(LaptopActivity.this,ChiTietSanPhamActivity.class);
                    intent.putExtra("thongtinchitietSP",arrayListLaptop.get(position));
                    startActivity(intent);
                    finish();
            }
        });

        listViewlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoadingPK == false &&  LimitdataPK==false){
                    // load dữ liệu
                    isLoadingPK = true; // bật load
                    ThreadPK threadDATA = new ThreadPK();
                    threadDATA.start();

                }
            }
        });
    }

    private void GetDataLaptop(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Service.DuongDanDienThoai+String.valueOf(Page);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Id = 0;
                String TenLaptop = "";
                int GiaLaptop = 0;
                String HinhanhLaptop = "";
                String MotaLaptop = "";
                int IdLaptop = 0;
                if (response != null && response.length()>0){
                    listViewlaptop.removeFooterView(footerView);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            Id = jsonObject.getInt("id");
                            TenLaptop = jsonObject.getString("tensp");
                            GiaLaptop = jsonObject.getInt("giasp");
                            HinhanhLaptop = jsonObject.getString("hinhanhsp");
                            MotaLaptop = jsonObject.getString("motasp");
                            IdLaptop = jsonObject.getInt("idsanpham");
                           arrayListLaptop.add(new SanPhamMoiNhat(Id,TenLaptop,GiaLaptop,HinhanhLaptop,MotaLaptop,IdLaptop));
                            adapterlaptop.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    LimitdataPK = true;
                    listViewlaptop.removeFooterView(footerView);
                    Toast.makeText(getApplicationContext(),"Hết Data",Toast.LENGTH_LONG).show();

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
                param.put("idsanpham",String.valueOf(IDLAPTOP));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa() {
        toolbarLaptop = (Toolbar) findViewById(R.id.toolbarLaptop);
        listViewlaptop = (ListView) findViewById(R.id.listviewLaptop);
        arrayListLaptop = new ArrayList<>();
        adapterlaptop = new LaptopAdapter(getApplicationContext(),arrayListLaptop);
        listViewlaptop.setAdapter(adapterlaptop);
        // gắn  progressbar
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.progressbar,null);
        mHandlerPK = new mHandlerPK();
    }

    private void actiontoolbarLaptop() {
        setSupportActionBar(toolbarLaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getIDlaptop() {
        Intent intent = getIntent();
         IDLAPTOP = intent.getIntExtra("idloaisp", -1);
        Log.d("giatrisanpham",IDLAPTOP+"");
    }
    public  class  mHandlerPK extends Handler {
        @Override
        public void handleMessage(Message msg) {  //dùng quản lí Thread gửi lên
            switch (msg.what){
                case 0: // nếu thread gửi lên biến bằng 0
                    listViewlaptop.addFooterView(footerView);
                    break;
                case 1: // nếu thread bằng 1 thì nó cập nhật đỗ dữ liệu lên listview
                   GetDataLaptop(++page);// ++ page khi nhảy vào fucntion nó cộng page trước khi vào function
                    isLoadingPK = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    // Luồng Thread
    public  class ThreadPK extends Thread{
        @Override
        public void run() {
            mHandlerPK.sendEmptyMessage(0);// gửi lên case 0
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //  Khởi tạo một message liên kết với handler trên
            Message message  = mHandlerPK.obtainMessage(1);
            mHandlerPK.sendMessage(message);
            super.run();
        }
    }
}
