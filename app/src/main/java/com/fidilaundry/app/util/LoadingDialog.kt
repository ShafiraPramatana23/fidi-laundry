package com.fidilaundry.app.util

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.fidilaundry.app.databinding.ProgressDialogBinding


class LoadingDialog {

    private lateinit var dialog: Dialog

    fun progressDialog(context: Activity, text: String): Dialog {
        dialog = Dialog(context)
        val binding: ProgressDialogBinding = ProgressDialogBinding.inflate(LayoutInflater.from(dialog.context))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)
        binding.tvText.text = text
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        return dialog
    }

    fun showProgressDialog(context: Activity, text: String) {
        progressDialog(context, text).show()
    }

    fun dismissDialog() {
        if((this::dialog.isInitialized))
            dialog.dismiss()
    }

    fun isShowLoad(): Boolean {
        return if((this::dialog.isInitialized))
            dialog.isShowing
        else
            false
    }
}