package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.ItemClickListener;
import com.example.myapplication.R;
import com.example.myapplication.model.DonHang;
import com.example.myapplication.model.EventBus.DonHangEvent;


import org.greenrobot.eventbus.EventBus;

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
        holder.diachi.setText("Địa chỉ: " + donHang.getDiachi());
        holder.trangthai.setText(trangThaiDon(donHang.getTrangthai()));

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
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if (isLongClick) {
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        });
    }

    private String trangThaiDon(int status){
        String result="";
        switch (status){
            case 0:
                result="Đơn hàng đang được xử lý";
                break;
            case 1:
                result="Đơn hàng đã được chấp nhập";
                break;
            case 2:
                result="Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result="Đơn hàng đã vận chuyển thành công";
                break;
            case 4:
                result="Đơn hàng đã bị hủy";
                break;
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return listdonhang.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang, txttontien, diachi,trangthai;
        RecyclerView recyclerViewChiTiet;
        ItemClickListener listener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            recyclerViewChiTiet = itemView.findViewById(R.id.recyclerview_chitiet);
            txttontien = itemView.findViewById(R.id.tongtien);
            trangthai = itemView.findViewById(R.id.tinhtrang);
            itemView.setOnLongClickListener(this);
        }

        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onClick(v,getAdapterPosition(),true);
            return false;
        }
    }
}
