package com.merryblue.myvoicealarm.ui.addalarm

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.database.Cursor
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.merryblue.myvoicealarm.MainActivity
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.click
import com.merryblue.myvoicealarm.common.extenstion.nowPlus
import com.merryblue.myvoicealarm.data.model.LibraryItem
import de.coldtea.smplr.smplralarm.models.WeekInfo
import com.merryblue.myvoicealarm.data.model.WeekItem
import com.merryblue.myvoicealarm.databinding.FragmentAddAlarmBinding
import com.merryblue.myvoicealarm.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.models.WeekDays
import kotlinx.android.synthetic.main.fragment_add_alarm.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AddAlarmFragment : BaseFragment() {

    private val viewModel by viewModels<AddAlarmViewModel>()
    private lateinit var dataBinding: FragmentAddAlarmBinding
    private val PERMISSION_RECORD_REQUEST = 1
    private var mediaPlayer: MediaPlayer? = null
    private var isPlay = true
    private var listDayOfWeek = ArrayList<WeekItem>()
    private var voicePath: String? = null
    private var nameVoice: String? = null
    private var isRecord = true
    private var nameAlarm: String? = null
    private var isUpdate: Boolean = false
    private var requestId: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentAddAlarmBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        viewModel.initAlarmListListener(requireContext().applicationContext)
        (requireActivity() as MainActivity).hideBottomNavigation()
        return dataBinding.root
    }

    override fun setUpView() {
        if (filePathForId(1).isNotEmpty()) {
            voicePath = filePathForId(1)
        } else {
            voicePath = getFileAudio()[0].path
        }
        if (voicePath.isNullOrEmpty()) {
            tvDefault.text = getFileAudio()[0].title
        }
        requestPermission()
        initView()
        btnBack.click {
            onBackPress()
        }
        btnSave.click {
            addNewAlarm()
        }
        constrainLib.setOnClickListener {
            LibraryDialogFragment(
                getFileAudio(),
                {
                    voicePath = it.path
                    nameVoice = it.title
                    tvDefault.text = it.title
            }).show(childFragmentManager, "delete")
        }

        tvNotes.click {
            NameAlarmFragment(
                "",
                {
                    nameAlarm = it
                }
            ).show(childFragmentManager,"name alarm")
        }

        constraintRepeat.setOnClickListener {
            RepeatDialogFragment(
                listWeek(),
                {
                    if (listDayOfWeek.isNotEmpty()) listDayOfWeek.clear()
                    listDayOfWeek.addAll(it)
                }
            ).show(childFragmentManager,"repeat")
        }

    }

    private fun listWeek(): ArrayList<WeekItem> {
        val list: ArrayList<WeekItem> = ArrayList<WeekItem>()
        list.add(WeekItem("Monday", false))
        list.add(WeekItem("Tuesday", false))
        list.add(WeekItem("Wednesday", false))
        list.add(WeekItem("Thursday", false))
        list.add(WeekItem("Friday", false))
        list.add(WeekItem("Saturday", false))
        list.add(WeekItem("Sunday", false))
        return list
    }

    override fun observable() = Unit


    override fun fireData() = Unit

    private fun initView() {
        with(dataBinding) {
            timePicker.setIs24HourView(true)
            btnRecord.setOnClickListener {
                viewModel?.toggleRecording(1)
                if (isRecord) {
                    lottieAnim.visibility = View.VISIBLE
                    voicePath = filePathForId(1)
                    isRecord = false
                } else {
                    lottieAnim.visibility = View.GONE
                    isRecord = true
                }
            }
            btnPlay.setOnClickListener {
                if (isPlay) {
                    startPlayBack(1)
                    isPlay = false
                } else {
                    mediaPlayer?.stop()
                    btnPlay.setImageResource(R.drawable.ic_play)
                    isPlay = true
                }
            }
            btnDelete.click {
                val filePath = filePathForId(1)
                if (File(filePath).exists() && filePath.isNotEmpty()) {
                    File(filePath).delete()
                }
            }
        }
        val alarmItem = arguments?.getParcelable<AlarmItem>("alarm")
        if (alarmItem != null) {
            updateAlarmItem(alarmItem)
        }
    }

    private fun updateAlarmItem(alarmItem: AlarmItem) {
        val defaultTime = Calendar.getInstance().nowPlus(0)
        isUpdate = true
        requestId = alarmItem.requestId
        timePicker.hour = alarmItem.hour
        timePicker.minute = alarmItem.minute
        try {
            var nameVoice = getFileAudio().filter { s -> s.path == alarmItem.voicePath }.single()
            tvDefault.text = nameVoice.title
        } catch (e: Exception) {
            tvDefault.text = getString(R.string.text_default)
        }
        voicePath = alarmItem.voicePath
        switchAlarm.isChecked = alarmItem.isVibrate
        var hour = alarmItem.hour - defaultTime.first
        var minute = alarmItem.minute - defaultTime.second
        if (hour < 0) {
            hour = 24 + hour
        }
        if (minute < 0) {
            hour = hour - 1
            minute = 60 + minute
        }
        dataBinding.tvStartAlarm.text =
            resources.getString(R.string.start_alarm) + " ${hour}" + ": ${minute}"
    }

    private fun requestPermission() {
        val permissionRequired = mutableListOf<String>()
        val hasRecordPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
        if (!hasRecordPermission) {
            permissionRequired.add(android.Manifest.permission.RECORD_AUDIO)
        }
        val hasStoragePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val hasReadFilePermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        if (!hasStoragePermission) {
            permissionRequired.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!hasReadFilePermission) {
            permissionRequired.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionRequired.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionRequired.toTypedArray(),
                PERMISSION_RECORD_REQUEST
            )
        }
    }

    private fun filePathForId(id: Int?): String {
        return context?.getExternalFilesDir(null)!!.absolutePath + "$id.aac"
    }

    @SuppressLint("SetTextI18n")
    private fun startPlayBack(id: Int?): Boolean {
        if (voicePath!!.isNotEmpty() && File(voicePath).exists()){
            mediaPlayer = MediaPlayer()
            mediaPlayer?.apply {
                setDataSource(voicePath)
                prepare()
                start()
                dataBinding.progressBar.link(mediaPlayer!!)
                val minute: Int = (mediaPlayer?.duration?.div(1000))?.div(60) ?: 0
                var second: Long = (mediaPlayer?.duration?.toLong()?.div(1000))?.rem(60) ?: 0
                val minutes = if (minute < 9) {
                    "0$minute"
                } else {
                    minute.toString()
                }
                val seconds = if (second < 9) {
                    "0$second"
                } else {
                    second.toString()
                }
                dataBinding.tvTimeAudio.text = "$minutes:$seconds"
                dataBinding.btnPlay.setImageResource(R.drawable.ic_stop)
                mediaPlayer?.setOnCompletionListener {
                    dataBinding.btnPlay.setImageResource(R.drawable.ic_play)
                }
                return true
            }
        }
        return false
    }

    override fun onStop() {
        mediaPlayer?.stop()
        super.onStop()
    }

    private fun stopPlayBack() {
        mediaPlayer?.apply {
            stop()
            release()
        }
        mediaPlayer = null

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_RECORD_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                } else {
                    requestPermission()
                    Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
    /**
     * [AlarmItem] param
     * default: hour = now, minute = now+1
     * [hour] = 0 -> 23
     * [minute] = 0 -> 59
     * [weekDays] = List<WeekDays> = getWeekInfo()
     * [isActive]: Boolean,
     * [isVibrate]: Boolean,
     * [nameAlarm]: String,
     * [voicePath]: String,
     *
     */
    private fun addNewAlarm() {
        val defaultTime = Calendar.getInstance().nowPlus(1)
        val weekInfo = dataBinding.getWeekInfo()
        val listWeekDays: ArrayList<WeekDays> = ArrayList<WeekDays>()
        if (listDayOfWeek.isNullOrEmpty()) {
            listWeekDays.add(WeekDays.MONDAY)
            listWeekDays.add(WeekDays.TUESDAY)
            listWeekDays.add(WeekDays.WEDNESDAY)
            listWeekDays.add(WeekDays.THURSDAY)
            listWeekDays.add(WeekDays.FRIDAY)
            listWeekDays.add(WeekDays.SATURDAY)
            listWeekDays.add(WeekDays.SUNDAY)
        }
        for (i in listDayOfWeek) {
            if (i.isSelect) {
                when (i.day) {
                    "Monday" -> {
                        weekInfo.monday = true
                        listWeekDays.add(WeekDays.MONDAY)
                    }
                    "Tuesday" -> {
                        weekInfo.tuesday = true
                        listWeekDays.add(WeekDays.TUESDAY)
                    }
                    "Wednesday" -> {
                        weekInfo.wednesday = true
                        listWeekDays.add(WeekDays.WEDNESDAY)
                    }
                    "Thursday" -> {
                        weekInfo.thursday = true
                        listWeekDays.add(WeekDays.THURSDAY)
                    }
                    "Friday" -> {
                        weekInfo.friday = true
                        listWeekDays.add(WeekDays.FRIDAY)
                    }
                    "Saturday" -> {
                        weekInfo.saturday = true
                        listWeekDays.add(WeekDays.SATURDAY)
                    }
                    "Sunday" -> {
                        weekInfo.sunday = true
                        listWeekDays.add(WeekDays.SUNDAY)
                    }
                }
            }
        }
        if (voicePath.isNullOrEmpty()){
            val libraryItem = getFileAudio()[0]
            dataBinding.tvDefault.text = libraryItem.title
            voicePath = libraryItem.path
            nameVoice = libraryItem.title
        }
        if (defaultTime.second < timePicker.minute || defaultTime.second > timePicker.minute) {
            val alarmItem = voicePath?.let {
                AlarmItem(
                    hour = timePicker.hour,
                    minute = timePicker.minute,
                    weekDays = listWeekDays,
                    isActive = true,
                    isVibrate = switchAlarm.isChecked,
                    nameAlarm = nameAlarm ?: getString(R.string.app_name),
                    voicePath = it,
                )
            }
            alarmItem?.let {
                if (isUpdate) {
                    it.requestId = requestId
                    viewModel.updateAlarm(alarmItem = it, requireContext())
                    onBackPress()
                } else {
                    viewModel.setFullScreenIntentAlarm(
                        alarmItem = it,
                        requireContext()
                    )
                    onBackPress()
                }
            }
            dataBinding.toastAlarm(defaultTime.first, defaultTime.second)
        } else {
            Toast.makeText(requireContext(), "Please select second", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 1. Only Once -> current day
     * 2. Every Day
     * -----------
     * 1. Monday
     * 2. Tuesday
     * 3. Wednesday
     * 4. Thursday
     * 5. Friday
     * 6. Saturday
     * 7. Sunday
     */
    private fun FragmentAddAlarmBinding.getWeekInfo(): WeekInfo {
        // if Only once -> get current day
        return WeekInfo(false, false, false, false, false, false, false)
    }


    /**
     * Hour + minute: alarm wakeup
     */
    private fun FragmentAddAlarmBinding.toastAlarm(hour: Int, minute: Int) {
        var toastText = "$hour:$minute - set alarm"
        Toast.makeText(requireContext(), toastText, Toast.LENGTH_SHORT).show()
    }

    private fun getFileAudio(): ArrayList<LibraryItem> {
        val audio: ArrayList<LibraryItem> = ArrayList<LibraryItem>()
        val cusor: Cursor? = requireContext().contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, arrayOf(
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA
            ), null, null, null
        )

        while (cusor!!.moveToNext()) {
            val name = cusor.getString(cusor.getColumnIndex(MediaStore.Audio.Media.TITLE))
            val id = cusor.getString(cusor.getColumnIndex(MediaStore.Audio.Media._ID))
            val path = cusor.getString(cusor.getColumnIndex(MediaStore.Audio.Media.DATA))
            audio.add(LibraryItem(id, name, false, path))
        }
        return audio
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).showBottomNavigation()
        super.onDestroy()
    }
}