package com.agriculture.marutisales;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.agriculture.marutisales.Fragments.Login;
import com.agriculture.marutisales.Fragments.Register;
import com.agriculture.marutisales.ModalClasses.Twice_BackPress;
import com.agriculture.marutisales.ViewPages.Cart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView hello;
    AssetManager assetManager;
    FirebaseDatabase fd;
    DatabaseReference dr;
    DatabaseReference df;
    LinearLayout navigation;
    LinearLayout cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hello = findViewById(R.id.hello);
        fd = FirebaseDatabase.getInstance();
        dr = fd.getReference();
        navigation=findViewById(R.id.navigation);
        cart=findViewById(R.id.cart);
        assetManager = getAssets();
        try {
            readCSVFileFromAssets();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ShopByCategories.class);
                startActivity(i);
            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment f=new Login();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main, f);
        transaction.commit();
        //here hide the  layout present in the main activity
        hello.setVisibility(View.GONE);


    }

    private void readCSVFileFromAssets() throws IOException {
        InputStream inputStream = assetManager.open("maruti_template.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        int i = 0;
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            try {
                Log.d("part012", "Name= " + parts[0] + " " + "Category= " + parts[1] + " " + parts[3]);
//
                HashMap<String, Object> map = new HashMap<>();
                map.put("product", parts[0]);
                map.put("price", parts[3]);

                i++;
                if (!parts[0].isEmpty()) {
                    FirebaseDatabase.getInstance().getReference().child(parts[1])
                            .child("P" + i).updateChildren(map);
                }
            } catch (Exception e) {
                Log.d("Line no", String.valueOf(i));
            }


        }
    }
}


