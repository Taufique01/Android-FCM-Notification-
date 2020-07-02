package com.taufique.shopnaija;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.taufique.shopnaija.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WebViewActivity extends AppCompatActivity {


    private WebView myWebView;
    //ProgressBar startProgressBar;
    ProgressBar mProgressBar;
    //ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);


        MovableFloatingActionButton fab = findViewById(R.id.fab);
        CoordinatorLayout.LayoutParams lp  = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
        fab.setCoordinatorLayout(lp);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i= new Intent(WebViewActivity.this,MainActivity.class);

            startActivity(i);

        }
    });


        //Intent intent=getIntent();

        Intent intent=getIntent();
        String url=intent.getStringExtra("URL");

        myWebView = findViewById(R.id.webview);
        myWebView.setVisibility(View.VISIBLE);
        mProgressBar =  findViewById(R.id.pb);

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Line of Code for opening links in app
        myWebView.setWebViewClient(new myWebViewClient());
        myWebView.setWebChromeClient(new WebChromeClient(){


            public void onProgressChanged(WebView view, int newProgress) {
                // Update the progress bar with page loading progress
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    // Hide the progressbar
                   mProgressBar.setVisibility(View.GONE);

                }

            }


        });


        myWebView.loadUrl(url);
    }


    public  class myWebViewClient extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

             super.onPageStarted(view, url, favicon);
           mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

           // startProgressBar.setVisibility(View.VISIBLE);
           // mProgressBar.setVisibility(View.VISIBLE);

            view.loadUrl(url);
            return true;

        }

        @Override
        public void onPageFinished(WebView view, String url) {

            super.onPageFinished(view, url);

           // startProgressBar.setVisibility(View.GONE);
           // imageView.setVisibility(View.GONE);
           //// mProgressBar.setVisibility(View.GONE);
             //// myWebView.setVisibility(View.VISIBLE);

        }

    }



    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }



}
