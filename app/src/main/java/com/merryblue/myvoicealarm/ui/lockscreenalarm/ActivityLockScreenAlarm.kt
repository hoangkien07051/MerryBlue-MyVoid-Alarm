package com.merryblue.myvoicealarm.ui.lockscreenalarm

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.activateLockScreen
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.deactivateLockScreen
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import com.merryblue.myvoicealarm.common.service.PlaySoundService
import com.merryblue.myvoicealarm.ui.addalarm.AddAlarmViewModel
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.smplrAlarmUpdate
import kotlinx.android.synthetic.main.activity_lock_screen_alarm.*
import java.util.*

class ActivityLockScreenAlarm : AppCompatActivity(), View.OnTouchListener {

    private var player: MediaPlayer? = null
    private var vibratorService: Vibrator? = null

   // private var baseLayout: RelativeLayout? = null

    private var previousFingerPosition = 0
    private var baseLayoutPosition = 0
    private var defaultViewHeight = 0

    private var isClosing = false
    private var isScrollingUp = false
    private var isScrollingDown = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_screen_alarm)
        stopService(Intent(this, PlaySoundService::class.java))
        updateUI()
        vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        tvReminder.click {
            reminderLater()
        }
        lnLockLayout.setOnTouchListener(this)
        activateLockScreen()
    }

    var notificationId = 0
    private fun updateUI() {
        notificationId = VoiceAlarmPref.getAlarmId(this)
        val listAlarm = VoiceAlarmPref.getListAlarms(this)
        val alarmItem = listAlarm?.firstOrNull { n -> n.requestId == notificationId }
        Log.e("a", "ActivityLockScreenAlarm alarmId:: $notificationId")
        if (alarmItem != null) {
            tvTime.text = alarmItem.getTime()
            tvTitle.text = alarmItem.nameAlarm
            tvRepeatName.text = alarmItem.getTimes()
            playSound(alarmItem)
        }
    }

    private fun reminderLater() {
        if (notificationId > 0) {
            val now = getHourAndMinute()
            val snoozeLocal = VoiceAlarmPref.getSnoozeInterval(applicationContext)
            val updatedTime = addTimeSnoozeInterval(snoozeLocal, intent.getIntExtra(AddAlarmViewModel.HOUR, now.first), intent.getIntExtra(AddAlarmViewModel.MINUTE, now.second))

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)

            smplrAlarmUpdate(applicationContext) {
                requestCode { notificationId }
                hour { updatedTime.first }
                min { updatedTime.second }
            }
            Toast.makeText(applicationContext, "Reminder later at ${updatedTime.first}:${updatedTime.second}", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun getHourAndMinute(): Pair<Int, Int> = Calendar.getInstance().let {
        it.get(Calendar.HOUR_OF_DAY) to it.get(Calendar.MINUTE)
    }

    /**
     * add Snooze Interval
     */
    private fun addTimeSnoozeInterval(snoozeLocal: Int, hour: Int, minute: Int): Pair<Int, Int> {
        var mMinute = minute + snoozeLocal
        var mHour = hour

        if (mMinute == 60) {
            mMinute -= 60
            mHour += 1
        }

        if (mHour > 23)
            mHour = 0

        return mHour to mMinute
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
//                player = MediaPlayer.create(this, R.raw.notification_sound)
                player = MediaPlayer()
                player?.setDataSource(alarmItem.voicePath)
//            player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
                player?.setAudioAttributes(audioAttribute)
                player?.setOnCompletionListener { mp -> mp.release() }
                player?.isLooping = true
                vibratorService?.vibrate(2000)
                player?.start()
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        } catch (e: Exception) {
            player?.release()
        }

    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
        super.onDestroy()
        deactivateLockScreen()
    }



    @SuppressLint("ObjectAnimatorBinding")
    fun closeUpAndDismissDialog(currentPosition: Int) {
        isClosing = true
        val positionAnimator = ObjectAnimator.ofFloat(
            lnLockLayout,
            "y",
            currentPosition.toFloat(),
            -lnLockLayout?.height?.toFloat()!!
        )
        positionAnimator.duration = 300
        positionAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {}
            override fun onAnimationEnd(animator: Animator) {
                finish()
            }

            override fun onAnimationCancel(animator: Animator) {}
            override fun onAnimationRepeat(animator: Animator) {}
        })
        positionAnimator.start()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        val Y: Int = p1!!.rawY.toInt()
        when (p1.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                defaultViewHeight = lnLockLayout!!.height

                previousFingerPosition = Y
                baseLayoutPosition = lnLockLayout!!.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                if (isScrollingUp) {
                    lnLockLayout!!.y = 0f
                    isScrollingUp = false
                }

                if (isScrollingDown) {
                    lnLockLayout!!.y = 0f
                    lnLockLayout!!.layoutParams.height = defaultViewHeight
                    lnLockLayout!!.requestLayout()
                    isScrollingDown = false
                }
            }
            MotionEvent.ACTION_MOVE -> if (!isClosing) {
                val currentYPosition = lnLockLayout!!.y.toInt()

                if (previousFingerPosition > Y) {
                    if (!isScrollingUp) {
                        isScrollingUp = true
                    }

                    if (lnLockLayout!!.height < defaultViewHeight) {
                        lnLockLayout!!.layoutParams.height =
                            lnLockLayout!!.height - (Y - previousFingerPosition)
                        lnLockLayout!!.requestLayout()
                    } else {
                        if (baseLayoutPosition - currentYPosition > defaultViewHeight / 3) {
                            closeUpAndDismissDialog(currentYPosition)
                            return true
                        }
                    }
                    lnLockLayout!!.y = lnLockLayout!!.y + (Y - previousFingerPosition)
                }
                previousFingerPosition = Y
            }
        }
        return true
    }
}