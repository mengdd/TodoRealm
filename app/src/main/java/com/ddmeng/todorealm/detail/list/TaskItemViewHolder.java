package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.ui.multiselect.MultiSelector;
import com.ddmeng.todorealm.ui.multiselect.SelectableHolder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class TaskItemViewHolder extends RecyclerView.ViewHolder implements SelectableHolder {
    @BindView(R.id.task_title)
    TextView titleTextView;
    @BindView(R.id.task_checkbox)
    CheckBox checkBox;

    private TaskListAdapter.TaskListCallback callback;
    private MultiSelector multiSelector;
    private Task task;

    public TaskItemViewHolder(View itemView, TaskListAdapter.TaskListCallback callback, MultiSelector multiSelector) {
        super(itemView);
        this.callback = callback;
        this.multiSelector = multiSelector;
        ButterKnife.bind(this, itemView);
    }

    public void populate(Task task) {
        this.task = task;
        titleTextView.setText(task.getTitle());
        checkBox.setChecked(task.isDone());
        multiSelector.bindHolder(this, getAdapterPosition());
    }

    @OnClick(R.id.task_item_container)
    void onItemClick() {
        if (!multiSelector.tapSelection(this)) {
            callback.onTaskItemClicked(task);
        }
    }

    @OnLongClick(R.id.task_item_container)
    boolean onItemLongClicked() {
        if (!multiSelector.isSelectable()) {
            multiSelector.setSelectable(true);
            multiSelector.setSelected(this, true);
            callback.onTaskItemLongClicked(task);
            return true;
        }

        return false;
    }

    @Override
    public void setActivated(boolean activated) {
        itemView.setActivated(activated);
    }
}
