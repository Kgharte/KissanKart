package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BuyerResetPs extends AppCompatActivity {
    ImageButton buyerresetps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_reset_ps);
        allocate();
        events();
    }

    private void events() {
        buyerresetps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyerResetPs.this, PRSuccessfulBuyer.class);
                startActivity(intent);
            }
        });
    }

    private void allocate() {
        buyerresetps = (ImageButton) findViewById(R.id.buyerresetps);
    }
}
