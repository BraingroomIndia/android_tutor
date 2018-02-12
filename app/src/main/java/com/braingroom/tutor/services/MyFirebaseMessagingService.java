package com.braingroom.tutor.services;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.braingroom.tutor.view.activity.SplashActivity;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import timber.log.Timber;

import static com.braingroom.tutor.utils.ConstantsKt.pushNotification;

/**
 * Created by godara on 01/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "FCMService";
    protected Tracker mTracker;
    protected FirebaseAnalytics mFirebaseAnalytics;
    protected Random random = new Random();
    private LinkedList<Target> strongReference = new LinkedList<>();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        sendNotification(remoteMessage);

    }

    private void sendNotification(RemoteMessage remoteMessage) {

        final Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        final Intent intent = new Intent(this, SplashActivity.class);
        final Bundle data = new Bundle();
        final String notificationType = remoteMessage.getData().get("notify_type"); //Image or Text
        final String notificationId = remoteMessage.getData().get("notification_id");  // for analytical purpose
        final String title = remoteMessage.getData().get("title");  // Message Title
        final String shortDescription = remoteMessage.getData().get("short_description");  // Message description
        final String detailDescription = remoteMessage.getData().get("detail_description");// BigText Style
        final String image = remoteMessage.getData().get("image");
        final String payload = objGson.toJson(remoteMessage.getData());
        final NotificationCompat.Builder notificationBuilder;
        generatePictureStyleNotification temp;
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        data.putBoolean(pushNotification, true);
        data.putString(pushNotification, payload);
        intent.setAction((random).nextInt(Integer.MAX_VALUE) + "");
        intent.putExtra(pushNotification, data);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        if (notificationType.equalsIgnoreCase("text"))
            sendTextNotification(title, shortDescription, detailDescription, pendingIntent, notificationManager);
      /*  else {
            temp = new generatePictureStyleNotification(this, notificationManager, title, image, shortDescription, pendingIntent);
            temp.execute();
        }
*/

        Timber.tag("Notification").d(payload);
    }


    public void sendTextNotification(String title, String shortDescription, String detailDescription, PendingIntent pendingIntent, NotificationManager notificationManager) {
        notificationManager.notify((random).nextInt(Integer.MAX_VALUE), createBigTextStyleNotification(title, shortDescription, detailDescription, pendingIntent).build());

    }

    ;

    public Bitmap getBitmapFromResource(int id) {
        return BitmapFactory.decodeResource(getResources(), id);
    }


    private NotificationCompat.Builder createBigTextStyleNotification(String title, String shortDescription, String detailDescription, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(this);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle(builder);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        style.bigText(detailDescription).setBigContentTitle(title);

        builder.setContentTitle(title).
                setSmallIcon(R.drawable.ic_notifications_64px).
                setLargeIcon(getBitmapFromResource(R.mipmap.ic_launcher)).
                setColor(getResources().getColor(R.color.push_notification)).
                setContentText(shortDescription).
                setStyle(style).
                setAutoCancel(true).
                setSound(defaultSoundUri).
                setContentIntent(pendingIntent);

        return builder;
    }


    private void sendImageNotification(String title, String shortDescription, Bitmap image, PendingIntent pendingIntent, NotificationManager notificationManager) {
        NotificationCompat.Builder builder;

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notifications_64px)
                .setLargeIcon(getBitmapFromResource(R.mipmap.ic_launcher))
                .setContentTitle(title == null ? "" : title)
                .setContentText(shortDescription)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle(builder);
        style.setBigContentTitle(title).setSummaryText(shortDescription).bigPicture(image);
        notificationManager.notify((random).nextInt(Integer.MAX_VALUE), builder.build());


    }

    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        final private Context mContext;
        final private String title, shortDescription, imageUrl;
        final NotificationManager notificationManager;
        final PendingIntent pendingIntent;

        public generatePictureStyleNotification(Context context, NotificationManager notificationManager, String title, String imageUrl, String shortDescription, PendingIntent pendingIntent) {
            super();
            this.mContext = context;
            this.notificationManager = notificationManager;
            this.title = title;
            this.shortDescription = shortDescription;
            this.imageUrl = imageUrl;
            this.pendingIntent = pendingIntent;
            ;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            sendImageNotification(title, shortDescription, result, pendingIntent, notificationManager);

        }
    }

}
