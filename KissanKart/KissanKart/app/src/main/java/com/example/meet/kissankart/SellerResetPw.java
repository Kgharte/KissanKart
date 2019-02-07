package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class SellerResetPw extends AppCompatActivity {
    ImageButton btnresetpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_reset_pw);
        allocate();
        events();
    }

    private void events() {
        btnresetpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(SellerResetPw.this, PRSuccessfull.class);
                startActivity(myintent);
            }
        });
    }

    private void allocate() {
        btnresetpwd = (ImageButton) findViewById(R.id.buyerloginbtn);
    }
}
