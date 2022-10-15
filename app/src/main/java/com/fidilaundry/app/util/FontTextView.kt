package com.fidilaundry.app.util

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.fidilaundry.app.R

class FontTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        applyCustomFont(this, context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        applyCustomFont(this, context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        applyCustomFont(this, context, attrs)
    }

    companion object {
        const val ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android"
        fun applyCustomFont(customFontTextView: TextView, context: Context, attrs: AttributeSet?) {
            val attributeArray = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.FontTextView)
            val fontName = attributeArray.getString(R.styleable.FontTextView_fontName)
            var textStyle = attributeArray.getInt(R.styleable.FontTextView_textStyle, 0)
            if (textStyle == 0) {
                textStyle = attrs!!.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL)
            }
            val customFont = selectTypeface(context, fontName!!, textStyle)
            customFontTextView.typeface = customFont
            attributeArray.recycle()
        }

        private fun selectTypeface(context: Context, fontName: String, textStyle: Int): Typeface? {
            return if (fontName.contentEquals(context.getString(R.string.font_name_fontawesome))) {
                FontCache.getTypeface("fonts/AvenirNextLTPro-Regular.otf", context)
            } else if (fontName.contentEquals(context.getString(R.string.font_name_dmsans))) {
                when (textStyle) {
                    11 -> FontCache.getTypeface("fonts/DMSans-Regular.ttf", context)
                    12 -> FontCache.getTypeface("fonts/DMSans-Medium.ttf", context)
                    13 -> FontCache.getTypeface("fonts/DMSans-Bold.ttf", context)
                    Typeface.NORMAL -> FontCache.getTypeface("fonts/DMSans-Regular.ttf", context)
                    else -> FontCache.getTypeface("fonts/DMSans-Regular.ttf", context)
                }
            } else {
                null
            }
        }
    }
}