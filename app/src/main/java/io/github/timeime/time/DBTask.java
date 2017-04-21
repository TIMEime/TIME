package io.github.timeime.time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBTask  {
    private Context context;
    public static final String TABLE_NAME = "TIME";
    public static final String KEY_ID = "_id";
    public static final String PASSWORD_COLUMN= "name";
    private SQLiteDatabase db;

    public DBTask(Context context) {
        db = DBHelper.getDatabase(context);
    }
    //新增資料
    public void addData(DB mDB) {
        ContentValues cv = new ContentValues();
        cv.put(PASSWORD_COLUMN, mDB.getProductName());
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public ArrayList<DB> getAll(){
        ArrayList<DB> mDBs = new ArrayList<DB>();
        String query = "Select * FROM " + TABLE_NAME + " ORDER BY _ID ASC";
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                DB mDB = new DB();
                mDB.setID(Integer.parseInt(cursor.getString(0)));
                mDB.setProductName(cursor.getString(1));
                mDBs.add(mDB);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mDBs;
    }

    public DB findProduct(String productname) {
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + PASSWORD_COLUMN + " =  \"" + productname + "\"";
        Cursor cursor = db.rawQuery(query, null);
        DB mDB = new DB();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            mDB.setProductName(cursor.getString(1));
            cursor.close();
        } else {
            mDB = null;
        }
        db.close();
        return mDB;
    }

    // 刪除資料
    public boolean deleteData(String productname) {
        boolean result = false;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + PASSWORD_COLUMN + " =  \"" + productname + "\"";
        Cursor cursor = db.rawQuery(query, null);

        DB mDB = new DB();

        if (cursor.moveToFirst()) {
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_NAME, KEY_ID + " = ?",
                    new String[] { String.valueOf(mDB.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}
