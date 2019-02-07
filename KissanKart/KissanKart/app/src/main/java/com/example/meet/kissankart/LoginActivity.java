package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    ImageButton sellerloginbtn;
    TextView sellerfp,sellergotosu;
    Intent myintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        allocate();
        events();
    }

    private void events() {
        sellerloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(myintent);
            }
        });
        sellerfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(LoginActivity.this, SellerFp.class);
                startActivity(myintent);
            }
        });
        sellergotosu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(LoginActivity.this, RegisterSeller.class);
                startActivity(myintent);
            }
        });
    }

    private void allocate() {
        sellerloginbtn = (ImageButton) findViewById(R.id.sellerloginbtn);
        sellergotosu = (TextView) findViewById(R.id.sellergotosu);
        sellerfp = (TextView) findViewById(R.id.sellerfp);
    }
}
