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

public class BuyerLogin extends AppCompatActivity {
    Button buyerloginbtn;
    TextView buyerfp,buyergotosu;
    EditText buyeremail,buyerpassword;
    Context ctx=this;
    Intent SwitchIntent;
    DataStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_login);
        allocate();
        events();
        CheckLoginOrNot();
    }

    private void events() {
        buyerloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = buyeremail.getText().toString();
                password = buyerpassword.getText().toString();
                String WebServiceUrl = Common.GetBaseServiceUrl() + "login.php";
                StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("trace", response.toString());
                        try {
                            JSONArray array = new JSONArray(response);
                            String error = array.getJSONObject(0).getString("error");
                            if (error.equals("no") == false) {
                                Toast.makeText(ctx, error, Toast.LENGTH_LONG).show();
                            } else {
                                //sucessfull login
                                storage.write("islogin", true); //boolean version
                                storage.write("login", "yes"); //string version
                                String id = array.getJSONObject(1).getString("id");
                                Log.d("trace", "id= " + id);
                                storage.write("id", array.getJSONObject(1).getString("id"));
                                SwitchIntent = new Intent(ctx, CategoryContainer.class);
                                startActivity(SwitchIntent);
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
                        KeyValuePair.put("email", buyeremail.getText().toString());
                        KeyValuePair.put("password", buyerpassword.getText().toString());

                        return KeyValuePair;
                    }
                };

                AppController.getInstance().addToRequestQueue(request);
            }

        });
        buyerfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent =new Intent(ctx, BuyerFp.class);
                startActivity(myintent);
            }
        });
        buyergotosu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent SwitchIntent = new  Intent(ctx,BuyerRegistration.class);
                startActivity(SwitchIntent);
            }
        });
    }

    private void CheckLoginOrNot()
    {
        String login = storage.read("login",DataStorage.STRING).toString();
        if(login.equals("yes")==true)
        {
            SwitchIntent =new Intent(ctx,CategoryContainer.class);
            startActivity(SwitchIntent);
            finish();
        }
    }




    private void allocate() {
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
        buyerloginbtn = (Button) findViewById(R.id.buyerloginbtn);
        buyerfp = (TextView) findViewById(R.id.buyerfp);
        buyergotosu = (TextView) findViewById(R.id.buyergotosu);
        buyeremail = (EditText) findViewById(R.id.buyeremail);
        buyerpassword = (EditText) findViewById(R.id.buyerpassword);

    }
}
