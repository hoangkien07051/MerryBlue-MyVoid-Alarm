package com.merryblue.myvoicealarm.common.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import de.coldtea.smplr.smplralarm.models.AlarmItem
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.File
import java.util.concurrent.TimeUnit

class PlaySoundService : Service() {
    companion object {
        val CHANNEL_ID = "PlaySoundService"
        val CHANNEL_NAME = "PlaySoundService"
    }

    private var player: MediaPlayer? = null
    private var disposable: Disposable? = null

    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
//        intervalPlaySound()
        startSound()
        createChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        onTaskRemoved(intent)
        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {

    }

    override fun onDestroy() {
        try {
            super.onDestroy()
            disposable?.dispose()
            player?.release()

        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        restartService()
        super.onTaskRemoved(rootIntent)
    }

    override fun onLowMemory() {

    }

    private fun createChannel() {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                (getSystemService(NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(channel)
                val notification = Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_bell)
                    .setContentTitle(getText(R.string.app_name))
                    .build()
                startForeground(1, notification)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun restartService() {

    }

    private fun startSound() {
        val notificationId = VoiceAlarmPref.getAlarmId(this)
        val listAlarm = VoiceAlarmPref.getListAlarms(this)
        val alarmItem = listAlarm?.firstOrNull { n -> n.requestId == notificationId }
        if (alarmItem != null) {
            if (File(alarmItem.voicePath).exists()) {
                playSound(alarmItem)
            }
        }
    }

    /**
     * play sound P
     */
    private fun playSound(alarmItem: AlarmItem) {
        try {
            try {
                val audioAttribute = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
//              player = MediaPlayer.create(this, R.raw.upset_sound_tone)
                player = MediaPlayer()
                val filetPath = alarmItem.voicePath
                player?.setDataSource(filetPath)
                player?.prepare()
//            player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                player?.setAudioAttributes(audioAttribute)
//                player?.setOnCompletionListener { mp -> mp.release() }
                player?.isLooping = true
                player?.start()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            disposable?.dispose()
            player?.release()
        }
    }

    /**
     * play sound every 2 Seconds
     */
    private fun intervalPlaySound() {
        try {
            disposable = Observable.interval(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .repeat()
                .subscribe({ it ->
                    Log.e("VoiceAlarm", "=>> Voice PlaySound")
                    startSound()
                }, { error ->
                    Log.e("VoiceAlarm", "==> Voice PlaySound error:: $error")
                })
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

}
