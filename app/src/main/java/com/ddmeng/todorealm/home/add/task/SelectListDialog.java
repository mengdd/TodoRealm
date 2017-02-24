package com.ddmeng.todorealm.home.add.task;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.TodoRepository;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class SelectListDialog extends DialogFragment implements SelectionsListAdapter.SelectListCallback {
    public static final String TAG = "SelectListDialog";

    @BindView(R.id.selections_list)
    RecyclerView selectionsList;

    private SelectionsListAdapter adapter;
    private TodoRepository repository;
    private SelectionsListAdapter.SelectListCallback callback;

    public void setCallback(SelectionsListAdapter.SelectListCallback callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LogUtils.footPrint();
        return super.onCreateDialog(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        return inflater.inflate(R.layout.fragment_select_list_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.footPrint();
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
        loadData();
    }

    @Override
    public void onStart() {
        super.onStart();
        initFullScreen(getDialog());
    }

    private void initFullScreen(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            ViewGroup.LayoutParams params = window.getAttributes();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes((android.view.WindowManager.LayoutParams) params);
        }
    }

    private void initViews() {
        selectionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SelectionsListAdapter(this);
        selectionsList.setAdapter(adapter);
    }

    private void loadData() {
        repository = TodoRepository.getInstance();
        RealmResults<TodoList> allLists = repository.getAllLists();
        adapter.setSelections(allLists);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onListSelected(long listId, String title) {
        dismiss();
        if (callback != null) {
            callback.onListSelected(listId, title);
        }
    }
}
