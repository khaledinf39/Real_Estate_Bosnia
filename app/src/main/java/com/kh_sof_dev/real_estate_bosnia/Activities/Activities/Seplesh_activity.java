package com.kh_sof_dev.real_estate_bosnia.Activities.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.kh_sof_dev.real_estate_bosnia.R;


public class Seplesh_activity extends AppCompatActivity {

    private static final long SPLASH_TIME = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seplesh_activity);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth=FirebaseAuth.getInstance();

                    Intent intent = new Intent(Seplesh_activity.this, MainActivity.class);
                    startActivity(intent );
                    finish();


            }
        }, SPLASH_TIME);
    }
}
