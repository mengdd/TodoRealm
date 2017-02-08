package com.ddmeng.todorealm.data.models;

import io.realm.RealmList;
import io.realm.RealmObject;

public class TodoList extends RealmObject {
    private String title;
    private RealmList<Task> tasks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<Task> getTasks() {
        return tasks;
    }
}
