package codepath.com.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    public static final int EDIT_CODE = 10;
    private List<String> items;
    private ArrayAdapter<String> itemsAdapter;
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
        itemsAdapter.add(text);
        etNewItem.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == EDIT_CODE) {
            String todoItem = data.getExtras().getString("todo_item");
            int position = data.getExtras().getInt("position", -1);
            if (position == -1) {
                Toast toast = Toast.makeText(this, "Failed to edit item!", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }
            items.set(position, todoItem);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(MainActivity.this, EditItemActivity.class);
                editIntent.putExtra("todo_item", items.get(position));
                editIntent.putExtra("position", position);
                startActivityForResult(editIntent, EDIT_CODE);
            }
        });
    }

    private void readItems() {
        File files = getFilesDir();
        File todoFile = new File(files, "todo.txt");

        try {
            items = new ArrayList<>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            e.printStackTrace();
            items = new ArrayList<>();
        }
    }

    private void writeItems() {
        File files = getFilesDir();
        File todoFile = new File(files, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
