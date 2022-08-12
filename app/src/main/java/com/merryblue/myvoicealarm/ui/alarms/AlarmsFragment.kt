package com.merryblue.myvoicealarm.ui.alarms

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.extenstion.observe
import com.merryblue.myvoicealarm.common.extenstion.show
import com.merryblue.myvoicealarm.common.pref.VoiceAlarmPref
import com.merryblue.myvoicealarm.databinding.FragmentAlarmsBinding
import com.merryblue.myvoicealarm.ui.adapter.AlarmsAdapter
import com.merryblue.myvoicealarm.ui.base.BaseFragment
import com.merryblue.myvoicealarm.ui.base.DeleteDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import de.coldtea.smplr.smplralarm.models.AlarmItem
import de.coldtea.smplr.smplralarm.models.WeekInfo
import kotlinx.android.synthetic.main.fragment_alarms.*

@AndroidEntryPoint
class AlarmsFragment : BaseFragment() {

    private val viewModel by viewModels<AlarmsViewModel>()
    private lateinit var dataBinding: FragmentAlarmsBinding

    private var listAlarms: ArrayList<AlarmItem> = arrayListOf()
    private lateinit var mAlarmsAdapter: AlarmsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dataBinding = FragmentAlarmsBinding.inflate(inflater)
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = viewModel
        viewModel.initAlarmListListener(requireContext().applicationContext)
        viewModel.initAllAlarmListListener(requireContext().applicationContext)
        return dataBinding.root
    }

    override fun setUpView() {
        mAlarmsAdapter = AlarmsAdapter(requireContext(), listAlarms, object : AlarmsAdapter.Iteractor {
            override fun onClickItem(model: AlarmItem, position: Int) {
                //TODO view detail ??
//                Toast.makeText(requireContext(), "Show Detail Alarm??", Toast.LENGTH_LONG).show()

                val bundle = Bundle()
                bundle.putParcelable("alarm", model)
                findNavController().navigate(R.id.navigation_add_alarm, bundle)
            }

            override fun onUpdateAlarm(model: AlarmItem, position: Int) {
                // on/of alarm
                if (model.isActive) {
                    model.weekDays.forEach { weekDay ->
                        //update weekInfo
                    }
                    val weekInfo = WeekInfo(false, true, false, false, false, false, false)
                    viewModel.updateAlarm(model.requestId, model.hour, model.minute, weekInfo, model.isActive, requireContext())
                } else {
                    // cancel alarm by Id
                    viewModel.cancelAlarm(model.requestId, requireContext())
                }
            }

            override fun onRemoveAlarm(model: AlarmItem, position: Int) { //Long Clicks
                DeleteDialogFragment("") { isDelete ->
                    if (isDelete) {
                        viewModel.cancelAlarm(model.requestId, requireContext())
                        //TODO listAlarms remove this item
                        listAlarms.removeAt(position)
                        mAlarmsAdapter.notifyItemRemoved(position)
                    }
                }.show(childFragmentManager, "delete")

            }

        })
        rvAlarm.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAlarmsAdapter
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observable() {
        observe(viewModel.alarmData) { data ->

        }
        observe(viewModel.userData) { data ->

        }
        // Data Alarm List
        viewModel.alarmAllList.observe(viewLifecycleOwner) {
            listAlarms.clear()
            listAlarms.addAll(it)
            VoiceAlarmPref.setListAlarms(requireContext(), listAlarms)
            mAlarmsAdapter.notifyDataSetChanged()
            llEmpty.show(listAlarms.isEmpty())
            rvAlarm.show(listAlarms.isNotEmpty())
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun fireData() {
        viewModel.requestAlarmList()
    }

}