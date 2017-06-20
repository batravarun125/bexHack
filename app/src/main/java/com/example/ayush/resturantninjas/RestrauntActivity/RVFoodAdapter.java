package com.example.ayush.resturantninjas.RestrauntActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ayush.resturantninjas.Main.Order;
import com.example.ayush.resturantninjas.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayush on 29-12-2016.
 */

public class RVFoodAdapter extends RecyclerView.Adapter<RVFoodAdapter.FoodViewHolder>{

        List<FoodItem> fooditem;
        public static Context context;
         private static RVFoodAdapter.ClickListner clickListner;
        RVFoodAdapter(Context context, List<FoodItem> fooditem)
        {
            this.context=context;
            this. fooditem= fooditem;
        }
    public static class FoodViewHolder extends RecyclerView.ViewHolder{
        TextView productname;
        TextView rate;
        TextView ratemoney;
        ImageView productImage;
        ImageView addtoCart;

        FoodViewHolder(final View itemView) {
            super(itemView);
            productname=(TextView)itemView.findViewById(R.id.ProductName);
            rate=(TextView)itemView.findViewById(R.id.Rate);
            ratemoney=(TextView)itemView.findViewById(R.id.RateMoeny);
            productImage=(ImageView)itemView.findViewById(R.id.ProductImage);
            addtoCart=(ImageView)itemView.findViewById(R.id.AddToCart);
            addtoCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListner!=null)
                    {
                        clickListner.ItemClicked(v,getPosition());
                    }
                }
            });

        }

    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_menu, viewGroup, false);
        RVFoodAdapter.FoodViewHolder pvh = new FoodViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int i) {
        holder.productname.setText(fooditem.get(i).foodname);
        holder.ratemoney.setText(String.valueOf(fooditem.get(i).Price));
        switch (i)
        {
            case 0: holder.productImage.setImageResource(R.drawable.skipline);
                break;
            case 1: holder.productImage.setImageResource(R.drawable.privatetour);
                break;
            case 2: holder.productImage.setImageResource(R.drawable.opera);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return fooditem.size();
    }
    public void setClickListner(RVFoodAdapter.ClickListner clickListner)
    {
        this.clickListner=clickListner;
    }

    public interface ClickListner
    {
        public void ItemClicked(View view , int position);
    }


    public static class BurgerKing extends AppCompatActivity implements ClickListner {
        public List<FoodItem> fooditem;
        Context context;
        DatabaseHandler db=new DatabaseHandler(this);
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_click);
            context=this;

            Intent intent=getIntent();
            int position=intent.getIntExtra("Position",-1);

            Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
            GridLayoutManager llm=new GridLayoutManager(this,2);
            rv.setLayoutManager(llm);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<Order> order = db.getAllOrder();
                    for (Order cn : order) {
                        String log = "Id: "+cn.foodname+" ,Name: " + cn.stallname + " ,Phone: " +
                                cn.qty;
                        Log.d("Name: ", log);
                    }

                }
            });
            fooditem=new ArrayList<>();
            fooditem.add(new FoodItem("Skip Line", 1000, "lalalala"));
            fooditem.add(new FoodItem("Private Tour", 2000, "lalalala"));
            fooditem.add(new FoodItem("Opera", 3000, "lalalala"));
            RVFoodAdapter adapter=new RVFoodAdapter(this,fooditem);
            adapter.setClickListner(this);
            rv.setAdapter(adapter);
        }

        @Override
        public void ItemClicked(View view, int position) {

            switch (position)
            {
                case 0:
                    db.addOrder(new Order("Hotel Trident", "Skip Line", 1, 1000));
                    break;
                case 1:
                    db.addOrder(new Order("Hotel Trident", "Private Tour", 1, 2000));
                    break;
                case 2:
                    db.addOrder(new Order("Hotel Trident", "Opera", 1, 3000));
                    break;

            }
            Snackbar.make(view, "Your Order is Added ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();


        }
    }
}
