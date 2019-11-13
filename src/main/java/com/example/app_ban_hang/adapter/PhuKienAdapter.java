package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PhuKienAdapter extends BaseAdapter {
    Context contextPhuKien;
    ArrayList<SanPhamMoiNhat> arrayListPK;

    public PhuKienAdapter(Context contextPK, ArrayList<SanPhamMoiNhat> arrayList) {
        this.contextPhuKien = contextPK;
        this.arrayListPK = arrayList;
    }

    @Override
    public int getCount() {
        return arrayListPK.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListPK.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private  class viewholderPK{
        ImageView imgPhonePK;
        TextView txttenPhonePK, txtGiaPhonePK, txtmotaPhonePK;


    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewholderPK viewholderPK = null;
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) contextPhuKien.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.dong_phukien_listview,null);
            viewholderPK = new viewholderPK();
            viewholderPK.imgPhonePK = (ImageView) convertView.findViewById(R.id.imgphukien);
            viewholderPK.txttenPhonePK = (TextView)convertView.findViewById(R.id.txttenphukien);
            viewholderPK.txtmotaPhonePK = (TextView)convertView.findViewById(R.id.txtmotaphukien);
            viewholderPK.txtGiaPhonePK = (TextView)convertView.findViewById(R.id.txtgiaphukien);
            convertView.setTag(viewholderPK);
        }else{

            viewholderPK = (viewholderPK) convertView.getTag();

        }
        SanPhamMoiNhat sanPhamMoiNhatPK = arrayListPK.get(position);

        viewholderPK.txttenPhonePK.setText(sanPhamMoiNhatPK.getTenSPNew());

        viewholderPK.txtmotaPhonePK.setMaxLines(2);// sét mô tả thành hai dòng
        viewholderPK.txtmotaPhonePK.setEllipsize(TextUtils.TruncateAt.END);// sét cắt dấu 3 chấm
        viewholderPK.txtmotaPhonePK.setText(sanPhamMoiNhatPK.getMotaSPNew());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewholderPK.txtGiaPhonePK.setText("Giá: "+decimalFormat.format(sanPhamMoiNhatPK.getGiaSPNew())+" Đồng");
        // gán giá trị cho sản phẩm
        Picasso.with(contextPhuKien).load(sanPhamMoiNhatPK.getHinhanhSPNew()).placeholder(R.drawable.noimage).error(R.drawable.imgerror)
                .into(viewholderPK.imgPhonePK);


        return convertView;
    }
}