package com.ddmeng.todorealm.ui.multiselect;

import android.util.SparseArray;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

class WeakHolderTracker {
    private SparseArray<WeakReference<SelectableHolder>> holderByPosition = new SparseArray<>();

    SelectableHolder getHolder(int position) {
        WeakReference<SelectableHolder> holderRef = holderByPosition.get(position);
        if (holderRef == null) {
            return null;
        }

        SelectableHolder viewHolder = holderRef.get();
        if (viewHolder == null || viewHolder.getAdapterPosition() != position) {
            holderByPosition.remove(position);
            return null;
        }
        return viewHolder;
    }

    void bindHolder(SelectableHolder holder, int position) {
        holderByPosition.put(position, new WeakReference<>(holder));
    }

    List<SelectableHolder> getTrackedHolders() {
        List<SelectableHolder> holders = new ArrayList<>();

        for (int i = 0; i < holderByPosition.size(); i++) {
            int key = holderByPosition.keyAt(i);
            SelectableHolder holder = getHolder(key);

            if (holder != null) {
                holders.add(holder);
            }
        }

        return holders;
    }
}
