package de.coldtea.smplr.smplralarm.apis

import android.content.Context
import android.util.Log
import de.coldtea.smplr.smplralarm.extensions.alarmsAsJsonString
import de.coldtea.smplr.smplralarm.models.ActiveAlarmList
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.models.AlarmNotification
import de.coldtea.smplr.smplralarm.repository.AlarmNotificationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmplrAlarmListRequestAPI(val context: Context) {

    internal var alarmListChangeOrRequestedListener: ((String) -> Unit)? = null
    internal var alarmAllChangeOrRequestedListener: ((ArrayList<AlarmItem>) -> Unit)? = null
    internal var alarmRequestedListener: ((AlarmNotification) -> Unit)? = null

    private var alarmListJson: String = ""
        set(value) {
            field = value

            alarmListChangeOrRequestedListener?.invoke(value)

        }

    private var alarmAllList: ArrayList<AlarmItem> = arrayListOf()
        set(value) {
            field = value
            alarmAllChangeOrRequestedListener?.invoke(value)

        }

    private val alarmNotificationRepository: AlarmNotificationRepository by lazy {
        AlarmNotificationRepository(context)
    }

    fun requestAlarmById(alarmId: Int){
        CoroutineScope(Dispatchers.IO).launch {
         val noti = alarmNotificationRepository.getAlarmNotification(alarmId)
            alarmRequestedListener?.invoke(noti)
        }
    }

    fun requestAlarmList() {
        CoroutineScope(Dispatchers.IO).launch {
            val alarmList = alarmNotificationRepository.getAllAlarmNotifications().map {
                AlarmItem(
                    it.alarmNotificationId,
                    it.hour,
                    it.min,
                    it.weekDays,
                    it.isActive,
                    it.isVibrate,
                    it.nameAlarm,
                    it.voicePath,
                )
            }

            alarmListJson = ActiveAlarmList(alarmList).alarmsAsJsonString().orEmpty()
        }
    }

    suspend fun getAlarmList(): List<AlarmItem> {
        val alarmList = alarmNotificationRepository.getAllAlarmNotifications().map {
            AlarmItem(
                it.alarmNotificationId,
                it.hour,
                it.min,
                it.weekDays,
                it.isActive,
                it.isVibrate,
                it.nameAlarm,
                it.voicePath,
            )
        }
        Log.e("a", "==> All Alarm: $alarmList")
        return alarmList
    }


}