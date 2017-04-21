package io.github.timeime.time;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import static io.github.timeime.time.DBTask.KEY_ID;
import static io.github.timeime.time.DBTask.PASSWORD_COLUMN;
import static io.github.timeime.time.DBTask.TABLE_NAME;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;// 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    private static final String DATABASE_NAME = "DB.db";// 資料庫名稱
    private static SQLiteDatabase db;

    // 建構子，在一般的應用都不需要修改
    public DBHelper(Context context, String name, CursorFactory factory,
                    int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteDatabase getDatabase(Context context) {
        if (db == null || !db.isOpen()) {
            db = new DBHelper(context, DATABASE_NAME,
                    null, DATABASE_VERSION).getWritableDatabase();
        }
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + PASSWORD_COLUMN + " TEXT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}






