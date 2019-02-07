package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class orderplaced extends AppCompatActivity {

    Button orderhis,contishop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderplaced);

        orderhis = (Button)findViewById(R.id.orderhis);
        contishop = (Button)findViewById(R.id.contishop);

        contishop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchintent = new Intent(orderplaced.this,CategoryContainer.class);
                startActivity(switchintent);
            }
        });

        orderhis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent si = new Intent(orderplaced.this,OrderHistoryContainer.class);
            startActivity(si);
            }
        });
    }
}
