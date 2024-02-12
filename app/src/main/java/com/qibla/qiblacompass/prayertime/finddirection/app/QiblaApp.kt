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

        val rasoolNamesImages: ArrayList<Int> = ArrayList()
        val rasoolNamesTranslations = arrayListOf<Pair<String, String>>()

        val numberImages : ArrayList<Int> = ArrayList()
        val audioAllahResources = arrayListOf<Int>()
        val audioRasoolResources = arrayListOf<Int>()
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
            add(R.drawable.nameallah20)
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
        rasoolNamesImages.apply {

            add(R.drawable.rasoolname1)
            add(R.drawable.rasoolname2)
            add(R.drawable.rasoolname3)
            add(R.drawable.rasoolname4)
            add(R.drawable.rasoolname5)
            add(R.drawable.rasoolname6)
            add(R.drawable.rasoolname7)
            add(R.drawable.rasoolname8)
            add(R.drawable.rasoolname9)
            add(R.drawable.rasoolname10)

            add(R.drawable.rasoolname11)
            add(R.drawable.rasoolname12)
            add(R.drawable.rasoolname13)
            add(R.drawable.rasoolname14)
            add(R.drawable.rasoolname15)
            add(R.drawable.rasoolname16)
            add(R.drawable.rasoolname17)
            add(R.drawable.rasoolname18)
            add(R.drawable.rasoolname19)
            add(R.drawable.rasoolname20)

            add(R.drawable.rasoolname21)
            add(R.drawable.rasoolname22)
            add(R.drawable.rasoolname23)
            add(R.drawable.rasoolname24)
            add(R.drawable.rasoolname25)
            add(R.drawable.rasoolname26)
            add(R.drawable.rasoolname27)
            add(R.drawable.rasoolname28)
            add(R.drawable.rasoolname29)
            add(R.drawable.rasoolname30)

            add(R.drawable.rasoolname31)
            add(R.drawable.rasoolname32)
            add(R.drawable.rasoolname33)
            add(R.drawable.rasoolname34)
            add(R.drawable.rasoolname35)
            add(R.drawable.rasoolname36)
            add(R.drawable.rasoolname37)
            add(R.drawable.rasoolname38)
            add(R.drawable.rasoolname39)
            add(R.drawable.rasoolname40)

            add(R.drawable.rasoolname41)
            add(R.drawable.rasoolname42)
            add(R.drawable.rasoolname43)
            add(R.drawable.rasoolname44)
            add(R.drawable.rasoolname45)
            add(R.drawable.rasoolname46)
            add(R.drawable.rasoolname47)
            add(R.drawable.rasoolname48)
            add(R.drawable.rasoolname49)
            add(R.drawable.rasoolname50)

            add(R.drawable.rasoolname51)
            add(R.drawable.rasoolname52)
            add(R.drawable.rasoolname53)
            add(R.drawable.rasoolname54)
            add(R.drawable.rasoolname55)
            add(R.drawable.rasoolname56)
            add(R.drawable.rasoolname57)
            add(R.drawable.rasoolname58)
            add(R.drawable.rasoolname59)
            add(R.drawable.rasoolname60)

            add(R.drawable.rasoolname61)
            add(R.drawable.rasoolname62)
            add(R.drawable.rasoolname63)
            add(R.drawable.rasoolname64)
            add(R.drawable.rasoolname65)
            add(R.drawable.rasoolname66)
            add(R.drawable.rasoolname67)
            add(R.drawable.rasoolname68)
            add(R.drawable.rasoolname69)
            add(R.drawable.rasoolname70)

            add(R.drawable.rasoolname71)
            add(R.drawable.rasoolname72)
            add(R.drawable.rasoolname73)
            add(R.drawable.rasoolname74)
            add(R.drawable.rasoolname75)
            add(R.drawable.rasoolname76)
            add(R.drawable.rasoolname77)
            add(R.drawable.rasoolname78)
            add(R.drawable.rasoolname79)
            add(R.drawable.rasoolname80)

            add(R.drawable.rasoolname81)
            add(R.drawable.rasoolname82)
            add(R.drawable.rasoolname83)
            add(R.drawable.rasoolname84)
            add(R.drawable.rasoolname85)
            add(R.drawable.rasoolname86)
            add(R.drawable.rasoolname87)
            add(R.drawable.rasoolname88)
            add(R.drawable.rasoolname89)
            add(R.drawable.rasoolname90)

            add(R.drawable.rasoolname91)
            add(R.drawable.rasoolname92)
            add(R.drawable.rasoolname93)
            add(R.drawable.rasoolname94)
            add(R.drawable.rasoolname95)
            add(R.drawable.rasoolname96)
            add(R.drawable.rasoolname97)
            add(R.drawable.rasoolname98)
            add(R.drawable.rasoolname99)


        }
        numberImages.apply {
            //10/20
            add(R.drawable.number1)
            add(R.drawable.number2)
            add(R.drawable.number3)
            add(R.drawable.number4)
            add(R.drawable.number5)
            add(R.drawable.number6)
            add(R.drawable.number7)
            add(R.drawable.number8)
            add(R.drawable.number9)
            add(R.drawable.number10)
            add(R.drawable.number11)
            add(R.drawable.number12)
            add(R.drawable.number13)
            add(R.drawable.number14)
            add(R.drawable.number15)
            add(R.drawable.number16)
            add(R.drawable.number17)
            add(R.drawable.number18)
            add(R.drawable.number19)
            add(R.drawable.number20)
//30/40
            add(R.drawable.number1)
            add(R.drawable.number2)
            add(R.drawable.number3)
            add(R.drawable.number4)
            add(R.drawable.number5)
            add(R.drawable.number6)
            add(R.drawable.number7)
            add(R.drawable.number8)
            add(R.drawable.number9)
            add(R.drawable.number10)
            add(R.drawable.number11)
            add(R.drawable.number12)
            add(R.drawable.number13)
            add(R.drawable.number14)
            add(R.drawable.number15)
            add(R.drawable.number16)
            add(R.drawable.number17)
            add(R.drawable.number18)
            add(R.drawable.number19)
            add(R.drawable.number20)
//50/60
            add(R.drawable.number1)
            add(R.drawable.number2)
            add(R.drawable.number3)
            add(R.drawable.number4)
            add(R.drawable.number5)
            add(R.drawable.number6)
            add(R.drawable.number7)
            add(R.drawable.number8)
            add(R.drawable.number9)
            add(R.drawable.number10)
            add(R.drawable.number11)
            add(R.drawable.number12)
            add(R.drawable.number13)
            add(R.drawable.number14)
            add(R.drawable.number15)
            add(R.drawable.number16)
            add(R.drawable.number17)
            add(R.drawable.number18)
            add(R.drawable.number19)
            add(R.drawable.number20)
//70/80
            add(R.drawable.number1)
            add(R.drawable.number2)
            add(R.drawable.number3)
            add(R.drawable.number4)
            add(R.drawable.number5)
            add(R.drawable.number6)
            add(R.drawable.number7)
            add(R.drawable.number8)
            add(R.drawable.number9)
            add(R.drawable.number10)
            add(R.drawable.number11)
            add(R.drawable.number12)
            add(R.drawable.number13)
            add(R.drawable.number14)
            add(R.drawable.number15)
            add(R.drawable.number16)
            add(R.drawable.number17)
            add(R.drawable.number18)
            add(R.drawable.number19)
            add(R.drawable.number20)
            //90/99
            add(R.drawable.number1)
            add(R.drawable.number2)
            add(R.drawable.number3)
            add(R.drawable.number4)
            add(R.drawable.number5)
            add(R.drawable.number6)
            add(R.drawable.number7)
            add(R.drawable.number8)
            add(R.drawable.number9)
            add(R.drawable.number10)
            add(R.drawable.number11)
            add(R.drawable.number12)
            add(R.drawable.number13)
            add(R.drawable.number14)
            add(R.drawable.number15)
            add(R.drawable.number16)
            add(R.drawable.number17)
            add(R.drawable.number18)
            add(R.drawable.number19)


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
       rasoolNamesTranslations.addAll(
            listOf(
                "زیادہ تعریف کیا گیا۔ تعریف والا" to "Highly praised",
                "سب سے ذیادہ حمد کرنے والا" to "Highly commendable",
                "بہت تعریف کرنےوالا، سراہنے والا" to "Praising, One who Praise",
                "بہت تعریف کیا گیا، سراہا گیا" to "Praised",
                "بانٹنے والا، تقسیم کرنے والا" to "Distributer",
                "پیچھے آنے والا، آخری" to "Latest, Last",
                "فاتح، کھولنے والا" to "Victor, Conqueror, Opener",
                "گواہ" to "Witness",
                "جگانے والا، اکٹھا کرنے وال" to "One who gather people, awakener",
                "ہدایت یافتہ، شریف، نیک" to "Well guided",
                "گواہی دیا گیا" to "Witnessed",
                "خوشخبری/ بشارت دینے والا" to "Bringer of good news",
                "ڈرانے والا" to "Warner",
                "بلانے والا" to "Caller",
                "شفاء دینے والا، شفاعت کرنے والا" to "Healer",
                "سیدھی راہ دکھانے والا" to "One who guides right",
                "ہداہت والا" to "One who is well guided",
                "مٹا دینے والا" to "One who wipes Out",
                "نجات والا" to "One who saves delivers",
                "منع کرنے والا" to "Safe",
                "پیغمبر، پیغام پہنچانے والا، رسالت لانے والا" to "Messenger",
                "نبی۔ نبوت لانے والا" to "Prophet",
                "جو کسی مکتب سے پڑھا ہوا نہ ہو" to "Unlettered",
                "حجاز والا" to "From the Tihama",
                "ہاشمی خاندان سے" to "Family of Hashim",
                "بطحیٰ والا" to "Belonging to al-Batha",
                "قریبی، شریف، غالب" to "Noble,Dear",
                "آپ کے خیر خواہ، لوگوں کے ایمان کی حرص کرنے والا" to "Full of concern for you",
                "ہلکا، دھیما، انکساری، نرم دل" to "Mild",
                "رحم کرنے والا" to "Merciful",
                "قران کی 20 ویں سورۃ کا آغاز" to "Beginning of Sura 20:1",
                "چنا ہوا" to "Elect",
                "قران کی 27 ویں سورۃ کا آغاز" to "Beginning of Sura 27:1",
                "پسندیدہ، برگزیدہ" to "Content, worthier",
                "قران کی 40 ویں مصد 46 ویں سورۃ کا آغاز" to "Beginning of surah 40 & 46",
                "چنا ہوا" to "Chosen",
                "قران کی 36 ویں سورۃ کا آغاز" to "Beginning of Sura 36:1",
                "سب سے اعلیٰ" to "Worthier,Most Worthy",
                "کپڑا اوڑہنے والا، کملی والا" to "Wrapped",
                "دوست" to "Friend",
                "چادر اوڑہنے والا" to "Covered",
                "مضبوط" to "Firm",
                "سچ کی تصدیق کرنے والا" to "Who Declares for True",
                "پاک" to "Good, Pure",
                "مدد کرنےوالا" to "Helper",
                "مدد دیا گیا" to "Helped (by Allah),Victorious",
                "چراغ" to "Lamp",
                "حکم دینے والا" to "Prince, Commander",
                "حجاز والا" to "From the Hijaz",
                "مضر بن ںضار کی اولاد سے" to "From Muzir bin Nazari family",
                "قریشی" to "From the Clan Quraish",
                "قبیلہ مضر سے" to "From the Tribe Mudar",
                "متوجہ ہونے والا نبی" to "The Prophet of Repentance",
                "یاد رکھنے والا" to "Preserver",
                "کامل۔ مکمل" to "Perfect",
                "سچا" to "Sincere",
                "امانت دار" to "Trustworthy",
                "اللہ کا بندہ" to "Allah’s Servant",
                "اللہ سے کلام کرنے والا" to "The Speaker with Allah",
                "اللہ کا دوست، اللہ کا محبوب" to "Allah’s Beloved Friend",
                "اللہ کا رازدار" to "Allah’s Intimate Friend",
                "اللہ کا مخلص دوست" to "Allah’s Sincere Friend",
                "نبیوں کا سلسہ ختم کرنے والا" to "Seal of the Prophets",
                "کافی" to "Respected",
                "قبول کرنے والا" to "Complying, Replying",
                "شکر گزار" to "Most Grateful",
                "میانہ روی پر چلنےوالا" to "Adopting a Middle Course",
                "مہربانی والا رسول" to "The Messenger of Mercy",
                "طاقت والا" to "Strong",
                "خبر رکھنے والا" to "Well-Informed",
                "امن والا" to "Trusted",
                "علم والا" to "Well Known",
                "حق" to "Truth",
                "ظاہر" to "Clear, Obvious",
                "تابعدار" to "Obedient",
                "پہلا، اول" to "First",
                "پیچھے" to "Last",
                "زاہر" to "Outward, External",
                "پوشیدہ، چھپا ہوا" to "Internal, Hidden,  Inner",
                "یتیم" to "Orphan",
                "سخی" to "Generous",
                "حکمت والا" to "Wise",
                "سردار" to "Lord",
                "چراغ" to "Lamp",
                "روشن" to "Radiant",
                "قابل عزت" to "Respectable, Forbidden, Immune",
                "عزت والا" to "Honored,Venerated",
                "خوشخبری دینے والا" to "Bringer of Good News",
                "نصیت کرنیوالے" to "Who makes Remember, Preacher",
                "پاکیزہ" to "Purified",
                "قریب" to "Near",
                "ولی ، دوست" to "Good Friend",
                "دعوت دینے والا" to "Who is Called",
                "سخی" to "Generous, Magnanimous",
                "ختم کرنے والا" to "Seal",
                "انصاف کرنے والا، عدل کرنے والا" to "Justice",
                "شہرت والا" to "Well Known, famous",
                "گواہ، شہید" to "Witnessing, Martyr",
                "رسول الملاحم" to "The Messenger of the Battles of the Last day",
                ))
        audioAllahResources.apply {
            add(R.raw.audio_allah_name_1)
            add(R.raw.audio_allah_name_2)
            add(R.raw.audio_allah_name_3)
            add(R.raw.audio_allah_name_4)
            add(R.raw.audio_allah_name_5)
            add(R.raw.audio_allah_name_6)
            add(R.raw.audio_allah_name_7)
            add(R.raw.audio_allah_name_8)
            add(R.raw.audio_allah_name_9)
            add(R.raw.audio_allah_name_10)

            add(R.raw.audio_allah_name_11)
            add(R.raw.audio_allah_name_12)
            add(R.raw.audio_allah_name_13)
            add(R.raw.audio_allah_name_14)
            add(R.raw.audio_allah_name_15)
            add(R.raw.audio_allah_name_16)
            add(R.raw.audio_allah_name_17)
            add(R.raw.audio_allah_name_18)
            add(R.raw.audio_allah_name_19)
            add(R.raw.audio_allah_name_20)

            add(R.raw.audio_allah_name_21)
            add(R.raw.audio_allah_name_22)
            add(R.raw.audio_allah_name_23)
            add(R.raw.audio_allah_name_24)
            add(R.raw.audio_allah_name_25)
            add(R.raw.audio_allah_name_26)
            add(R.raw.audio_allah_name_27)
            add(R.raw.audio_allah_name_28)
            add(R.raw.audio_allah_name_29)
            add(R.raw.audio_allah_name_30)

            add(R.raw.audio_allah_name_31)
            add(R.raw.audio_allah_name_32)
            add(R.raw.audio_allah_name_33)
            add(R.raw.audio_allah_name_34)
            add(R.raw.audio_allah_name_35)
            add(R.raw.audio_allah_name_36)
            add(R.raw.audio_allah_name_37)
            add(R.raw.audio_allah_name_38)
            add(R.raw.audio_allah_name_39)
            add(R.raw.audio_allah_name_40)

            add(R.raw.audio_allah_name_41)
            add(R.raw.audio_allah_name_42)
            add(R.raw.audio_allah_name_43)
            add(R.raw.audio_allah_name_44)
            add(R.raw.audio_allah_name_45)
            add(R.raw.audio_allah_name_46)
            add(R.raw.audio_allah_name_47)
            add(R.raw.audio_allah_name_48)
            add(R.raw.audio_allah_name_49)
            add(R.raw.audio_allah_name_50)

            add(R.raw.audio_allah_name_51)
            add(R.raw.audio_allah_name_52)
            add(R.raw.audio_allah_name_53)
            add(R.raw.audio_allah_name_54)
            add(R.raw.audio_allah_name_55)
            add(R.raw.audio_allah_name_56)
            add(R.raw.audio_allah_name_57)
            add(R.raw.audio_allah_name_58)
            add(R.raw.audio_allah_name_59)
            add(R.raw.audio_allah_name_60)

            add(R.raw.audio_allah_name_61)
            add(R.raw.audio_allah_name_62)
            add(R.raw.audio_allah_name_63)
            add(R.raw.audio_allah_name_64)
            add(R.raw.audio_allah_name_65)
            add(R.raw.audio_allah_name_66)
            add(R.raw.audio_allah_name_67)
            add(R.raw.audio_allah_name_68)
            add(R.raw.audio_allah_name_69)
            add(R.raw.audio_allah_name_70)

            add(R.raw.audio_allah_name_71)
            add(R.raw.audio_allah_name_72)
            add(R.raw.audio_allah_name_73)
            add(R.raw.audio_allah_name_74)
            add(R.raw.audio_allah_name_75)
            add(R.raw.audio_allah_name_76)
            add(R.raw.audio_allah_name_77)
            add(R.raw.audio_allah_name_78)
            add(R.raw.audio_allah_name_79)
            add(R.raw.audio_allah_name_80)

            add(R.raw.audio_allah_name_81)
            add(R.raw.audio_allah_name_82)
            add(R.raw.audio_allah_name_83)
            add(R.raw.audio_allah_name_84)
            add(R.raw.audio_allah_name_85)
            add(R.raw.audio_allah_name_86)
            add(R.raw.audio_allah_name_87)
            add(R.raw.audio_allah_name_88)
            add(R.raw.audio_allah_name_89)
            add(R.raw.audio_allah_name_90)

            add(R.raw.audio_allah_name_91)
            add(R.raw.audio_allah_name_92)
            add(R.raw.audio_allah_name_93)
            add(R.raw.audio_allah_name_94)
            add(R.raw.audio_allah_name_95)
            add(R.raw.audio_allah_name_96)
            add(R.raw.audio_allah_name_97)
            add(R.raw.audio_allah_name_98)
            add(R.raw.audio_allah_name_99)

        }
        audioRasoolResources.apply {
            add(R.raw.m_1)
            add(R.raw.m_2)
            add(R.raw.m_3)
            add(R.raw.m_4)
            add(R.raw.m_5)
            add(R.raw.m_6)
            add(R.raw.m_7)
            add(R.raw.m_8)
            add(R.raw.m_9)
            add(R.raw.m_10)

            add(R.raw.m_11)
            add(R.raw.m_12)
            add(R.raw.m_13)
            add(R.raw.m_14)
            add(R.raw.m_15)
            add(R.raw.m_16)
            add(R.raw.m_17)
            add(R.raw.m_18)
            add(R.raw.m_19)
            add(R.raw.m_20)

            add(R.raw.m_21)
            add(R.raw.m_22)
            add(R.raw.m_23)
            add(R.raw.m_24)
            add(R.raw.m_25)
            add(R.raw.m_26)
            add(R.raw.m_27)
            add(R.raw.m_28)
            add(R.raw.m_29)
            add(R.raw.m_30)

            add(R.raw.m_31)
            add(R.raw.m_32)
            add(R.raw.m_33)
            add(R.raw.m_34)
            add(R.raw.m_35)
            add(R.raw.m_36)
            add(R.raw.m_37)
            add(R.raw.m_38)
            add(R.raw.m_39)
            add(R.raw.m_40)

            add(R.raw.m_41)
            add(R.raw.m_42)
            add(R.raw.m_43)
            add(R.raw.m_44)
            add(R.raw.m_45)
            add(R.raw.m_46)
            add(R.raw.m_47)
            add(R.raw.m_48)
            add(R.raw.m_49)
            add(R.raw.m_50)

            add(R.raw.m_51)
            add(R.raw.m_52)
            add(R.raw.m_53)
            add(R.raw.m_54)
            add(R.raw.m_55)
            add(R.raw.m_56)
            add(R.raw.m_57)
            add(R.raw.m_58)
            add(R.raw.m_59)
            add(R.raw.m_60)

            add(R.raw.m_61)
            add(R.raw.m_62)
            add(R.raw.m_63)
            add(R.raw.m_64)
            add(R.raw.m_65)
            add(R.raw.m_66)
            add(R.raw.m_67)
            add(R.raw.m_68)
            add(R.raw.m_69)
            add(R.raw.m_70)

            add(R.raw.m_71)
            add(R.raw.m_72)
            add(R.raw.m_73)
            add(R.raw.m_74)
            add(R.raw.m_75)
            add(R.raw.m_76)
            add(R.raw.m_77)
            add(R.raw.m_78)
            add(R.raw.m_79)
            add(R.raw.m_80)

            add(R.raw.m_81)
            add(R.raw.m_82)
            add(R.raw.m_83)
            add(R.raw.m_84)
            add(R.raw.m_85)
            add(R.raw.m_86)
            add(R.raw.m_87)
            add(R.raw.m_88)
            add(R.raw.m_89)
            add(R.raw.m_90)

            add(R.raw.m_91)
            add(R.raw.m_92)
            add(R.raw.m_93)
            add(R.raw.m_94)
            add(R.raw.m_95)
            add(R.raw.m_96)
            add(R.raw.m_97)
            add(R.raw.m_98)
            add(R.raw.m_99)

        }
    }
}