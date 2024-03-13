package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.quranSurahDetail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentQuranSurahDetailBinding


class QuranSurahDetailFragment :
    BaseFragment<FragmentQuranSurahDetailBinding>(R.layout.fragment_quran_surah_detail) {


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
            findNavController().navigate(R.id.quranFragment)
        }

        val recyclerView: RecyclerView = binding.recyclerViewQuranSurahDetail


        val surahList = ArrayList<QuranSurahDetailData>().apply {
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
        val adapter = QuranSurahDetailAdapter(surahList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }


}
