package com.agriculture.marutisales.ModalClasses;

import androidx.appcompat.app.AppCompatActivity;

public class Base_Class extends AppCompatActivity {
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            // Close the application
            finishAffinity();
            System.exit(0);
        } else {
            // Go to previous activity
            super.onBackPressed();
        }
    }

    protected void setDoubleBackToExitPressedOnce(boolean doubleBackToExitPressedOnce) {
        this.doubleBackToExitPressedOnce = doubleBackToExitPressedOnce;
    }
}
