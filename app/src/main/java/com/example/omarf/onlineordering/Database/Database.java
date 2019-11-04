package com.example.omarf.onlineordering.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.omarf.onlineordering.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;


public class Database extends SQLiteAssetHelper {

    private static final String db_name = "OnlineOrderingDB.db";
    private static final int db_version = 1;


    public Database(Context context) {

        super(context, db_name, null, db_version);
    }


    public List<Order> getCarts(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount"};
        String sqlTable = "OrderDetail";
        sqLiteQueryBuilder.setTables(sqlTable);
        Cursor cursor = sqLiteQueryBuilder.query(sqLiteDatabase,sqlSelect,null, null, null, null, null);

        final List<Order> result = new ArrayList<>();
        if(cursor.moveToFirst())
        {
            do {
                {
                    result.add(new Order(cursor.getString(cursor.getColumnIndex("ProductId")),
                            cursor.getString(cursor.getColumnIndex("ProductName")),
                            cursor.getString(cursor.getColumnIndex("Quantity")),
                            cursor.getString(cursor.getColumnIndex("Price")),
                            cursor.getString(cursor.getColumnIndex("Discount"))
                            ));
                }
            }while (cursor.moveToNext());
        }

        return result;
    }


    public void addToCart(Order order){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount) VALUES('%s', '%s', '%s', '%s', '%s');",
                order.getProductId(), order.getProductName(), order.getQuantity(), order.getPrice(), order.getDiscount());
        sqLiteDatabase.execSQL(query);
    }
    public void deleteCart(){

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        sqLiteDatabase.execSQL(query);
    }

}
