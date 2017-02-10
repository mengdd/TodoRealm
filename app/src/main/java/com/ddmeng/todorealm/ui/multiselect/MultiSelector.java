package com.ddmeng.todorealm.ui.multiselect;

import android.util.SparseArray;
import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Thanks to the great work of https://github.com/bignerdranch/recyclerview-multiselect
 */
public class MultiSelector {

    private SparseBooleanArray selections = new SparseBooleanArray();
    private WeakHolderTracker holderTracker = new WeakHolderTracker();
    private SparseArray<Long> itemIdsTracker = new SparseArray<>();
    private boolean isSelectable;

    public boolean isSelectable() {
        return isSelectable;
    }

    public void setSelectable(boolean selectable) {
        isSelectable = selectable;
        refreshAllHolders();
    }

    public void bindHolder(SelectableHolder holder, int position) {
        holderTracker.bindHolder(holder, position);
        itemIdsTracker.put(position, holder.getItemId());
        refreshHolder(holder);

    }

    public void setSelected(SelectableHolder holder, boolean isSelected) {
        setSelected(holder.getAdapterPosition(), isSelected);
    }

    public void setSelected(int position, boolean isSelected) {
        selections.put(position, isSelected);
        refreshHolder(holderTracker.getHolder(position));
    }

    public boolean isSelected(int position) {
        return selections.get(position);
    }

    public List<Integer> getSelectedPositions() {
        List<Integer> positions = new ArrayList<>();

        for (int i = 0; i < selections.size(); i++) {
            if (selections.valueAt(i)) {
                positions.add(selections.keyAt(i));
            }
        }

        return positions;
    }

    public List<Long> getSelectedItemIds() {
        List<Long> ids = new ArrayList<>();

        for (int i = 0; i < selections.size(); i++) {
            if (selections.valueAt(i)) {
                int selectedPosition = selections.keyAt(i);
                ids.add(itemIdsTracker.get(selectedPosition));
            }
        }

        return ids;
    }

    public void clearSelections() {
        selections.clear();
        refreshAllHolders();
    }

    public boolean tapSelection(SelectableHolder holder) {
        return tapSelection(holder.getAdapterPosition());
    }

    public boolean tapSelection(int position) {
        if (isSelectable) {
            boolean isSelected = isSelected(position);
            setSelected(position, !isSelected);
            return true;
        } else {
            return false;
        }

    }

    public void refreshAllHolders() {
        for (SelectableHolder holder : holderTracker.getTrackedHolders()) {
            refreshHolder(holder);
        }
    }

    private void refreshHolder(SelectableHolder holder) {
        if (holder == null) {
            return;
        }

        boolean isActivated = selections.get(holder.getAdapterPosition());
        holder.setActivated(isActivated);
    }

}
