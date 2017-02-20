package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

    private int position;
    private TodoItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        long id = getIntent().getLongExtra("todo_item", -1);
        item = TodoItem.findById(TodoItem.class, id);
        position = getIntent().getIntExtra("position", -1);

        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        String name = item.getName();
        etEditItem.setText(name);
        etEditItem.setSelection(name.length(), name.length());
    }

    public void onSave(View view) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        item.setName(etEditItem.getText().toString());
        item.save();
        Intent data = new Intent();
        data.putExtra("todo_item", item.getId());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
