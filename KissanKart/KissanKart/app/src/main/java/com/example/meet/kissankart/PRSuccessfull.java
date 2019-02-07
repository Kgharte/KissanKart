package com.example.meet.kissankart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PRSuccessfull extends AppCompatActivity {
    ImageButton btnnewlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prsuccessfull);
        allocate();
        events();
    }

    private void events() {

    btnnewlogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent myintent =new Intent(PRSuccessfull.this, LoginActivity.class);
            startActivity(myintent);
        }
    });
    }

    private void allocate()
    {
      btnnewlogin = (ImageButton) findViewById(R.id.btnnewlogin);
    }
}
