package com.agriculture.marutisales.ViewPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.agriculture.marutisales.Adapters.Product_View_Adapter;
import com.agriculture.marutisales.Adapters.SBC_Adapter;
import com.agriculture.marutisales.ModalClasses.Product_View_class;
import com.agriculture.marutisales.ModalClasses.SBC_class;
import com.agriculture.marutisales.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Product_View extends AppCompatActivity {
    FirebaseDatabase fd;
    DatabaseReference dr;
    static RecyclerView rv;
    static Product_View_Adapter recycleradapter;
    String TAG="Product_View";
    static ArrayList<Product_View_class> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        fd=FirebaseDatabase.getInstance();
        dr=fd.getReference();
        rv=findViewById(R.id.arrangement);
        SharedPreferences sh=getSharedPreferences("Category",MODE_PRIVATE);
        String category=sh.getString("category"," ");
        Log.d("category in Product view",category);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        showproducts(category);
    }

    private void showproducts(String category) {
        // TODO: 29-03-2023 Here the clicked category will be passed
        dr.child(category).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot snap:snapshot.getChildren())
                {
                    String children=snap.getKey();
                    Log.d(TAG,children);

                        Product_View_class pvc=new Product_View_class();
                        pvc.setImage(snap.child("image").getValue().toString());
                        pvc.setName(snap.child("product").getValue().toString());
                        pvc.setPrice(snap.child("price").getValue().toString());
                        list.add(pvc);


                }
                recycleradapter=new Product_View_Adapter(list,getApplicationContext());
                rv.setAdapter(recycleradapter);
                recycleradapter.notifyDataSetChanged();
              rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.VERTICAL));
               rv.addItemDecoration(new DividerItemDecoration(rv.getContext(), DividerItemDecoration.HORIZONTAL));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}