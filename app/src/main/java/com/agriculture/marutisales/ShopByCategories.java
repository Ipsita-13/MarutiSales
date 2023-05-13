package com.agriculture.marutisales;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agriculture.marutisales.Adapters.SBC_Adapter;
import com.agriculture.marutisales.ModalClasses.SBC_class;
import com.agriculture.marutisales.ModalClasses.Twice_BackPress;
import com.agriculture.marutisales.ViewPages.Account;
import com.agriculture.marutisales.ViewPages.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopByCategories extends Twice_BackPress {
FirebaseDatabase fd;
DatabaseReference dr;
LinearLayout cart,account;
    static RecyclerView rv;
    static SBC_Adapter recycleradapter;
    static ArrayList<SBC_class> list;
    TextView sbc_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_by_categories);
        fd=FirebaseDatabase.getInstance();
        sbc_txt=findViewById(R.id.sbc_txt);
        dr=fd.getReference();
        cart=findViewById(R.id.cart);
        account=findViewById(R.id.account);
        rv=findViewById(R.id.arrangement);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Check if the sbc_txt view is visible
                boolean isTxtVisible = sbc_txt.getGlobalVisibleRect(new Rect());

                // If the RecyclerView has been scrolled and the sbc_txt view is visible, hide it
                if (dy > 0 && isTxtVisible) {
                    sbc_txt.setVisibility(View.GONE);
                } else if (dy < 0 && !isTxtVisible) {
                    // If the RecyclerView has been scrolled up and the sbc_txt view is not visible, show it
                    sbc_txt.setVisibility(View.VISIBLE);
                }
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShopByCategories.this, Cart.class);
                startActivity(i);
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ShopByCategories.this, Account.class);
                startActivity(i);
            }
        });
        dr.child("Categories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    int i=0;
                    try {
                        String children=snap.getKey();
                        //Log.d("children",children);
                        SBC_class hm=new SBC_class();
                        i++;
                        Log.d("category",snap.child("category").getValue().toString());
                        hm.setCategory(snap.child("category").getValue().toString());
                        hm.setImage(snap.child("image").getValue().toString());
                        list.add(hm);
                    }
                    catch(Exception e)
                    {
                        Log.d("here correct",e.toString());
                    }


                }
                recycleradapter=new SBC_Adapter(list,getApplicationContext());
                rv.setAdapter(recycleradapter);
                recycleradapter.notifyDataSetChanged();
//                rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
//                rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.HORIZONTAL));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}