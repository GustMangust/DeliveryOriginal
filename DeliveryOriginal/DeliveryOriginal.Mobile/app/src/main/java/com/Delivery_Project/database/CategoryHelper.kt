package com.Delivery_Project.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Category
import java.lang.Exception
import kotlin.collections.ArrayList

class CategoryHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    companion object {
        private val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "orders.db"
        private const val CATEGORY = "category"
        private const val ID = "id"
        private const val NAME = "name"
        private const val IMAGE = "imageUrl"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createCartTable = ("CREATE TABLE " + CATEGORY + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + IMAGE + " TEXT)")
        db?.execSQL(createCartTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CATEGORY")
        onCreate(db)
    }

    fun getAllCategories():ArrayList<Category>{
        val categoryList: ArrayList<Category> = ArrayList()
        val selectQuery = "SELECT * FROM $CATEGORY"
        val db = this.readableDatabase

        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return  ArrayList()
        }

        var id: Int
        var name: String
        var image :String

        if(cursor.moveToFirst()){
            do {

                id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                image = cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"))

                val category = Category(id, image,name)
                categoryList.add(category)
            }while(cursor.moveToNext())
        }
        return categoryList
    }
}
