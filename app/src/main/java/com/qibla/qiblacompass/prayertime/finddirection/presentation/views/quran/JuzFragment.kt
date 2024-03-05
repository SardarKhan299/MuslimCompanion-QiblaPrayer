package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quran

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentJuzBinding

class JuzFragment : BaseFragment<FragmentJuzBinding>(R.layout.fragment_juz) {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(JuzFragment::class.java.simpleName, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            juzFragment = this@JuzFragment
        }
        recyclerView = binding.recyclerViewQuranJuz
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL, false
        )
        val data = ArrayList<JuzData>()
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))
        data.add(JuzData("1", "Juz 1", "1-2"))

        val adapter = JuzAdapter(data)
        recyclerView.adapter = adapter
    }

}