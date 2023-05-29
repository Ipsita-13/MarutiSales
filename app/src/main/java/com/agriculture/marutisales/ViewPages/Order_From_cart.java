package com.agriculture.marutisales.ViewPages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.agriculture.marutisales.R;

public class Order_From_cart extends AppCompatActivity {
TextView name_itm,price_itm;
Button confirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_from_cart);
     name_itm=findViewById(R.id.name_itm);
     price_itm=findViewById(R.id.price_itm);
     confirm=findViewById(R.id.confirm);
        SharedPreferences sp=getSharedPreferences("BuyFromCart",MODE_PRIVATE);
        String name=sp.getString("name","");
        String price=sp.getString("price","");

        name_itm.setText(name);
        price_itm.setText(price);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Order_From_cart.this, "Payment Integration Set", Toast.LENGTH_SHORT).show();
            }
        });

    }
}