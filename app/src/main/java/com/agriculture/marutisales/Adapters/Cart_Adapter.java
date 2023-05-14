package com.agriculture.marutisales.Adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.agriculture.marutisales.ModalClasses.Cart_class;
import com.agriculture.marutisales.ModalClasses.Product_View_class;
import com.agriculture.marutisales.R;
import com.agriculture.marutisales.ViewPages.Cart;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        holder.remove_item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //on clicking get the parent of the item clicked

                String clickedItemName = list.get(position).getName();
                    SharedPreferences sphone = context.getSharedPreferences("Phonenumber", MODE_PRIVATE);
                    String get_phone = sphone.getString("phone", "");
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart").child(get_phone);



           cartRef.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   for(DataSnapshot snap:snapshot.getChildren()) {
                       //check for the name that is clicked
                       String itemName = snap.child("name").getValue(String.class);
                       if (itemName.equals(clickedItemName))
                       {
                           // Remove the item from the database
                           Log.d("key of the clicked value",snap.getKey());
                           //snap.getRef().removeValue();
                           cartRef.child(snap.getKey()).removeValue();

//                           Intent i=new Intent(context,Cart.class);
//                           i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                           context.startActivity(i);
                       }
                       break;
                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {

               }
           });

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView price,name;
        Button remove_item;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            remove_item=itemView.findViewById(R.id.remove);
        }
    }
}
