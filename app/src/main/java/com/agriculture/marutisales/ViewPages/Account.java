package com.agriculture.marutisales.ViewPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.agriculture.marutisales.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Account extends AppCompatActivity {
EditText name,phone_number,address;
FirebaseDatabase fd;
DatabaseReference dr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // TODO: 14-04-2023 Search for the email entered in login and search for the details
        name=findViewById(R.id.name_edt);
        phone_number=findViewById(R.id.phone_edt);
        address=findViewById(R.id.address_edt);
        fd=FirebaseDatabase.getInstance();
        dr=fd.getReference();
        SharedPreferences sp=getSharedPreferences("Login",MODE_PRIVATE);
        String email=sp.getString("email","");
 Log.d("email login",email);
        dr.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren())
                {
                    String get_email= snap.child("email").getValue().toString();


                    if (get_email.equals(email))
                    {
                        String name_user=snap.child("name").getValue().toString();
                        String phone_user=snap.child("phone_number").getValue().toString();
                        String address_user=snap.child("address").getValue().toString();
                        name.setText(name_user);
                        phone_number.setText(phone_user);
                        address.setText(address_user);
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