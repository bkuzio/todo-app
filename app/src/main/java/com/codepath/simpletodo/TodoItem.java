package com.codepath.simpletodo;

import com.orm.SugarRecord;

public class TodoItem extends SugarRecord {
    private String name;

    public TodoItem() {
    }

    public TodoItem(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
