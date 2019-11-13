package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.controller.GioHangActivity;
import com.example.app_ban_hang.controller.MainActivity;
import com.example.app_ban_hang.model.GioHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    Context contextGioHang;
    ArrayList<GioHang> arrayListGH;

    public GioHangAdapter(Context contextGioHang, ArrayList<GioHang> arrayListGH) {
        this.contextGioHang = contextGioHang;
        this.arrayListGH = arrayListGH;
    }

    @Override
    public int getCount() {
        return arrayListGH.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListGH.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
   public class ViewHolderGH{
        TextView txtTenGioHang, txtGiaGioHang;
        ImageView imgGioHang, imgDelete;
        Button btnTru, btnCong, btnValues;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolderGH viewHolderGH;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) contextGioHang.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolderGH = new ViewHolderGH();
            viewHolderGH.imgDelete = convertView.findViewById(R.id.imgDelete);
            viewHolderGH.txtTenGioHang =convertView.findViewById(R.id.txtTenSPGioHang);
            viewHolderGH.imgGioHang = convertView.findViewById(R.id.imgSPGioHang);
            viewHolderGH.txtGiaGioHang =  convertView.findViewById(R.id.txtgiaGioHang);
            viewHolderGH.btnTru =  convertView.findViewById(R.id.btnGiamGioHang);
            viewHolderGH.btnCong =  convertView.findViewById(R.id.btnTangGioHang);
            viewHolderGH.btnValues =  convertView.findViewById(R.id.btnSoLuongGioHang);
            convertView.setTag(viewHolderGH);
        }else {
            viewHolderGH = (ViewHolderGH) convertView.getTag();
        }
        GioHang gioHang = (GioHang) getItem(position);
        viewHolderGH.txtTenGioHang.setText(gioHang.getTenGH());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolderGH.txtGiaGioHang.setText("Giá: " + decimalFormat.format(gioHang.getGiaGH())+" Đồng");
        Picasso.with(contextGioHang).load(gioHang.getHinhGH()).placeholder(R.drawable.noimage).error(R.drawable.imgerror)
                .into(viewHolderGH.imgGioHang);
        viewHolderGH.btnValues.setText(gioHang.getIdsoluongGH()+"");
        viewHolderGH.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(contextGioHang);
                builder.setTitle("Bạn muốn xóa sản phẩm này!");
                builder.setMessage("bạn có chắc xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.manggiohang.size()<=0){
                            GioHangActivity.txtThongBao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                            GioHangActivity.EventUltil();
                            if(MainActivity.manggiohang.size()<=0){
                                GioHangActivity.txtThongBao.setVisibility(View.VISIBLE);


                            }else {
                                GioHangActivity.txtThongBao.setVisibility(View.INVISIBLE);
                                GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                                GioHangActivity.EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GioHangActivity.gioHangAdapter.notifyDataSetChanged();
                        GioHangActivity.EventUltil();
                    }
                });
                builder.show();
            }
        });




        int slButton = Integer.parseInt(viewHolderGH.btnValues.getText().toString());
        if(slButton>=10){
            viewHolderGH.btnCong.setVisibility(View.INVISIBLE);
            viewHolderGH.btnTru.setVisibility(View.VISIBLE);
        }else if(slButton<=1){
            viewHolderGH.btnTru.setVisibility(View.INVISIBLE);
        }else if(slButton>=1){
            viewHolderGH.btnTru.setVisibility(View.VISIBLE); viewHolderGH.btnCong.setVisibility(View.VISIBLE);
        }
        // event cộng sl
        viewHolderGH.btnCong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update giá từng sản phẩm khi click btncong
                int slmoinhat = Integer.parseInt(viewHolderGH.btnValues.getText().toString()) + 1;
                int slhienTai = MainActivity.manggiohang.get(position).getIdsoluongGH();
                int giaHientai = MainActivity.manggiohang.get(position).getGiaGH();
                MainActivity.manggiohang.get(position).setIdsoluongGH(slmoinhat);

                // điều kiện trước giá mới nhất
                if(slmoinhat == 9){
                    viewHolderGH.btnCong.setVisibility(View.INVISIBLE);
                    viewHolderGH.btnTru.setVisibility(View.VISIBLE);
                    viewHolderGH.btnValues.setText(String.valueOf(slmoinhat));
                }else {
                    viewHolderGH.btnTru.setVisibility(View.VISIBLE);
                    viewHolderGH.btnCong.setVisibility(View.VISIBLE);

                    viewHolderGH.btnValues.setText(String.valueOf(slmoinhat));
                }

                int giamoinhat = (giaHientai*slmoinhat)/slhienTai;
                MainActivity.manggiohang.get(position).setGiaGH(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolderGH.txtGiaGioHang.setText("Giá: " + decimalFormat.format(giamoinhat)+" Đồng");
                GioHangActivity.EventUltil();


            }
        });
        viewHolderGH.btnTru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update giá từng sản phẩm khi click btnTRU
                int slmoinhat = Integer.parseInt(viewHolderGH.btnValues.getText().toString()) - 1;
                int slhienTai = MainActivity.manggiohang.get(position).getIdsoluongGH();
                int giaHientai = MainActivity.manggiohang.get(position).getGiaGH();
                MainActivity.manggiohang.get(position).setIdsoluongGH(slmoinhat);
                int giamoinhat = (giaHientai*slmoinhat)/slhienTai;
                MainActivity.manggiohang.get(position).setGiaGH(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolderGH.txtGiaGioHang.setText("Giá: " + decimalFormat.format(giamoinhat)+" Đồng");
                GioHangActivity.EventUltil();

                if(slmoinhat < 2){
                    viewHolderGH.btnCong.setVisibility(View.VISIBLE);
                    viewHolderGH.btnTru.setVisibility(View.INVISIBLE);
                    viewHolderGH.btnValues.setText(String.valueOf(slmoinhat));
                }else {
                    viewHolderGH.btnTru.setVisibility(View.VISIBLE);
                    viewHolderGH.btnCong.setVisibility(View.VISIBLE);
                    viewHolderGH.btnValues.setText(String.valueOf(slmoinhat));
                }
            }
        });

        return convertView;
    }
}
