package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ddmeng.todorealm.data.models.Task;

import butterknife.ButterKnife;

public class TaskItemViewHolder extends RecyclerView.ViewHolder {
    public TaskItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populate(Task task){

    }
}
