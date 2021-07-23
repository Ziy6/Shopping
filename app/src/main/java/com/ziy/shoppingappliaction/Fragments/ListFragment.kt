package com.ziy.shoppingappliaction.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ziy.shoppingappliaction.Helpers.DatabaseHelper
import com.ziy.shoppingappliaction.R

class ListFragment : Fragment()
{
    lateinit var listView: ListView
    lateinit var listAdapter: ArrayAdapter<String>
    lateinit var  database: DatabaseHelper
    var arrayList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        database = DatabaseHelper(view.context) //initialise database first, init uses it
        intialiseListAdapters(view)
        initialiseButtons(view)

        // Inflate the layout for this fragment
        return view
    }

    //setup arraylist and array adapter
    fun intialiseListAdapters(view: View)
    {
        //fill initial list with database lists
        arrayList = database.getLists() as ArrayList<String>

        listView = view.findViewById(R.id.listView)
        listAdapter = ArrayAdapter(this.requireContext(),
            android.R.layout.simple_list_item_multiple_choice, arrayList)

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        listView.adapter = listAdapter
    }

    //setup button initialisation and click listeners
    fun initialiseButtons(view: View)
    {
        val addButton = view.findViewById<Button>(R.id.addListButton)
        val removeButton = view.findViewById<Button>(R.id.deleteButton)
        val selectButton = view.findViewById<Button>(R.id.selectListButton)
        val listEditText = view.findViewById<EditText>(R.id.listsEditText)
        var editListText = listEditText.text.toString()

        addButton.setOnClickListener {  view ->
            if(editListText != "")
            {
                editListText = listEditText.text.toString()
                database.insertList(editListText)
                arrayList.add(editListText)
                listAdapter.notifyDataSetChanged()

                //Change to the listItem fragment
                val itemsFragment = ItemsFragment(editListText)
                val fragmentTransaction = parentFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.navFragmentView, itemsFragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        }

        removeButton.setOnClickListener {   view ->
            if (!arrayList.isEmpty())
            {
                for(i in arrayList.size-1 downTo 0)
                {
                    if(listView.isItemChecked(i))
                    {
                        database.removeList(arrayList.get(i))
                        arrayList.removeAt(i)
                        listView.setItemChecked(i, false)
                    }
                }
                listAdapter.notifyDataSetChanged()
            }
        }

        selectButton.setOnClickListener {   view ->
            if(!arrayList.isEmpty())
            {   //Only one item
                if (listView.checkedItemCount == 1)
                {   //Which item
                    for(i in arrayList.size-1 downTo 0)
                    {
                        if(listView.isItemChecked(i))
                        {
                            val checkedItem = arrayList.get(i)

                            //Change to the listItem fragment
                            val itemsFragment = ItemsFragment(checkedItem)
                            val fragmentTransaction = parentFragmentManager.beginTransaction()
                            fragmentTransaction.replace(R.id.navFragmentView, itemsFragment)
                            fragmentTransaction.addToBackStack(null)
                            fragmentTransaction.commit()
                        }
                    }
                }
            }
        }
    }

}