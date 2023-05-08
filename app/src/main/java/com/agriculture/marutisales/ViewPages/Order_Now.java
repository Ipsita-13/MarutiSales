package com.agriculture.marutisales.ViewPages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agriculture.marutisales.R;
import com.agriculture.marutisales.gmail_sender;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.exception.AppNotFoundException;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class Order_Now extends AppCompatActivity implements PaymentStatusListener {
TextView name,price,delivery,pay;
ImageView imageView;
Button confirm,cancel;
    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
//here payments will be done
    private PaymentsClient paymentsClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_now);
        SharedPreferences prefs = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String name_sp = prefs.getString("name", "");
        String price_sp=prefs.getString("price","");
        String delivery_sp= prefs.getString("delivery","");
        String image_sp=prefs.getString("image","");
        name=findViewById(R.id.name_order);
        price=findViewById(R.id.price_order);
        delivery=findViewById(R.id.delivery_order);
        imageView=findViewById(R.id.image_order);
        confirm=findViewById(R.id.confirm_order);
        pay=findViewById(R.id.pay);
        cancel=findViewById(R.id.cancel_order);
        Wallet.WalletOptions walletOptions=new Wallet.WalletOptions.Builder()
                .setEnvironment(WalletConstants.ENVIRONMENT_TEST).build();
        paymentsClient=Wallet.getPaymentsClient(this,walletOptions);
        name.setText(name_sp);
        price.setText(price_sp);
        delivery.setText(delivery_sp);
        Glide.with(getApplicationContext())
                .load(image_sp)
                .into(imageView);




        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new gmail_sender("1941012073.ipsita@gmail.com","Order placed",
//                        "The order for product "+name_sp+" price "+ price_sp +"is placed" +
//                                "Thank you for choosing Maruti Sales").execute();
                Uri uri =
                        new Uri.Builder()
                                .scheme("upi")
                                .authority("pay")
                                .appendQueryParameter("pa", "ipsita8808@oksbi")
                                .appendQueryParameter("pn", "Test Merchant")
                                .appendQueryParameter("mc", "1234")
                                .appendQueryParameter("tr", "123456789")
                                .appendQueryParameter("tn", "test transaction note")
                                .appendQueryParameter("am", "1.00")
                                .appendQueryParameter("cu", "INR")
//                                .appendQueryParameter("url", "https://test.merchant.website")
                                .build();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                startActivityForResult(intent, TEZ_REQUEST_CODE);
            }


        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Order_Now.this,Detail_Product_View.class);
                startActivity(i);
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));
        }
    }
    private void makePayment(String amount, String upiid, String name, String desc, String transactionid) throws Exception {
        EasyUpiPayment.Builder builder=new EasyUpiPayment.Builder(this)
                .setPayeeName("ipsita mishra").setDescription("Food")
                .setPayeeVpa("ipsita8808@oksbi").setAmount("1.00").setTransactionId("1000")
                .setTransactionRefId("124234242525").setPayeeMerchantCode("12345");

        EasyUpiPayment upi=builder.build();
        upi.startPayment();
    }

    @Override
    public void onTransactionCancelled() {
        Toast.makeText(this, "Transaction cancelled..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
        Toast.makeText(this, "Transaction successfully completed..", Toast.LENGTH_SHORT).show();
    }
}
