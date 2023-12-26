package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard

import android.content.Intent
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
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding.OnboardingActivity
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.qibaldirection.CompassDirectionActivity
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
        binding.tvAds.setOnClickListener {
            //Navigation.findNavController(requireView()).navigate(R.id.tasbihCounterFragment)
            startActivity(Intent(mContext, CompassDirectionActivity::class.java))

        }
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
        data.add(QiblaData(R.drawable.qibla_icn, "Qibal"))
        data.add(QiblaData(R.drawable.zakat_icn, "Zakat"))
        data.add(QiblaData(R.drawable.names_icn, "Names"))
        data.add(QiblaData(R.drawable.tasbeeh_icn, "Tasbih"))
        data.add(QiblaData(R.drawable.prayer_icn, "Prayer"))
        data.add(QiblaData(R.drawable.quran_icn, "Quran"))
        data.add(QiblaData(R.drawable.makah_live_icn, "Makkah Live"))
        data.add(QiblaData(R.drawable.near_me_icn, "Near me"))
        data.add(QiblaData(R.drawable.hijri_calendar_icn, "Hijri Calendar"))
        data.add(QiblaData(R.drawable.hadith_icn, "Hadith"))


        val adapter = QiblaAdapter(requireContext(), data) { position ->
            navigateToSpecificScreen(position)
        }
        rView.adapter = adapter

    }

    private fun navigateToSpecificScreen(position: Int) {
        // Navigate to specific screens based on position clicked
        when (position) {
            0 -> {
                // Handle click on position 0
                // Navigate to screen 0
                Navigation.findNavController(requireView()).navigate(R.id.qibalDirectionFragment)

            }
            1 -> {

                Navigation.findNavController(requireView()).navigate(R.id.zakatFragment)
            }
            3 -> {

                Navigation.findNavController(requireView()).navigate(R.id.tasbihFragment)
            }
            6->{
                Navigation.findNavController(requireView()).navigate(R.id.makkahLiveFragment)

            }
            // Add more cases for other positions
        }
    }

}