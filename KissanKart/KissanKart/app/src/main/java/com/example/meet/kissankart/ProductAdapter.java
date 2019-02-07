package com.example.meet.kissankart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by KALPESH GHARTE on 12/20/2017.
 */

class ProductAdapter extends BaseAdapter {

    private Context ctx;
    ArrayList<Product> ProductList = new ArrayList<Product>();
    LayoutInflater inflater ;
    ImageLoader loader;
    public ProductAdapter(Context ctx,ArrayList<Product> ProductList)
    {
        this.ctx = ctx;
        this.ProductList =ProductList;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loader = AppController.getInstance().getImageLoader();
    }
    public int getCount() {
        return ProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return ProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View CurrentGridView;
        CurrentGridView = view;
        MyViewHolder holder;
        if(CurrentGridView==null)
        {
            holder = new MyViewHolder();
            CurrentGridView = inflater.inflate(R.layout.buyer_product_row,null);
            holder.imgproduct = (NetworkImageView) CurrentGridView.findViewById(R.id.imgcartphoto);
            holder.lblproductbuyer = (TextView)CurrentGridView.findViewById(R.id.lblproductbuyer);
            CurrentGridView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder)CurrentGridView.getTag();
        }
        final Product currentproduct = ProductList.get(position);


        holder.lblproductbuyer.setText(currentproduct.getTitle());
        String ImageUrl = Common.GetBaseImageUrl() + "product/" + currentproduct.getImage();
        holder.imgproduct.setImageUrl(ImageUrl,loader);
        holder.imgproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int id = currentproduct.getId();
                Intent SwitchIntent = new Intent(ctx,ProductDetailActivity.class);
                Log.d("trace id", ""+id);
                SwitchIntent.putExtra("id",id);
                ctx.startActivity(SwitchIntent);
            }
        });
        return CurrentGridView;
    }
    class MyViewHolder
    {
        public NetworkImageView imgproduct;
        public TextView lblproductbuyer;
    }
}
