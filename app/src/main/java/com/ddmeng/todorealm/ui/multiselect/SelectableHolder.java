package com.ddmeng.todorealm.ui.multiselect;

public interface SelectableHolder {
    int getAdapterPosition();

    void setActivated(boolean activated);

    long getItemId();
}
