package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {

    String registerid;
    TextView username,useremail,usermobile,myorders;
    ImageView editpen;
    DataStorage storage;
    Context ctx;
    Register r1 = new Register();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        allocatememory();
        setevents();
        registerid = storage.read("id",DataStorage.STRING).toString();
        Log.d("trace register", String.valueOf(registerid));
        String WebserviceUrl = Common.GetBaseServiceUrl() + "profile.php?id=" + registerid;
        Log.d("trace url",""+WebserviceUrl);
//        Toast.makeText(ctx, storage.read("id",DataStorage.INTEGER).toString(),Toast.LENGTH_LONG).show();

        JsonArrayRequest request = new JsonArrayRequest(WebserviceUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                String Error = null;
                try
                {
                    Log.d("trace response",""+response);
                    Error = response.getJSONObject(0).getString("error");
                    if(Error.equals("no")==false)
                    {
                        Toast.makeText(ctx,Error,Toast.LENGTH_LONG).show();

                    }
                    else
                    {
                        int count = response.getJSONObject(1).getInt("count");
                        if(count==1)
                        {
                            JSONObject register = response.getJSONObject(2);
                            username.setId(register.getInt("id"));
                            username.setText(register.getString("name"));
                            useremail.setText("Email: " + register.getString("email"));
                            usermobile.setText("Mobile No: " + register.getString("mobileno"));

                            r1.setId(register.getInt("id"));
                            r1.setName(register.getString("name"));
                            r1.setEmail(register.getString("email"));
                            r1.setMobileno(register.getString("mobileno"));




                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("trace error",""+e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("trace error",""+error.toString());
            }
        });

        AppController.getInstance().addToRequestQueue(request);
    }

    private void setevents() {

        editpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchintent = new Intent(ctx,ChangePassword.class);
                startActivity(switchintent);
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfile.this,OrderHistoryContainer.class);
                startActivity(intent);

            }
        });
    }

    private void allocatememory() {

        ctx = this;
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
        username = (TextView) findViewById(R.id.username);
        useremail = (TextView) findViewById(R.id.useremail);
        usermobile = (TextView) findViewById(R.id.usermobile);
        editpen = (ImageView) findViewById(R.id.editpen);
        myorders = (TextView) findViewById(R.id.myorders);

    }
}
