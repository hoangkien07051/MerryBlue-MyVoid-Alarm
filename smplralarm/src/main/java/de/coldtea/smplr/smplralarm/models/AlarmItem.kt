package de.coldtea.smplr.smplralarm.models

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmItem(
    var requestId: Int = 0,
    var hour: Int,
    var minute: Int,
    var weekDays: List<WeekDays>,
    var isActive: Boolean,
    var isVibrate: Boolean,
    var nameAlarm: String,
    var voicePath: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        TODO("weekDays"),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    fun getTime(): String {
        return "$hour:$minute"
    }

    fun getTimes(): String {
        val times = arrayListOf<String>()
        weekDays.forEach { day ->
            if (day.value == WeekDays.MONDAY.value) {
                times.add("Monday")
            }
            if (day.value == WeekDays.TUESDAY.value) {
                times.add("Tuesday")
            }
            if (day.value == WeekDays.WEDNESDAY.value) {
                times.add("Wednesday")
            }
            if (day.value == WeekDays.THURSDAY.value) {
                times.add("Thursday")
            }
            if (day.value == WeekDays.FRIDAY.value) {
                times.add("Friday")
            }
            if (day.value == WeekDays.SATURDAY.value) {
                times.add("Saturday")
            }
            if (day.value == WeekDays.SUNDAY.value) {
                times.add("Sunday")
            }
        }
        var daysDisplay = ""
        if (times.size == 1) {
            daysDisplay = times.get(0)
        } else if (times.size == 7) {
            daysDisplay = "Everyday"
        } else {
            times.forEachIndexed { index, day ->
                times += day
                if (index < times.size - 1) {
                    times += ", "
                }
            }
        }

        return daysDisplay
    }

        fun convertWeekInfo(): WeekInfo {
            val weekInfo = WeekInfo(
                monday = false,
                tuesday = false,
                wednesday = false,
                thursday = false,
                friday = false,
                saturday = false,
                sunday = false
            )
            weekDays.forEach { day ->
                if (day.value == WeekDays.MONDAY.value) {
                    weekInfo.monday = true
                }
                if (day.value == WeekDays.TUESDAY.value) {
                    weekInfo.tuesday = true
                }
                if (day.value == WeekDays.WEDNESDAY.value) {
                    weekInfo.wednesday = true
                }
                if (day.value == WeekDays.THURSDAY.value) {
                    weekInfo.thursday = true
                }
                if (day.value == WeekDays.FRIDAY.value) {
                    weekInfo.friday = true
                }
                if (day.value == WeekDays.SATURDAY.value) {
                    weekInfo.saturday = true
                }
                if (day.value == WeekDays.SUNDAY.value) {
                    weekInfo.sunday = true
                }
            }
            return weekInfo

    }

    override fun toString(): String {
        return "AlarmItem(requestId=$requestId, hour=$hour, minute=$minute, weekDays=$weekDays, isActive=$isActive, isVibrate=$isVibrate, nameAlarm='$nameAlarm', voicePath='$voicePath')"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<AlarmItem> {
        override fun createFromParcel(parcel: Parcel): AlarmItem {
            return AlarmItem(parcel)
        }

        override fun newArray(size: Int): Array<AlarmItem?> {
            return arrayOfNulls(size)
        }
    }
}