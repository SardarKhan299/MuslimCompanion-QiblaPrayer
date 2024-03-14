package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quranSurahDetail

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentQuranSurahDetailBinding


class QuranSurahDetailFragment :
    BaseFragment<FragmentQuranSurahDetailBinding>(R.layout.fragment_quran_surah_detail) {

    lateinit var themeImageOne: ImageView
    lateinit var themeImageTwo: ImageView
    lateinit var themeImageThree: ImageView
    lateinit var fontImageView: ImageView
    lateinit var imgTextIncrease: ImageView
    lateinit var imgTextDecrease: ImageView
    lateinit var imgTextArabic: ImageView
    lateinit var readerModeSwitch: SwitchCompat
    lateinit var adapter: QuranSurahDetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()
        Log.d(QuranSurahDetailFragment::class.java.simpleName, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            quranSurahDetailFragment = this@QuranSurahDetailFragment
        }
        binding.toolbarQuranSurahDetail.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarQuranSurahDetail.tvToolbarSubScreen.text = "Quran"
        binding.toolbarQuranSurahDetail.viewSubScreen.setOnClickListener {
           // findNavController().navigate(R.id.quranFragment)
           findNavController().navigate(R.id.action_quranSurahDetailFragment_to_quranFragment)
        }

        val recyclerView: RecyclerView = binding.recyclerViewQuranSurahDetail


        val surahList = ArrayList<QuranSurahDetailData>().apply {
            add(
                QuranSurahDetailData(
                    "بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمَِۙ",
                    "اللہ کے نام سے شروع جو نہایت مہربان ہمیشہ رحم فرمانے والا ہے",
                    "In the Name of Allah, the Most Compassionate, the Ever-Merciful"
                )
            )
            add(
                QuranSurahDetailData(
                    "اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِیْنَۙ",
                    "سب تعریفیں اللہ ہی کے لئے ہیں جو تمام جہانوں کی پرورش فرمانے والا ہے",
                    "All praise be to Allah alone, the Sustainer of all the worlds"
                )
            )
            add(
                QuranSurahDetailData(
                    "الرَّحْمٰنِ الرَّحِیْمَِۙۙ",
                    " نہایت مہربان بہت رحم فرمانے والا ہے",
                    "Most Compassionate, Ever-Merciful"
                )
            )
            add(
                QuranSurahDetailData(
                    " مٰلِكِ یَوْمِ الدِّیْنَِؕۙ",
                    "روزِ جزا کا مالک ہے, ",
                    "Master of the Day of Judgment."
                )
            )
            add(
                QuranSurahDetailData(
                    "اِیَّاكَ نَعْبُدُ وَ اِیَّاكَ نَسْتَعِیْنَُۙ",
                    " (اے اللہ!) ہم تیری ہی عبادت کرتے ہیں اور ہم تجھ ہی سے مدد چاہتے ہیں",
                    "(O Allah!) You alone do we worship and to You alone do we look for help."
                )
            )
            add(
                QuranSurahDetailData(
                    "اِهْدِنَا الصِّرَاطَ الْمُسْتَقِیْمََۙۙ",
                    "ہمیں سیدھا راستہ دکھا",
                    "Show us the straight path,"
                )
            )
            add(
                QuranSurahDetailData(
                    "صِرَاطَ الَّذِیْنَ اَنْعَمْتَ عَلَیْهِمۦَۙ",
                    "ان لوگوں کا راستہ جن پر تو نے انعام فرمایا",
                    "The path of those upon whom You have bestowed Your favours,"
                )
            )
            add(
                QuranSurahDetailData(
                    "غَیْرِ الْمَغْضُوْبِ عَلَیْهِمْ وَ لَا الضَّآلِّیْنَ۠",
                    "ان لوگوں کا نہیں جن پر غضب کیا گیا ہے اور نہ (ہی) گمراہوں کا",
                    "Not of those who have been afflicted with wrath, nor of those who have gone astray."
                )
            )

        }


        val layoutManager = LinearLayoutManager(mContext)
        adapter = QuranSurahDetailAdapter(surahList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


        binding.viewPage.setOnClickListener {
            showBottomSheetDisplayTheme()
        }
    }

    private fun showBottomSheetDisplayTheme() {
        val bottomSheetView =
            View.inflate(requireContext(), R.layout.bottom_sheet_quran_display_theme, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(bottomSheetView)
        themeImageOne = bottomSheetView.findViewById(R.id.img_theme_one)
        themeImageTwo = bottomSheetView.findViewById(R.id.img_theme_two)
        themeImageThree = bottomSheetView.findViewById(R.id.img_theme_three)

        fontImageView = bottomSheetView.findViewById(R.id.img_font_size)
        imgTextIncrease = bottomSheetView.findViewById(R.id.img_text_increase)
        imgTextDecrease = bottomSheetView.findViewById(R.id.img_text_decrease)
        imgTextArabic = bottomSheetView.findViewById(R.id.img_text_arabic)

        imgTextDecrease.setOnClickListener {
            val newSize = adapter.decreaseFontSize(5f) // Adjust the decrement as needed

            // Ensure the font size does not go below the minimum value
            if (newSize < MIN_FONT_SIZE) {
                adapter.setFontSize(MIN_FONT_SIZE.toFloat())
            }
        }
        imgTextIncrease.setOnClickListener {
            // Increase the font size by a certain amount
            val newSize = adapter.increaseFontSize(5f) // Adjust the increment as needed

            // Ensure the font size does not exceed the maximum value
            if (newSize > MAX_FONT_SIZE) {
                adapter.setFontSize(MAX_FONT_SIZE.toFloat())
            }
        }

        readerModeSwitch = bottomSheetView.findViewById(R.id.switch_reader_mode)
        themeImageOne.setOnClickListener {
            binding.layoutQuranSurahDetailFragment.setBackgroundResource(R.drawable.ic_quran_background_one)
            bottomSheetDialog.dismiss()
        }
        themeImageTwo.setOnClickListener {
            binding.layoutQuranSurahDetailFragment.setBackgroundResource(R.drawable.ic_quran_background_two)
            bottomSheetDialog.dismiss()
        }
        themeImageThree.setOnClickListener {
            binding.layoutQuranSurahDetailFragment.setBackgroundResource(R.drawable.bg)
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
        val seekBarFontSize = bottomSheetView.findViewById<SeekBar>(R.id.seekBarFontSize)
        seekBarFontSize.thumb.setTint(ContextCompat.getColor(mContext, R.color.goal_text_color))
        seekBarFontSize.progressDrawable.setTint(
            ContextCompat.getColor(
                mContext,
                R.color.primary_color
            )
        )

        seekBarFontSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val fontSize = MIN_FONT_SIZE + (MAX_FONT_SIZE - MIN_FONT_SIZE) * (progress / 100f)
                adapter.setFontSize(fontSize)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

    }

    companion object {
        private const val MIN_FONT_SIZE = 10
        private const val MAX_FONT_SIZE = 100
    }
}
