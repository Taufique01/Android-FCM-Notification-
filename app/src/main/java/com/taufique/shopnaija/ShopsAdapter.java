package com.taufique.shopnaija;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taufique.shopnaija.R;

import java.util.List;

public class ShopsAdapter extends  RecyclerView.Adapter<ShopsAdapter.MyViewHolder> {

    private List<Shops> shopsList;

    Context mContext;
    CustomItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descrip;
        public ImageView dp_imageView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title_textView);
            descrip = (TextView) view.findViewById(R.id.des_textView);
            dp_imageView=(ImageView) view.findViewById(R.id.dp_imageView);

        }
    }

    public ShopsAdapter(List<Shops> shopsList) {
        this.shopsList = shopsList;

    }
    public void setCustomItemClickListener(CustomItemClickListener listener){



        this.listener=listener;
    }

    @NonNull
    @Override
    public ShopsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shop_list_row, parent, false);

        final MyViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ShopsAdapter.MyViewHolder holder, int position) {
        Shops shop = shopsList.get(position);

        holder.title.setText(shop.title);
        holder.descrip.setText(shop.descrip);
        holder.dp_imageView.setImageBitmap(shop.image);
    }

    @Override
    public int getItemCount() {
        return shopsList.size();
    }
}
