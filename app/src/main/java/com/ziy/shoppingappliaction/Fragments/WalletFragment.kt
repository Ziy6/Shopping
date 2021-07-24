package com.ziy.shoppingappliaction.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ziy.shoppingappliaction.Activities.CheckoutActivity
import com.ziy.shoppingappliaction.Helpers.DatabaseHelper
import com.ziy.shoppingappliaction.R


class WalletFragment : Fragment()
{
    private lateinit var database: DatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        database = DatabaseHelper(view.context)
        changeDetails(view) //reliant on database initialisation
        initialiseButtons(view, container?.context!!)

        // Inflate the layout for this fragment
        return view
    }

    fun changeDetails(view: View)
    {
        val defaultDetails = view.findViewById<TextView>(R.id.defaultInformation)
        val cardName: String?
        val cardNumber: String

        if(database.getDefaultOption() != null)
        {
            val defaultOption = database.getDefaultOption()

            cardName = defaultOption?.cardName
            cardNumber = defaultOption?.cardNumber.toString()
            Log.d("Logging", "Entered the change Details, not null block")
        }
        else
        {
            cardName = "Default Name"
            cardNumber = "xxxx-xxxx-xxxx-xxxx"
            Log.d("Logging", "Entered the change Details, else block")
        }
        defaultDetails.setText("Card Name: $cardName \nCard Number: $cardNumber")
    }

    //setup button initialisation and click listeners
    fun initialiseButtons(view: View, context: Context)
    {
        val changeInfoButton = view.findViewById<Button>(R.id.changeInfoButton)
        val checkoutButton = view.findViewById<Button>(R.id.checkoutButton)

        changeInfoButton.setOnClickListener {  view ->
            //Change to the PaymentChangeFragment
            val changePayFragment = PaymentChangeFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.navFragmentView, changePayFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        checkoutButton.setOnClickListener {   view ->

            //swap to new activity
            startNewActivity()
            }
    }

    fun startNewActivity()
    {
        val intent = Intent(activity, CheckoutActivity::class.java)
        startActivity(intent)
    }
}