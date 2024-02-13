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

class PopUpDialog(var title : String,
                  var message : String,
                  val ok_btn_callback: (String) -> Unit,
                  val error_icn: Int = 0,
                  val isSecondButtonRequired: Boolean = false,
                  ) : DialogFragment() {
    override fun onStart() {
        super.onStart()
        Log.d("PopUpDialog"::class.simpleName, "onStart: ")
        dialog?.setCancelable(false)
        val window: Window = dialog?.window!!
        window.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(
            R.layout.fragment_popup_dialog,
            container,
            false)
        rootView.findViewById<TextView>(R.id.tv_dialog_title).text = title
        rootView.findViewById<TextView>(R.id.tv_dialog_detail).text = message

        if(!isSecondButtonRequired){
            rootView.findViewById<TextView>(R.id.tv_dialog_cancel).visibility = View.GONE
        }

        rootView.findViewById<Button>(R.id.btn_dialog_yes).setOnClickListener {
            dismiss()
            ok_btn_callback.invoke("okay")
        }
        rootView.findViewById<TextView>(R.id.tv_dialog_cancel).setOnClickListener {
            dismiss()
        }
        return rootView
    }
}