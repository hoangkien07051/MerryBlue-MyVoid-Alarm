package com.merryblue.myvoicealarm.ui.addalarm

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.merryblue.myvoicealarm.MainActivity
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.receiver.ActionReceiver
import com.merryblue.myvoicealarm.common.receiver.AlarmBroadcastReceiver
import com.merryblue.myvoicealarm.domain.usecase.UserAlarmUseCase
import com.merryblue.myvoicealarm.ui.alarms.AlarmsViewModel
import com.merryblue.myvoicealarm.ui.base.BaseViewModel
import com.merryblue.myvoicealarm.ui.lockscreenalarm.ActivityLockScreenAlarm
import dagger.hilt.android.lifecycle.HiltViewModel
import de.coldtea.smplr.smplralarm.*
import de.coldtea.smplr.smplralarm.apis.SmplrAlarmListRequestAPI
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.models.WeekInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AddAlarmViewModel @Inject constructor(
    app: Application,
    private val recorder: Recorder
) : BaseViewModel(app) {


    companion object {
        internal const val ACTION_KEY = "ACTION_KEY"
        internal const val ACTION_SNOOZE = "action_snooze"
        internal const val ACTION_DISMISS = "action_dismiss"
        internal const val ACTION_NOTIFICATION_DISMISS = "action_notification_dismiss"
        internal const val HOUR = "hour"
        internal const val MINUTE = "minute"
    }

    @Inject
    lateinit var userAlarmUseCase: UserAlarmUseCase

    val alarmData = MutableLiveData<String>()

    val userData = MutableLiveData<List<AlarmsViewModel>>()
    lateinit var smplrAlarmListRequestAPI: SmplrAlarmListRequestAPI

    private val _alarmListAsJson = MutableLiveData<String>()
    val alarmListAsJson: LiveData<String>
        get() = _alarmListAsJson

    fun initAlarmListListener(applicationContext: Context) =
        smplrAlarmChangeOrRequestListener(applicationContext) {
            _alarmListAsJson.postValue(it)
        }.also {
            smplrAlarmListRequestAPI = it
        }

    /**
     * [hour] = 0 -> 23
     * [minute] =0 -> 59
     * [weekDays] = List<WeekDays> = getWeekInfo()
     * [isActive]: Boolean = true,
     * [isVibrate]: Boolean,
     * [nameAlarm]: String,
     * [voicePath]: String,
     */
    fun setFullScreenIntentAlarm(
        alarmItem: AlarmItem,
        applicationContext: Context
    ): Int {
        val onClickShortcutIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        val fullScreenIntent = Intent(
            applicationContext,
            ActivityLockScreenAlarm::class.java
        )

        val alarmReceivedIntent = Intent(
            applicationContext,
            AlarmBroadcastReceiver::class.java
        )

        val snoozeIntent = Intent(applicationContext, ActionReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(HOUR, alarmItem.hour)
            putExtra(MINUTE, alarmItem.minute)
        }

        val dismissIntent = Intent(applicationContext, ActionReceiver::class.java).apply {
            action = ACTION_DISMISS
        }

        val notificationDismissIntent = Intent(applicationContext, ActionReceiver::class.java).apply {
            action = ACTION_NOTIFICATION_DISMISS
        }

        fullScreenIntent.putExtra(ACTION_KEY, "My Voice Alarm product by Merry Blue!!")

        return smplrAlarmSet(applicationContext) {
            hour { alarmItem.hour }
            min { alarmItem.minute }
            contentIntent { onClickShortcutIntent }
            receiverIntent { fullScreenIntent }
            alarmReceivedIntent { alarmReceivedIntent }
            weekdays {
                if (alarmItem.convertWeekInfo().monday) monday()
                if (alarmItem.convertWeekInfo().tuesday) tuesday()
                if (alarmItem.convertWeekInfo().wednesday) wednesday()
                if (alarmItem.convertWeekInfo().thursday) thursday()
                if (alarmItem.convertWeekInfo().friday) friday()
                if (alarmItem.convertWeekInfo().saturday) saturday()
                if (alarmItem.convertWeekInfo().sunday) sunday()
            }
            notification {
                alarmNotification {
                    smallIcon { R.drawable.ic_bell }
                    title { "${alarmItem.hour}:${alarmItem.minute}" }
                    autoCancel { true }
                    firstButtonText { "Snooze" }
                    secondButtonText { "Turn off" }//Turn off
                    firstButtonIntent { snoozeIntent }
                    secondButtonIntent { dismissIntent }
                    notificationDismissedIntent { notificationDismissIntent }
                }
            }
            isVibrate { alarmItem.isVibrate }
            nameAlarm { alarmItem.nameAlarm }
            setVoicePath { alarmItem.voicePath }
        }
    }

    fun setNotificationAlarm(
        hour: Int,
        minute: Int,
        weekInfo: WeekInfo,
        applicationContext: Context
    ): Int {
        val alarmReceivedIntent = Intent(
            applicationContext,
            AlarmBroadcastReceiver::class.java
        )
        val onClickShortcutIntent = Intent(
            applicationContext,
            MainActivity::class.java
        )

        onClickShortcutIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        return smplrAlarmSet(applicationContext) {
            hour { hour }
            min { minute }
            weekdays {
                if (weekInfo.monday) monday()
                if (weekInfo.tuesday) tuesday()
                if (weekInfo.wednesday) wednesday()
                if (weekInfo.thursday) thursday()
                if (weekInfo.friday) friday()
                if (weekInfo.saturday) saturday()
                if (weekInfo.sunday) sunday()
            }
            contentIntent { onClickShortcutIntent }
            alarmReceivedIntent { alarmReceivedIntent }
            notification {
                alarmNotification {
                    smallIcon { R.drawable.ic_bell }
                    title { "Voice Alarm is ringing" }
                    message { "Voice Alarm is ringing" }
                    bigText { "Voice Alarm is ringing" }
                    autoCancel { true }
                    firstButtonText { "Snooze" }
                    secondButtonText { "Dismiss" }
                }
            }
        }
    }

    fun setNoNotificationAlarm(
        hour: Int,
        minute: Int,
        weekInfo: WeekInfo,
        nameAlarm: String,
        applicationContext: Context
    ): Int {

        val fullScreenIntent = Intent(
            applicationContext,
            ActivityLockScreenAlarm::class.java
        )

        val alarmReceivedIntent = Intent(
            applicationContext,
            AlarmBroadcastReceiver::class.java
        )

        fullScreenIntent.putExtra(ACTION_KEY, "My Voice Alarm product by Merry Blue")

        return smplrAlarmSet(applicationContext) {
            hour { hour }
            min { minute }
            receiverIntent { fullScreenIntent }
            alarmReceivedIntent { alarmReceivedIntent }
            weekdays {
                if (weekInfo.monday) monday()
                if (weekInfo.tuesday) tuesday()
                if (weekInfo.wednesday) wednesday()
                if (weekInfo.thursday) thursday()
                if (weekInfo.friday) friday()
                if (weekInfo.saturday) saturday()
                if (weekInfo.sunday) sunday()
            }

            nameAlarm { nameAlarm }
        }
    }

    fun updateAlarm(
        alarmItem: AlarmItem,
        applicationContext: Context
    ) {
        smplrAlarmUpdate(applicationContext) {
            requestCode { alarmItem.requestId }
            hour { alarmItem.hour }
            min { alarmItem.minute }
            weekdays {
                if (alarmItem.convertWeekInfo().monday) monday()
                if (alarmItem.convertWeekInfo().tuesday) tuesday()
                if (alarmItem.convertWeekInfo().wednesday) wednesday()
                if (alarmItem.convertWeekInfo().thursday) thursday()
                if (alarmItem.convertWeekInfo().friday) friday()
                if (alarmItem.convertWeekInfo().saturday) saturday()
                if (alarmItem.convertWeekInfo().sunday) sunday()

            }
            isActive { alarmItem.isActive }
            isVibrate { alarmItem.isVibrate }
            nameAlarm { alarmItem.nameAlarm }
            setVoicePath { alarmItem.voicePath }
        }
    }

    fun requestAlarmList() = smplrAlarmListRequestAPI.requestAlarmList()

    fun cancelAlarm(requestCode: Int, applicationContext: Context) =
        smplrAlarmCancel(applicationContext) {
            requestCode { requestCode }
        }

    private val _state = MutableStateFlow(RecordingState(false))

    val state: Flow<RecordingState>
        get() = _state

    fun toggleRecording(id: Int?) {
        updateRecordingState(!_state.value.recording, id)
    }

    private fun updateRecordingState(recording: Boolean, id: Int?) {
        _state.value = _state.value.copy(recording = recording)
        if (recording) {
            recorder.startRecording(id)
        } else {
            recorder.stopRecording()
        }
    }

    override fun onCleared() {
        recorder.release()
        super.onCleared()
    }

    data class RecordingState(val recording: Boolean)

}