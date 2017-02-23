package com.codepath.simpletodo;

import com.orm.SugarRecord;

import java.util.Date;

public class TodoItem extends SugarRecord {
    private String name;
    private boolean done;
    private Date dueDate;

    public TodoItem() {
    }

    public TodoItem(String name) {
        this.name = name;
        this.done = false;
        this.dueDate = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return name;
    }
}
