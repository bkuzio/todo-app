package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodoItemAdapter extends ArrayAdapter<TodoItem> {

    private static class ViewHolder {
        TextView name;
        TextView dueDate;
        CheckBox done;
    }


    public TodoItemAdapter(Context context, List<TodoItem> users) {
        super(context, R.layout.todo_item, users);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position

        TodoItem todoItem = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {

            // If there's no view to re-use, inflate a brand new view for row
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.todo_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.dueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
            viewHolder.done = (CheckBox) convertView.findViewById(R.id.cbDone);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.name.setText(todoItem.getName());
        viewHolder.done.setChecked(todoItem.isDone());
        Date dueDate = todoItem.getDueDate();
        if (dueDate != null) {
            viewHolder.dueDate.setText(SimpleDateFormat.getDateTimeInstance().format(dueDate));
        }
        // Return the completed view to render on screen
        return convertView;
    }
}