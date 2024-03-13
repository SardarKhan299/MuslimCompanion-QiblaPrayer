package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentSuraBinding


class SuraFragment : BaseFragment<FragmentSuraBinding>(R.layout.fragment_sura) {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
        Log.d(SuraFragment::class.java.simpleName, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            suraFragment = this@SuraFragment
        }
        recyclerView = binding.recyclerViewQuranSura
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        val data = ArrayList<SuraData>()
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))
        data.add(SuraData("1", "Al Faatiha", "The Opener", "الفاتهة"))


        val adapter = SuraAdapter(data){
            findNavController().navigate(R.id.quranSurahDetailFragment)
        }
        recyclerView.adapter = adapter

    }

}