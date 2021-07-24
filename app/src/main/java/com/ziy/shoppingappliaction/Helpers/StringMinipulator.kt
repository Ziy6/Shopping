package com.ziy.shoppingappliaction.Helpers

import android.util.Log

class StringMinipulator()
{
    //searches for numbers from end of string
    fun cardFromStr(cardDetails: String): String?
    {
        val numArray = arrayOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-')
        var endCheck = false    //end of string
        var beginningCheck = false  //beginning of string
        var end: Int = -1
        var beginning: Int

        //looping through string to compare
        for(index in cardDetails.length-1 downTo 0)
        {
            //looping through numArray to compare
            for (arrIndex in 0 until numArray.size-1)
            {
                //if match - only for begining
                if(cardDetails.get(index) == numArray.get(arrIndex) && !endCheck)
                {
                    end = index
                    endCheck = true
                }
                else if(!numArray.contains(cardDetails.get(index)) && endCheck)
                {
                    beginning = index
                    return cardDetails.subSequence(beginning+1, end+1).toString()
                }
            }
        }
        return null
    }

    fun getStuff(viewString: String, nameSubStr: String, cardSubStr: String): String
    {
        val numIndex = viewString.indexOf(cardSubStr, 0)
        val nameIndex = nameSubStr.length
        val name = viewString.substring(nameIndex, numIndex-2)
        val number = viewString.substring(numIndex + 12, viewString.length-1)

        return name + number
    }

}