package com.ziy.shoppingappliaction.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.ziy.shoppingappliaction.R

class CheckFragment : Fragment()
{
    lateinit var listView: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    val arrayList = ArrayList<String>()
    var x = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_check, container, false)

        intialiseListAdapters(view)
        initialiseButtons(view)

        // Inflate the layout for this fragment
        return view
    }

    //setup arraylist and array adapter
    fun intialiseListAdapters(view: View)
    {
        listView = view.findViewById(R.id.checkList)
        listAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_list_item_multiple_choice, arrayList)

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView.adapter = listAdapter
    }

    //setup button initialisation and click listeners
    fun initialiseButtons(view: View)
    {
        val proceedButton = view.findViewById<Button>(R.id.buyButton)

        proceedButton.setOnClickListener {  view ->
            //Change to the listItem fragment
            val driverFragment = DriverFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.CheckoutFragmentContainer, driverFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}