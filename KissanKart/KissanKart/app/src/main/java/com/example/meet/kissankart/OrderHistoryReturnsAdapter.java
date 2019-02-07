package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class OrderHistoryReturnsAdapter extends RecyclerView.Adapter
{

    Context ctx;
    ArrayList<ShowOrder> OrderList2;
    ImageLoader loader;
    DataStorage storage;

    public OrderHistoryReturnsAdapter(Context ctx, ArrayList<ShowOrder> orderList2) {
        this.ctx = ctx;
        OrderList2 = orderList2;
        storage = new DataStorage(ctx.getResources().getString(R.string.sharedprefname),ctx);
        loader = AppController.getInstance().getImageLoader();
        Log.d("trace","constructor called");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("trace","onCreateViewHolder called");
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View MyView = inflater.inflate(R.layout.activity_show_order_returns,null);
        OrderHistoryReturnsAdapter.MyViewHolder holder = new OrderHistoryReturnsAdapter.MyViewHolder(MyView);
        holder.imgproduct4 = (NetworkImageView) MyView.findViewById(R.id.imgproduct4);
        holder.productid = (TextView) MyView.findViewById(R.id.productid);
        holder.producttitle = (TextView) MyView.findViewById(R.id.producttitle);
        holder.productprice = (TextView) MyView.findViewById(R.id.productprice);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("trace","onBindViewHolder called position = " + position);

        OrderHistoryReturnsAdapter.MyViewHolder MyHolder =(OrderHistoryReturnsAdapter.MyViewHolder) holder;

        final ShowOrder currentorder2 = OrderList2.get(position);

        MyHolder.productid.setText("Product Id: " + OrderList2.get(position).getId() + "");
        MyHolder.producttitle.setText(OrderList2.get(position).getTitle() + "");
        MyHolder.productprice.setText("Price: Rs " + OrderList2.get(position).getPrice() + "");

        String ImageUrl = Common.GetBaseImageUrl() + "product/" +OrderList2.get(position).getImage();
        MyHolder.imgproduct4.setImageUrl(ImageUrl,loader);
        MyHolder.imgproduct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = currentorder2.getId();
                Intent SwitchIntent = new Intent(ctx,ProductDetailActivity.class);
                Log.d("trace id", ""+id);
                SwitchIntent.putExtra("id",id);
                ctx.startActivity(SwitchIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return OrderList2.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public NetworkImageView imgproduct4;
        public  TextView productid,producttitle,productprice;
        public MyViewHolder(View myView) {
            super(myView);
        }
    }
}