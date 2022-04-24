package com.Delivery_Project.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.Delivery_Project.model.OrderModel
import java.lang.Exception

class OrderHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 1
        private const val  DATABASE_NAME = "orders.db"
        private const val  TBL_ORDERS = "tbl_orders"
        private const val ID = "id"
        private const val  NAME = "name"
        private const val IMAGE = "image"
        private const val DESCRIPTION = "description"
        private const val AMOUNT = "amount"
        private const val COST = "cost"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblOrders = ("CREATE TABLE " + TBL_ORDERS +  "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + IMAGE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + AMOUNT + " INTEGER,"
                + COST + " REAL" + ")" )
        db?.execSQL(createTblOrders)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ORDERS")
        onCreate(db)
    }

    fun insertOrder(order: OrderModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, order.name)
        contentValues.put(IMAGE, order.image)
        contentValues.put(DESCRIPTION, order.description)
        contentValues.put(AMOUNT, order.amount)
        contentValues.put(COST, order.cost)
        val success = db.insert(TBL_ORDERS, null, contentValues)
        db.close()
        return success
    }

    fun getAllOrders():ArrayList<OrderModel>{
        val orderList: ArrayList<OrderModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ORDERS"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return  ArrayList()
        }

        var name: String
        var image :String
        var description :String
        var amount: Int
        var cost: Double

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                amount = cursor.getInt(cursor.getColumnIndexOrThrow("amount"))
                cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))

                val order = OrderModel(name = name, image = image, description = description, amount = amount, cost = cost)
                orderList.add(order)
            }while(cursor.moveToNext())
        }
        return orderList
    }
    fun getTotalCost():Double{
        val selectQuery = "SELECT SUM(cost) FROM $TBL_ORDERS "
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(selectQuery, null)
        var amount:Double

        if(cursor.moveToFirst()){
            amount = cursor.getDouble(0)
        }else {
            amount = -1.0
        }
        cursor.close()
        return amount
    }
}