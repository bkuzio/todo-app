package codepath.com.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends Activity {

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        String item = getIntent().getStringExtra("todo_item");
        position = getIntent().getIntExtra("position", -1);

        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        etEditItem.setText(item);
        etEditItem.setSelection(item.length(), item.length());
    }

    public void onSave(View view) {
        EditText etEditItem = (EditText) findViewById(R.id.etEditItem);
        Intent data = new Intent();
        data.putExtra("todo_item", etEditItem.getText().toString());
        data.putExtra("position", position);
        setResult(RESULT_OK, data);
        finish();
    }
}
