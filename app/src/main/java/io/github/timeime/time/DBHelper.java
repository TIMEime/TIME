package io.github.timeime.time;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 8;// 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    private static final String DATABASE_NAME = "4444444444444.db";// 資料庫名稱

    private static final String DATA_TABLE_NAME = "DATA_TABLE";//表格名稱
    private static final String DATA_ID_COLUMN = "_ID";//欄位名稱
    private static final String DATA_ACCOUNT_COLUMN = "ACCOUNT";//欄位名稱
    private static final String DATA_NAME_COLUMN = "NAME";//欄位名稱
    private static final String DATA_DATA_COLUMN= "DATA";//欄位名稱

    private static final String ACCOUNT_TABLE_NAME = "ACCOUNT_TABLE";//表格名稱
    private static final String ACCOUNT_ID_COLUMN = "_ID";//欄位名稱
    private static final String ACCOUNT_ACCOUNT_COLUMN = "ACCOUNT";//欄位名稱
    private static final String ACCOUNT_PASSWORD_COLUMN= "PASSWORD";//欄位名稱
    private static final String ACCOUNT_EMAIL_COLUMN= "EMAIL";//欄位名稱

    //建構子，都是一樣的格式不用改他
    public DBHelper(Context context, String name, CursorFactory factory,int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    //創建表格
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_DATA_TABLE = "CREATE TABLE " + DATA_TABLE_NAME + "("
                + DATA_ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL,"
                + DATA_ACCOUNT_COLUMN + " TEXT NOT NULL,"
                + DATA_NAME_COLUMN + " TEXT NOT NULL,"
                + DATA_DATA_COLUMN +  " TEXT NOT NULL"
                + ")";
        final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + ACCOUNT_TABLE_NAME + "("
                + ACCOUNT_ID_COLUMN + " INTEGER PRIMARY KEY NOT NULL,"
                + ACCOUNT_ACCOUNT_COLUMN + " TEXT NOT NULL,"
                + ACCOUNT_PASSWORD_COLUMN + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_DATA_TABLE);
        db.execSQL(CREATE_ACCOUNT_TABLE);
    }

    //更新表格
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE_NAME);
        onCreate(db);
    }

    //新增資料
    public void addData(DB mDB) {
        ContentValues values = new ContentValues();
        values.put(DATA_ACCOUNT_COLUMN,mDB.getAccount());
        values.put(DATA_DATA_COLUMN, mDB.getData());
        values.put(DATA_NAME_COLUMN, mDB.getName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(DATA_TABLE_NAME, null, values);
        db.close();
    }

    //新增帳戶
    public void addAccount(AccountDB mDB){
        ContentValues values =new ContentValues();
        values.put(ACCOUNT_ACCOUNT_COLUMN,mDB.getAccount());
        values.put(ACCOUNT_PASSWORD_COLUMN,mDB.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNT_TABLE_NAME, null, values);
        db.close();
    }

    //取得某帳戶內的資料
    public ArrayList<DB> getAccountData(String account){
        ArrayList<DB> mDBs = new ArrayList<DB>();
        String query = "Select * FROM " + DATA_TABLE_NAME + " WHERE " + DATA_ACCOUNT_COLUMN + " =  \"" + account + "\""+" ORDER BY _ID ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int rows_num = cursor.getCount();
        if(rows_num != 0) {
            cursor.moveToFirst();
            for(int i=0; i<rows_num; i++) {
                DB mDB = new DB();
                mDB.setAccount(cursor.getString(1));
                mDB.setName(cursor.getString(2));
                mDB.setData(cursor.getString(3));
                mDBs.add(mDB);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return mDBs;
    }

    //找尋某筆資料
    public DB findData(String name) {
        String query = "Select * FROM " + DATA_TABLE_NAME + " WHERE " + DATA_NAME_COLUMN + " =  \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DB mDB = new DB();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            mDB.setData(cursor.getString(3));
            cursor.close();
        } else {
            mDB = null;
        }
        db.close();
        return mDB;
    }

    //驗證密碼
    public boolean findPassword(String account,String password){
        int flag=0;
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + " WHERE " + ACCOUNT_ACCOUNT_COLUMN + " =  \"" + account + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        AccountDB mDB = new AccountDB();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mDB.setPassword(cursor.getString(2));
            cursor.close();
        } else {
            mDB = null;
        }
        if(!password.equals(mDB.getPassword())){
            flag=1;
        }
        db.close();
        if(flag==1){
            return false;
        }else{
            return true;
        }
    }

    //看資料名稱是否重複
    public boolean findDataExists(String name){
        int flag=0;
        String query = "Select * FROM " + DATA_TABLE_NAME + " WHERE " + DATA_NAME_COLUMN + " =  \"" + name + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount()==0) {
            flag=0;
        }else{
            flag=1;
        }
        db.close();
        if(flag==1){
            return false;
        }else{
            return true;
        }
    }

    //找尋帳號是否存在
    public boolean findAccount(String account){
        int flag=0;
        String query = "Select * FROM " + ACCOUNT_TABLE_NAME + " WHERE " + ACCOUNT_ACCOUNT_COLUMN + " =  \"" + account + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount()==0) {
            flag=0;
        }else{
            flag=1;
        }
        db.close();
        if(flag==1){
            return false;
        }else{
            return true;
        }
    }

    // 刪除資料
    public boolean deleteData(String data) {
        boolean result = false;
        String query = "Select * FROM " + DATA_TABLE_NAME + " WHERE " + DATA_NAME_COLUMN + " =  \"" + data + "\"";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DB mDB = new DB();
        if (cursor.moveToFirst()) {
            mDB.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(DATA_TABLE_NAME, DATA_ID_COLUMN + " = ?",new String[] { String.valueOf(mDB.getID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }
}







