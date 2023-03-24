package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.DonHang;


import java.text.DecimalFormat;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    Context context;
    List<DonHang> listdonhang;


    public DonHangAdapter(Context context, List<DonHang> listdonhang) {
        this.context = context;
        this.listdonhang = listdonhang;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn Hàng: " + donHang.getId());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewChiTiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adpter chi tiet
        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(context,donHang.getItem());
        holder.recyclerViewChiTiet.setLayoutManager(layoutManager);
        holder.recyclerViewChiTiet.setAdapter(chiTietAdapter);
        holder.recyclerViewChiTiet.setRecycledViewPool(viewPool);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txttontien.setText("Tổng: "+decimalFormat.format(Double.parseDouble(donHang.getTongtien()))+ "₫");
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtdonhang, txttontien;
        RecyclerView recyclerViewChiTiet;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            recyclerViewChiTiet = itemView.findViewById(R.id.recyclerview_chitiet);
            txttontien = itemView.findViewById(R.id.tongtien);
        }
    }
}
