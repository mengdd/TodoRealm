package com.ddmeng.todorealm.data;

import com.ddmeng.todorealm.data.models.TodoList;

import io.realm.Realm;
import io.realm.RealmResults;

public class TodoRepository {

    public RealmResults<TodoList> getAllLists() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<TodoList> todoLists = realm.where(TodoList.class).findAll();
        return todoLists;
    }
}
