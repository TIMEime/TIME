package io.github.timeime.time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";
    private static final String TABLE_PRODUCTS = "products";

    public static final String COLUMN_ID = "_ID";
    public static final String COLUMN_PRODUCTNAME = "name";

    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                                        + COLUMN_ID + " INTEGER PRIMARY KEY,"
                                        + COLUMN_PRODUCTNAME + " TEXT"
                                        + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void addProduct(DB newDB) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, newDB.getProductName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_PRODUCTS, null, values);
        db.close();
    }


    public ArrayList<DB> getAllProduct(){
        ArrayList<DB> dbs = new ArrayList<DB>();
        String query = "Select * FROM " + TABLE_PRODUCTS + " ORDER BY _ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                DB newDB = new DB();
                newDB.setID(Integer.parseInt(cursor.getString(0)));
                newDB.setProductName(cursor.getString(1));
                dbs.add(newDB);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return dbs;
    }

    public DB findProduct(String productname) {
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DB newDB = new DB();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            newDB.setID(Integer.parseInt(cursor.getString(0)));
            newDB.setProductName(cursor.getString(1));
            cursor.close();
        } else {
            newDB = null;
        }
        db.close();
        return newDB;
    }

    // 刪除資料
    public boolean deleteProduct(String productname) {

        boolean result = false;
        String query = "Select * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DB product = new DB();

        if (cursor.moveToFirst()) {
            product.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?",
                    new String[] { String.valueOf(product.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

}

