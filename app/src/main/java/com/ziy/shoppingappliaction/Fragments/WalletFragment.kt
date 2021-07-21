package com.ziy.shoppingappliaction.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ziy.shoppingappliaction.Activities.CheckoutActivity
import com.ziy.shoppingappliaction.R


class WalletFragment : Fragment()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_wallet, container, false)
        changeDetails(view, "Card Name: A Card Name \n " +
                "Card Number: xxxx-xxxx-xxxx-xxxxx")

        initialiseButtons(view, container?.context!!)

        // Inflate the layout for this fragment
        return view
    }

    fun changeDetails(view: View, paymentDetails: String)
    {
        val defaultDetails = view.findViewById<TextView>(R.id.defaultInformation)
        defaultDetails.setText(paymentDetails)
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