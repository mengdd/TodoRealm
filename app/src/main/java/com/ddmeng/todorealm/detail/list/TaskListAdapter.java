package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.Task;
import com.ddmeng.todorealm.ui.decoration.DividerDecoration;
import com.ddmeng.todorealm.ui.multiselect.MultiSelector;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements CompletedToggleViewHolder.CompletedToggleCallback, DividerDecoration.DividerAdapter {
    private static final int VIEW_TYPE_TASK = 0;
    private static final int VIEW_TYPE_COMPLETED_TOGGLE = 1;
    private List<Task> taskList;
    private TaskListCallback callback;
    private MultiSelector multiSelector;
    private int todoTasksCount;
    private boolean isShowingDone;


    public TaskListAdapter(TaskListCallback callback, MultiSelector multiSelector) {
        this.callback = callback;
        this.multiSelector = multiSelector;
        setHasStableIds(true);
    }

    public void setTaskList(final List<Task> todoTasks, final List<Task> doneTasks) {
        this.taskList = new ArrayList<>();
        taskList.addAll(todoTasks);
        taskList.addAll(doneTasks);
        todoTasksCount = todoTasks.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == todoTasksCount) {
            return VIEW_TYPE_COMPLETED_TOGGLE;
        } else {
            return VIEW_TYPE_TASK;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_TASK: {
                View view = layoutInflater.inflate(R.layout.task_item_view_holder_layout, parent, false);
                return new TaskItemViewHolder(view, callback, multiSelector);
            }
            case VIEW_TYPE_COMPLETED_TOGGLE: {
                View view = layoutInflater.inflate(R.layout.completed_toggle_view_holder_layout, parent, false);
                return new CompletedToggleViewHolder(view, this);
            }

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TaskItemViewHolder) {
            TaskItemViewHolder taskItemHolder = (TaskItemViewHolder) holder;
            Task task = getTask(position);
            taskItemHolder.populate(task);
        }
    }

    @Override
    public long getItemId(int position) {
        Task task = getTask(position);
        if (task != null) {
            return task.getId();
        } else {
            return super.getItemId(position);
        }
    }

    @Override
    public int getItemCount() {
        return isShowingDone ? taskList.size() + 1 : todoTasksCount + 1;
    }

    private Task getTask(int adapterPosition) {
        if (adapterPosition < todoTasksCount) {
            return taskList.get(adapterPosition);
        } else if (adapterPosition > todoTasksCount) {
            return taskList.get(adapterPosition - 1);
        } else {
            return null;
        }
    }

    @Override
    public void onToggleCheckedChanged(boolean isToggleOn) {
        isShowingDone = isToggleOn;
    }

    @Override
    public boolean hasDivider(int position) {
        return position != todoTasksCount && position != getItemCount() - 1;
    }

    public interface TaskListCallback {
        void onTaskItemCheckedChanged(Task task, boolean isDone);

        void onTaskItemClicked(Task task);

        void onTaskItemLongClicked(Task task);
    }
}
