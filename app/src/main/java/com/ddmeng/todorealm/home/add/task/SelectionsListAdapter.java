package com.ddmeng.todorealm.home.add.task;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ddmeng.todorealm.R;
import com.ddmeng.todorealm.data.models.TodoList;

import java.util.List;

public class SelectionsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TodoList> selections;
    private SelectListCallback callback;

    public SelectionsListAdapter(SelectListCallback callback) {
        this.callback = callback;
    }

    public void setSelections(List<TodoList> selections) {
        this.selections = selections;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selection_item_view_holder, parent, false);
        return new SelectionViewHolder(view, callback);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SelectionViewHolder) holder).populate(selections.get(position));
    }

    @Override
    public int getItemCount() {
        return selections != null ? selections.size() : 0;
    }

    public interface SelectListCallback {
        void onListSelected(long listId, String title);
    }
}
