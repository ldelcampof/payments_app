package com.dconsultores.cobranzamalaga.ui;

/**
 * Created by ldelcampo on 25/06/16.
 */
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.support.annotation.IntegerRes;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by ldelcampo on 29/04/16.
 */
public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {
    //La medida en pixeles del espaciado
    private int mItemOffset;

    public ItemOffsetDecoration(Context context, @IntegerRes int integerResId){
        // Obtener el recurso
        int itemOffsetDp = context.getResources().getInteger(integerResId);
        // Convertir los dp a pixeles
        mItemOffset = convertToPx(itemOffsetDp, context.getResources().getDisplayMetrics());
    }

    public int convertToPx (int offsetDp, DisplayMetrics metrics){
        // Formula para convertir dp en pixeles
        return offsetDp * (metrics.densityDpi / 160);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
    }
}
