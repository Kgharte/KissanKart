package com.example.meet.kissankart;

import android.content.Context;
import android.os.Trace;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderHistoryContainer extends AppCompatActivity {
    String RegisterId;
    RecyclerView orderlist;
    DataStorage storage;
    private Context ctx=this;
    ArrayList<Order> OrderList = new ArrayList<Order>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_container);
        orderlist = (RecyclerView) findViewById(R.id.orderlist);
        RecyclerView.LayoutManager MyLayoutManager = new LinearLayoutManager(ctx);
        orderlist.setLayoutManager(MyLayoutManager);
        orderlist.setItemAnimator(new DefaultItemAnimator());
        allocatememory();
        RegisterId = (String) storage.read("id",DataStorage.STRING).toString();
        String WebServiceUrl = Common.GetBaseServiceUrl() + "orderhistory.php?registerid=" + RegisterId;
        StringRequest request = new StringRequest(StringRequest.Method.GET, WebServiceUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.d("trace",response);
                try
                {
                    JSONArray array = new JSONArray(response);
                    String Error = array.getJSONObject(0).getString("error");
                    if(Error.equals("no")==false)
                    {
                        Toast.makeText(ctx,Error,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        int count = array.getJSONObject(1).getInt("count");
                        if(count==0)
                        {
                            Toast.makeText(ctx,"No any order placed yet!",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            int size = array.length();
                            for(int i=2;i<size;i++)
                            {
                                Log.d("trace","success");
                                JSONObject object =array.getJSONObject(i);
                                Order o = new Order(Integer.parseInt(object.getString("id")),
                                        Integer.parseInt(object.getString("amount")),
                                        object.getString("orderdate"),
                                        object.getString("fullname"));


                                OrderList.add(o);
                            }
                            OrderHistoryAdapter adapter = new OrderHistoryAdapter(ctx,OrderList);
                            orderlist.setAdapter(adapter);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(request);

    }

    private void allocatememory() {

        orderlist = (RecyclerView) findViewById(R.id.orderlist);
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
    }
}
