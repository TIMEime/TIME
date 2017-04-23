package io.github.timeime.time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;// 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    private static final String DATABASE_NAME = "12345.db";// 資料庫名稱
    private static final String TABLE_NAME = "ACCOUNT";//表格名稱
    public static final String ID_COLUMN = "_ID";//欄位名稱
    public static final String NAME_COLUMN = "DATA";//欄位名稱
    public static final String PASSWORD_COLUMN= "NAME";//欄位名稱

    //建構子，都是一樣的格式不用改他
    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //創建表格
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY,"
                + PASSWORD_COLUMN + " TEXT,"
                + NAME_COLUMN +  " TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    //更新表格
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //新增資料
    public void addData(DB mDB) {
        ContentValues values = new ContentValues();
        values.put(PASSWORD_COLUMN, mDB.getData());
        values.put(NAME_COLUMN, mDB.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //取得全部資料
    public ArrayList<DB> getAll(){
        ArrayList<DB> mDBs = new ArrayList<DB>();
        String query = "Select * FROM " + TABLE_NAME + " ORDER BY _ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                DB mDB = new DB();
                mDB.setID(Integer.parseInt(cursor.getString(0)));
                mDB.setData(cursor.getString(1));
                mDB.setName(cursor.getString(2));
                mDBs.add(mDB);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mDBs;
    }

    //找尋資料
    public DB findData(String data) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + PASSWORD_COLUMN + " =  \"" + data + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DB mDB = new DB();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            mDB.setData(cursor.getString(1));
            cursor.close();
        } else {
            mDB = null;
        }
        db.close();
        return mDB;
    }

    // 刪除資料
    public boolean deleteData(String data) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + PASSWORD_COLUMN + " =  \"" + data + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DB mDB = new DB();
        if (cursor.moveToFirst()) {
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, ID_COLUMN + " = ?",new String[] { String.valueOf(mDB.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}







