package com.Delivery_Project.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.Delivery_Project.constants.Constants
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.CART
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.CATEGORY
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.CATEGORY_ID
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.COST
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.DATABASE_NAME
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.DATABASE_VERSION
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.DESCRIPTION
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.DISH
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.DISH_ID
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.ID
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.IMAGE
import com.Delivery_Project.constants.Constants.SharedPreferences.Companion.NAME
import com.Delivery_Project.model.CartModel
import com.Delivery_Project.pojo.Category
import com.Delivery_Project.pojo.Dish
import java.lang.Exception
import java.math.BigDecimal
import java.math.RoundingMode

class DatabaseHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createCategoryTable = ("CREATE TABLE " + " IF NOT EXISTS "+ CATEGORY +  "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + IMAGE + " TEXT)")

        val createDishTable = ("CREATE TABLE " + " IF NOT EXISTS " + DISH +   "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT,"
                + IMAGE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + CATEGORY_ID + " INTEGER,"
                + COST + " REAL,"
                + "FOREIGN KEY($CATEGORY_ID) REFERENCES category(id) on delete cascade)" )

        val createCartTable = ("CREATE TABLE " + " IF NOT EXISTS "+ CART +  "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DISH_ID + " INTEGER,"
                + NAME + " TEXT,"
                + IMAGE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + CATEGORY_ID + " INTEGER,"
                + COST + " REAL" + ","
                + "FOREIGN KEY($DISH_ID) REFERENCES dish(id) on delete cascade,"
                + "FOREIGN KEY($CATEGORY_ID) REFERENCES category(id) on delete cascade" + ")" )

        db?.execSQL(createCartTable)
        db?.execSQL(createCategoryTable)
        db?.execSQL(createDishTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $CART")
        onCreate(db)
    }

    fun insertOrder(cart: CartModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NAME, cart.name)
        contentValues.put(DISH_ID, cart.dishId)
        contentValues.put(IMAGE, cart.image)
        contentValues.put(DESCRIPTION, cart.description)
        contentValues.put(CATEGORY_ID, cart.category?.Id)
        contentValues.put(COST, cart.cost)
        val success = db.insert(CART, null, contentValues)
        db.close()
        return success
    }
    fun insertDishes(dishList: ArrayList<Dish>){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        for(dish in dishList){
            contentValues.put(ID, dish.Id)
            contentValues.put(NAME, dish.Name)
            contentValues.put(IMAGE, dish.ImageUrl)
            contentValues.put(CATEGORY_ID, dish.Category?.Id)
            contentValues.put(DESCRIPTION, dish.Description)
            contentValues.put(COST, dish.Cost)
            db.insert(DISH, null, contentValues)
        }
        db.close()
    }
    fun insertCategories(categoryList: ArrayList<Category>){
        val db = this.writableDatabase

        val contentValues = ContentValues()
        for(category in categoryList){
            contentValues.put(ID, category.Id)
            contentValues.put(NAME, category.Name)
            contentValues.put(IMAGE, category.ImageUrl)
            db.insert(CATEGORY, null, contentValues)
        }
        db.close()
    }

    fun getAllOrders(context: Context):ArrayList<CartModel>{
        val cartList: ArrayList<CartModel> = ArrayList()
        val selectQuery = "SELECT * FROM $CART"
        val db = this.readableDatabase
        val categoryDb = DatabaseHelper(context);
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return  ArrayList()
        }

        var name: String
        var image :String?
        var description :String
        var categoryId : Int
        var dishId: Int
        var cost: Double

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                image = try{
                    cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"))
                } catch (err: Error){
                    null
                }
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("categoryId"))
                dishId = cursor.getInt(cursor.getColumnIndexOrThrow("dishId"))
                cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))
                val category = categoryDb.getAllCategories().filter { x -> x.Id == categoryId }[0];
                val order = CartModel(dishId = dishId, name = name, image = image, description = description, category = category, cost = cost)
                cartList.add(order)
            }while(cursor.moveToNext())
        }
        return cartList
    }
    fun getTotalCost():Double{
        val selectQuery = "SELECT SUM(cost) FROM $CART "
        val db = this.readableDatabase
        val cursor: Cursor?
        cursor = db.rawQuery(selectQuery, null)
        var amount:Double

        if(cursor.moveToFirst()){
            amount = cursor.getDouble(0)
        }else {
            amount = -1.0
        }
        val decimal = BigDecimal(amount).setScale(2,RoundingMode.HALF_EVEN).toDouble()

        cursor.close()
        return decimal
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


    fun getAllDishes():ArrayList<Dish>{
        val dishList: ArrayList<Dish> = ArrayList()
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
        var image :String?
        var description :String
        var dishId: Int
        var cost: Double
        var categoryId: Int

        if(cursor.moveToFirst()){
            do {
                name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                image = try{
                    cursor.getString(cursor.getColumnIndexOrThrow("imageUrl"))
                } catch (err: Error){
                    null;
                }
                description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                dishId = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))
                categoryId = cursor.getInt(cursor.getColumnIndexOrThrow("categoryId"))

                val category = this.getAllCategories().filter { x -> x.Id == categoryId }[0];
                val dish = Dish(Id = dishId,Name = name, ImageUrl = image, Description = description, Category = category, Cost = cost)
                dishList.add(dish)
            }while(cursor.moveToNext())
        }
        return dishList
    }

    fun amountIdenticalDishes(cartDish: CartModel) : Int{
        val dishId = cartDish.dishId;
        val selectQuery = "SELECT * FROM $CART WHERE $DISH_ID = $dishId"
        val db = this.readableDatabase

        var dishAmount = 0
        try {
            dishAmount = db.rawQuery(selectQuery, null).count
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
        }

        db.close()

        return dishAmount
    }

    fun cleanCart(){
        val db = this.writableDatabase
        db.delete(CART, null, null);
    }

    fun deleteOrder(order: CartModel) {
        val db = this.writableDatabase
        val dishId = order.dishId
        db.execSQL("DELETE FROM $CART WHERE $ID = (SELECT $ID FROM $CART WHERE $DISH_ID = $dishId LIMIT 1 )")
        db.close()
    }

    fun updateDishes(dishList:ArrayList<Dish>){
        val db = this.writableDatabase

        db.execSQL("DELETE FROM $DISH")

        insertDishes(dishList);
    }

    fun deleteRemovedDishes(){
        val db = this.writableDatabase

        db.execSQL("delete from $CART where id in (select id from cart where id not in (select cart.id from cart inner join dish on dish.id = cart.dishId))")
    }

    fun updateCategories(categoryList: ArrayList<Category>){
        val db = this.writableDatabase

        db.execSQL("DELETE FROM $CATEGORY")

        insertCategories(categoryList)
    }
    fun deleteDish(dishId: Int){
        val db = this.writableDatabase
        db.execSQL("delete from $CART where $DISH_ID = $dishId")
    }
}