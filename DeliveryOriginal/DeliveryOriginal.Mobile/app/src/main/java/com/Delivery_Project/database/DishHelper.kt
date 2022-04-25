package com.Delivery_Project.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.Delivery_Project.model.CartModel
import java.lang.Exception

class DishHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private val DATABASE_VERSION = 2
        private const val  DATABASE_NAME = "orders.db"
        private const val  DISH = "dish"
        private const val ID = "id"
        private const val  NAME = "name"
        private const val IMAGE = "imageUrl"
        private const val DESCRIPTION = "description"
        private const val CATEGORY_ID = "categoryId"
        private const val COST = "cost"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCartTable = ("CREATE TABLE " + DISH +  "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + IMAGE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + CATEGORY_ID + " INTEGER,"
                + COST + " REAL,"
                + "FOREIGN KEY($CATEGORY_ID) REFERENCES category(id))" )
        db?.execSQL(createCartTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $DISH")
        onCreate(db)
    }

    fun getAllDishes():ArrayList<CartModel>{
        val cartList: ArrayList<CartModel> = ArrayList()
        val selectQuery = "SELECT * FROM $DISH"
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
        var dishId: Int
        var cost: Double

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                image = cursor.getString(cursor.getColumnIndexOrThrow("image"))
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                dishId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))

                val order = CartModel(dishId = dishId,name = name, image = image, description = description, category = null, cost = cost)
                cartList.add(order)
            }while(cursor.moveToNext())
        }
        return cartList
    }
}