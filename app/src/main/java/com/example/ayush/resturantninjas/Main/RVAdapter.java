package com.example.ayush.resturantninjas.Main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ayush.resturantninjas.R;

import java.util.List;

/**
 * Created by Ayush on 29-12-2016.
 */

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    private static ClickListner clickListner;
    public static Context context;
    List<Resturant> resturants;

    RVAdapter(Context context,List<Resturant> resturants){
        this.context=context;
        this.resturants = resturants;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView resturantname;
        TextView resturantdistance;
        ImageView resturantPhoto;

        PersonViewHolder(final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListner!=null)
                    {
                        clickListner.ItemClicked(v,getPosition());
                    }
                }
            });
            resturantname = (TextView)itemView.findViewById(R.id.name);
            resturantdistance = (TextView)itemView.findViewById(R.id.distance);
            resturantPhoto = (ImageView)itemView.findViewById(R.id.resturantphoto);
        }

    }



    @Override
    public RVAdapter.PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.resturant_ui, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;

    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.resturantname.setText(resturants.get(i).name);
        personViewHolder.resturantdistance.setText(String.valueOf(resturants.get(i).distance));
        if(i==1)
        personViewHolder.resturantPhoto.setImageResource(R.drawable.acttivites_main);
        if(i==0)
            personViewHolder.resturantPhoto.setImageResource(R.drawable.hotel_main);
        if(i==2)
            personViewHolder.resturantPhoto.setImageResource(R.drawable.hotel4);




    }
    @Override
    public int getItemCount() {
        return resturants.size();
    }

    public void setClickListner(ClickListner clickListner)
    {
        this.clickListner=clickListner;
    }

    public interface ClickListner
    {
        public void ItemClicked(View view , int position);
    }
}
