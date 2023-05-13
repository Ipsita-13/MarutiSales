package com.agriculture.marutisales.ViewPages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.agriculture.marutisales.Adapters.Cart_Adapter;
import com.agriculture.marutisales.Adapters.Product_View_Adapter;
import com.agriculture.marutisales.ModalClasses.Base_Class;
import com.agriculture.marutisales.ModalClasses.Cart_class;
import com.agriculture.marutisales.ModalClasses.Product_View_class;
import com.agriculture.marutisales.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;

public class Cart extends Base_Class {

    FirebaseDatabase fd;
    double total_price;
    TextView total;
    DatabaseReference dr;
    String clicked="False";
    ArrayList<Cart_class> list;
    static RecyclerView rv;
    static Cart_Adapter recycleradap;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        rv=findViewById(R.id.arrangement);
        total=findViewById(R.id.total);
        SharedPreferences sp= getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences mp=getSharedPreferences("cart_detail",MODE_PRIVATE);
        String email=sp.getString("email","");
       String clicke=mp.getString("clicked","");
        Log.d("Cart email",email);
        fd=FirebaseDatabase.getInstance();
        dr= fd.getReference("Cart");
        rv.setLayoutManager(new LinearLayoutManager(this));



        //search for this email in the users

Log.d("clicked",clicke);

        show_products_in_cart();








    }

    private void show_products_in_cart() {
        SharedPreferences sphone = getSharedPreferences("Phonenumber", MODE_PRIVATE);
        String get_phone = sphone.getString("phone", "");
dr.child(get_phone).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        list=new ArrayList<>();
        for (DataSnapshot snap:snapshot.getChildren())
        {
            String key=snap.getKey();
            Log.d("children",key);
            Cart_class cc=new Cart_class();
            Log.d("name",snap.child("name").getValue().toString());
            cc.setImage(snap.child("image").getValue().toString());
            cc.setName(snap.child("name").getValue().toString());
            total_price=total_price+Double.parseDouble(snap.child("price").getValue().toString());
            cc.setPrice(snap.child("price").getValue().toString());
            list.add(cc);
        }
        total.setText(String.valueOf((int) total_price));
        Collections.reverse(list);
        recycleradap=new Cart_Adapter(list,getApplicationContext());
        rv.setAdapter(recycleradap);
        recycleradap.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});


    }
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            // Close the application
//            finishAffinity();
//            System.exit(0);
//        } else {
//            // Go to previous activity
//            super.onBackPressed();
//        }
//    }


}