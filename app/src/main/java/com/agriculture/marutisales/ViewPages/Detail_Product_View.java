package com.agriculture.marutisales.ViewPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agriculture.marutisales.ModalClasses.Detail_Product_class;
import com.agriculture.marutisales.R;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detail_Product_View extends AppCompatActivity {
ImageView image;
TextView price,name,delivery;
Button buy_btn;
Context context;
String TAG="Detail_Product_View";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product_view);
        image=findViewById(R.id.image);
        price=findViewById(R.id.price_value);
        name=findViewById(R.id.name_value);
        delivery=findViewById(R.id.delivery_value);
        buy_btn=findViewById(R.id.buy_btn);
      context=getApplicationContext();
        getdetail();
        buy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Detail_Product_View.this, Order_Now.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               startActivity(intent);
            }
        });

    }

    private void getdetail() {
// TODO: 04-04-2023 Change the Activator to the clicked category and then clicked products details as child
        SharedPreferences sh = getSharedPreferences("myprefs",MODE_PRIVATE);
        String s1 = sh.getString("name", "");
        SharedPreferences sp=getSharedPreferences("Category",MODE_PRIVATE);
        String category=sp.getString("category","");
        Log.d("get detail category",category);
        Log.d("get detail name",s1);
        FirebaseDatabase.getInstance().getReference().child(category)
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String children = snap.getKey();
                    Log.d("Views", snap.child("product").getValue().toString());


                    if (snap.child("product").getValue().toString().equals(s1)) {
                        String imageUrl = snap.child("image").getValue().toString();
                        String price_firebase = snap.child("price").getValue().toString();
                        String delivery_firebase = snap.child("delivery").getValue().toString();
                        String name_firebase = snap.child("product").getValue().toString();
                        Glide.with(getApplicationContext())
                                .load(imageUrl)
                                .into(image);

                        price.setText(price_firebase);
                        delivery.setText(delivery_firebase);
                        name.setText(name_firebase);
                        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

// Get a reference to the editor to modify shared preferences
                        SharedPreferences.Editor editor = prefs.edit();

// Add a key-value pair to the editor
                        editor.putString("name", name_firebase);
                        editor.putString("price", price_firebase);
                        editor.putString("delivery", delivery_firebase);
                        editor.putString("image", imageUrl);

// Commit the changes
                        editor.commit();
                        break;
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}