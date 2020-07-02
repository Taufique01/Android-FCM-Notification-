package com.taufique.shopnaija;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taufique.shopnaija.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.MyViewHolder>{



    private List<Notification> notifications;

    Context mContext;
    CustomItemClickListener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, descrip;

        public ImageView dp_imageView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_title_n);
            descrip = (TextView) view.findViewById(R.id.textView_description_n);
            dp_imageView=(ImageView) view.findViewById(R.id.imageView_n);

        }
    }

    public NotificationsAdapter(List<Notification> notifications) {

        //this.notifications.clear();

        this.notifications = notifications;
        for(Notification n:this.notifications){
            Log.d("ffff","vvvvvvvv"+n.getTitle());


        }
    }
    public void setCustomItemClickListener(CustomItemClickListener listener){



        this.listener=listener;
    }

    @NonNull
    @Override
    public NotificationsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_row, parent, false);

        final NotificationsAdapter.MyViewHolder mViewHolder = new NotificationsAdapter.MyViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        return mViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsAdapter.MyViewHolder holder, int position) {
        Notification notification = notifications.get(position);
        //Log.d("ffff","bbbbbb"+notification.getTitle());

        holder.title.setText(notification.getTitle());
        holder.descrip.setText(notification.getDescription());
        try {


            Picasso.get().load(notification.getImgurl()).error(R.drawable.icons).into(holder.dp_imageView);
        }catch (Exception e){

            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/shopnaija.appspot.com/o/Icon-512.png?alt=media&token=74810275-2ef4-43c3-b23f-11970c5883f6").error(R.drawable.icons).into(holder.dp_imageView);


        }
       // Log.d("ffff","bbbbbbbbbbbbbb"+notification.getTitle());

        //holder.dp_imageView.setImageBitmap(notification.getImgurl());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
    }

}
