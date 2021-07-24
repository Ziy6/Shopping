package com.ziy.shoppingappliaction.Fragments

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.ziy.shoppingappliaction.DatabaseObjects.PaymentOption
import com.ziy.shoppingappliaction.Helpers.DatabaseHelper
import com.ziy.shoppingappliaction.Helpers.StringMinipulator
import com.ziy.shoppingappliaction.R

class PaymentChangeFragment : Fragment()
{
    lateinit var listView: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var  database: DatabaseHelper
    var arrayList = ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view= inflater.inflate(R.layout.fragment_payment_change, container, false)
        database = DatabaseHelper(view.context) //initialise database first, init uses it
        intialiseListAdapters(view)
        initialiseViews(view)

        return view // Inflate the layout for this fragment
    }

    //setup arraylist and array adapter
    fun intialiseListAdapters(view: View)
    {
        listView = view.findViewById(R.id.cardListView)
        val paymentOption = database.getPaymentOptions() as ArrayList<PaymentOption>

        //if received list not empty
        if (paymentOption.size > 0)
        {//add to a string array using received object list details
            for (index in 0 until paymentOption.size)
            {
                arrayList.add("Card Name: ${paymentOption.get(index).cardName} \nCard Number: " +
                        " ${paymentOption.get(index).cardNumber}")
            }
        }
        listAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_list_item_single_choice, arrayList)

        listView.choiceMode = ListView.CHOICE_MODE_SINGLE
        listView.adapter = listAdapter
    }

    //setup button initialisation and click listeners
    fun initialiseViews(view: View)
    {
        val nameInputView = view.findViewById<TextView>(R.id.nameTextEdit)
        val numberInputView = view.findViewById<TextView>(R.id.numberEditText)
        val addItemButton = view.findViewById<Button>(R.id.addCardButton)
        val removeItemButton = view.findViewById<Button>(R.id.removeCardButton)
        val selecPaymentButton = view.findViewById<Button>(R.id.selectPaymentButton)

        numberInputView.setOnClickListener {    view ->
            //for number edit text clear text and change to numbers only when cicked on
            numberInputView.text = ""
            numberInputView.inputType = InputType.TYPE_CLASS_NUMBER
        }

        addItemButton.setOnClickListener {  view ->
            //if both edit text views not empty and number edit text view is convertible to int
            if(nameInputView.text != "" && numberInputView.text != "" &&
                numberInputView.text.toString().toIntOrNull() != null)
            {
                val name = nameInputView.text.toString()
                val number = numberInputView.text.toString()
                val stringDisplayed = "Card Name: $name \nCard Number: $number"

                database.insertPaymentOption(PaymentOption(name,
                    number))
                database.resetDefaultOption()
                database.insertDefaultPaymentOption(PaymentOption(name, number))
                arrayList.add(stringDisplayed)
                listAdapter.notifyDataSetChanged()
            }
            else
            {
                Toast.makeText(this.context, "Not a valid input", Toast.LENGTH_SHORT).show()
            }
        }

        removeItemButton.setOnClickListener {   view ->
            if (!arrayList.isEmpty())
            {
                val strMinipulator = StringMinipulator()
                val checkedIndex = listView.checkedItemPosition
                val subStr = strMinipulator.cardFromStr(arrayList.get(checkedIndex))

                if(subStr != null)
                {
                    val v = database.removePaymentOption(subStr)
                    arrayList.removeAt(checkedIndex)   //removes full string from view
                    listView.setItemChecked(checkedIndex, false)

                    //if is same as default payment option
                    if(database.getDefaultOption()?.cardNumber?.compareTo(subStr) == 0)
                    {
                        database.resetDefaultOption()
                    }
                }
                listAdapter.notifyDataSetChanged()
            }
        }
        selecPaymentButton.setOnClickListener { view ->
            val paymentOption = database.getDefaultOption()
            val storedName = paymentOption?.cardName
            val storedNum = paymentOption?.cardNumber
            val checkedIndex = listView.checkedItemPosition

            val arrString = arrayList.get(checkedIndex)

            //reference point for substrings
            val numSubstr = "Card Number: "
            val nameIndex = 12
            val numberIndex = arrString.indexOf("Card Number: ", 0)

            val name = arrString.substring(11, numberIndex-2)  //-2 for the \n
            val number = arrString.substring(numberIndex+13, arrString.length)

            database.resetDefaultOption()
            database.insertDefaultPaymentOption(PaymentOption(name, number))


        }
    }
}