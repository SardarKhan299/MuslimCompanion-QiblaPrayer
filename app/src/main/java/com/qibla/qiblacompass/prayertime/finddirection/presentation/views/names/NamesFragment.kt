package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.visible
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNamesBinding
import com.qibla.qiblacompass.prayertime.finddirection.presentation.views.tasbihcounter.TasbihCounterFragment


class NamesFragment : BaseFragment<FragmentNamesBinding>(R.layout.fragment_names) {
    lateinit var recyclerView: RecyclerView

    private val imageResource = listOf(
        R.drawable.ic_name_one,
        R.drawable.ic_name_two,
        R.drawable.ic_name_three
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(NamesFragment::class.simpleName, "onCreate: ")
        (activity as AppCompatActivity?)?.hideActionBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            namesFragment = this@NamesFragment
        }
        val toolbar = binding.toolbarNames
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.names)
        recyclerView = binding.recyclerViewNames
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }


        binding.viewRasoolNames.setOnClickListener {
            if (binding.viewRasoolNames.visibility == View.VISIBLE) {
                binding.viewRasoolNames.visibility = View.INVISIBLE
                binding.tvRasoolNames.setTextAppearance(
                    mContext,
                    R.style.forgot_password_text_style
                )
                //}
            }
            binding.viewAllahNames.setOnClickListener {
                binding.viewAllahNames.visibility = View.GONE
            }
            binding.viewRasoolNames.setOnClickListener {
                if (binding.viewRasoolNames.visibility == View.INVISIBLE) {
                    binding.viewRasoolNames.visibility = View.VISIBLE
                    binding.tvRasoolNames.setTextAppearance(
                        mContext,
                        R.style.advertisement_text_style
                    )
                } else {
                    binding.viewRasoolNames.visibility = View.INVISIBLE
                    binding.tvRasoolNames.setTextAppearance(
                        mContext,
                        R.style.forgot_password_text_style
                    )

                }
            }
            val adapter = NamesAdapter(imageResource) {
            }
            // Set up RecyclerView with GridLayoutManager
            val gridLayoutManager =
                GridLayoutManager(mContext, 2) // Change the span count as needed
            recyclerView.layoutManager = gridLayoutManager
            recyclerView.adapter = adapter
        }
    }
}