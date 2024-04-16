package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.addOwnTasbih

import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentAddOwnTasbihBinding


class AddOwnTasbihFragment :
    BaseFragment<FragmentAddOwnTasbihBinding>(R.layout.fragment_add_own_tasbih) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            addOwnTasbihFragment =
                this@AddOwnTasbihFragment
        }
        val name = binding.edtName
        val stringNum = String.format(getString(R.string.name), "*")
        val startPosition = 3
        val endPosition = stringNum.length
        val spannableStr = SpannableString(stringNum)
        spannableStr.setSpan(
            R.color.about_us_text_color,
            startPosition,
            endPosition,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
      //  name.text = spannableStr as Editable
    }
}