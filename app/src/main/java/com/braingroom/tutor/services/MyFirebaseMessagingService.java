package com.braingroom.tutor.services;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
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
import android.support.annotation.DrawableRes;
import android.support.v4.app.NotificationCompat;

import com.braingroom.tutor.R;
import com.braingroom.tutor.common.CustomApplication;
import com.braingroom.tutor.common.modules.GlideApp;
import com.braingroom.tutor.view.activity.SplashActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;

import static com.braingroom.tutor.utils.CommonUtilsKt.getScreenWidth;
import static com.braingroom.tutor.utils.ConstantsKt.pushNotification;

/*
 * Created by godara on 01/02/18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = "MyFirebaseMessagingService";
    private static final String NOTIFICATION_CHANNEL_ID = "Braingroom";
    protected Random random = new Random();
    private int screenWidth;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (screenWidth != 0)
            screenWidth = getScreenWidth();

        final Gson objGson = new GsonBuilder().setPrettyPrinting().create();
        final Intent intent = new Intent(this, SplashActivity.class);
        final Bundle data = new Bundle();
        final String notificationType = remoteMessage.getData().get("notify_type"); //Image or Text
        final String notificationId = remoteMessage.getData().get("notification_id");  // for analytical purpose
        final String title = remoteMessage.getData().get("title");  // Message Title
        final String shortDescription = remoteMessage.getData().get("short_description");  // Message description
        final String detailDescription = remoteMessage.getData().get("detail_description");// BigText Style
        final String image = remoteMessage.getData().get("image"); // Image Url
        final String payload = objGson.toJson(remoteMessage.getData());
        final NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        data.putBoolean(pushNotification, true);
        data.putString(pushNotification, payload);
        intent.setAction((random).nextInt(Integer.MAX_VALUE) + "");
        intent.putExtra(pushNotification, data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Braingroom", NotificationManager.IMPORTANCE_DEFAULT);

            // Configure the notification channel.
            notificationChannel.setDescription("Default");
            //notificationChannel.enableLights(true);
            //notificationChannel.setLightColor(Color.RED);
            //notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            //notificationChannel.enableVibration(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        if (notificationManager != null)
            if ("text".equalsIgnoreCase(notificationType))
                notificationManager.notify((random).nextInt(Integer.MAX_VALUE), createBigTextStyleNotification(title, shortDescription, detailDescription, pendingIntent).build());
            else if ("image".equalsIgnoreCase(notificationType))
                notificationManager.notify((random).nextInt(Integer.MAX_VALUE), createBigImageStyleNotification(title, shortDescription, getBitMapFromUrl(image), pendingIntent).build());
            else
                notificationManager.notify((random).nextInt(Integer.MAX_VALUE), createNoStyleNotification(title, shortDescription, pendingIntent).build());

        Timber.tag("Notification").d(payload);

    }

    private Bitmap getBitmapFromResource(@DrawableRes int id) {
        try {
            return GlideApp.with(this).asBitmap().override(screenWidth, screenWidth / 2).load(id).submit().get();
        } catch (InterruptedException e) {
            Timber.tag(TAG).d(e);
            return null;
        } catch (ExecutionException e) {
            Timber.tag(TAG).d(e);
            return null;
        }
    }

    private Bitmap getBitMapFromUrl(String imageUrl) {
        try {
            return GlideApp.with(this).asBitmap().override(screenWidth, screenWidth / 2).load(imageUrl).submit().get();
        } catch (InterruptedException e) {
            Timber.tag(TAG).e(e);
            return null;
        } catch (ExecutionException e) {
            Timber.tag(TAG).e(e);
            return null;
        }
    }


    private NotificationCompat.Builder createBigTextStyleNotification(String title, String shortDescription, String detailDescription, PendingIntent pendingIntent) {
        final NotificationCompat.Builder builder = createNoStyleNotification(title, shortDescription, pendingIntent);
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle(builder);
        style.bigText(detailDescription).setBigContentTitle(title);
        return builder.setStyle(style);

    }

    private NotificationCompat.Builder createNoStyleNotification(String title, String shortDescription, PendingIntent pendingIntent) {
        return new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID).setContentTitle(title).
                setSmallIcon(R.drawable.ic_notifications_64px).
                setLargeIcon(getBitmapFromResource(R.mipmap.ic_launcher)).
                setColor(getResources().getColor(R.color.push_notification)).
                setContentText(shortDescription).
                setAutoCancel(true).
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).
                setContentIntent(pendingIntent);


    }


    private NotificationCompat.Builder createBigImageStyleNotification(String title, String shortDescription, Bitmap image, PendingIntent pendingIntent) {
        final NotificationCompat.Builder builder = createNoStyleNotification(title, shortDescription, pendingIntent);
        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle(builder);
        style.setBigContentTitle(title).setSummaryText(shortDescription).bigPicture(image);
        return builder.setStyle(style);


    }


}
