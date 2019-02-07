package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by KALPESH GHARTE on 4/3/2018.
 */

public class OrderHistoryAdapter extends RecyclerView.Adapter
{
    Context ctx;
    ArrayList<Order> OrderList;
    ImageLoader loader;
    DataStorage storage;

    public OrderHistoryAdapter(Context ctx, ArrayList<Order> orderList) {

        this.ctx = ctx;
        OrderList = orderList;
        storage = new DataStorage(ctx.getResources().getString(R.string.sharedprefname),ctx);
        loader = AppController.getInstance().getImageLoader();
        Log.d("trace","constructor called");

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("trace","onCreateViewHolder called");
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View MyView = inflater.inflate(R.layout.order_row,null);
        OrderHistoryAdapter.MyViewHolder holder = new OrderHistoryAdapter.MyViewHolder(MyView);
        holder.orderid = (TextView) MyView.findViewById(R.id.orderid);
        holder.orderdate = (TextView) MyView.findViewById(R.id.orderdate);
        holder.orderamount = (TextView) MyView.findViewById(R.id.orderamount);
        holder.fullname = (TextView) MyView.findViewById(R.id.fullname);
        holder.oredercancel = (ImageView) MyView.findViewById(R.id.ordercancel);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Log.d("trace","onBindViewHolder called position = " + position);
        final Order currentorder = OrderList.get(position);

        OrderHistoryAdapter.MyViewHolder MyHolder =(OrderHistoryAdapter.MyViewHolder) holder;
        MyHolder.orderid.setText("Order Id : " + OrderList.get(position).getId() + "");
        MyHolder.orderdate.setText("Order placed date: " + OrderList.get(position).getOrderdate() + "");
        MyHolder.orderamount.setText("Order amount: Rs" + OrderList.get(position).getAmount() + "");
        MyHolder.fullname.setText("Deliver to: " + OrderList.get(position).getFullname() + "");

        MyHolder.orderid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = currentorder.getId();
                Intent SwitchIntent = new Intent(ctx,OrderHistoryContainerReturns.class);
                Log.d("trace id", ""+id);
                SwitchIntent.putExtra("orderid",id);
                ctx.startActivity(SwitchIntent);
            }
        });

        MyHolder.oredercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String WebServiceUrl = Common.GetBaseServiceUrl() + "removeorder.php?id=" + OrderList.get(position).getId();
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
                                Toast.makeText(ctx,"order removed",Toast.LENGTH_LONG).show();
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
        return OrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView orderid,orderdate,orderamount,fullname;
        public ImageView oredercancel;
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
