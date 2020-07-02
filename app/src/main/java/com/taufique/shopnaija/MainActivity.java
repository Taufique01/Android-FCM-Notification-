package com.taufique.shopnaija;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.taufique.shopnaija.R;


public class MainActivity extends AppCompatActivity {


    private ShopsFragment shopsFragment;
    private NotificationFragment notificationFragment;
    public static boolean swap_flag=true;
    private FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_shopping_cart);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        // Remove default title text
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        notificationFragment = new NotificationFragment();
        shopsFragment = new ShopsFragment();


            if (swap_flag == false) {

                ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.placeholder_fragment, notificationFragment);
                ft.commit();

            }else
           {
            // Begin the transaction
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.placeholder_fragment, shopsFragment);
            // ft.add(R.id.placeholder_fragment,notificationFragment);
            //ft.hide(notificationFragment);
            //ft.show(shopsFragment);
            ft.commit();
          }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            swap();

            //Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void swap(){

    ft=getSupportFragmentManager().beginTransaction();
        if(swap_flag)
        {
           // if(notificationFragment.isAdded())
             //   ft.show(notificationFragment);
            //else
                ft.replace(R.id.placeholder_fragment,notificationFragment);

            //if(shopsFragment.isAdded())
              //  ft.hide(shopsFragment);

            swap_flag=false;
        }else
        {
            //if(shopsFragment.isAdded())
              //  ft.show(shopsFragment);
            //else
             ft.replace(R.id.placeholder_fragment,shopsFragment);

            //if(notificationFragment.isAdded())
              // ft.hide(notificationFragment);
            swap_flag=true;
        }
        ft.commit();
    }


}
