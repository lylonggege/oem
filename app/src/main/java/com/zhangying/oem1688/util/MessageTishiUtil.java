package com.zhangying.oem1688.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class MessageTishiUtil {

    private long lastNotifiyTime;
    protected AudioManager audioManager;
    protected Vibrator vibrator;
    private Ringtone ringtone;
    private MessageTishiUtil(){}
    private static MessageTishiUtil messageTishiUtil;
    public static MessageTishiUtil getInstance(){
        if(messageTishiUtil==null){
            synchronized (MessageTishiUtil.class){
                if(messageTishiUtil == null){
                    messageTishiUtil = new MessageTishiUtil();
                }
            }
        }
        return messageTishiUtil;
    }



    public void vibrateAndPlayTone(Context context) {
        audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE); //此方法是由Context调用的
        vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);  //同上
        if (System.currentTimeMillis() - lastNotifiyTime < 1000) {
            // received new messages within 2 seconds, skip play ringtone
            return;
        }

        try {
            lastNotifiyTime = System.currentTimeMillis();

            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                return;
            }
            long[] pattern = new long[]{0, 180, 80, 120};
            vibrator.vibrate(pattern, -1);  //震动

            if (ringtone == null) {
                Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                ringtone = RingtoneManager.getRingtone(context, notificationUri);
                if (ringtone == null) {
                    return;
                }
            }


            if (!ringtone.isPlaying()) {
                ringtone.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
