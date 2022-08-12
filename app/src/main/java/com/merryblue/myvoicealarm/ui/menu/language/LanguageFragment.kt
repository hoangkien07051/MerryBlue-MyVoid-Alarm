package com.merryblue.myvoicealarm.ui.menu.language

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.merryblue.myvoicealarm.MainActivity
import com.merryblue.myvoicealarm.R
import com.merryblue.myvoicealarm.common.pref.SharedPrefsHelper
import com.merryblue.myvoicealarm.custom.ToolbarCustomer
import com.merryblue.myvoicealarm.databinding.FragmentLanguageBinding
import java.util.*

class LanguageFragment() : Fragment(R.layout.fragment_language), Parcelable {
    private var _binding: FragmentLanguageBinding? = null

    private val binding get() = _binding!!
    private val lstLanguage = ArrayList<Language>()
    private var adapter: ChangeLanguageAdapter? = null
    private var position : Int = -1
    private var lang : Language?= null
    private var firsTimeInApp = false

    constructor(parcel: Parcel) : this() {
        position = parcel.readInt()
    }

    constructor(arg1: Boolean) : this() {
        firsTimeInApp = arg1;
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLanguageBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        val language: String = SharedPrefsHelper.getString(requireContext(),SharedPrefsHelper.SETTING_LANGUAGE,"") ?: ""
        if (language.isNotEmpty()){
            for (i in 0 until lstLanguage.size){
                if (language == lstLanguage[i].value){
                    position = i
                    lang = lstLanguage[position]
                    lang?.selected = true
                    break
                }
            }
        }

        lang?.let {
            lstLanguage[position] = it
            adapter?.updateData(lstLanguage)
        }

        binding.toolbar.setTextBtnNext(getString(R.string.next))
        binding.toolbar.setListener(object : ToolbarCustomer.Listener {
            override fun onClick() {
                    SharedPrefsHelper.saveString(
                        requireContext(),
                        SharedPrefsHelper.MyPREFERENCES,
                        SharedPrefsHelper.SETTING_LANGUAGE,
                        lang?.value
                    )
                    Handler(Looper.getMainLooper()).postDelayed({
                        /* Create an Intent that will start the Menu-Activity. */
                        startActivity(Intent(requireActivity(), MainActivity::class.java))
                        requireActivity().finish()
                    }, 500)
            }
        })
    }

    private fun initData() {

        if (firsTimeInApp){
            binding.toolbar.visibility = View.VISIBLE

        }
        lstLanguage.add(Language(getString(R.string.txt_language_en), false, "en",R.drawable.flag_en))
        lstLanguage.add(Language(getString(R.string.txt_language_chi), false, "za",R.drawable.flag_cn))
        lstLanguage.add(Language(getString(R.string.txt_language_ja), false, "ja",R.drawable.flag_jp))
        lstLanguage.add(Language(getString(R.string.txt_language_vi), false, "vi",R.drawable.flag_vi))
        lstLanguage.add(Language(getString(R.string.txt_language_spanish), false, "es",R.drawable.flag_sp))
        lstLanguage.add(Language(getString(R.string.txt_language_portuguese), false, "pt",R.drawable.flag_ft))
        lstLanguage.add(Language(getString(R.string.txt_language_russiane), false, "ru",R.drawable.flag_rs))
        lstLanguage.add(Language(getString(R.string.txt_language_korean), false, "ko",R.drawable.flag_kr))
        lstLanguage.add(Language(getString(R.string.txt_language_french), false, "fr",R.drawable.flag_fr))
        lstLanguage.add(Language(getString(R.string.txt_language_german), false, "de",R.drawable.flag_gr))
        adapter = ChangeLanguageAdapter(requireContext()) {
            lang = it
        }
        adapter?.updateData(lstLanguage)
        binding.rclEnglish.adapter = adapter
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(position)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LanguageFragment> {
        override fun createFromParcel(parcel: Parcel): LanguageFragment {
            return LanguageFragment(parcel)
        }

        override fun newArray(size: Int): Array<LanguageFragment?> {
            return arrayOfNulls(size)
        }
    }
}