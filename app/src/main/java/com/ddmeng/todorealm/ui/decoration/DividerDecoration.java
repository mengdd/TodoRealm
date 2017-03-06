package com.ddmeng.todorealm.ui.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DividerDecoration extends RecyclerView.ItemDecoration {
    public interface DividerAdapter {
        boolean hasDivider(int position);
    }

    private final Drawable divider;
    private final DividerAdapter adapter;

    public DividerDecoration(Drawable divider, DividerAdapter dividerAdapter) {
        this.divider = divider;
        this.adapter = dividerAdapter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0, 0, 0, divider.getIntrinsicHeight());
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);
            if (adapter.hasDivider(position)) {
                drawDividerView(canvas, child, left, right);
            }
        }
    }

    private void drawDividerView(Canvas canvas, View child, int left, int right) {
        final RecyclerView.LayoutParams params =
                (RecyclerView.LayoutParams) child.getLayoutParams();
        int top = child.getBottom() + params.bottomMargin;
        int bottom = top + divider.getIntrinsicHeight();
        divider.setBounds(left, top, right, bottom);
        divider.draw(canvas);
    }
}
