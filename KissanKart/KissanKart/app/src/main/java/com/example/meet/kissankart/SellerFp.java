package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SellerFp extends AppCompatActivity {
    ImageButton btnsendnowseller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_fw);
        allocate();
        events();
    }

    private void events() {
        btnsendnowseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(SellerFp.this, SellerResetPw.class);
                startActivity(myintent);
            }
        });
    }

    private void allocate() {
        btnsendnowseller = (ImageButton) findViewById(R.id.btnsendnowseller);
    }
}
