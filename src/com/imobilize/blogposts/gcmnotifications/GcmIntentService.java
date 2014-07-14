/**
* Copyright 2014 iMobilize Ltd.
*
* Licensed under the Apache License, Version 2.0 (the "License"); you may
* not use this file except in compliance with the License. You may obtain
* a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package com.imobilize.blogposts.gcmnotifications;

import info.androidhive.slidingmenu.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.imobilize.blogposts.activities.ArticleActivity;
import com.imobilize.blogposts.constants.Constants;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString());
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                String articleDataString = extras.getString("article_data");
                sendNotification(articleDataString);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String articleDataString) {
        mNotificationManager = (NotificationManager)
        this.getSystemService(Context.NOTIFICATION_SERVICE);
        
        JSONObject articleDataJson = null;
        try {
			articleDataJson = new JSONObject(articleDataString);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        
        Intent intentForArticleActivity = new Intent(this, ArticleActivity.class);
        
        String notificationMessage = null;
        try {
			intentForArticleActivity.putExtra(Constants.ARTICLE_TITLE, articleDataJson.getString("title"));
			intentForArticleActivity.putExtra(Constants.ARTICLE_METADATA, "Posted by " + articleDataJson.getString("author") + " on " + articleDataJson.getString("creationDate"));
			intentForArticleActivity.putExtra(Constants.ARTICLE_CONTENT, articleDataJson.getString("content"));
			intentForArticleActivity.putExtra(Constants.ARTICLE_KEY, articleDataJson.getString("key"));
			notificationMessage = "Read our new article from category " + articleDataJson.getString("categories") + " called '" + articleDataJson.getString("title") + "'";
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intentForArticleActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.drawable.ic_gcm)
        .setAutoCancel(true)
        .setContentTitle("BLOGPOSTS")
        .setStyle(new NotificationCompat.BigTextStyle()
        .bigText(notificationMessage))
        .setContentText(notificationMessage);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    } 
}
