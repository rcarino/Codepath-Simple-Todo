package com.codepath.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends Activity {
    public static final int RESPONSE_OK = 0;

    private Button btnSave;
    private int itemPos;

    private EditText etEditPriority;
    private EditText etEditDueDate;
    private EditText etEditInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etEditPriority = (EditText) findViewById(R.id.etEditPriority);
        etEditDueDate = (EditText) findViewById(R.id.etEditDueDate);
        etEditInput = (EditText) findViewById(R.id.etEditInput);
        btnSave = (Button) findViewById(R.id.btnSave);

        int priority = getIntent().getIntExtra("itemPriority", 0);
        String dueDate = getIntent().getStringExtra("itemDueDate");
        String itemText = getIntent().getStringExtra("itemText");
        itemPos = getIntent().getIntExtra("itemPos", 0);

        etEditPriority.setText(Integer.toString(priority));
        etEditDueDate.setText(dueDate);
        etEditInput.setText(itemText);
        etEditInput.requestFocus();

        setupSaveButtonListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_item, menu);
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

    public void onSubmit(View v) {
        this.finish();
    }

    private void setupSaveButtonListener() {
        btnSave.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View button) {
                Intent data = new Intent();
                data.putExtra("itemPriority", Integer.parseInt(etEditPriority.getText().toString()));
                data.putExtra("itemDueDate", etEditDueDate.getText().toString());
                data.putExtra("itemText", etEditInput.getText().toString());
                data.putExtra("itemPos", itemPos);
                setResult(RESPONSE_OK, data);
                finish();
            }
        });
    }
}
