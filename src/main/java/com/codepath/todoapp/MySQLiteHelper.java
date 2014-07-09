package com.codepath.todoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by rcarino on 7/8/14.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String TABLE_TODO_ITEMS = "todo_items";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRIORITY = "priority";
    public static final String COLUMN_DUE_DATE = "due_date";
    public static final String COLUMN_TEXT = "text";

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_TODO_ITEMS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PRIORITY + " INTEGER NOT NULL, " +
                    COLUMN_DUE_DATE + " TEXT NOT NULL, " +
                    COLUMN_TEXT + " TEXT NOT NULL" +
                    ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data"
        );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_ITEMS);
        onCreate(db);
    }

}

