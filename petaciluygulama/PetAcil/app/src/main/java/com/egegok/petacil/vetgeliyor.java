package com.egegok.petacil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class vetgeliyor extends AppCompatActivity {
    EditText iletisim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetgeliyor);
        iletisim=findViewById(R.id.iletisim);
    }
    public void onBackPressed() {
    }
    public void iletisimbtn(View view){
        iletisim.setVisibility(View.VISIBLE);
    }
    public void cikis(View view){
       // Intent intent = new Intent(vetgeliyor.this,button.class);
       // startActivity(intent);
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }
}