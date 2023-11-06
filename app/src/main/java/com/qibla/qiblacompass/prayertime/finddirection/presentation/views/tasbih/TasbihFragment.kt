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
////        val data = ArrayList<TasbihZhikrData>()
////        data.add(TasbihZhikrData("Subhan Allah", R.drawable.ic_subhan_allah))
////        data.add(TasbihZhikrData("Alhamdulillah", R.drawable.allhamdulillah))
////        data.add(TasbihZhikrData("la ilaha illa Allah", R.drawable.laillaha))
////        data.add(TasbihZhikrData("Allahu Akbar", R.drawable.allahoakbar))
//
//
////        val adapter = TasbihZhikrAdapter(data)
////        recyclerView.adapter = adapter
//
//        val adapter = TasbihZhikrAdapter() { selectedImageName ->
//            SharedPreferences.saveImageValue(selectedImageName.toString())
//            Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
//        }

//        recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        recyclerView.adapter = adapter

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