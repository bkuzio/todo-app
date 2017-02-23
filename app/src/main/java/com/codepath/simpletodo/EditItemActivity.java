package com.codepath.simpletodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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


        TextView tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        if (item.getDueDate() != null) {
            tvDueDate.setText(SimpleDateFormat.getDateInstance().format(item.getDueDate()));
        }
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

    public void onEditDueDate(View view) {
        Calendar cal = Calendar.getInstance();
        if (item.getDueDate() != null) {
            cal.setTime(item.getDueDate());
        }
        DatePickerDialog dialog = new DatePickerDialog(this, this, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar instance = Calendar.getInstance();
        instance.set(year, month, dayOfMonth);
        item.setDueDate(instance.getTime());
    }
}
