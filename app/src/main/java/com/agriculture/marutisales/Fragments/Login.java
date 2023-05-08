package com.agriculture.marutisales.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.agriculture.marutisales.MainActivity;
import com.agriculture.marutisales.R;
import com.agriculture.marutisales.ShopByCategories;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

public class Login extends Fragment {

    EditText email,password;
    Button login;
    FirebaseAuth auth;
    TextView go_signup;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.login_layout,container,false);
        email=view.findViewById(R.id.email_login);
        password=view.findViewById(R.id.password_login);
        login=view.findViewById(R.id.login);
        auth=FirebaseAuth.getInstance();
        go_signup=view.findViewById(R.id.go_sign_up);
        SharedPreferences sp=getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor se=sp.edit();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_u=email.getText().toString();
                se.putString("email",email_u);
                se.commit();
                String password_u=password.getText().toString();
                if (TextUtils.isEmpty(email_u))
                {
                    Toast.makeText(getActivity(), "Enter the email", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password_u))
                {
                    Toast.makeText(getActivity(), "Enter the password", Toast.LENGTH_SHORT).show();
                }
                else 
                    login_user(email_u,password_u);
            }
        });
        go_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Register f = new Register();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.login_container, f);
                transaction.commit();
                go_signup.setVisibility(v.GONE);
                login.setVisibility(v.GONE);
                Log.d("login btn ", "Login is pressed");
            }
        });
        return view;
    }

    private void login_user(String email_u, String password_u) {
        auth.signInWithEmailAndPassword(email_u,password_u).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Login Succesful", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(getContext(), ShopByCategories.class);
                    startActivity(i);


                }
                else
                    Toast.makeText(getContext(), "Some Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}
