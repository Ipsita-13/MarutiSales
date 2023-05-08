package com.agriculture.marutisales.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agriculture.marutisales.ModalClasses.SBC_class;
import com.agriculture.marutisales.R;
import com.agriculture.marutisales.ViewPages.Product_View;
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SBC_Adapter extends RecyclerView.Adapter<SBC_Adapter.viewHolder> {
    ArrayList<SBC_class> list;
    Context context;
    public SBC_Adapter(ArrayList<SBC_class> list,Context context) {
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public SBC_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_by_categories_layout,parent,false);
        return new SBC_Adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SBC_Adapter.viewHolder holder, int position) {
        SBC_class sbc=list.get(position);
        Glide.with(context).load(list.get(position).getImage())
                .into(holder.image);
        holder.category.setText(sbc.getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category= sbc.getCategory().toUpperCase();
                SharedPreferences sp= context.getSharedPreferences("Category",Context.MODE_PRIVATE);
                SharedPreferences.Editor myedit=sp.edit();
                myedit.putString("category",category);
                myedit.commit();
                Log.d("TAG in adapter",category);
                Intent i=new Intent(context, Product_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView category;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            category=itemView.findViewById(R.id.category);
            image=itemView.findViewById(R.id.image);
        }
    }
}
