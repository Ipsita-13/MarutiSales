package com.agriculture.marutisales.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agriculture.marutisales.ModalClasses.Cart_class;
import com.agriculture.marutisales.ModalClasses.Product_View_class;
import com.agriculture.marutisales.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.viewHolder> {
    ArrayList<Cart_class> list;
    Context context;
    public Cart_Adapter(ArrayList<Cart_class> list, Context context) {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public Cart_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_layout,parent,false);
        return  new Cart_Adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Adapter.viewHolder holder, int position) {
        Cart_class pvc=list.get(position);
        Glide.with(context).load(list.get(position).getImage())
                .into(holder.image);
        holder.name.setText(pvc.getName());
        holder.price.setText(pvc.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price,name;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
}
