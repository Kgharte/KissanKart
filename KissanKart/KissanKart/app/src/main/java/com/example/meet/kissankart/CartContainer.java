package com.example.meet.kissankart;

import android.support.v7.app.AlertDialog;

        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.GridView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

public class CartContainer extends AppCompatActivity {

    RecyclerView clist;
    DataStorage storage;
    private Context ctx=this;
    String RegisterId;
    Button btnconfirm;
    TextView lbltotal;
    int carttotal;
    CartAdapter adapter;
    ArrayList<Cart> CartList = new ArrayList<Cart>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_container);
        clist = (RecyclerView) findViewById(R.id.clist);

        RecyclerView.LayoutManager MyLayoutManager = new LinearLayoutManager(ctx);
        clist.setLayoutManager(MyLayoutManager);
        clist.setItemAnimator(new DefaultItemAnimator());
        allocatememory();
        setevents();
        RegisterId = (String) storage.read("id",DataStorage.STRING);
        String WebServiceUrl = Common.GetBaseServiceUrl() + "cart.php?registerid=" + RegisterId;
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
                            Toast.makeText(ctx,"No Item, in Cart",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            int size = array.length();
                            for(int i=2;i<size;i++)
                            {
                                JSONObject object =array.getJSONObject(i);
                                Cart c = new Cart(Integer.parseInt(object.getString("price")),
                                        Integer.parseInt(object.getString("quantity")),
                                        object.getString("title"),object.getString("image"),
                                        object.getString("id"),object.getString("productid"));
                                carttotal += (Integer.parseInt(object.getString("price")) * Integer.parseInt(object.getString("quantity")));
                                CartList.add(c);
                            }
                            lbltotal.setText("total amount = " + carttotal);
                            adapter = new CartAdapter(ctx,CartList);
                            clist.setAdapter(adapter);
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

    private void setevents()
    {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mybuilder = new AlertDialog.Builder(ctx);
                mybuilder.setTitle("Select payment mode");
                String[] PaymentModes =   {"Cash on Delivery","Online Payment","Pay using Bitcoin"};
                mybuilder.setItems(PaymentModes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if(which!=0)
                            Toast.makeText(ctx,"this method is not available yet",Toast.LENGTH_LONG).show();
                        else
                        {
                            Intent SwitchIntent = new Intent(ctx,CourierAddressActivity.class);
                            ctx.startActivity(SwitchIntent);
                        }
                    }
                });

                mybuilder.create().show();
            }
        });
    }

    private void allocatememory()
    {
        clist = (RecyclerView) findViewById(R.id.clist);
        btnconfirm = (Button) findViewById(R.id.btnconfirm);
        lbltotal = (TextView) findViewById(R.id.lbltotal);
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
    }
}

