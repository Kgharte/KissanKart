package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class CourierAddressActivity extends AppCompatActivity {

    EditText txtfullname,txtaddress,txtcity,txtpincode;
    Button btnconfirm2;
    DataStorage storage;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_address);
        allocatememory();
        setevents();
    }

    private void setevents()
    {
        btnconfirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String WebServiceUrl = Common.GetBaseServiceUrl() + "payment.php";
                StringRequest request = new StringRequest(StringRequest.Method.POST, WebServiceUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("trace",response);
                        try
                        {
                            JSONArray array = new JSONArray(response);
                            String Error = array.getJSONObject(0).getString("error");
                            if(Error.equals("no")==false)
                                Toast.makeText(ctx,Error,Toast.LENGTH_SHORT).show();
                            else
                            {
                                String Message = array.getJSONObject(1).getString("msg");
                                Toast.makeText(ctx,Message,Toast.LENGTH_LONG).show();
                               Intent switchintent = new Intent(ctx,orderplaced.class);
                                startActivity(switchintent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError
                    {
                        HashMap<String,String> KeyValue = new HashMap<String, String>();
                        KeyValue.put("registerid",storage.read("id",DataStorage.STRING).toString());
                        KeyValue.put("fullname",txtfullname.getText().toString());
                        KeyValue.put("address",txtaddress.getText().toString());
                        KeyValue.put("pincode",txtpincode.getText().toString());
                        KeyValue.put("city",txtcity.getText().toString());
                        return KeyValue;
                    }
                };

                AppController.getInstance().addToRequestQueue(request);
            }
        });
    }

    private void allocatememory()
    {
        txtfullname = (EditText) findViewById(R.id.txtfullname);
        txtaddress = (EditText) findViewById(R.id.txtaddress);
        txtcity = (EditText) findViewById(R.id.txtcity);
        txtpincode = (EditText) findViewById(R.id.txtpincode);
        btnconfirm2 = (Button) findViewById(R.id.btnconfirm2);
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
    }
}
