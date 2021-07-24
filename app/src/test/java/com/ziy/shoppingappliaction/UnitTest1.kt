package com.ziy.shoppingappliaction

import android.util.Log
import com.ziy.shoppingappliaction.Helpers.StringMinipulator
import org.junit.Test

class UnitTest1
{
    @Test fun checkCardFromString()
    {
        val strMinipulator = StringMinipulator()

        val num = strMinipulator.cardFromStr("Card Name: Card Name1 " +
                "\nCard Number: 1234-5678")

        if(num == null)
        {
            System.out.println("StringMinipulator: cardFromStr: Failed")
        }
        else
        {
            System.out.println("StringMinipulator: cardFromStr: Sucess -$num")
        }
    }

    @Test fun findCardSubStr()
    {
        val cardDetails = "Card Name: Card Name1 \nCard Number: 1234-5678"
        val nameSubStr = "Card Name: "
        val cardSubStr = "Card Number: "
        val strMinipulator = StringMinipulator()

        System.out.println("findCardSubStr: " + strMinipulator.getStuff(cardDetails,
            nameSubStr, cardSubStr))
    }
}