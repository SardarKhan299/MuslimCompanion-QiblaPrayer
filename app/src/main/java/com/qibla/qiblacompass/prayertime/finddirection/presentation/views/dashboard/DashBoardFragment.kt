package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentDashBoardBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.sidemenu.SideMenuFragment


class DashBoardFragment : BaseFragment<FragmentDashBoardBinding>(R.layout.fragment_dash_board) {
    private lateinit var rView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(DashBoardFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            boardFragment = this@DashBoardFragment
        }
        binding.toolbarBoard.groupToolbarProfile.visibility = View.GONE
        binding.toolbarBoard.groupToolbar.visibility = View.VISIBLE
        rView = binding.layoutBoardFragment.findViewById(R.id.rv_qibla)
        binding.toolbarBoard.imgToolbar.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.sideMenuFragment)
        }
        val dashBoardFajrScreen =
            binding.layoutBoardFragment.findViewById<ImageView>(R.id.img_fajr_screen)

        dashBoardFajrScreen.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.nextPrayerTimeFragment)
        }
        rView.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        setUpQibla()
    }

    private fun setUpQibla() {
        val data = ArrayList<QiblaData>()
        data.add(QiblaData(R.drawable.ic_compass_frame, "Qibal"))
        data.add(QiblaData(R.drawable.ic_zakat_frame, "Zakat"))
        data.add(QiblaData(R.drawable.ic_name_frame, "Names"))
        data.add(QiblaData(R.drawable.ic_tasbih_frame, "Tasbih"))
        data.add(QiblaData(R.drawable.ic_prayer_frame, "Prayer"))
        data.add(QiblaData(R.drawable.ic_quran_frame, "Quran"))
        data.add(QiblaData(R.drawable.ic_makkah_frame, "Makkah Live"))
        data.add(QiblaData(R.drawable.ic_near_me_frame, "Near me"))
        data.add(QiblaData(R.drawable.ic_calendar_frame, "Hijri Calendar"))
        data.add(QiblaData(R.drawable.ic_hathid, "Hadith"))


        val adapter = QiblaAdapter(requireContext(), data, itemClickListenerCallback())
        rView.adapter = adapter

    }

    private fun itemClickListenerCallback(): (QiblaData) -> Unit {
        return {
            Log.d(DashBoardFragment::class.simpleName, "ok_btn_callback:")

        }
    }

}