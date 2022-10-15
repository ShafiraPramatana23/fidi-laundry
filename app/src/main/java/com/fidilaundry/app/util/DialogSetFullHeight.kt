package com.fidilaundry.app.util

import android.app.Dialog
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

object DialogCustomHeight {
    fun setCustomHeight(
        dialog: Dialog?,
        context: FragmentActivity,
        view: RecyclerView?,
        size: Int,
        offset: Int,
        marginBottom: Float
    ) {
        val metrics = DisplayMetrics()
        context.windowManager?.defaultDisplay?.getMetrics(metrics)

        if (size > 10) {
            (dialog as? BottomSheetDialog)?.behavior?.apply {
                isFitToContents = false
                expandedOffset = offset
                state = BottomSheetBehavior.STATE_EXPANDED
                peekHeight = metrics.heightPixels
            }

            if (view != null) {
                val r: Resources = context.resources
                val pxBottom = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    marginBottom,
                    r.displayMetrics
                ).toInt()

                val pxTop = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5F,
                    r.displayMetrics
                ).toInt()

                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(0, pxTop, 0, pxBottom)
                view?.layoutParams = params
            }
        }

        dialog!!.setOnShowListener {
            if (size > 10) {
                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { it ->
                    val behaviour = BottomSheetBehavior.from(it)
                    setupFullHeight(it)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}