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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.agriculture.marutisales.ModalClasses.Product_View_class;
import com.agriculture.marutisales.ModalClasses.SBC_class;
import com.agriculture.marutisales.R;
import com.agriculture.marutisales.ViewPages.Cart;
import com.agriculture.marutisales.ViewPages.Detail_Product_View;
import com.agriculture.marutisales.ViewPages.Product_View;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product_View_Adapter extends RecyclerView.Adapter<Product_View_Adapter.viewHolder>{
    ArrayList<Product_View_class> list;
    Context context;
DatabaseReference dr;
    boolean already_present=false;
    public Product_View_Adapter(ArrayList<Product_View_class> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Product_View_Adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_view_layout,parent,false);
        dr=FirebaseDatabase.getInstance().getReference();
        return  new Product_View_Adapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Product_View_Adapter.viewHolder holder, int position) {

        Product_View_class pvc=list.get(position);
        Glide.with(context).load(list.get(position).getImage())
                .into(holder.image);
        holder.name.setText(pvc.getName());
        holder.price.setText(pvc.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here you have put the name of the product
                SharedPreferences sp= context.getSharedPreferences("myprefs", MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sp.edit();
                myEdit.putString("name", (String) holder.name.getText());
                myEdit.commit();

                Intent i=new Intent(context, Detail_Product_View.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        holder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

// TODO: 12-04-2023 collect the details from here
                SharedPreferences sharedPreferences=context.getSharedPreferences("cart_detail", MODE_PRIVATE);
                SharedPreferences.Editor details=sharedPreferences.edit();
                details.putString("name", pvc.getName());
                details.putString("price", pvc.getPrice());
                details.putString("image", pvc.getImage());
                details.putString("clicked","True");
                details.commit();
                SharedPreferences sp= context.getSharedPreferences("Login", Context.MODE_PRIVATE);
                String email=sp.getString("email","");



                // TODO: 22-05-2023  check the value exits or not in database of cart
                SharedPreferences sphone = context.getSharedPreferences("Phonenumber", MODE_PRIVATE);
                String get_phone = sphone.getString("phone", "");
//                FirebaseDatabase.getInstance().getReference().child("Cart").child(get_phone)
//                        .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        SharedPreferences sharedPreferences=context.getSharedPreferences("cart_detail", MODE_PRIVATE);
//                       String get_item=sharedPreferences.getString("name","");
//                       //searching for the name already present or not
//                        for (DataSnapshot snap: snapshot.getChildren()) {
//                            String added = snap.child("name").getValue().toString();
//                            Log.d("already added ", added);
//                            Log.d("already added", "line ---------");
//
//                            if (get_item.equals(added)) {
//                                Log.d("nametrue", added + " " + get_item);
//                                already_present = true;
//                                Toast.makeText(context, "Already added", Toast.LENGTH_SHORT).show();
//                                break;
//                            }
//
//
//
//                        }
//
//                    //after the data is being added again on data change is called
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//        Log.d("already present",String.valueOf(already_present));
//                if(already_present)
//                {
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                    search_email(email);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView price;
        TextView name;
        Button add_to_cart;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            price=itemView.findViewById(R.id.price);
            name=itemView.findViewById(R.id.name);
            add_to_cart=itemView.findViewById(R.id.add_to_cart);
        }
    }
    private void search_email(String email) {
        dr.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    String get_email= snap.child("email").getValue().toString();
                    if(email.equals(get_email))
                    {
                        String phone_number=snap.child("phone_number").getValue().toString();
                        SharedPreferences ph_number=context.getSharedPreferences("Phonenumber",MODE_PRIVATE);
                        SharedPreferences.Editor sph=ph_number.edit();
                        sph.putString("phone",phone_number);
                        sph.apply();
                        Log.d("phone number",phone_number);

                        make_cart(phone_number);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void make_cart(String phone_number) {
// TODO: 12-04-2023 here we have to bring the details of the selected item in product view
        SharedPreferences sharedPreferences = context.getSharedPreferences("cart_detail", MODE_PRIVATE);


        Map<String, Object> map = new HashMap<>();
        map.put("name", sharedPreferences.getString("name", ""));
        map.put("price", sharedPreferences.getString("price", ""));
        map.put("image", sharedPreferences.getString("image", ""));

        FirebaseDatabase.getInstance().getReference("Cart").child(phone_number)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.getChildrenCount() == 0) {
                            FirebaseDatabase.getInstance().getReference("Cart").child(phone_number)
                                    .child("1").updateChildren(map);
                        } else {
                            String new_number = String.valueOf(snapshot.getChildrenCount() + 1);
                            FirebaseDatabase.getInstance().getReference("Cart").child(phone_number)
                                    .child(new_number).updateChildren(map);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    }
