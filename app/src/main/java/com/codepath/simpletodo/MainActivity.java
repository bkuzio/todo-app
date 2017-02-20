package com.codepath.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final int EDIT_CODE = 10;
    private List<TodoItem> items;
    private ArrayAdapter<TodoItem> itemsAdapter;
    private ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void onAddItem(View view) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String text = etNewItem.getText().toString().trim();
        if (text.trim().length() == 0) {
            return;
        }
        TodoItem item = new TodoItem(text);
        itemsAdapter.add(item);
        item.save();
        etNewItem.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_CODE) {
            long todoItem = data.getExtras().getLong("todo_item");
            int position = data.getExtras().getInt("position", -1);
            if (position == -1) {
                Toast toast = Toast.makeText(this, "Failed to edit item!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            items.set(position, TodoItem.findById(TodoItem.class, todoItem));
            itemsAdapter.notifyDataSetChanged();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                itemsAdapter.notifyDataSetChanged();
                TodoItem todoItem = items.remove(position);
                todoItem.delete();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                editIntent.putExtra("todo_item", items.get(position).getId());
                editIntent.putExtra("position", position);
                startActivityForResult(editIntent, EDIT_CODE);
            }
        });
    }

    private void readItems() {
        items = new ArrayList<>(TodoItem.listAll(TodoItem.class));
    }
}
