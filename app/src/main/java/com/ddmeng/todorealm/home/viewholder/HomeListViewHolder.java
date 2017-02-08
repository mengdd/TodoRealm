package com.ddmeng.todorealm.home.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;
import com.ddmeng.todorealm.home.HomeListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class HomeListViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.number)
    TextView numberTextView;

    private HomeListAdapter.HomeListCallback callback;

    public HomeListViewHolder(View itemView, HomeListAdapter.HomeListCallback callback) {
        super(itemView);
        this.callback = callback;
        ButterKnife.bind(this, itemView);
    }

    public void populate(TodoList list) {
        titleTextView.setText(list.getTitle());
        int tasksCount = list.getTasks().size();
        numberTextView.setText(String.valueOf(tasksCount));
    }

    @OnLongClick(R.id.list_item_container)
    boolean onItemLongClicked() {
        callback.onListItemLongClicked();
        itemView.setSelected(true);
        return true;
    }
}
