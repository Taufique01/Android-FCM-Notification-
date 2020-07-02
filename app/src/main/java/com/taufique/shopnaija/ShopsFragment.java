package com.taufique.shopnaija;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taufique.shopnaija.R;

import java.util.ArrayList;
import java.util.List;


public class ShopsFragment extends Fragment {

    private List<Shops> shopsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShopsAdapter shopsAdapter;
    public ShopsFragment() {
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
        return inflater.inflate(R.layout.fragment_shops, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);

        shopsAdapter=new ShopsAdapter(shopsList);
        Context context=getActivity();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shopsAdapter);
        shopsAdapter.setCustomItemClickListener(new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Toast.makeText(MainActivity.this,"clicked :"+position,Toast.LENGTH_SHORT).show();
                startWebViewActivity(position);


            }
        });

        prepareData();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }







    public void prepareData(){

        shopsList.clear();

        Bitmap bitmap_jumia = BitmapFactory.decodeResource(this.getResources(),R.drawable.jumia);
        Bitmap bitmap_konga = BitmapFactory.decodeResource(this.getResources(),R.drawable.konga);
        Bitmap bitmap_ali = BitmapFactory.decodeResource(this.getResources(),R.drawable.ali);
        Bitmap bitmap_slot = BitmapFactory.decodeResource(this.getResources(),R.drawable.sloat);
        Bitmap bitmap_kara = BitmapFactory.decodeResource(this.getResources(),R.drawable.kara);
        Bitmap bitmap_dealdey= BitmapFactory.decodeResource(this.getResources(),R.drawable.dealdey);
        Bitmap bitmap_travelng= BitmapFactory.decodeResource(this.getResources(),R.drawable.travel);
        Bitmap bitmap_travelstart= BitmapFactory.decodeResource(this.getResources(),R.drawable.travelstart);


        Shops jumia=new Shops("JUMIA NIGERIA"," Buy Cheap Things Online",bitmap_jumia);
        jumia.url=" http://c.jumia.io/?a=104878&c=11&p=r&E=kkYNyk2M4sk%3d&ckmrdr=&utm_source=cake&utm_medium=affiliation&utm_campaign=104878&utm_term=";

        Shops konga=new Shops("KONGA NIGERIA"," Shop Online",bitmap_konga);
        konga.url="https://www.konga.com/?k_id=natt12345";

        Shops ali=new Shops("ALIEXPRESS ","Buy Cheap and Quality Products From China",bitmap_ali);
        ali.url="http://s.click.aliexpress.com/e/bIYDuF4U";

        Shops slot=new Shops("SLOT NIGERIA","Online Store of Phones",bitmap_slot);
        slot.url="https://www.slot.ng";

        Shops kara=new Shops("KARA NIGERIA","Shop Online",bitmap_kara);
        kara.url="http://www.kara.com.ng";

        Shops dealdey=new Shops("DEALDEY","Get Deals VB Online",bitmap_dealdey);
        dealdey.url="https://www.dealdey.com";

        Shops travelng=new Shops("BOOK HOTELS IN NIGERIA","Book Cheap Hotels",bitmap_travelng);
        travelng.url="http://www.travelsng.commissions.ng";

        Shops travels=new Shops("BOOK CHEAP  FLIGHTS","Book Flights Online",bitmap_travelstart);
        travels.url="https://www.travelstart.com/?affId=213424&utm_source=affiliate&utm_medium=213424";


        shopsList.add(jumia);
        shopsList.add(konga);
        shopsList.add(ali);
        shopsList.add(slot);
        shopsList.add(kara);
        shopsList.add(dealdey);
        shopsList.add(travelng);
        shopsList.add(travels);









    }


    public void startWebViewActivity(int index){

        Intent intent=new Intent(getActivity(),WebViewActivity.class);
        intent.putExtra("URL",shopsList.get(index).url);

        startActivity(intent);



    }




}
