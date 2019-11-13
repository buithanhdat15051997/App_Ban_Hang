package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.controller.ChiTietSanPhamActivity;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamNewAdapter extends RecyclerView.Adapter<SanPhamNewAdapter.ViewHolder> {
     Context context;
     ArrayList<SanPhamMoiNhat> moiNhatArrayList; // truyền vào cái khuôn của sản phẩm

    public SanPhamNewAdapter(Context context, ArrayList<SanPhamMoiNhat> moiNhatArrayList) {
        this.context = context;
        this.moiNhatArrayList = moiNhatArrayList;
    }

    @NonNull
    @Override
    //Phương thức tạo View :LayoutInflater, gọi Itemview từ Holer vào
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertview = inflater.inflate(R.layout.dong_sanphamnew,null);
        ViewHolder viewHolder = new ViewHolder(convertview);
        return viewHolder;
    }

    // Gán giá trị cho View, từ arraylist: khung SanPhamMoiNhat
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SanPhamMoiNhat sanPhamMoiNhat = moiNhatArrayList.get(i);
        viewHolder.txtTenSanPhamNew.setMaxLines(1);// sét mô tả thành hai dòng
        viewHolder.txtTenSanPhamNew.setEllipsize(TextUtils.TruncateAt.END);// sét cắt dấu 3 chấm
        viewHolder.txtTenSanPhamNew.setText(sanPhamMoiNhat.getMotaSPNew());
        // định dạng kiểu tiền 1,000,000
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiasanphamNew.setText("Giá: "+ decimalFormat.format(sanPhamMoiNhat.getGiaSPNew())+" Đồng");
        // dùng Picassoc đọc URL ảnh
        Picasso.with(context)
                .load(sanPhamMoiNhat.getHinhanhSPNew()).placeholder(R.drawable.noimage)
                .error(R.drawable.imgerror).into(viewHolder.imgSanPhamNew);

    }

    @Override
    public int getItemCount() {
        return moiNhatArrayList.size();
    }
    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenSanPhamNew,txtGiasanphamNew;
        ImageView imgSanPhamNew;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //ánh xạ view
            txtTenSanPhamNew = itemView.findViewById(R.id.txtTenSanPhamNew);
            txtGiasanphamNew = itemView.findViewById(R.id.giaSanPhamNew);
            imgSanPhamNew = itemView.findViewById(R.id.imgSanPhamNew);
            //bắt sự kiện cho sản phẩm mới nhất
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentnew = new Intent(context, ChiTietSanPhamActivity.class);
                    intentnew.putExtra("thongtinchitietSP",moiNhatArrayList.get(getPosition()));
                    // set intent một flag để không lỗi
                    intentnew.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intentnew);
                }
            });
        }
    }


}
