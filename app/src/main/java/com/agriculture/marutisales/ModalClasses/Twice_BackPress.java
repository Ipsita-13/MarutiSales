package com.agriculture.marutisales.ModalClasses;

import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Twice_BackPress extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed(); // Close the activity
        } else {
            doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press back again to go back", Toast.LENGTH_SHORT).show();
            new android.os.Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false; // Reset the flag after a delay
                }
            }, 2000);
        }
    }
}
