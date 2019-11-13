package com.example.app_ban_hang;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_ban_hang.controller.MainActivity;
import com.example.app_ban_hang.ultil.CheckConnection;
import com.example.app_ban_hang.ultil.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ThongtinKhachHang extends AppCompatActivity {
    EditText edtTenKh, edtEmailKh, edtPhoneKh, edtAddress;
    Button btnUpKh, btnBack;
    TextView txtDayTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtin_khach_hang);
        anhxa();
        datecurrent();
        Event();
    }

    private void datecurrent() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String DatePayment = dateFormat.format(calendar.getTime());
        txtDayTime.setText(DatePayment);
    }

    private void Event() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenKH = edtTenKh.getText().toString();
                final String emailKH = edtEmailKh.getText().toString();
                final String phoneKH = edtPhoneKh.getText().toString();
                final String diachiKH = edtAddress.getText().toString();
                final String ngaydathangKH = txtDayTime.getText().toString();
                if(tenKH.length()>0 && emailKH.length()>0&&phoneKH.length()>0&&diachiKH.length()>0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Service.DuongDanThongTinKH, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Toast.makeText(ThongtinKhachHang.this, "Thanh Cong", Toast.LENGTH_SHORT).show();
                            if(Integer.parseInt(madonhang) >0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Service.DuongDanChiTietDonHang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("POSTCHITIETDONHANG", response);
                                        // trả về 1 clear giỏ hàng
                                        if(response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_short(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công!");
                                            Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent2);
                                        }else {
                                            CheckConnection.ShowToast_short(getApplicationContext(),"Lỗi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d("POSTCHITIETDONHANG", ""+error);
                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for(int i = 0 ; i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdGH());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTenGH());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiaGH());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getIdsoluongGH());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        // gửi lên chuỗi json
                                        HashMap<String, String> param2 = new HashMap<>();
                                        param2.put("json", String.valueOf(jsonArray));
                                        return param2;
                                    }
                                };

                                queue.add(stringRequest1);

                            }
                            Log.d("DONHANG",madonhang);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("DONHANG",error+"");
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> param = new HashMap<>();
                            param.put("tenkhachhang",tenKH);
                            param.put("sodienthoai",phoneKH);
                            param.put("email",emailKH);
                            param.put("diachi",diachiKH);
                            param.put("ngaydathang",ngaydathangKH);
                            return param;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_short(getApplicationContext(),"hãy kiểm tra lại dữ liệu");
                }

            }
        });

    }

    private void anhxa() {
        txtDayTime = findViewById(R.id.txtDateTime);
        edtTenKh = findViewById(R.id.edtTenKH);
        edtEmailKh = findViewById(R.id.edtEMAIL);
        edtAddress = findViewById(R.id.edtDiaChi);
        edtPhoneKh = findViewById(R.id.edtSDT);
        btnUpKh = findViewById(R.id.btnGuiThongtin);
        btnBack = findViewById(R.id.btnBack);

    }
}
