package com.example.app_ban_hang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_ban_hang.R;
import com.example.app_ban_hang.model.LoaiSP;
import com.example.app_ban_hang.model.SanPhamMoiNhat;
import com.example.app_ban_hang.model.SanPhamNgangMoiNhat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanPhamNgangAdapter extends RecyclerView.Adapter<SanPhamNgangAdapter.viewHolder> {

    private ArrayList<SanPhamNgangMoiNhat> loaiSPArrayList;
    private Context mContext;

    public SanPhamNgangAdapter(ArrayList<SanPhamNgangMoiNhat> loaiSPArrayList, Context mContext) {
        this.loaiSPArrayList = loaiSPArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_horzital,viewGroup,false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {

        viewHolder.txtNgang.setText(loaiSPArrayList.get(i).getTenSPNgang());
        Picasso.with(mContext)
                .load(loaiSPArrayList.get(i).getImgSPNgang())
                .error(R.drawable.imgerror).into(viewHolder.imgNgang);

    }

    @Override
    public int getItemCount() {
        return loaiSPArrayList.size();
    }

    public  class  viewHolder extends RecyclerView.ViewHolder{

        private TextView txtNgang;
        private ImageView imgNgang;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            txtNgang = itemView.findViewById(R.id.txtNgang);
            imgNgang = itemView.findViewById(R.id.imgNgang);



        }
    }
}
