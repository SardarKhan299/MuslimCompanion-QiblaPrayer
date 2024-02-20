package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbih

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALHAMDULILLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.ALLAHU_AKBAR
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.LA_ILAHA_ILLA_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.ApplicationConstant.Companion.SUBHAN_ALLAH
import com.qibla.qiblacompass.prayertime.finddirection.common.CommonMethods.Companion.getCurrentDateFormatted
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentTasbihBinding


class TasbihFragment : BaseFragment<FragmentTasbihBinding>(R.layout.fragment_tasbih) {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tasbihFragment = this@TasbihFragment
        }
        recyclerView = binding.layoutTasbihFragment.findViewById(R.id.recycler_view_zhikr)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.tvTasbihDate.text = getCurrentDateFormatted()

        val data = ArrayList<TasbihZhikrData>()
        data.add(TasbihZhikrData(SUBHAN_ALLAH, R.drawable.ic_subhan_allah))
        data.add(TasbihZhikrData(ALHAMDULILLAH, R.drawable.allhamdulillah))
        data.add(TasbihZhikrData(LA_ILAHA_ILLA_ALLAH, R.drawable.laillaha))
        data.add(TasbihZhikrData(ALLAHU_AKBAR, R.drawable.allahoakbar))

        val adapter = TasbihZhikrAdapter(data) { selectedImageName ->
            SharedPreferences.saveImageValue(requireContext(),selectedImageName)
            Log.d("TasbihFragment", "onViewCreated: $selectedImageName")
            Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
        }
        recyclerView.adapter = adapter

        binding.imgTasbihClose.setOnClickListener {
            findNavController().closeCurrentScreen()

        }
    }


}