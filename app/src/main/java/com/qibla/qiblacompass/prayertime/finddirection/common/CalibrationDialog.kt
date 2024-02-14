package com.qibla.qiblacompass.prayertime.finddirection.common

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.qibla.qiblacompass.prayertime.finddirection.R

class CalibrationDialog : DialogFragment() {
    override fun onStart() {
        super.onStart()
        Log.d("CalibrationDialog"::class.simpleName, "onStart: ")
        dialog?.setCancelable(false)
        val window: Window = dialog?.window!!
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView: View = inflater.inflate(
            R.layout.calibration_layout,
            container,
            false)


        rootView.findViewById<ImageView>(R.id.close).setOnClickListener {
            dismiss()
        }
        return rootView
    }
}