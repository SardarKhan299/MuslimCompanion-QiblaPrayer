package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.addOwnTasbih

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.common.invisible
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
        val toolbar = binding.toolbarAddOwnTasbih
        toolbar.groupToolbarTasbihCounter.visibility = View.VISIBLE
        toolbar.titleCounter.text = getString(R.string.add_own_tasbih)
        toolbar.imgNavigateBack.setOnClickListener {
            findNavController().closeCurrentScreen()
        }
        toolbar.imgAddMore.invisible()


        val nameInputLayout = binding.layoutTextTasbihName
        val stringNum = String.format(getString(R.string.name), "*")
        val startPosition = 4
        val endPosition = stringNum.length
        val spannableStr = SpannableString(stringNum)
        spannableStr.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    mContext,
                    R.color.zakat_point_heading_text_color
                )
            ),
            startPosition,
            endPosition,
            Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
        nameInputLayout.hint = spannableStr

    }
}