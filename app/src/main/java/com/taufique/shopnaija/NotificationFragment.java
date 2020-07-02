package com.taufique.shopnaija;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taufique.shopnaija.R;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {


    private List<Notification> notificationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotificationsAdapter notificationsAdapter;

    public NotificationFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView=view.findViewById(R.id.textView);

        prepareData();

        if (notificationList.isEmpty())
        textView.setVisibility(View.VISIBLE);
        else
            textView.setVisibility(View.GONE);

        recyclerView=view.findViewById(R.id.recycler_view_n);

        notificationsAdapter=new NotificationsAdapter(notificationList);
        Context context=getActivity();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(notificationsAdapter);
        notificationsAdapter.setCustomItemClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(MainActivity.this,"clicked :"+position,Toast.LENGTH_SHORT).show();
                startWebViewActivity(position);


            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void startWebViewActivity(int index){

        Intent intent=new Intent(getActivity(),WebViewActivity.class);
        intent.putExtra("URL",notificationList.get(index).getUrl());

        startActivity(intent);


    }

    public void prepareData(){


        DatabaseHelper databaseHelper=new DatabaseHelper(getActivity().getApplicationContext());
        notificationList.clear();
        notificationList=databaseHelper.getAllNotifications();



    }

}
