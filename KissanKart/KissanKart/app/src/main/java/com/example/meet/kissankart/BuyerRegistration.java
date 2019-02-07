package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class BuyerRegistration extends AppCompatActivity {
    Button btnregisterbuyer;
    TextView lblalreadyregbuyer;
    Intent SwitchIntent; //declration
    Context ctx = this;
    DataStorage storage;
    EditText buyeremailregistration, buyermno, buyername, buyerpasswordregistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_registration);
        allocate();
        CheckRegiterOrNot();
        events();
    }

    private void events() {
        btnregisterbuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String WebServiceUrl = Common.GetBaseServiceUrl() + "register.php";
                StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trace", response);
                        try {
                            JSONArray array = new JSONArray(response);
                            String error = array.getJSONObject(0).getString("error");
                            if (error.equals("no") == false)
                            {
                                Toast.makeText(ctx, error, Toast.LENGTH_LONG).show();
                            }
                            else
                                {
                                SwitchIntent = new Intent(ctx/* source */, CategoryContainer.class /* destination */);
                                storage.write("register", "yes");
                                storage.write("id",array.getJSONObject(1).getInt("id"));
                                 //string version
                                BuyerRegistration.this.startActivity(SwitchIntent);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> KeyValuePair = new HashMap<String, String>();
                        //username=ankit2&password=123123&email=ankit33852@gmail.com&mobile=12345678902&regid=0
                        /* if(txtusername.getText().toString().trim().length()>0 &&
                                txtmobileno.getText().toString().trim().length()>=10)
                        {
                            //give your error message here
                        } */
                        KeyValuePair.put("name", buyername.getText().toString().trim());
                        KeyValuePair.put("password", buyerpasswordregistration.getText().toString().trim());
                        KeyValuePair.put("email", buyeremailregistration.getText().toString().trim());
                        KeyValuePair.put("mobileno", buyermno.getText().toString().trim());
                        KeyValuePair.put("regid", "1");

                        return KeyValuePair;
                    }
                };

                AppController.getInstance().addToRequestQueue(request);
            }
        });

        lblalreadyregbuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent =new Intent(BuyerRegistration.this, BuyerLogin.class);
                startActivity(myintent);
            }
        });
    }

    private void CheckRegiterOrNot()
    {
        String register = storage.read("register",DataStorage.STRING).toString();
        if(register.equals("yes")==true)
        {
            SwitchIntent = new Intent(ctx,CategoryContainer.class);
            startActivity(SwitchIntent);
            finish();
        }
    }

    private void allocate() {

        ctx = this;
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
        buyeremailregistration = (EditText)findViewById(R.id.buyeremailregistration);
        buyername = (EditText)findViewById(R.id.buyername);
        buyerpasswordregistration = (EditText)findViewById(R.id.buyerpasswordregistration);

        buyermno = (EditText)findViewById(R.id.buyermno);
        btnregisterbuyer = (Button) findViewById(R.id.btnregisterbuyer);
        lblalreadyregbuyer = (TextView) findViewById(R.id.lblalreadyregbuyer);
    }

}
