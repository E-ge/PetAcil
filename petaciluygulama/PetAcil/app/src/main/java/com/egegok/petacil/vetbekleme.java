package com.egegok.petacil;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class vetbekleme extends AppCompatActivity {
    AnimationDrawable animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetbekleme);
        ConstraintLayout constraintLayout=findViewById(R.id.layout);
        AnimationDrawable animationDrawable= (AnimationDrawable) constraintLayout.getBackground();

        new CountDownTimer(11000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                animationDrawable.start();
            }

            @Override
            public void onFinish() {
                animationDrawable.stop();
                Intent intent = new Intent(vetbekleme.this,MapsActivity.class);
                startActivity(intent);

            }
        }.start();

    }







}
