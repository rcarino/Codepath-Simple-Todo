package com.codepath.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TodoActivity extends Activity {
    public static final int REQUEST_CODE = 20;

    private List<TodoItem> todoItems;
    private TodoItemsAdapter todoAdapter;
    private ListView lvItems;

    private EditText etPriority;
    private EditText etDueDate;
    private EditText etNewItem;

    private TodoItemsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        etPriority = (EditText) findViewById(R.id.etEditPriority);
        etDueDate = (EditText)findViewById(R.id.etEditDueDate);
        etNewItem = (EditText) findViewById(R.id.etNewItem);

        lvItems = (ListView) findViewById(R.id.lvItems);

        dataSource = new TodoItemsDataSource(this);
        dataSource.open();
        todoItems = dataSource.getAllTodoItems();
        Collections.sort(todoItems, TodoItem.PRIORITY_ORDER);

        todoAdapter = new TodoItemsAdapter(this, todoItems);
        lvItems.setAdapter(todoAdapter);

        setupListViewListener(); // List view listens to short clicks and long clicks
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
                dataSource.deleteTodoItem(todoItems.remove(pos));
                handleTodoItemsUpdate();
                return false;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View item, int pos, long id) {
                Intent i = new Intent(TodoActivity.this, EditItemActivity.class);
                TodoItem curItem = todoItems.get(pos);
                i.putExtra("itemPriority", curItem.priority);
                i.putExtra("itemDueDate", curItem.dueDate);
                i.putExtra("itemText", curItem.text);
                i.putExtra("itemPos", pos);
                i.putExtra("mode", 2);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onAddedItem(View v) {
        try {
            int priority = Integer.parseInt(etPriority.getText().toString());
            String dueDate = etDueDate.getText().toString();
            String itemText = etNewItem.getText().toString();
            etPriority.setText("");
            etDueDate.setText("");
            etNewItem.setText("");

            todoAdapter.add(dataSource.createTodoItem(priority, dueDate, itemText));
            handleTodoItemsUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == EditItemActivity.RESPONSE_OK && requestCode == REQUEST_CODE) {
            try {
                int itemPos = data.getIntExtra("itemPos", 0);
                int itemPriority = data.getIntExtra("itemPriority", 0);
                String itemDueDate = data.getStringExtra("itemDueDate");
                String itemText = data.getStringExtra("itemText");
                TodoItem curItem = todoItems.get(itemPos);

                curItem.priority = itemPriority;
                curItem.dueDate = itemDueDate;
                curItem.text = itemText;

                dataSource.pushUpdatedTodoItem(curItem);
                handleTodoItemsUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handleTodoItemsUpdate() {
        Collections.sort(todoItems, TodoItem.PRIORITY_ORDER);
        todoAdapter.notifyDataSetChanged();
    }
}
