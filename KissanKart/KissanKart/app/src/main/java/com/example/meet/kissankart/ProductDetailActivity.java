package com.example.meet.kissankart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {

    int productid,productid1;
    TextView lbltitle2,lblprice2,lblleft2,lbldescription,lblstatus2,lblweight2;
    NetworkImageView imgproduct2;
    Button btnaddtocart;
    Context ctx;
    DataStorage storage;
    Product p1 = new Product();
    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        allocatememory();
        setevents();
        productid = getIntent().getExtras().getInt("id");
        Log.d("trace productid", String.valueOf(productid));
        String WebserviceUrl = Common.GetBaseServiceUrl() + "product-detail.php?id=" + productid;

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
                            JSONObject product = response.getJSONObject(2);

                            lbltitle2.setText(product.getString("title"));
                            lbldescription.setText("Description: \n" + product.getString("description"));
                            lblleft2.setText("Quantity: " + product.getString("quantity"));
                            lblprice2.setText("Price: " + product.getString("price"));
                            lblweight2.setText("Weight: " + product.getString("weight"));
                            if(product.getString("status").equals("1")==true) {
                                lblstatus2.setTextColor(Color.GREEN);
                                lblstatus2.setText("Stock availability: " + "Available");
                            }
                            else
                            {
                                lblstatus2.setTextColor(Color.RED);
                                lblstatus2.setText("Stock availability: " + "Not Available");

                            }
                            p1.setId(product.getInt("id"));
                            p1.setTitle(product.getString("title"));
                            p1.setPrice(Integer.parseInt(product.getString("price")));
                            p1.setImage(product.getString("image"));
                            p1.setWeight(product.getString("weight"));

                            String ImageUrl = Common.GetBaseImageUrl() + "product/" + product.getString("image");
                            ImageLoader loader = AppController.getInstance().getImageLoader();
                            imgproduct2.setImageUrl(ImageUrl,loader);

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
        btnaddtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                int SelectedProductId = p1.getId();
                int RegisterUserId = 1;
                Log.d("trace id",""+productid);
               // Toast.makeText(ctx,"Selected Product Id " + SelectedProductId,Toast.LENGTH_LONG).show();
                String WebServiceUrl = Common.GetBaseServiceUrl() + "addtocart.php?registerid="
                        + storage.read("id",DataStorage.STRING)
                        + "&productid=" + p1.getId() + "&price=" + p1.getPrice() + "&title=" + p1.getTitle() + "&image=" + p1.getImage();
                Log.d("trace",WebServiceUrl);
                Log.d("trace",""+lblprice2);
                StringRequest request = new StringRequest(StringRequest.Method.GET, WebServiceUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response)
                            {
                                Log.d("trace",response);
                                try
                                {
                                    JSONArray array = new JSONArray(response);
                                    String error = array.getJSONObject(0).getString("error");
                                    if(error.equals("no")==false)
                                    {
                                        Toast.makeText(ctx,error,Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        String msg = array.getJSONObject(1).getString("msg");
                                        Toast.makeText(ctx,msg,Toast.LENGTH_LONG).show();
                                        Toast.makeText(ctx,p1.getTitle(),Toast.LENGTH_LONG).show();


                                    }

                                }
                                catch (JSONException e) {
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
        });
    }


    private void allocatememory()
    {
        ctx = this;
        lbltitle2 = (TextView) findViewById(R.id.lbltitle2);
        lblprice2 = (TextView) findViewById(R.id.lblprice2);
        lblleft2 = (TextView) findViewById(R.id.lblleft2);
        lblstatus2 = (TextView) findViewById(R.id.lblstatus2);
        lblweight2 = (TextView) findViewById(R.id.lblweight2);
        imgproduct2 = (NetworkImageView) findViewById(R.id.imgproduct2);
        lbldescription = (TextView)findViewById(R.id.lbldescription);
        btnaddtocart = (Button)findViewById(R.id.btnaddtocart);
        storage = new DataStorage(getResources().getString(R.string.sharedprefname),ctx);
    }}
