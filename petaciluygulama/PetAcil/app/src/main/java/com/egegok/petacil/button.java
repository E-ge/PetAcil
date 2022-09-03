package com.egegok.petacil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class button extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.button);

        button = findViewById (R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(button.this,"FOTOĞRAF ÇEKİNİZ", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(button.this,fotodurum.class);
                startActivity(intent);
            }
        });
    }
}