package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.zakat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.AdUtil
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentZakatBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.dashboard.DashBoardFragment


class ZakatFragment : BaseFragment<FragmentZakatBinding>(R.layout.fragment_zakat) {
    lateinit var recyclerView: RecyclerView
    private lateinit var adView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            zakatFragment = this@ZakatFragment
        }
        MobileAds.initialize(mContext) {
            Log.d(
                DashBoardFragment::class.java.simpleName,
                "onViewCreated: onInitializationCompleted"
            )
        }
        adView = binding.adsBannerZakat
        // Initialize AdUtil and load the banner ad
        AdUtil.initialize(requireContext(), adView)
        binding.toolbarZakat.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarZakat.tvToolbarSubScreen.text = getString(R.string.zakat_calculator)
        binding.toolbarZakat.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        binding.btnCalculateZakat.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.zakatCalculateFragment)
        }
        recyclerView = binding.layoutZakatFragment.findViewById(R.id.recycler_view_zakat)
        val gridLayoutManager =
            GridLayoutManager(mContext, 3) // Adjust the number of columns as needed
        recyclerView.layoutManager = gridLayoutManager

        val data = ArrayList<ZakatData>()
        data.add(
            ZakatData(
                R.drawable.ic_gold,
                getString(R.string.gold),
                getString(R.string._10_tola)
            )
        )
        data.add(ZakatData(R.drawable.ic_silver, getString(R.string.Silver), "5 Tola"))
        data.add(ZakatData(R.drawable.ic_stones, getString(R.string.Stones), "0.9 million"))
        data.add(ZakatData(R.drawable.ic_agriculture, getString(R.string.agriculture), "2.3 million"))
        data.add(ZakatData(R.drawable.ic_property, getString(R.string.property), "1.5 million"))
        data.add(ZakatData(R.drawable.ic_business, getString(R.string.business), "1.2 million"))
        data.add(ZakatData(R.drawable.ic_partnership, getString(R.string.partnership), "1000 PKR"))
        data.add(ZakatData(R.drawable.ic_loan, getString(R.string.loan), "5 million"))

        val adapter = ZakatAdapter(data){
            findNavController().navigate(R.id.zakatCalculateFragment)
        }
        recyclerView.adapter = adapter
    }
    override fun onResume() {
        adView.resume()
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }
    companion object{
        private val TAG = "ZakatFragment"
    }
}