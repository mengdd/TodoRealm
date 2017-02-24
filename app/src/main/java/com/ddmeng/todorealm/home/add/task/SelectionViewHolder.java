package com.ddmeng.todorealm.home.add.task;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectionViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView titleView;

    private SelectionsListAdapter.SelectListCallback callback;
    private TodoList list;

    public SelectionViewHolder(View itemView, SelectionsListAdapter.SelectListCallback callback) {
        super(itemView);
        this.callback = callback;
        ButterKnife.bind(this, itemView);
    }

    public void populate(TodoList list) {
        this.list = list;
        titleView.setText(list.getTitle());
    }

    @OnClick(R.id.title)
    void onItemClicked() {
        if (callback != null) {
            callback.onListSelected(list.getId(), list.getTitle());
        }
    }
}
