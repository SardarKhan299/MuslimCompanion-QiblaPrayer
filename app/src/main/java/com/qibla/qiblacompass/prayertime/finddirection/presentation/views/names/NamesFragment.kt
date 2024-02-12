package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.names

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences
import com.qibla.qiblacompass.prayertime.finddirection.common.SharedPreferences.Companion.allahNamesTranslations
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentNamesBinding


class NamesFragment : BaseFragment<FragmentNamesBinding>(R.layout.fragment_names) {
    lateinit var recyclerView: RecyclerView
    val position = Int

    // Lists to store data
    private lateinit var imageResource: ArrayList<NamesData>
    private lateinit var imageResourceRasool: ArrayList<NamesData>

    private lateinit var adapter: NamesAdapter

    // SharedPreferences key
    private val PREFS_KEY = "selected_data"
    private val PREFS_SELECTED_KEY = "isAllahNamesSelected"
  //  private val allahNamesTranslations = arrayListOf<Pair<String, String>>(
//        "انتہائی مہربانُ" to "The Most Gracious",
//        "انتہائی رحم کرنے والاُ" to "The Most Merciful",
//        "مالک، بادشاہُ" to "The Owner, The King, The Ruler",
//        "مقدس، پاک، عیبوں سے پاکُ" to "The Absolutely Pure, The most Holy, The Most sacred",
//        "السَّلَامُ" to "The Source of Peace",
//        "الْمُؤْمِنُ" to "The Inspirer of Faith",
//        "الْمُهَيْمِنُ" to "The Guardian",
//        "الْعَزِيزُ" to "The Victorious",
//        "الْجَبَّارُ" to "The Compeller",
//        "الْمُتَكَبِّرُ" to "The Greatest",
//        "الْخَالِقُ" to "The Creator",
//        "الْبَارِئُ" to "The Maker of Order",
//        "الْمُصَوِّرُ" to "The Shaper of Beauty",
//        "الْغَفَّارُ" to "The Forgiving",
//        "الْقَهَّارُ" to "The Subduer",
//        "الْوَهَّابُ" to "The Giver of All",
//        "الرَّزَّاقُ" to "The Sustainer",
//        "الْفَتَّاحُ" to "The Opener",
//        "اَلْعَلِيْمُ" to "The Knower of All",
//        "الْقَابِضُ" to "The Constrictor",
//        "الْبَاسِطُ" to "The Reliever",
//        "الْخَافِضُ" to "The Abaser",
//        "الرَّافِعُ" to "The Exalter",
//        "الْمُعِزُّ" to "The Bestower of Honors",
//        "المُذِلُّ" to "The Humiliator",
//        "السَّمِيعُ" to "The Hearer of All",
//        "الْبَصِيرُ" to "The Seer of All",
//        "الْحَكَمُ" to "The Judge",
//        "الْعَدْلُ" to "The Just",
//        "اللَّطِيفُ" to "The Subtle One",
//        "الْخَبِيرُ" to "The All-Aware",
//        "الْحَلِيمُ" to "The Forbearing",
//        "الْعَظِيمُ" to "The Magnificent",
//        "الْغَفُورُ" to "The Forgiver and Hider of Faults",
//        "الشَّكُورُ" to "The Rewarder of Thankfulness",
//        "الْعَلِيُّ" to "The Highest",
//        "الْكَبِيرُ" to "The Greatest",
//        "الْحَفِيظُ" to "The Preserver",
//        "الْمُقِيتُ" to "The Nourisher",
//        "الْحَسِيبُ" to "The Accounter",
//        "الْجَلِيلُ" to "The Mighty",
//        "الْكَرِيمُ" to "The Generous",
//        "الرَّقِيبُ" to "The Watchful One",
//        "الْمُجِيبُ" to "The Responsive",
//        "الْوَاسِعُ" to "The Vast",
//        "الْحَكِيمُ" to "The Wise",
//        "الْوَدُودُ" to "The Loving",
//        "الْمَجِيدُ" to "The Majestic",
//        "الْبَاعِثُ" to "The Resurrector",
//        "الشَّهِيدُ" to "The Witness",
//        "الْحَقُّ" to "The Truth",
//        "الْوَكِيلُ" to "The Trustee",
//        "الْقَوِيُّ" to "The Possessor of All Strength",
//        "الْمَتِينُ" to "The Forceful One",
//        "الْوَلِيُّ" to "The Governor",
//        "الْحَمِيدُ" to "The Praised One",
//        "الْمُحْصِي" to "The Appraiser",
//        "الْمُبْدِئُ" to "The Originator",
//        "الْمُعِيدُ" to "The Restorer",
//        "الْمُحْيِي" to "The Giver of Life",
//        "الْمُمِيتُ" to "The Taker of Life",
//        "الْحَيُّ" to "The Ever-Living",
//        "الْقَيُّومُ" to "The Self-Existing",
//        "الْوَاجِبُ" to "The All-Perceiving"
//    )

//private val rasoolNamesTranslation = arrayListOf<Pair<String, String>>(
//    "زیادہ تعریف کیا گیا۔ تعریف والا" to "Highly praised",
//    "سب سے ذیادہ حمد کرنے والا" to "Highly commendable",
//    "بہت تعریف کرنےوالا، سراہنے والا" to "Praising, One who Praise",
//)

    // Flag to determine which set of data to display
    private val PREFS_SELECTED_KEY_ALLAH = "isAllahNamesSelected"
    private val PREFS_SELECTED_KEY_RASOOL = "isRasoolNamesSelected"

    private var isAllahNamesSelected = false
    private var isRasoolNamesSelected = false

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
        adapter = NamesAdapter(ArrayList(), ::onItemClick)
        // Initialize the lists with data
        imageResource = ArrayList()
        imageResource.add(NamesData(R.drawable.name_allah_one, R.drawable.ic_one_number))
        imageResource.add(NamesData(R.drawable.name_allah_two, R.drawable.ic_two_number))
        imageResource.add(NamesData(R.drawable.name_allah_three, R.drawable.ic_three_number))
        imageResource.add(NamesData(R.drawable.name_allah_four, R.drawable.ic_four_number))
        imageResource.add(NamesData(R.drawable.name_allah_five, R.drawable.ic_five_number))


        imageResourceRasool = ArrayList()
        imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_two, R.drawable.ic_one_number))
        imageResourceRasool.add(NamesData(R.drawable.ic_rasool_name_one, R.drawable.ic_two_number))

        // isAllahNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
        //  isRasoolNamesSelected = getSelectionFromSharedPreferences(PREFS_SELECTED_KEY_RASOOL)


        isAllahNamesSelected =
            SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)
        isRasoolNamesSelected =
            SharedPreferences.getSelectionFromSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
        // Set the initial data based on the stored preference
        if (isAllahNamesSelected) {
            adapter.setData(imageResource)
            updateViewStyles()
        } else if (isRasoolNamesSelected) {
            adapter.setData(imageResourceRasool)
            updateViewStyles()
        }

        // Set up the RecyclerView with a grid layout manager
        val spanCount = 2 // Adjust as needed
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.adapter = adapter

        view.findViewById<View>(R.id.viewAllahNames)

        binding.viewAllahNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated:view Name clicked.")
            isAllahNamesSelected = true
            isRasoolNamesSelected = false
            adapter.setData(imageResource)
            // saveSelectionToSharedPreferences(true) // Save the selection type
            SharedPreferences.saveSelectionToSharedPreferences(mContext, false)
            SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_ALLAH)

            //saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_ALLAH)
            updateViewStyles()
        }

        binding.viewRasoolNames.setOnClickListener {
            Log.d(NamesFragment::class.java.simpleName, "onViewCreated: view Rasool Name clicked.")
            isAllahNamesSelected = false
            isRasoolNamesSelected = true
            adapter.setData(imageResourceRasool)
            // saveSelectionToSharedPreferences(false) // Save the selection type
            SharedPreferences.saveSelectionToSharedPreferences(mContext, false)
            //  saveSelectionToSharedPreferences(PREFS_SELECTED_KEY_RASOOL)
            SharedPreferences.saveSelectionToSharedPreferences(mContext, PREFS_SELECTED_KEY_RASOOL)
            updateViewStyles()
        }
    }


    private fun onItemClick(namesData: NamesData) {

        val position = if (isAllahNamesSelected) {
            imageResource.indexOf(namesData)

        } else {
            imageResourceRasool.indexOf(namesData)
        }
        val translation = if (isAllahNamesSelected) {
            allahNamesTranslations[position]
        } else {
            SharedPreferences.rasoolNamesTranslation[position]
        }
        //save player click position.
        SharedPreferences.saveSelectedPlayerPosition(requireContext(), position)

        saveSelectedDataToSharedPreferences(namesData, translation)
        // Navigate to the detail screen
        findNavController().navigate(R.id.nameDetailFragment)
    }

    private fun saveSelectedDataToSharedPreferences(
        namesData: NamesData,
        translation: Pair<String, String>
    ) {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Access the integer values from namesData:
        editor.putInt("selected_name_image", namesData.nameImage)  // Use namesData.nameImage
        editor.putInt(
            "selected_name_number_image",
            namesData.nameNumberImage
        )  // Use namesData.nameNumberImage
        editor.putString("selected_translation_urdu", translation.first)
        editor.putString("selected_translation_english", translation.second)
        editor.apply()
    }

    private fun updateViewStyles() {
        if (isAllahNamesSelected) {
            // Change the text style for Allah Names
            binding.viewAllahNames.setBackgroundResource(R.drawable.button_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.tvAllahNames.setTextAppearance(
                requireContext(),
                R.style.advertisement_text_style
            )
            binding.tvRasoolNames.setTextAppearance(
                requireContext(),
                R.style.forgot_password_text_style
            )
        } else if (isRasoolNamesSelected) {
            // Change the text style for Rasool Names
            binding.tvAllahNames.setTextAppearance(
                requireContext(),
                R.style.forgot_password_text_style
            )
            binding.tvRasoolNames.setTextAppearance(
                requireContext(),
                R.style.advertisement_text_style
            )
            binding.viewAllahNames.setBackgroundResource(R.drawable.name_transparent_bg)
            binding.viewRasoolNames.setBackgroundResource(R.drawable.button_bg)
        }
    }
}



