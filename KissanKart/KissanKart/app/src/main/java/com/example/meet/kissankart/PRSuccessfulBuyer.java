package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PRSuccessfulBuyer extends AppCompatActivity {
    ImageButton btnnewloginbuyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prsuccessful_buyer);
        allocate();
        events();
    }

    private void events()
    {
        btnnewloginbuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PRSuccessfulBuyer.this, BuyerLogin.class);
                startActivity(intent);
            }
        });
    }

    private void allocate()
    {
       btnnewloginbuyer = (ImageButton) findViewById(R.id.btnnewloginbuyer);
    }

}
