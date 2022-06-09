package com.task.notes.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.task.notes.ui.screens.adapter.NotesAdapter

class SwipeToDelete(
    adapter: NotesAdapter,
    deleteIconRes: Drawable
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val mAdapter = adapter
    private val deleteIcon = deleteIconRes

    private val inWidth = deleteIcon.intrinsicWidth
    private val inHeight = deleteIcon.intrinsicHeight

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    // todo: check delete from index
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.deleteItem(viewHolder.absoluteAdapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        val iconMargin = (itemHeight - inHeight) / 4
        val iconTop = itemView.top + (itemHeight - inHeight) / 2
        val iconLeft = itemView.right - iconMargin - inWidth
        val iconRight = itemView.right - iconMargin
        val iconBottom = iconTop + inHeight

        deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

}