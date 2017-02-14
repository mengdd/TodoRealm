package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.Task;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.task_title)
    TextView titleTextView;
    @BindView(R.id.task_checkbox)
    CheckBox checkBox;

    public TaskItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Task task) {
        titleTextView.setText(task.getTitle());
        checkBox.setChecked(task.isDone());
    }
}
