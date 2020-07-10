package br.com.gransistemas.taurus.notification;

public class NotificationManager {
    public static final String CHANNEL_NOTIFICATION = "channel_notification";

    public static void notifyEvent(String channel, long notificationId){
        //Context.getCacheManager().publish(channel, String.valueOf(notificationId));
    }
}
