package com.ziy.shoppingappliaction.Helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.ziy.shoppingappliaction.DatabaseObjects.Driver
import com.ziy.shoppingappliaction.DatabaseObjects.PaymentOption

private val DATABASE_NAME = "shopping_database"

val DRIVER_TABLE = "driver_table"
val DRIVER_NAME = "drive_name"
val DRIVER_COST = "cost"

val PAYMENT_OPTION_TABLE = "payment_option_table"
val PAYMENT_OPTION_NAME = "card_name"
val PAYMENT_OPTION_NUMBER = "card_number"

val DEFAULT_PAYMENT_OPTION_TABLE = "default_payment_option_table"
val DEFAULT_PAYMENT_OPTION_NAME = "default_payment_option_name"
val DEFAULT_PAYMENT_OPTION_NUMBER = "default_payment_option_number"

val LIST_TABLE = "list_table"
val LIST_NAME = "list_name"

val ITEM_TABLE = "item_table"
val ITEM_ID = "item_id"
val ITEM_NAME = "item_name"

val CHECKOUT_TABLE = "checkout_table"
val CHECKOUT_NAME = "checkout_list_name"

class DatabaseHelper(val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, 6)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        Log.d("Logging", "Entering onCreate block")
        //create table for shopping table names
        val listsTable = "CREATE TABLE $LIST_TABLE ( $LIST_NAME VARCHAR(256))"
        db?.execSQL(listsTable)

        //creating Item table
        val createItemsTable = "CREATE TABLE IF NOT EXISTS $ITEM_TABLE ($ITEM_ID VARCHAR(256), " +
                "$ITEM_NAME VARCHAR(256))"
        db?.execSQL(createItemsTable)

        //Creating Driver Table
        val driversTable = "CREATE TABLE $DRIVER_TABLE ($DRIVER_NAME  VARCHAR(256)," +
                "$DRIVER_COST DOUBLE)"
        db?.execSQL(driversTable)

        //creating Default Payment Option
        val defaultPaymentOptions = "CREATE TABLE $DEFAULT_PAYMENT_OPTION_TABLE " +
                "($DEFAULT_PAYMENT_OPTION_NAME VARCHAR(256), " +
                "$DEFAULT_PAYMENT_OPTION_NUMBER VARCHAR(256))"
        db?.execSQL(defaultPaymentOptions)

        //creating Payment Options table
        val paymentOptions = "CREATE TABLE $PAYMENT_OPTION_TABLE " +
                "($PAYMENT_OPTION_NAME VARCHAR(256), " +
                "$PAYMENT_OPTION_NUMBER VARCHAR(256))"
        db?.execSQL(paymentOptions)

        //creating checkout table
        val createCheckoutTable = "CREATE TABLE  $CHECKOUT_TABLE ($CHECKOUT_NAME VARCHAR(256))"
        db?.execSQL(createCheckoutTable)

        //setup default drivers
        val driverNames = arrayOf("driver 1", "driver 2", "driver 3", "driver 4", "driver 5")
        val driverCosts = arrayOf(6.30, 5.60, 7.80, 6.00, 7.90)

        //add default drivers into tables
        for(index in 0 until driverNames.size)
        {
            val contentValues = ContentValues()
            contentValues.put(DRIVER_NAME, driverNames[index])
            contentValues.put(DRIVER_COST, driverCosts[index])

            db?.insert(DRIVER_TABLE, null, contentValues)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {
        Log.d("Logging", "Entering onUpdate block")
        db?.execSQL("DROP TABLE IF EXISTS $LIST_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $ITEM_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DEFAULT_PAYMENT_OPTION_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $PAYMENT_OPTION_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $DRIVER_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $CHECKOUT_TABLE")
        onCreate(db)
    }

    /*****************************  Lists  *******************************************/
    //insert list names into Table
    fun insertList(listName: String): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LIST_NAME, listName)

        return (db.insert(LIST_TABLE, null, contentValues)) > 0
    }

    fun removeList(listName: String): Boolean
    {
        val db = this.writableDatabase
        return (db.delete(LIST_TABLE, "$LIST_NAME =?", arrayOf(listName))) > 0
    }

    //get all List names in list
    fun getLists(): MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $LIST_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val listName = result.getString(result.getColumnIndex(LIST_NAME))
                list.add(listName)
            }
            while(result.moveToNext())
        }
        return list
    }
    /********************************** Items  ****************************************/

    //Insert item to shopping list
    fun insertItem(item: String, list: String): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ITEM_NAME, item)
        contentValues.put(ITEM_ID, list)

        return (db.insert(ITEM_TABLE, null, contentValues)) > 0
    }

    //remove item from shopping list - change to handle IDs
    fun removeItem(shopItem: String, fromList: String): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ITEM_NAME, shopItem)

        return (db.delete(ITEM_TABLE, "$ITEM_NAME =?", arrayOf(shopItem))) > 0
    }

    //get all list items in list
    fun getItem(ListTable: String): MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $ITEM_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val shoppingItem = result.getString(result.getColumnIndex(ITEM_NAME))
                list.add(shoppingItem)
            }
            while(result.moveToNext())
        }
        return list
    }
    /********************************** Drivers ****************************************/
    fun getDrivers(): MutableList<Driver>
    {
        val list: MutableList<Driver> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $DRIVER_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val driverName = result.getString(result.getColumnIndex(DRIVER_NAME))
                val driverCost = result.getDouble(result.getColumnIndex(DRIVER_COST))

                val driver = Driver(driverName, driverCost)
                list.add(driver)
            }
            while(result.moveToNext())
        }
        return list
    }

    /***************************** Default Payment Options ********************************/
    fun insertDefaultPaymentOption(paymentOption : PaymentOption): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DEFAULT_PAYMENT_OPTION_NAME, paymentOption.cardName)
        contentValues.put(DEFAULT_PAYMENT_OPTION_NUMBER, paymentOption.cardNumber)

        return (db.insert(DEFAULT_PAYMENT_OPTION_TABLE, null, contentValues)) > 0
    }

    fun resetDefaultOption()
    {
        val db = this.writableDatabase
        db.delete(DEFAULT_PAYMENT_OPTION_TABLE, null, null)
    }

    fun getDefaultOption(): PaymentOption?
    {
        val db = this.readableDatabase
        val query = "SELECT * FROM $DEFAULT_PAYMENT_OPTION_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToNext())
        {
            val paymentName = result.getString(result.getColumnIndex(DEFAULT_PAYMENT_OPTION_NAME))
            val paymentNum = result.getString(result.getColumnIndex(DEFAULT_PAYMENT_OPTION_NUMBER))
            return PaymentOption(paymentName, paymentNum)
        }
        else
        {
            return null
        }
    }

    /********************************** Payment Options ********************************/
    //Insert payment option object to table
    fun insertPaymentOption(paymentOption: PaymentOption): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PAYMENT_OPTION_NAME, paymentOption.cardName)
        contentValues.put(PAYMENT_OPTION_NUMBER, paymentOption.cardNumber)

        return (db.insert(PAYMENT_OPTION_TABLE, null, contentValues)) > 0
    }

    fun removePaymentOption(number: String): Boolean
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        Log.d("Logging", "removePaymentOption:$number")
        contentValues.put(PAYMENT_OPTION_NUMBER, number)

        return (db.delete(PAYMENT_OPTION_TABLE, "$PAYMENT_OPTION_NUMBER =?",
            arrayOf(number))) > 0
    }

    //get all payment options in list
    fun getPaymentOptions(): MutableList<PaymentOption>
    {
        val list: MutableList<PaymentOption> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $PAYMENT_OPTION_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val paymentName = result.getString(result.getColumnIndex(PAYMENT_OPTION_NAME))
                val paymentNum = result.getString(result.getColumnIndex(PAYMENT_OPTION_NUMBER))

                val paymentOption = PaymentOption(paymentName, paymentNum)
                list.add(paymentOption)
            }
            while(result.moveToNext())
        }
        return list
    }

    /********************************** Checkout **********************************************/
    //Insert shopping list item object to table
    fun insertCheckoutList(listName: String)
    {
        //insert list name into checkout list
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(LIST_NAME, listName)

        db.insert(LIST_TABLE, null, contentValues)
    }

    fun removeCheckoutList(listName: String)
    {
        //remove list name from checkout list
        val db = this.writableDatabase
        val contentValues = ContentValues()

        db.delete(LIST_TABLE, "$LIST_NAME =?", arrayOf(listName))
    }

    fun getCheckoutList(): MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CHECKOUT_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val listName = result.getString(result.getColumnIndex(CHECKOUT_NAME))
                list.add(listName)
            }
            while(result.moveToNext())
        }
        return list
    }
}