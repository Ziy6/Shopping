package com.ziy.shoppingappliaction.Helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.ziy.shoppingappliaction.DatabaseObjects.Driver
import com.ziy.shoppingappliaction.DatabaseObjects.PaymentOption
import com.ziy.shoppingappliaction.DatabaseObjects.ShoppingItem

private val DATABASE_NAME = "TestDatabase1"

val DRIVER_TABLE = "driverTable"
val DRIVER_NAME = "driverName"
val DRIVER_COST = "cost"

val PAYMENT_OPTION_TABLE = "paymentOptionTable"
val PAYMENT_OPTION_NAME = "cardName"
val PAYMETN_OPTION_NUMBER = "cardNumber"

val SHOPPING_LIST_TABLE = "shoppingListNameTable"
val SHOPPING_LIST_NAME = "shoppingListName"

val SHOPPING_ITEM_TABLE = "shoppingItemTable"
val SHOPPING_ITEM_NAME = "shoppingItemName"

val CHECKOUT_LIST_TABLE = "checkoutTable"
val CHECKOUT_LIST_NAME = "checkoutListName"

class DatabaseHelper(val context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase?)
    {
        //create table for shopping table names
        val listsTable = "CREATE TABLE " + SHOPPING_LIST_TABLE + " (" +
                SHOPPING_LIST_NAME + " VARCHAR(256))"
        db?.execSQL(listsTable)

        //Creating Driver Table
        val driverTable = "CREATE TABLE " + DRIVER_TABLE + " (" +
                DRIVER_NAME + " VARCHAR(256)," +
                DRIVER_COST + " DOUBLE )"
        db?.execSQL(driverTable)

        //creating Payment Options table
        val paymentOptions = "CREATE TABLE " + PAYMENT_OPTION_TABLE + " (" +
                PAYMENT_OPTION_NAME + " VARCHAR(256)," +
                PAYMETN_OPTION_NUMBER + " INTEGER )"
        db?.execSQL(paymentOptions)

        //creating checkout table
        val createCheckoutTable = "CREATE TABLE " + CHECKOUT_LIST_TABLE + " (" +
                CHECKOUT_LIST_NAME + " VARCHAR(256))"
        db?.execSQL(createCheckoutTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int)
    {    }

    /***************************** Shopping Lists (Multiple)  ***********************************/
    //insert list names into Table
    fun insertList(listName: String)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHOPPING_LIST_NAME, listName)

        db.insert(SHOPPING_LIST_TABLE, null, contentValues)
    }

    fun removeList(listName: String)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        db.delete(SHOPPING_LIST_TABLE, SHOPPING_LIST_NAME + " =?", arrayOf(listName))
    }

    //get all List names in list
    fun getLists(): MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $SHOPPING_LIST_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val listName =
                    result.getString(result.getColumnIndex(SHOPPING_LIST_NAME))

                list.add(listName)
            }
            while(result.moveToNext())
        }
        return list
    }

    /********************************** Shopping Items  ****************************************/
    //creating shopping Item table for 1 list
    fun createListTable(tableName: String)
    {
        val db = this.writableDatabase
        //creating Shopping Item table
        val createShoppingTable = "CREATE TABLE " + tableName + " (" +
                SHOPPING_ITEM_TABLE + " VARCHAR(256)"
        db?.execSQL(createShoppingTable)
    }

    //remove shopping list
    fun dropListTable(tableName: String)
    {
        val db = this.writableDatabase
        val query = "DROP TABLE IF EXISTS " + tableName
        val cursor = db.rawQuery(query, null)

        cursor.moveToFirst()
        cursor.close()
    }

    //Insert item to shopping list
    fun insertListItem(shoppingItem: ShoppingItem, tableName: String)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SHOPPING_ITEM_NAME, shoppingItem.productName)

        db.insert(tableName, null, contentValues)
    }

    //remove item from shopping list
    fun removeListItem(shoppingItem: ShoppingItem, tableName: String)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        db.delete(tableName, SHOPPING_ITEM_NAME + " =?",
            arrayOf(shoppingItem.productName))
    }

    //get all list items in list
    fun getListItem(ListTable: String): MutableList<ShoppingItem>
    {
        val list: MutableList<ShoppingItem> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $ListTable"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val shoppingItem = result.getString(result.getColumnIndex(SHOPPING_ITEM_NAME))

                val paymentOption = ShoppingItem(shoppingItem)
                list.add(paymentOption)
            }
            while(result.moveToNext())
        }
        return list
    }
    /********************************** Drivers ***********************************************/
    //Insert driver object to table
    fun insertDriver(driver: Driver)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DRIVER_NAME, driver.driverName)
        contentValues.put(DRIVER_COST, driver.cost)

        db.insert(DRIVER_TABLE, null, contentValues)
    }

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

    /********************************** Payment Options ***************************************/
    //Insert payment option object to table
    fun insertPaymentOprion(paymentOption: PaymentOption)
    {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(PAYMENT_OPTION_NAME, paymentOption.cardName)
        contentValues.put(PAYMETN_OPTION_NUMBER, paymentOption.cardNumber)

        db.insert(PAYMENT_OPTION_TABLE, null, contentValues)
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
                val paymentNum = result.getInt(result.getColumnIndex(PAYMETN_OPTION_NUMBER))

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
        contentValues.put(CHECKOUT_LIST_NAME, listName)

        db.insert(CHECKOUT_LIST_TABLE, null, contentValues)
    }

    fun removeCheckoutList(listName: String)
    {
        //remove list name from checkout list
        val db = this.writableDatabase
        val contentValues = ContentValues()

        db.delete(CHECKOUT_LIST_TABLE, CHECKOUT_LIST_NAME + " =?", arrayOf(listName))
    }

    fun getCheckoutList(): MutableList<String>
    {
        val list: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $CHECKOUT_LIST_TABLE"
        val result = db.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do
            {
                val listName = result.getString(result.getColumnIndex(CHECKOUT_LIST_NAME))
                list.add(listName)
            }
            while(result.moveToNext())
        }
        return list
    }
}