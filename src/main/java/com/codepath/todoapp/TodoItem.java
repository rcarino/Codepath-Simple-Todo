package com.codepath.todoapp;

import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by rcarino on 7/6/14.
 */
public class TodoItem {
    // Uses due date as a secondary order
    public static final Comparator<TodoItem> PRIORITY_ORDER = new Comparator<TodoItem>() {
        @Override
        public int compare(TodoItem todoItem, TodoItem todoItem2) {
            if (todoItem.priority > todoItem2.priority) {
                return 1;
            } else if (todoItem.priority < todoItem2.priority) {
                return -1;
            } else {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    return format.parse(todoItem.dueDate).compareTo(format.parse(todoItem2.dueDate));
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
    };

    public long id;
    public int priority;
    public String dueDate;
    public String text;

    public TodoItem() {

    }

    public TodoItem(int priority, String dueDate, String text) {
        this.priority = priority;
        this.dueDate = dueDate;
        this.text = text;
    }
}
