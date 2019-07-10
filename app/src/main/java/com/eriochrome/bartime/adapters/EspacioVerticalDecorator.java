package com.eriochrome.bartime.adapters;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class EspacioVerticalDecorator extends RecyclerView.ItemDecoration {

    private final int espacioVertical;

    public EspacioVerticalDecorator(int espacioVertical) {
        this.espacioVertical = espacioVertical;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1) {
            outRect.bottom = espacioVertical;
        }
    }
}
