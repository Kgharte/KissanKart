package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductContainer extends AppCompatActivity {

    int categoryid;
    private Context ctx = this;
    ArrayList<Product> ProductList = new ArrayList<Product>();
    GridView gridproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_container);
        gridproduct = (GridView) findViewById(R.id.gridproduct);
        categoryid = this.getIntent().getExtras().getInt("categoryid");
        String WebServiceUrl = Common.GetBaseServiceUrl() + "product.php?categoryid=" + categoryid;
        JsonArrayRequest request = new JsonArrayRequest(WebServiceUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response)
            {
                try
                {
                    String error = response.getJSONObject(0).getString("error");
                    if(error.equals("no")==false)
                    {
                        Toast.makeText(ctx,error,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        int total = response.getJSONObject(1).getInt("total");
                        if(total==0)
                        {
                            Toast.makeText(ctx,"no product found",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            int size = response.length();
                            for(int i=2;i<size;i++)
                            {
                                JSONObject CurrentProduct = response.getJSONObject(i);
                                   Product p1 = new Product();/*Integer.parseInt(CurrentProduct.getString("id")),
                                            Float.parseFloat(CurrentProduct.getString("price")),
                                            Integer.parseInt(CurrentProduct.getString("quantity")),
                                            CurrentProduct.getString("weight"),
                                            Boolean.parseBoolean(CurrentProduct.getString("status")),*/
                                            p1.setId(CurrentProduct.getInt("id"));
                                        p1.setTitle(CurrentProduct.getString("title"));
                                            p1.setImage(CurrentProduct.getString("image"));
                                ProductList.add(p1);
                            }
                            ProductAdapter adapter = new ProductAdapter(ctx,ProductList);
                            gridproduct.setAdapter(adapter);
                        }
                    }
                } catch (JSONException e)
                {
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

}
