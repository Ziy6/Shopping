package com.ziy.shoppingappliaction.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.ziy.shoppingappliaction.DatabaseObjects.ShoppingItem
import com.ziy.shoppingappliaction.Helpers.DatabaseHelper
import com.ziy.shoppingappliaction.R

class ItemsFragment(item: String) : Fragment()
{
    val passedItem = item
    lateinit var listView: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    var arrayList = ArrayList<String>()
    lateinit var  database: DatabaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_items_list, container, false)
        database = DatabaseHelper(view.context)
        intialiseListAdapters(view)
        initialiseButtons(view)

        // Inflate the layout for this fragment
        return view
    }

    //setup arraylist and array adapter
    fun intialiseListAdapters(view: View)
    {
        arrayList = database.getItem(passedItem) as ArrayList<String>
        listView = view.findViewById(R.id.itemListView)
        listAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_list_item_multiple_choice, arrayList)

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView.adapter = listAdapter
    }

    //setup button initialisation and click listeners
    fun initialiseButtons(view: View)
    {
        val addItemButton = view.findViewById<Button>(R.id.addItemButton)
        val removeItemButton = view.findViewById<Button>(R.id.removeItemButton)
        val backButton = view.findViewById<Button>(R.id.backButton)
        val itemEditText = view.findViewById<EditText>(R.id.itemEditText)

        addItemButton.setOnClickListener {  view ->
            if(itemEditText.text.toString() != "")
            {
                val newItemName = itemEditText.text.toString()
                database.insertItem(passedItem, newItemName)
                arrayList.add(newItemName)
                listAdapter.notifyDataSetChanged()
            }
        }

        removeItemButton.setOnClickListener {   view ->
            if (!arrayList.isEmpty())
            {
                for(i in arrayList.size-1 downTo 0)
                {
                    if(listView.isItemChecked(i))
                    {
                        val shopItem = arrayList.get(i)
                        database.removeItem(shopItem, passedItem)
                        arrayList.removeAt(i)
                        listView.setItemChecked(i, false)
                    }
                }
                listAdapter.notifyDataSetChanged()
            }
        }

        backButton.setOnClickListener {   view ->
            //Change to the list fragment
            val listFragment = ListFragment()
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.navFragmentView, listFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}