package com.example.meet.kissankart;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

import java.util.ArrayList;


public class CartAdapter extends RecyclerView.Adapter {
    Context ctx;
    ArrayList<Cart> CartList;
    ImageLoader loader;
    DataStorage storage;
    Intent SwitchIntent;
    public CartAdapter(Context ctx, ArrayList<Cart> CartList)
    {
        this.ctx = ctx;
        this.CartList = CartList;
        storage = new DataStorage(ctx.getResources().getString(R.string.sharedprefname),ctx);
        loader = AppController.getInstance().getImageLoader();
        Log.d("trace","constructor called");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("trace","onCreateViewHolder called");

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View MyView = inflater.inflate(R.layout.cart_row,null);
        CartAdapter.MyViewHolder holder = new CartAdapter.MyViewHolder(MyView);
        holder.lblcarttitle = (TextView) MyView.findViewById(R.id.lblcarttitle);
        holder.lblcartprice = (TextView) MyView.findViewById(R.id.lblcartprice);
        holder.btnremove = (ImageView)MyView.findViewById(R.id.btnremove);
        holder.lblcartquantity = (TextView) MyView.findViewById(R.id.lblcartquantity);
        holder.imgcartphoto = (NetworkImageView) MyView.findViewById(R.id.imgcartphoto);
        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        Log.d("trace","onBindViewHolder called position = " + position);

        MyViewHolder MyHolder = (MyViewHolder) holder;
        MyHolder.lblcarttitle.setText(CartList.get(position).getTitle());
        MyHolder.lblcartprice.setText("Price: " + CartList.get(position).getPrice() + "");
        MyHolder.lblcartquantity.setText("Quantity: " + CartList.get(position).getQuantity() + "");

        String ImageUrl = Common.GetBaseImageUrl() + "product/" +CartList.get(position).getImage();
        MyHolder.imgcartphoto.setImageUrl(ImageUrl,loader);
        MyHolder.btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String WebServiceUrl = Common.GetBaseServiceUrl() + "removefromcart.php?cartid=" + CartList.get(position).getId();
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
                                Toast.makeText(ctx,"Product removed form cart",Toast.LENGTH_LONG).show();


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
        });
    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }
    class MyViewHolder extends  RecyclerView.ViewHolder
    {
        public TextView lblcarttitle,lblcartprice,lblcartquantity;
        public NetworkImageView imgcartphoto;
        public ImageView btnremove;

        public MyViewHolder(View itemView)
        {
            super(itemView);
        }
    }
}