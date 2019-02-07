package com.example.meet.kissankart;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
;

public class BuyerSeller extends AppCompatActivity {
    ImageView buyerlogo,sellerlogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_seller);
        allocate();
        events();

    }

    private void events() {
       sellerlogo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent myintent =new Intent(BuyerSeller.this, CategoryContainer.class);
               startActivity(myintent);
           }
       });
        buyerlogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(BuyerSeller.this,BuyerLogin.class);
                startActivity(myintent);
            }
        });
    }

    private void allocate() {
        buyerlogo = (ImageView) findViewById(R.id.buyerlogo);
        sellerlogo = (ImageView) findViewById(R.id.sellerlogo);
    }


}

