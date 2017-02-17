package com.ddmeng.todorealm.detail.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;

import com.ddmeng.todorealm.R;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class CompletedToggleViewHolder extends RecyclerView.ViewHolder {

    private CompletedToggleCallback callback;

    public CompletedToggleViewHolder(View itemView, CompletedToggleCallback callback) {
        super(itemView);
        this.callback = callback;
        ButterKnife.bind(this, itemView);
    }

    @OnCheckedChanged(R.id.completed_toggle_button)
    void onToggleCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        callback.onToggleCheckedChanged(isChecked);
    }

    public interface CompletedToggleCallback {
        void onToggleCheckedChanged(boolean isToggleOn);
    }
}
