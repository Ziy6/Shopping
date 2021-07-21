package com.ziy.shoppingappliaction.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ziy.shoppingappliaction.R

class CongratulationsFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View?
    {
        val view = inflater.inflate(R.layout.fragment_congratulations, container, false)
        // Inflate the layout for this fragment
        return view
    }
}