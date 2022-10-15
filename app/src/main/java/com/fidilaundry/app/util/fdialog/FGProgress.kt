package com.fidilaundry.app.util.fdialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.fidilaundry.app.R
import pl.droidsonroids.gif.GifImageView

class FGProgress {
    class Builder(private val activity: Activity) {
        private var message: String? = null
        private var cancel = false
        private var gifImageResource = 0
        val dialog = Dialog(activity)

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun isCancellable(cancel: Boolean): Builder {
            this.cancel = cancel
            return this
        }

        fun setGifResource(gifImageResource: Int): Builder {
            this.gifImageResource = gifImageResource
            return this
        }

        fun dismissProgress() {
            dialog.dismiss()
        }

        fun build(): FancyGifDialog {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(cancel)
            dialog.setContentView(R.layout.fancygifprogress)

            //getting resources
            val message1: TextView = dialog.findViewById(R.id.message)
            val gifImageView: GifImageView = dialog.findViewById(R.id.gifImageView)
            gifImageView.setImageResource(gifImageResource)
            message1.text = message

            dialog.show()
            return FancyGifDialog()
        }
    }
}