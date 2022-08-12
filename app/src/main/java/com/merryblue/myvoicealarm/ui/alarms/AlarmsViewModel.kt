package com.merryblue.myvoicealarm.ui.alarms

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.coldtea.smplr.smplralarm.models.WeekInfo
import com.merryblue.myvoicealarm.domain.usecase.UserAlarmUseCase
import com.merryblue.myvoicealarm.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.coldtea.smplr.smplralarm.apis.SmplrAlarmListRequestAPI
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.smplrAlarmCancel
import de.coldtea.smplr.smplralarm.smplrAlarmChangeOrRequestListener
import de.coldtea.smplr.smplralarm.smplrAlarmListChangeOrRequestListener
import de.coldtea.smplr.smplralarm.smplrAlarmUpdate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(app: Application) : BaseViewModel(app) {

    lateinit var smplrAlarmListRequestAPI: SmplrAlarmListRequestAPI
    private val _alarmList = MutableLiveData<List<AlarmItem>>()

    val alarmAllList: LiveData<List<AlarmItem>>
        get() = _alarmList

    fun initAlarmListListener(applicationContext: Context) =
        smplrAlarmChangeOrRequestListener(applicationContext) {
            //TODO Json text
        }.also {
            smplrAlarmListRequestAPI = it
        }

    fun initAllAlarmListListener(applicationContext: Context) =
        smplrAlarmListChangeOrRequestListener(applicationContext) {
            _alarmList.postValue(it)
        }.also {
            smplrAlarmListRequestAPI = it
        }

    @Inject
    lateinit var userAlarmUseCase: UserAlarmUseCase

    val alarmData = MutableLiveData<String>()

    val userData = MutableLiveData<List<AlarmsViewModel>>()

    fun cancelAlarm(requestCode: Int, applicationContext: Context) =
        smplrAlarmCancel(applicationContext) {
            requestCode { requestCode }
        }

    fun requestAlarmList() {
        CoroutineScope(Dispatchers.IO).launch {
            _alarmList.postValue(smplrAlarmListRequestAPI.getAlarmList())
        }
    }

    fun updateAlarm(
        requestCode: Int,
        hour: Int,
        minute: Int,
        weekInfo: WeekInfo,
        isActive: Boolean,
        applicationContext: Context
    ) {
        smplrAlarmUpdate(applicationContext) {
            requestCode { requestCode }
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
            isActive { isActive }
        }
    }

}