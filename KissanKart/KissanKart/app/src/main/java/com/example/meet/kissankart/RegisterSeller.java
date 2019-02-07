package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RegisterSeller extends AppCompatActivity {
    ImageButton btnregisterseller;
    TextView lblalreadyregseller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_seller);
        allocate();
        events();
    }

    private void events() {
        btnregisterseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(RegisterSeller.this, HomeActivity.class);
                startActivity(myintent);
            }
        });

        lblalreadyregseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(RegisterSeller.this, BuyerLogin.class);
                startActivity(myintent);
            }
        });
    }

    private void allocate() {

        btnregisterseller = (ImageButton) findViewById(R.id.btnregisterseller);
        lblalreadyregseller = (TextView) findViewById(R.id.lblalreadyregseller);

    }
}
