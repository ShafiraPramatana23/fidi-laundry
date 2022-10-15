package com.fidilaundry.app.util.fdialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.airbnb.lottie.LottieAnimationView
import com.fidilaundry.app.R


class FancyGifDialog {
    class Builder(private val activity: Activity) {
        private var title: String? = null
        private var message: String? = null
        private var type: String? = null
        private var positiveBtnText: String? = null
        private var negativeBtnText: String? = null
        private var pBtnColor: String? = null
        private var nBtnColor: String? = null
        private var pListener: FancyGifDialogListener? = null
        private var nListener: FancyGifDialogListener? = null
        private var cancel = false
        private var gifImageResource = ""
        private var imageResource = 0
        private var scaleType: ImageView.ScaleType? = null

        fun setTitle(title: String?): Builder {
            this.title = title
            return this
        }

        fun setMessage(message: String?): Builder {
            this.message = message
            return this
        }

        fun setType(type: String?): Builder {
            this.type = type
            return this
        }

        fun setScaleType(str: ImageView.ScaleType): Builder {
            this.scaleType = str
            return this
        }

        fun setPositiveBtnText(positiveBtnText: String?): Builder {
            this.positiveBtnText = positiveBtnText
            return this
        }

        fun setPositiveBtnBackground(pBtnColor: String?): Builder {
            this.pBtnColor = pBtnColor
            return this
        }

        fun setNegativeBtnText(negativeBtnText: String?): Builder {
            this.negativeBtnText = negativeBtnText
            return this
        }

        fun setNegativeBtnBackground(nBtnColor: String?): Builder {
            this.nBtnColor = nBtnColor
            return this
        }

        //set Positive listener
        fun OnPositiveClicked(pListener: FancyGifDialogListener?): Builder {
            this.pListener = pListener
            return this
        }

        //set Negative listener
        fun OnNegativeClicked(nListener: FancyGifDialogListener?): Builder {
            this.nListener = nListener
            return this
        }

        fun isCancellable(cancel: Boolean): Builder {
            this.cancel = cancel
            return this
        }

        fun setGifResource(gifImageResource: String): Builder {
            this.gifImageResource = gifImageResource
            return this
        }

        fun setImageResource(imageResource: Int): Builder {
            this.imageResource = imageResource
            return this
        }

        fun build(): FancyGifDialog {
            val message1: TextView
            val title1: TextView
            val nBtn: Button
            val pBtn: Button
            val oBtn: Button
            val llPos2: LinearLayout
            val llConfirm: LinearLayout
            val rl1: RelativeLayout
            val rl2: RelativeLayout
            val rlDivider: RelativeLayout
            val lav: LottieAnimationView
            val rlLav: RelativeLayout
            val view: View
            val dialog = Dialog(activity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(cancel)
            dialog.setContentView(R.layout.fancygifdialog)

            //getting resources
            title1 = dialog.findViewById(R.id.title)
            message1 = dialog.findViewById(R.id.message)
            nBtn = dialog.findViewById(R.id.negativeBtn)
            pBtn = dialog.findViewById(R.id.positiveBtn)
            oBtn = dialog.findViewById(R.id.btnPos2)
            llPos2 = dialog.findViewById(R.id.llPos2)
            llConfirm = dialog.findViewById(R.id.llConfirm)
            rlDivider = dialog.findViewById(R.id.rlDivider)
            lav = dialog.findViewById(R.id.lav)
            rlLav = dialog.findViewById(R.id.rlLav)
            view = dialog.findViewById(R.id.view)

            if (gifImageResource != "") {
                lav.setAnimation("lottiefiles/$gifImageResource")
                lav.playAnimation()
            } else {
                rlLav.visibility = View.GONE
                view.visibility = View.VISIBLE
            }

            title1.text = title
            message1.text = message

            if (!title.equals("")) {
                title1.visibility = View.VISIBLE
            } else {
                title1.visibility = View.GONE
            }

            if (positiveBtnText != null) {
                pBtn.text = positiveBtnText
                if (pBtnColor != null) {
                    val bgShape = pBtn.background as GradientDrawable
                    bgShape.setColor(Color.parseColor(pBtnColor))
                }
                pBtn.setOnClickListener {
                    if (pListener != null) pListener!!.onClick()
                    dialog.dismiss()
                }
            } else {
                pBtn.visibility = View.GONE
            }

            if (positiveBtnText != null) {
                oBtn.text = positiveBtnText
                if (pBtnColor != null) {
                    val bgShape = pBtn.background as GradientDrawable
                    bgShape.setColor(Color.parseColor(pBtnColor))
                }
                oBtn.setOnClickListener {
                    if (pListener != null) pListener!!.onClick()
                    dialog.dismiss()
                }
            } else {
                oBtn.visibility = View.GONE
            }

            if (negativeBtnText != null) {
                nBtn.text = negativeBtnText
                nBtn.setOnClickListener {
                    if (nListener != null) nListener!!.onClick()
                    dialog.dismiss()
                }
                if (nBtnColor != null) {
                    val bgShape = nBtn.background as GradientDrawable
                    bgShape.setColor(Color.parseColor(nBtnColor))
                }
            } else {
                nBtn.visibility = View.GONE
                rlDivider.visibility = View.GONE
            }
            dialog.show()

            if (type.equals("confirm") || type.equals("maintenance")) {
                val window: Window? = dialog.window
                window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
//            else if (type.equals("maintenance")) {
//                val window: Window? = dialog.window
//                window?.setLayout(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT
//                )
//
//                gifImageView2.setImageResource(imageResource)
//                llPos2.visibility = View.VISIBLE
//                llConfirm.visibility = View.GONE
//                rl1.visibility = View.GONE
//                rl2.visibility = View.VISIBLE
//            }
            return FancyGifDialog()
        }
    }
}