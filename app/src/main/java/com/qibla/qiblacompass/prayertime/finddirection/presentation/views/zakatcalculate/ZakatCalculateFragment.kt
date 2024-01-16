package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.zakatcalculate

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.qibla.qiblacompass.prayertime.finddirection.R
import com.qibla.qiblacompass.prayertime.finddirection.base.BaseFragment
import com.qibla.qiblacompass.prayertime.finddirection.common.closeCurrentScreen
import com.qibla.qiblacompass.prayertime.finddirection.common.hideActionBar
import com.qibla.qiblacompass.prayertime.finddirection.databinding.FragmentZakatCalculateBinding

class ZakatCalculateFragment :
    BaseFragment<FragmentZakatCalculateBinding>(R.layout.fragment_zakat_calculate) {

    private lateinit var edittext1: EditText
    private lateinit var edittext2: EditText
    private lateinit var edittext3: EditText
    private lateinit var edittext4: EditText
    private lateinit var edittext5: EditText

    private lateinit var edittext6: EditText
    private lateinit var edittext7: EditText
    private lateinit var edittext8: EditText
    private lateinit var edittext9: EditText
    private lateinit var edittext10: EditText

    private lateinit var edittext11: EditText
    private lateinit var edittext12: EditText
    private lateinit var edittext13: EditText
    private lateinit var edittext14: EditText
    private lateinit var edittext15: EditText

    private lateinit var edittext16: EditText
    private lateinit var edittext17: EditText
    private lateinit var edittext18: EditText
    private lateinit var edittext19: EditText
    private lateinit var edittext20: EditText

    private lateinit var edittext21: EditText
    private lateinit var edittext22: EditText
    private lateinit var edittext23: EditText
    private lateinit var edittext24: EditText
    private lateinit var edittext25: EditText

    private lateinit var edittextFinancial1: EditText
    private lateinit var edittextFinancial2: EditText
    private lateinit var edittextFinancial3: EditText
    private lateinit var edittextFinancial4: EditText
    private lateinit var edittextFinancial5: EditText
    private lateinit var edittextFinancial6: EditText
    private lateinit var edittextFinancial7: EditText
    private lateinit var edittextSilverAmount: EditText

    private lateinit var textViewFinancialResponsibilities: TextView
    private lateinit var goldTextValue: TextView
    private lateinit var amountZakatDuaOnText: TextView
    private lateinit var zakatableWealthTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)?.hideActionBar()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            zakatCalculateFragment = this@ZakatCalculateFragment
        }
        binding.toolbarZakatCalculate.groupToolbarSubScreenProfile.visibility = View.VISIBLE
        binding.toolbarZakatCalculate.tvToolbarSubScreen.text = getString(R.string.zakat_calculator)
        val zakatFragment = binding.includeZakatCalculateForm
        edittext1 = zakatFragment.edtGoldAmount
        edittext2 = zakatFragment.edtGoldTrust
        edittext3 = zakatFragment.edtSilver
        edittext4 = zakatFragment.edtSilverTrust
        edittext5 = zakatFragment.edtCashHeldPointOne

        edittext6 = zakatFragment.edtForeignCurrency
        edittext7 = zakatFragment.edtCashSaveForPurpose
        edittext8 = zakatFragment.edtCashDepositBank
        edittext9 = zakatFragment.edtTrustedMoney
        edittext10 = zakatFragment.edtInsurance

        edittext11 = zakatFragment.edtLoan
        edittext12 = zakatFragment.edtSecurityDeposit
        edittext13 = zakatFragment.edtMoneyInvest
        edittext14 = zakatFragment.edtSavingCertificates
        edittext15 = zakatFragment.edtFinancialDocuments

        edittext16 = zakatFragment.edtCashableFinancialDocuments
        edittext17 = zakatFragment.edtAmountsReceived
        edittext18 = zakatFragment.edtInstallmentsCommittee
        edittext19 = zakatFragment.edtTradingGoods
        edittext20 = zakatFragment.edtGoodsForSale

        edittext21 = zakatFragment.edtReceivableGoodsSold
        edittext22 = zakatFragment.edtStockForSale
        edittext23 = zakatFragment.edtSharesCompanies
        edittext24 = zakatFragment.edtZakatableItem
        edittext25 = zakatFragment.edtPropertyItems

        edittextFinancial1 = zakatFragment.edtObligation
        edittextFinancial2 = zakatFragment.edtObligationPaymentRent
        edittextFinancial3 = zakatFragment.edtSalaryEmployees
        edittextFinancial4 = zakatFragment.edtFinancialHeadingFour
        edittextFinancial5 = zakatFragment.edtFinancialHeadingFive
        edittextFinancial6 = zakatFragment.edtFinancialHeadingSix
        edittextFinancial7 = zakatFragment.edtFinancialHeadingSeven

        edittextSilverAmount = zakatFragment.edtSilverHeading
        textViewFinancialResponsibilities = zakatFragment.tvUpdatedDeductAmount
        amountZakatDuaOnText = zakatFragment.tvUpdatedDueAmount
        zakatableWealthTextView = zakatFragment.tvUpdatedZakatAmountCalculated
        goldTextValue = zakatFragment.tvUpdatedWealthAmount


        val groupGold = binding.includeZakatCalculateForm.groupZakatGold
        val groupSilver = binding.includeZakatCalculateForm.groupZakatSilver

        binding.toolbarZakatCalculate.viewSubScreen.setOnClickListener {
            findNavController().closeCurrentScreen()
        }

        binding.includeZakatCalculateForm.viewHeadingZakat.setOnClickListener {
            if (groupGold.visibility == View.GONE) {
                groupGold.visibility = View.VISIBLE
                binding.includeZakatCalculateForm.imgArrowDown.setImageResource(R.drawable.ic_arrow_drop_up)

            } else {
                groupGold.visibility = View.GONE
                binding.includeZakatCalculateForm.imgArrowDown.setImageResource(R.drawable.ic_arrow_drop_down)

            }
        }

        edittextSilverAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not needed for this example
            }

            override fun afterTextChanged(s: Editable?) {
                val enteredValue = s?.toString() ?: "" // Get the entered text

                if (enteredValue.isNotEmpty() && enteredValue.matches(Regex("[0-9.]+"))) {
                    // Make the detailTextView visible
                    binding.includeZakatCalculateForm.groupSilverAmount.visibility = View.VISIBLE
                    val result = enteredValue.toDouble() * 52.5
                    // Set the entered value in the detailTextView

                    binding.includeZakatCalculateForm.tvSilverAmount.text = "Rs.$result"
                } else {
                    binding.includeZakatCalculateForm.groupSilverAmount.visibility = View.GONE

                }
            }
        })

        binding.includeZakatCalculateForm.goldViewSeparator.setOnClickListener {
            if (groupSilver.visibility == View.GONE) {
                groupSilver.visibility = View.VISIBLE
               binding.includeZakatCalculateForm.imgFinancialDropDown.setImageResource(R.drawable.ic_arrow_drop_up)
            } else {
                groupSilver.visibility = View.GONE
                binding.includeZakatCalculateForm.imgFinancialDropDown.setImageResource(R.drawable.ic_arrow_drop_down)

            }
        }
        // TextWatcher to calculate sum dynamically
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                calculateSumGold()
            }
        }

        // Add TextWatcher to all EditText views
        edittext1.addTextChangedListener(textWatcher)
        // Add more EditText views up to editText10 as needed
        edittext2.addTextChangedListener(textWatcher)
        edittext3.addTextChangedListener(textWatcher)
        edittext4.addTextChangedListener(textWatcher)
        edittext5.addTextChangedListener(textWatcher)

        edittext6.addTextChangedListener(textWatcher)
        edittext7.addTextChangedListener(textWatcher)
        edittext8.addTextChangedListener(textWatcher)
        edittext9.addTextChangedListener(textWatcher)
        edittext10.addTextChangedListener(textWatcher)

        edittext11.addTextChangedListener(textWatcher)
        edittext12.addTextChangedListener(textWatcher)
        edittext13.addTextChangedListener(textWatcher)
        edittext14.addTextChangedListener(textWatcher)
        edittext15.addTextChangedListener(textWatcher)

        edittext16.addTextChangedListener(textWatcher)
        edittext17.addTextChangedListener(textWatcher)
        edittext18.addTextChangedListener(textWatcher)
        edittext19.addTextChangedListener(textWatcher)
        edittext20.addTextChangedListener(textWatcher)

        edittext21.addTextChangedListener(textWatcher)
        edittext22.addTextChangedListener(textWatcher)
        edittext23.addTextChangedListener(textWatcher)
        edittext24.addTextChangedListener(textWatcher)
        edittext25.addTextChangedListener(textWatcher)

        val textWatcherSilver = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                calculateSumSilver()
            }
        }
        edittextFinancial1.addTextChangedListener(textWatcherSilver)
        edittextFinancial2.addTextChangedListener(textWatcherSilver)
        edittextFinancial3.addTextChangedListener(textWatcherSilver)
        edittextFinancial4.addTextChangedListener(textWatcherSilver)
        edittextFinancial5.addTextChangedListener(textWatcherSilver)
        edittextFinancial6.addTextChangedListener(textWatcherSilver)
        edittextFinancial7.addTextChangedListener(textWatcherSilver)

        calculateResultZakat()
    }

    private fun calculateSumGold() {
        // Get values from EditText views, default to 0 if empty
        val value1 = edittext1.text.toString().toIntOrNull() ?: 0
        val value2 = edittext2.text.toString().toIntOrNull() ?: 0
        val value3 = edittext3.text.toString().toIntOrNull() ?: 0
        val value4 = edittext4.text.toString().toIntOrNull() ?: 0
        val value5 = edittext5.text.toString().toIntOrNull() ?: 0

        val value6 = edittext6.text.toString().toIntOrNull() ?: 0
        val value7 = edittext7.text.toString().toIntOrNull() ?: 0
        val value8 = edittext8.text.toString().toIntOrNull() ?: 0
        val value9 = edittext9.text.toString().toIntOrNull() ?: 0
        val value10 = edittext10.text.toString().toIntOrNull() ?: 0

        val value11 = edittext11.text.toString().toIntOrNull() ?: 0
        val value12 = edittext12.text.toString().toIntOrNull() ?: 0
        val value13 = edittext13.text.toString().toIntOrNull() ?: 0
        val value14 = edittext14.text.toString().toIntOrNull() ?: 0
        val value15 = edittext15.text.toString().toIntOrNull() ?: 0

        val value16 = edittext16.text.toString().toIntOrNull() ?: 0
        val value17 = edittext17.text.toString().toIntOrNull() ?: 0
        val value18 = edittext18.text.toString().toIntOrNull() ?: 0
        val value19 = edittext19.text.toString().toIntOrNull() ?: 0
        val value20 = edittext20.text.toString().toIntOrNull() ?: 0

        val value21 = edittext21.text.toString().toIntOrNull() ?: 0
        val value22 = edittext22.text.toString().toIntOrNull() ?: 0
        val value23 = edittext23.text.toString().toIntOrNull() ?: 0
        val value24 = edittext24.text.toString().toIntOrNull() ?: 0
        val value25 = edittext25.text.toString().toIntOrNull() ?: 0


        // Get more values from EditText views up to editText10 as needed

        // Calculate sum
        val sum = value1 + value2 + value3 + value4 + value5 +
                value6 + value7 + value8 + value9 + value10 +
                value11 + value12 + value13 + value14 + value15 +
                value16 + value17 + value18 + value19 + value20 +
                value21 + value22 + value23 + value24 + value25
        /* + other values as needed */

        // Display the sum in the TextView
        goldTextValue.text = "$sum"
        // Call calculateResult to update the subtraction result
        calculateResult()
    }


    private fun calculateSumSilver() {
        // Get values from EditText views, default to 0 if empty
        val value1 = edittextFinancial1.text.toString().toIntOrNull() ?: 0
        val value2 = edittextFinancial2.text.toString().toIntOrNull() ?: 0
        val value3 = edittextFinancial3.text.toString().toIntOrNull() ?: 0
        val value4 = edittextFinancial4.text.toString().toIntOrNull() ?: 0
        val value5 = edittextFinancial5.text.toString().toIntOrNull() ?: 0
        val value6 = edittextFinancial6.text.toString().toIntOrNull() ?: 0
        val value7 = edittextFinancial7.text.toString().toIntOrNull() ?: 0

        // Calculate sum
        val sum = value1 + value2 + value3 + value4 + value5 +
                value6 + value7

        // Display the sum in the TextView
        textViewFinancialResponsibilities.text = "$sum"
        // Call calculateResult to update the subtraction result
        calculateResult()
    }

    private fun calculateResult() {
        // Get numerical values from TextViews
        val valueGold = goldTextValue.text.toString().toDoubleOrNull() ?: 0.0
        val valueSilver = textViewFinancialResponsibilities.text.toString().toDoubleOrNull() ?: 0.0

        // Perform subtraction
        val result = valueGold - valueSilver

        // Set the result in another TextView
        amountZakatDuaOnText.text = "$result"
        calculateResultZakat()
    }

    private fun calculateResultZakat() {
        // Get numerical value from amountZakatDuaOnText
        val amountZakatDua = amountZakatDuaOnText.text.toString().toDoubleOrNull() ?: 0.0

        // Perform calculation: Zakatable Wealth = 2.5% of amountZakatDua
        val zakatableWealth = amountZakatDua * 0.025

        // Set the result in the Zakatable Wealth TextView
        zakatableWealthTextView.text = "$zakatableWealth"
    }
}
