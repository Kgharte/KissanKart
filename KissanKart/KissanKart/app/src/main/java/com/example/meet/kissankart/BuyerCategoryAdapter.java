package com.example.meet.kissankart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by Meet on 19-12-2017.
 */

public class BuyerCategoryAdapter extends BaseAdapter

{
    private Context ctx;
    private ArrayList<buyercategory> CategoryList;
    LayoutInflater inflater ;
    ImageLoader loader;


    public BuyerCategoryAdapter(Context ctx,ArrayList<buyercategory> CategoryList)
    {
        this.ctx = ctx;
        this.CategoryList =CategoryList;
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loader = AppController.getInstance().getImageLoader();
    }

    @Override
    public int getCount() {

        return CategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return CategoryList.get(position);
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
            CurrentGridView = inflater.inflate(R.layout.buyer_category_row,null);
            holder.imgcategory = (NetworkImageView) CurrentGridView.findViewById(R.id.imgcategory);
            holder.lblcategorybuyer = (TextView)CurrentGridView.findViewById(R.id.lblcategorybuyer);
            CurrentGridView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder)CurrentGridView.getTag();
        }
        final buyercategory currentcategory = CategoryList.get(position);


        holder.lblcategorybuyer.setText(currentcategory.getTitle());
        String ImageUrl = Common.GetBaseImageUrl() + "category/" + currentcategory.getImage();
        holder.imgcategory.setImageUrl(ImageUrl,loader);
        return CurrentGridView;
    }
    class MyViewHolder
    {
        public NetworkImageView imgcategory;
        public TextView lblcategorybuyer;
    }
    }

