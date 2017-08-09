package com.dev.sanskar.newscgpioneer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.saeid.fabloading.LoadingView;

public class FAB_Splash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab__splash);


        LoadingView yo = (LoadingView)findViewById(R.id.loading_view);
        yo.addAnimation(Color.parseColor("#ffd633"),R.drawable.pion20,LoadingView.FROM_BOTTOM);
        yo.addAnimation(Color.parseColor("#ffd633"),R.drawable.pion20,LoadingView.FROM_BOTTOM);
        yo.addAnimation(Color.parseColor("#ff9900"),R.drawable.pion20,LoadingView.FROM_BOTTOM);
        yo.addAnimation(Color.parseColor("#ffffff"),R.drawable.pion20,LoadingView.FROM_BOTTOM);
        yo.addAnimation(Color.parseColor("#00cc00"),R.drawable.pion20,LoadingView.FROM_BOTTOM);
        yo.addAnimation(Color.parseColor("#ffd633"), R.drawable.pion20, LoadingView.FROM_BOTTOM);

        yo.startAnimation();


        Thread soja = new Thread(){
            @Override
            public void run() {

                try{
                    Thread.sleep(3200);
                }catch(InterruptedException e){

                }
                finally {
                    Intent intentObject = new Intent(FAB_Splash.this,MainActivity.class);
                    startActivity(intentObject);
                    finish();
                }

            }
        };soja.start();

    }
}
