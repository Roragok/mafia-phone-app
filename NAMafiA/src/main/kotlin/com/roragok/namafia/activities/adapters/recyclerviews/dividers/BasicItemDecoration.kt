/*
 * Copyright 2019 Roragok. All Rights Reserved.
 */

package com.roragok.namafia.activities.adapters.recyclerviews.dividers

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Style
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.State
import com.roragok.namafia.R

/**
 * Creates a new instance of [BasicItemDecoration]
 *
 * @param context the current context
 * @param dividerAfterLastItem flag if a divider should be shown after the last item
 * @param dividerHeightDp how tall the divider should be, in DP
 * @param dividerColor the color of the divider. Defaults to R.color.gray_light (#dddddd)
 *
 * @since 1.0.0
 */
class BasicItemDecoration(
    private val context: Context,
    private val dividerBeforeFirstItem: Boolean = false,
    private val dividerAfterLastItem: Boolean = true,
    private val dividerHeightDp: Float = 1f,
    @ColorRes private val dividerColor: Int = R.color.divider
) : ItemDecoration() {

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dividerHeightPx: Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerHeightDp, context.resources.displayMetrics).toInt()

    init {
        paint.color = ContextCompat.getColor(context, dividerColor)
        paint.style = Style.FILL
        paint.strokeWidth = dividerHeightDp
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: State) {
        super.getItemOffsets(outRect, view, parent, state)

        if (parent.getChildAdapterPosition(view) == 0) {
            return
        }

        outRect.top = dividerHeightPx
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: State) {
        var childCount = parent.childCount

        childCount = if (dividerAfterLastItem)
            childCount
        else
            childCount - 1

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            if (dividerBeforeFirstItem && i == 0) {
                canvas.drawRect(Rect(0, child.top, parent.width, child.top + dividerHeightPx), paint)
            }

            canvas.drawRect(Rect(0, child.bottom, parent.width, child.bottom + dividerHeightPx), paint)
        }
    }
}