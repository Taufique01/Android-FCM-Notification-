package com.taufique.shopnaija;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.taufique.shopnaija.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Map;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String PREFERENCE_LAST_NOTIF_ID = "PREFERENCE_LAST_NOTIF_ID";

    Target target = new Target() {


        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            sendNotification(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            sendNotification2(BitmapFactory.decodeResource(getResources(),R.drawable.icons));


        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        super.onMessageReceived(remoteMessage);
        //Log.d("receice1", "From: " + remoteMessage.getFrom());
        if(remoteMessage.getData()!=null) {
           // Log.d("receice2", "From: " + remoteMessage.getFrom());
            getImage(remoteMessage);
            saveToDatabase(remoteMessage);
        }


    }



    private void getImage(final RemoteMessage remoteMessage){



        Map<String, String> data = remoteMessage.getData();
        Config.title = data.get("title");
        Config.content = data.get("content");
        Config.imageUrl = data.get("imageUrl");
        Config.gameUrl = data.get("url");
        Log.d("kk0", Config.imageUrl);


        if(isNullOrEmpty( Config.title)|| Config.title.trim().isEmpty())
            Config.title="Happy Shopping";
        if(isNullOrEmpty( Config.content)|| Config.content.trim().isEmpty())
            Config.content="Thanks for being with shopnaija.";

        if(isNullOrEmpty( Config.gameUrl)||data.get("url").trim().isEmpty())
            Config.gameUrl="https://www.google.com";

        if(isNullOrEmpty(  data.get("imageUrl"))||data.get("imageUrl").trim().isEmpty())
            Config.imageUrl="https://firebasestorage.googleapis.com/v0/b/shopnaija.appspot.com/o/Icon-512.png?alt=media&token=74810275-2ef4-43c3-b23f-11970c5883f6";


        Log.d("kk", Config.imageUrl);
        //Create thread to fetch image from notification
        if(remoteMessage.getData()!=null){

            Handler uiHandler = new Handler(Looper.getMainLooper());
            uiHandler.post(new Runnable() {
                @Override
                public void run() {
                    // Get image from data Notification
                    try {
                        Picasso.get()
                                .load(Config.imageUrl)
                                .into(target);
                    }catch(Exception exp){

                        Picasso.get()
                                .load("https://firebasestorage.googleapis.com/v0/b/shopnaija.appspot.com/o/Icon-512.png?alt=media&token=74810275-2ef4-43c3-b23f-11970c5883f6")
                                .into(target);
                    }

                }
            }) ;
        }
    }

    private void sendNotification(Bitmap bitmap){
        String CHANNEL_ID = "101";
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder  nBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_shopping_cart)
                .setContentTitle(Config.title)
                .setContentText(Config.content)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(defaultSound);

        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra("URL", Config.gameUrl);
        int dummyuniqueInt = new Random().nextInt(543254);

        intent.setAction("dummy action"+dummyuniqueInt);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addNextIntentWithParentStack(intent);
       // Get the PendingIntent containing the entire back stack
        //PendingIntent resultPendingIntent =
              //  stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent,
                0);
        nBuilder.setContentIntent(resultPendingIntent);
        createNotificationChannel(CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
                  notificationManager.notify(getNextNotifId(getApplicationContext()), nBuilder.build());



    }

    private void sendNotification2(Bitmap bitmap){
        String CHANNEL_ID = "101";
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder  nBuilder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_shopping_cart)
                .setContentTitle(Config.title)
                .setContentText(Config.content)
                .setLargeIcon(bitmap)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSound(defaultSound);
        Intent intent=new Intent(this,WebViewActivity.class);
        intent.putExtra("URL", Config.gameUrl);
        int dummyuniqueInt = new Random().nextInt(543254);

        intent.setAction("dummy action"+dummyuniqueInt);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        //TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        //stackBuilder.addNextIntentWithParentStack(intent);
        // Get the PendingIntent containing the entire back stack
        //PendingIntent resultPendingIntent =
        //  stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, intent,
                0);
        nBuilder.setContentIntent(resultPendingIntent);
        createNotificationChannel(CHANNEL_ID);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(getNextNotifId(getApplicationContext()), nBuilder.build());



    }

    private void createNotificationChannel(String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "rafi's notification";//getString(R.string.channel_name);
            String description ="rafis notification description"; ///getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);



        }
    }

    public void saveToDatabase(RemoteMessage remoteMessage){

       // Map<String, String> data = remoteMessage.getData();


        DatabaseHelper databaseHelper=new DatabaseHelper(getApplicationContext());

        if(databaseHelper.getNotificationsCount()>30)
            databaseHelper.deleteLastNotification();

        databaseHelper.insertNotification(
                Config.title,
                Config.content,
                Config.gameUrl,
                Config.imageUrl
                );



    }

    private static int getNextNotifId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sharedPreferences.getInt(PREFERENCE_LAST_NOTIF_ID, 0) + 1;
        if (id >=Integer.MAX_VALUE-5) { id = 0; }
        sharedPreferences.edit().putInt(PREFERENCE_LAST_NOTIF_ID, id).apply();
        return id;
    }

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;

        //if(Strings.)
        return true;
    }


}

      /*  {“data”: {
        “title”: “Hey”,
        “content” : “Check Out This Awesome Game!”,
        “imageUrl”: “http://h5.4j.com/thumb/Ninja-Run.jpg",
        “gameUrl”: “https://h5.4j.com/Ninja-Run/index.php?pubid=noad"
        },
        “to”: “/topics/all”
        }*/