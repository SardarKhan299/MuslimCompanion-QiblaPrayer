package com.qibla.qiblacompass.prayertime.finddirection.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.qibla.qiblacompass.prayertime.finddirection.R
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QiblaApp : Application() {

    companion object {
        // Declare the static ArrayList to hold instances of AllahName
        val allahNamesImages: ArrayList<Int> = ArrayList()
        val allahNamesTranslations = arrayListOf<Pair<String, String>>()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(QiblaApp::class.simpleName, "onCreate: ")
        FirebaseApp.initializeApp(this)
        // Initialize the ArrayList with the image resources of the 99 names
        allahNamesImages.apply {
            add(R.drawable.nameallah1)
            add(R.drawable.nameallah2)
            add(R.drawable.nameallah3)
            add(R.drawable.nameallah4)
            add(R.drawable.nameallah5)
            add(R.drawable.nameallah6)
            add(R.drawable.nameallah7)
            add(R.drawable.nameallah8)
            add(R.drawable.nameallah9)
            add(R.drawable.nameallah10)
            add(R.drawable.nameallah11)
            add(R.drawable.nameallah12)
            add(R.drawable.nameallah13)
            add(R.drawable.nameallah14)
            add(R.drawable.nameallah15)
            add(R.drawable.nameallah16)
            add(R.drawable.nameallah17)
            add(R.drawable.nameallah18)
            add(R.drawable.nameallah19)
            add(R.drawable.nameallah12)
            add(R.drawable.nameallah21)
            add(R.drawable.nameallah22)
            add(R.drawable.nameallah23)
            add(R.drawable.nameallah24)
            add(R.drawable.nameallah25)
            add(R.drawable.nameallah26)
            add(R.drawable.nameallah27)
            add(R.drawable.nameallah28)
            add(R.drawable.nameallah29)
            add(R.drawable.nameallah30)
            add(R.drawable.nameallah31)
            add(R.drawable.nameallah32)
            add(R.drawable.nameallah33)
            add(R.drawable.nameallah34)
            add(R.drawable.nameallah35)
            add(R.drawable.nameallah36)
            add(R.drawable.nameallah37)
            add(R.drawable.nameallah38)
            add(R.drawable.nameallah39)
            add(R.drawable.nameallah40)
            add(R.drawable.nameallah41)
            add(R.drawable.nameallah42)

            add(R.drawable.nameallah43)
            add(R.drawable.nameallah44)
            add(R.drawable.nameallah45)
            add(R.drawable.nameallah46)
            add(R.drawable.nameallah47)
            add(R.drawable.nameallah48)
            add(R.drawable.nameallah49)
            add(R.drawable.nameallah50)

            add(R.drawable.nameallah51)
            add(R.drawable.nameallah52)
            add(R.drawable.nameallah53)
            add(R.drawable.nameallah54)
            add(R.drawable.nameallah55)
            add(R.drawable.nameallah56)
            add(R.drawable.nameallah57)
            add(R.drawable.nameallah58)
            add(R.drawable.nameallah59)
            add(R.drawable.nameallah60)

            add(R.drawable.nameallah61)
            add(R.drawable.nameallah62)
            add(R.drawable.nameallah63)
            add(R.drawable.nameallah64)
//            add(R.drawable.nameallah65)
//            add(R.drawable.nameallah66)
//            add(R.drawable.nameallah67)
            add(R.drawable.nameallah68)
            add(R.drawable.nameallah69)
            add(R.drawable.nameallah70)

            add(R.drawable.nameallah71)
            add(R.drawable.nameallah72)
            add(R.drawable.nameallah73)
            add(R.drawable.nameallah74)
            add(R.drawable.nameallah75)
            add(R.drawable.nameallah76)
            add(R.drawable.nameallah77)
            add(R.drawable.nameallah78)
            add(R.drawable.nameallah79)
            add(R.drawable.nameallah80)
            add(R.drawable.nameallah81)
            add(R.drawable.nameallah82)
            add(R.drawable.nameallah83)
            add(R.drawable.nameallah84)
            add(R.drawable.nameallah85)
            add(R.drawable.nameallah86)
            add(R.drawable.nameallah87)
            add(R.drawable.nameallah88)
            add(R.drawable.nameallah89)
            add(R.drawable.nameallah90)
            add(R.drawable.nameallah91)
            add(R.drawable.nameallah92)
            add(R.drawable.nameallah93)
            add(R.drawable.nameallah94)
            add(R.drawable.nameallah95)
            add(R.drawable.nameallah96)
            add(R.drawable.nameallah97)
            add(R.drawable.nameallah98)
            add(R.drawable.nameallah99)


        }

        // Initialize the allahNamesTranslations ArrayList here
        allahNamesTranslations.addAll(
            listOf(
                "انتہائی مہربانُ" to "The Most Gracious",
                "انتہائی رحم کرنے والاُ" to "The Most Merciful",
                "مالک، بادشاہ" to "The Owner, The King, The Ruler",
                "مقدس، پاک، عیبوں سے پاک" to "The Absolutely Pure, The most Holy, The Most sacred",
                "سلامتی والا" to "Only provider of Peace",
                "ایمان عطاء کرنے والا، امن دینے والا" to "The One Who gives Emaan and Security",
                "محافظ" to "The Guardian, The Witness, The Overseer",
                "غالب، زبردست، غلبہ والا، عزت دینے والا" to "The All Mighty",
                "زبردست" to "The Compeller, The Restorer",
                "بڑائی والا، بزرگی والا" to "The Supreme, The Majestic",
                "پیدا کرنے والا" to "The Creator, The Maker",
                "پیدا کرنے والا" to "The Originator",
                "صورت بنانے والا" to "The Fashioner",
                "بخشنے والا" to "The All- and Oft-Forgiving",
                "زبردست، قہر نازل کرنے والا" to "The Subduer, The Ever-Dominating",
                "عطاء کرنے والا" to "The Giver of Gifts",
                "رزق دینے والا" to "The Provider",
                "کھولنے والا" to "The Opener, The Judge",
                "جاننے والا، علم والا، باخبر" to "The All-Knowing, The Omniscient",
                "قبض کرنے والا" to "The Withholder",
                "فراخ کرنے والا" to "The Extender",
                "پست کرنے والا" to "The Reducer, The Abaser",
                "بلند کرنے والا" to "The Exalter, The Elevator",
                "عزت دینے والا" to "The Honourer, The Bestower",
                "ذلت دینے والا" to "The Dishonourer, The Humiliator",
                "سننے والا" to "The All-Hearing",
                "دیکھنے والا" to "The All-Seeing",
                "فیصلہ کرنے والا، حاکم" to "The Judge, The Giver of Justice",
                "انصاف کرنے والا" to "The Utterly Just",
                "مہربان" to "The Subtle One, The Most Gentle",
                "خبردار، جاننے والا،خبر رکھنے والا" to "The Acquainted, the All-Aware",
                "بردبار" to "The Most Forbearing",
                "عظمت والا، بڑائی والا، بڑا" to "The Magnificent, The Supreme",
                "معافی دینے والا" to "The Forgiving, The Exceedingly Forgiving",
                "قدردان" to "The Most Appreciative",
                "اعلیٰ، سب سے افضل، برتر" to "The Most High, The Exalted",
                "بزرگی والا" to "The Greatest, The Most Grand",
                "حفاظت کرنے والا، نگہبان" to "The Preserver, The All-Heedful and All-Protecting",
                "باقی رہنے والا، روزی دینے والا، نگہبان، قوت دینے والا" to "The Sustainer",
                "حساب لینے والا" to "The Reckoner, The Sufficient",
                "بزرگ" to "The Majestic",
                "کرم کرنے والا" to "The Most Generous, The Most Esteemed",
                "خیال رکھنے والا" to "The Watchful",
                "قبول کرنے والا" to "The Responsive One",
                "لامحدود" to "The All-Encompassing, the Boundless",
                "حکمت والا" to "The All-Wise",
                "محبت کرنے والا" to "The Most Loving",
                "بزرگی والا" to "The Glorious, The Most Honorable",
                "اسباب پیدا کرنے والا" to "The Resurrector, The Raiser of the Dead",
                "گواہ" to "The All  and Ever Witnessing",
                "سچ" to "The Absolute Truth",
                "وکیل،کارساز" to "The Trustee, The Disposer of Affairs",
                "سب سے زیادہ طاقتور" to "The All Strong",
                "مضبوط، قوت والا" to "The Firm, The Steadfast",
                "دوست" to "The Protecting Associate",
                "تعریف والا" to "The Praiseworthy",
                "شمار کرنے والا" to "The All-Enumerating, The Counter",
                "عدم سے عالم کو وجود لانے والا" to "The Originator, The Initiator",
                "لوٹانے والا، پناہ دینے والا" to "The Restorer, The Reinstater",
                "زندگی دینے والا" to "The Giver of Life",
                "موت دینے والا" to "The Bringer of Death, the Destroyer",
                "زندہ، ہمیشہ رہنے والا" to "The Ever-Living",
                "قائم رہنے والا" to "The Sustainer, The Self-Subsisting",
                "حاصل کرنے والا" to "The Perceiver",
                "بزرگی دینے والا" to "The Illustrious, the Magnificent",
                "اکیلا" to "The One",
                "ایک" to "The Unique, The Only One",
                "بے نیاز، مضبوط" to "The Eternal, Satisfier of Needs",
                "قدرت والا" to "The Capable, The Powerful",
                "اقتدار والا،  قادر مطلق" to "The Omnipotent",
                "آگے کرنے والا" to "The Expediter, The Promoter",
                "پیچھے کرے والا" to "The Delayer, the Retarder",
                "پہلا" to "The First",
                "آخر" to "The Last",
                "ظاہر" to "The Manifest",
                "چھپا ہوا، باطن کو جاننے والا" to "The Hidden One, Knower of the Hidden",
                "سرپرست، متولی" to "The Governor, The Patron",
                "بزرگ" to "The Self Exalted",
                "نیکیوں کا سرچشمہ" to "The Source of Goodness, the Kind Benefactor",
                "توبہ قبول کرنے والا" to "The Ever-Pardoning, The Relenting",
                "انتقام لینے والا" to "The Avenger",
                "معاف کرنے والا" to "The Pardoner",
                "رحم کرنے والا، شفقت کرنے ولا" to "The Most Kind",
                "دو جہاں کا مالک" to "Master of the Kingdom, Owner of the Dominion",
                "جلال اور انعام و اکرام والا" to "Possessor of Glory and Honour, Lord of Majesty and Generosity",
                "انصاف کرنے والا" to "The Equitable, the Requiter",
                "جمع کرنے والا" to "The Gatherer, the Uniter",
                "بے پرواہ، غنی" to "The Self-Sufficient, The Wealthy",
                "بے نیاز" to "The Enricher",
                "روکنے والا، باز رکھنے والا" to "The Withholder",
                "نقصان کا مالک" to "The Distresser,The creator of harm",
                "نفع کا مالک، نفع دینے والا" to "The Propitious, the Benefactor",
                "روشن، روشنی دینے والا" to "The Light, The Illuminator",
                "ہدایت دینے والا، راستہ دکھانے والا" to "The Guide",
                "نادر پیدا کرنے والا" to "The Incomparable Originator",
                "ہمیشہ رہنے والا" to "The Ever-Surviving, The Everlasting",
                "وارث" to "The Inheritor, The Heir",
                "ہدایت دینے والا، رہنمائی کرنے والا" to "The Guide, Infallible Teacher",
                "صبر کرنے والا" to "The Forbearing, The Patient"
            )
        )
    }
}