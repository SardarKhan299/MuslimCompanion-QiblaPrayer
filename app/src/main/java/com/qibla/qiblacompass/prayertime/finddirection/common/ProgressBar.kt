package com.qibla.qiblacompass.prayertime.finddirection.common


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.qibla.qiblacompass.prayertime.finddirection.R

class ProgressBar {
    companion object {
        private var alertDialog: AlertDialog? = null
        fun showProgressBar(context: Context, loadText: String) {
            val dialogBuilder = AlertDialog.Builder(context)
            val dialogView = LayoutInflater.from(context).inflate((R.layout.anim_layout), null)
            dialogBuilder.setView(dialogView)
            val radioGroupChat = dialogView.findViewById<TextView>(R.id.loading_msg)
            radioGroupChat.text = loadText
            alertDialog = dialogBuilder.create()
            alertDialog?.setCanceledOnTouchOutside(false)
            alertDialog?.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            alertDialog?.setCancelable(false)
            alertDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
        }

        fun hideProgressBar() {
            try {
                if (alertDialog?.isShowing!!) {
                    alertDialog?.dismiss()
                }
            } catch (e: Exception) {
                alertDialog?.dismiss()
                alertDialog = null

            }
        }
    }
}