package com.agriculture.marutisales.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agriculture.marutisales.MainActivity;
import com.agriculture.marutisales.R;
import com.agriculture.marutisales.ShopByCategories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends Fragment {
    EditText name,phone_number,email,password,address;
    Button register;

    FirebaseAuth auth;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.register_layout,container,false);
        name=view.findViewById(R.id.name);
        phone_number=view.findViewById(R.id.phone_number);
        email=view.findViewById(R.id.email);
        password=view.findViewById(R.id.password);
        address=view.findViewById(R.id.address);
        register=view.findViewById(R.id.register);
        auth=FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_u=email.getText().toString();
                String password_u=password.getText().toString();
                String name_u=name.getText().toString();
                String phone_u=phone_number.getText().toString();
                String address_u=address.getText().toString();
                if(TextUtils.isEmpty(email_u))
                {
                    Toast.makeText(getActivity(), "Enter EmailId", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password_u))
                {
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(name_u))
                {
                    Toast.makeText(getActivity(), "Enter name", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(phone_u))
                {
                    Toast.makeText(getActivity(), "Enter phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(address_u)) {
                    Toast.makeText(getActivity(), "Enter the address", Toast.LENGTH_SHORT).show();

                } else
                {
                    register_user(email_u,password_u,phone_u,address_u,name_u);

                }
            }
        });
        return view;
    }

    private void register_user(String email_u, String password_u, String phone_u,String address_u,String name_u) {
        auth.createUserWithEmailAndPassword(email_u,password_u).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String user_email=email.getText().toString();
                String user_password=password.getText().toString();
                String user_name=name.getText().toString();
                String user_phone=phone_number.getText().toString();
                String user_address=address.getText().toString();
                Map<String,Object> map=new HashMap<>();
                map.put("name",user_name);
                map.put("email",user_email);
                map.put("password",user_password);
                map.put("phone_number",user_phone);
                map.put("address",user_address);
                FirebaseDatabase.getInstance().getReference().child("Users").push().updateChildren(map);
                if(task.isSuccessful()) {
                    Intent i = new Intent(getActivity(), ShopByCategories.class);
                    startActivity(i);
                }
                else
                {
                    Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                    Log.d("error message",""+task.getException());
                }


            }
        });
    }
}
