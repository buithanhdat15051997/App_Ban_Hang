package com.example.app_ban_hang.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_ban_hang.R;
import com.example.app_ban_hang.adapter.PhuKienAdapter;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.example.app_ban_hang.ultil.Service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PhuKienActivity extends AppCompatActivity {
    ListView listViewphukien;
    PhuKienAdapter phuKienAdapter;
    ArrayList<SanPhamMoiNhat> arrayListphukienActivity;
    int IdPhukien= 0;
    int page = 1;
    Toolbar toolbarphukien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phu_kien);
        getIDsanphamPhukien();
        anhxa();
        ActionToolbarphukien();
        getDataPhuKien(page);
        LoadmoreDataP();
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
    private void LoadmoreDataP() {
       listViewphukien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(PhuKienActivity.this,ChiTietSanPhamActivity.class);
               intent.putExtra("thongtinchitietSP",arrayListphukienActivity.get(position));
               startActivity(intent);

           }
       });
      }

    private void getDataPhuKien(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Service.DuongDanDienThoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int Idphukien = 0;
                String Tensptyphukien = "";
                int Giasptyphukien = 0;
                String Hinhanhsptyphukien = "";
                String Motasptyphukien = "";
                int Idsptyphukien = 0;
                if (response != null && response.length()>0){

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0 ; i<jsonArray.length();i++){

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Idphukien = jsonObject.getInt("id");
                            Tensptyphukien = jsonObject.getString("tensp");
                            Giasptyphukien = jsonObject.getInt("giasp");
                            Hinhanhsptyphukien = jsonObject.getString("hinhanhsp");
                            Motasptyphukien = jsonObject.getString("motasp");
                            Idsptyphukien = jsonObject.getInt("idsanpham");

                            arrayListphukienActivity.add(new SanPhamMoiNhat(Idphukien,Tensptyphukien,Giasptyphukien,Hinhanhsptyphukien
                                    ,Motasptyphukien,Idsptyphukien));
                            phuKienAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
                param.put("idsanpham",String.valueOf(IdPhukien));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ActionToolbarphukien() {
        setSupportActionBar(toolbarphukien);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarphukien.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void getIDsanphamPhukien() {
        Intent intent = getIntent();
        IdPhukien = intent.getIntExtra("idloaisp",-1);
        Log.d("aaaa", String.valueOf(IdPhukien));
    }
    private void anhxa() {
        listViewphukien = (ListView) findViewById(R.id.listviewPhukien);
        toolbarphukien = (Toolbar) findViewById(R.id.toolbarphukien);
        arrayListphukienActivity = new ArrayList<>();
        phuKienAdapter = new PhuKienAdapter(getApplicationContext(),arrayListphukienActivity);
        listViewphukien.setAdapter(phuKienAdapter);
    }


}
