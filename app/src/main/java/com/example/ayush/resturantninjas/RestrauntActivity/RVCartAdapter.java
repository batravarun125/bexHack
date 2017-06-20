package com.example.ayush.resturantninjas.RestrauntActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ayush.resturantninjas.Main.Order;
import com.example.ayush.resturantninjas.R;

import java.util.List;

/**
 * Created by Ayush on 29-12-2016.
 */

public class RVCartAdapter extends RecyclerView.Adapter<RVCartAdapter.OrderViewHolder> {

    List<Order> orderList;
    public static Context context;
    RVCartAdapter(Context context, List<Order> orderlist)
    {
        this.context=context;
        this.orderList= orderlist;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ordercart, viewGroup, false);
        OrderViewHolder o=new OrderViewHolder(v);
        return o;

    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int i) {
        holder.name.setText(orderList.get(i).stallname);
        holder.foodname.setText(orderList.get(i).foodname);
        holder.quantity.setText(String.valueOf(orderList.get(i).qty));
        holder.oneprice.setText(String.valueOf(orderList.get(i).priceone));
        holder.totalprice.setText(String.valueOf(orderList.get(i).totalprice));
        if(orderList.get(i).stallname.equals("Hotel Leela"))
        {
            holder.resturantphoto.setImageResource(R.drawable.hotel_main);
        }
        if(orderList.get(i).stallname.equals("Skydiving"))
        {
            holder.resturantphoto.setImageResource(R.drawable.acttivites_main);
        }
        if(orderList.get(i).stallname.equals("Hotel Trident"))
        {
            holder.resturantphoto.setImageResource(R.drawable.hotel4);
        }

    }

    @Override
    public int getItemCount() {
       return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        TextView foodname;
        TextView quantity;
        TextView oneprice;
        TextView totalprice;
        ImageView resturantphoto;

         OrderViewHolder(View itemView) {
            super(itemView);
             name= (TextView) itemView.findViewById(R.id.name);
             foodname= (TextView) itemView.findViewById(R.id.distance);
             quantity=(TextView)itemView.findViewById(R.id.qty);
             oneprice= (TextView) itemView.findViewById(R.id.price);
             totalprice= (TextView) itemView.findViewById(R.id.TotalPrice);
             resturantphoto= (ImageView) itemView.findViewById(R.id.resturantphoto);

         }
    }

}
