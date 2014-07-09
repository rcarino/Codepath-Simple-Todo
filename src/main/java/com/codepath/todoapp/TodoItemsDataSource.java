package com.codepath.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcarino on 7/8/14.
 */
public class TodoItemsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PRIORITY, MySQLiteHelper.COLUMN_DUE_DATE,
            MySQLiteHelper.COLUMN_TEXT
    };

    public TodoItemsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public TodoItem createTodoItem(int priority, String dueDate, String text) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_PRIORITY, priority);
        values.put(MySQLiteHelper.COLUMN_DUE_DATE, dueDate);
        values.put(MySQLiteHelper.COLUMN_TEXT, text);

        long insertId = database.insert(MySQLiteHelper.TABLE_TODO_ITEMS, null,
                values);

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TODO_ITEMS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();

        TodoItem newTodoItem = cursorToTodoItem(cursor);
        cursor.close();
        return newTodoItem;
    }

    public void pushUpdatedTodoItem(TodoItem todoItem) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_PRIORITY, todoItem.priority);
        values.put(MySQLiteHelper.COLUMN_DUE_DATE, todoItem.dueDate);
        values.put(MySQLiteHelper.COLUMN_TEXT, todoItem.text);

        database.update(MySQLiteHelper.TABLE_TODO_ITEMS, values,
                MySQLiteHelper.COLUMN_ID + " = " + todoItem.id, null);
    }

    public void deleteTodoItem(TodoItem todoItem) {
        long id = todoItem.id;
        database.delete(MySQLiteHelper.TABLE_TODO_ITEMS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<TodoItem> getAllTodoItems() {
        List<TodoItem> todoItems = new ArrayList<TodoItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_TODO_ITEMS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TodoItem todoItem = cursorToTodoItem(cursor);
            todoItems.add(todoItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return todoItems;
    }



    private TodoItem cursorToTodoItem(Cursor cursor) {
        TodoItem todoItem = new TodoItem();
        todoItem.id = cursor.getLong(0);
        todoItem.priority = cursor.getInt(1);
        todoItem.dueDate = cursor.getString(2);
        todoItem.text = cursor.getString(3);

        return todoItem;
    }
}
