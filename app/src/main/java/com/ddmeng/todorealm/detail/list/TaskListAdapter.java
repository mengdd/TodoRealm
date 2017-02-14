package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.Task;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Task> taskList;

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_view_holder_layout, parent, false);
        return new TaskItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TaskItemViewHolder) holder).populate(taskList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
