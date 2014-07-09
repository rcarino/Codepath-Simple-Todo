package com.codepath.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rcarino on 7/6/14.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {
    public TodoItemsAdapter(Context context, List<TodoItem> todoItems) {
        super(context, R.layout.item_todo, todoItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItem todo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_todo, parent, false);
        }
        // Lookup view for data population
        TextView tvPriority = (TextView) convertView.findViewById(R.id.tvPriority);
        TextView tvDueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
        TextView tvText = (TextView) convertView.findViewById(R.id.tvText);
        // Populate the data into the template view using the data object
        tvPriority.setText(String.valueOf(todo.priority));
        tvDueDate.setText(todo.dueDate);
        tvText.setText(todo.text);
        return convertView;
    }
}
