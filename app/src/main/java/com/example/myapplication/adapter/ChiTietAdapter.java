package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.model.Item;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder> {
    Context context;
    List<Item> itemList;

    public ChiTietAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText(item.getTensanpham() + "");
        holder.txtsoluong.setText("Số lượng: " + item.getSoluong() + "");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgia.setText(decimalFormat.format(item.getGia())+ "₫");
        Glide.with(context).load(item.getHinhanh()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtten,txtsoluong,txtgia;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_temspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
            txtgia = itemView.findViewById(R.id.item_giachitiet);
        }
    }
}
