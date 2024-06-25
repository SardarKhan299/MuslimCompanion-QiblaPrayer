package com.qibla.qiblacompass.prayertime.finddirection.base

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.qibla.qiblacompass.prayertime.finddirection.common.FullScreenAdUtil

abstract class BaseFragment<VDB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {

    private var _binding: VDB? = null
    protected val binding: VDB get() = _binding!!
    protected lateinit var mContext: Context


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate Method
        Log.d(BaseFragment::class.simpleName, "onCreateView: ")
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        // Optionally set lifecycle owner if needed
        binding.lifecycleOwner = viewLifecycleOwner
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Show ad before displaying fragment content
        binding.root.visibility = View.GONE
        FullScreenAdUtil.showInterstitialAd(requireActivity()) {
            // Show fragment content after ad is closed
            binding.root.visibility = View.VISIBLE
        }

        return binding.root
    }

    // Removing the binding reference when not needed is recommended as it avoids memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(BaseFragment::class.simpleName, "onDestroyView: ")
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(BaseFragment::class.simpleName, "onAttach: ")
        mContext = context
    }


}
