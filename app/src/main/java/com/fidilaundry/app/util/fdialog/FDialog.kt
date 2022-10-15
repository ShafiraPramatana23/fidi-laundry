package com.fidilaundry.app.util.fdialog

import android.app.Activity
import com.fidilaundry.app.R


fun SuccessMessage(activity: Activity, title: String, messages: String) {
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setPositiveBtnText("OK")
        .setImageResource(R.drawable.success)
        .setGifResource("success.json")
        .isCancellable(false)
        .build()
}

fun SuccessMessage(activity: Activity, title: String, messages: String, callback: FGCallback){
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setPositiveBtnText("OK")
        .setImageResource(R.drawable.success)
        .setGifResource("success.json")
        .isCancellable(false)
        .OnPositiveClicked(object : FancyGifDialogListener {
            override fun onClick() {
                callback.onCallback()
            }
        })
        .build()
}

fun SuccessMessage(activity: Activity, messages: String) {
    FancyGifDialog.Builder(activity)
        .setMessage(messages)
        .setPositiveBtnText("OK")
        .setImageResource(R.drawable.success)
        .setGifResource("success.json")
        .isCancellable(false)
        .build()
}

fun WarningMessage(activity: Activity, title: String, messages: String) {
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setPositiveBtnText("OK")
        .setImageResource(R.drawable.success)
        .setGifResource("alert.json")
        .isCancellable(false)
        .build()
}


fun ErrorMessageLogout(activity: Activity, title: String, messages: String, posCallback: FGCallback) {
    FancyGifDialog.Builder(activity)
            .setTitle(title)
            .setMessage(messages)
            .setPositiveBtnText("OK")
            .setImageResource(R.drawable.success)
            .setGifResource("failed.json")
            .isCancellable(false)
            .OnPositiveClicked(object : FancyGifDialogListener {
                override fun onClick() {
                    posCallback.onCallback()
                }
            })
            .build()
}

fun ErrorMessage(activity: Activity, title: String, messages: String) {
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setPositiveBtnText("OK")
        .setImageResource(R.drawable.success)
        .setGifResource("failed.json")
        .isCancellable(false)
        .build()
}

fun ProgressMessage(activity: Activity, messages: String) {
    FGProgress.Builder(activity)
        .setMessage(messages)
        .setGifResource(R.drawable.success)
        .isCancellable(false)
        .build()
}

fun ConfirmMessage(activity: Activity, title: String, messages: String, img: String, btnPositive: String, btnNegative: String, posCallback: FGCallback){
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setType("confirm")
        .setPositiveBtnText(btnPositive)
        .setNegativeBtnText(btnNegative)
        .setImageResource(R.drawable.success)
        .setGifResource(img)
        .isCancellable(false)
        .OnPositiveClicked(object : FancyGifDialogListener {
            override fun onClick() {
                posCallback.onCallback()
            }
        })
        .build()
}

fun ConfirmMessage(activity: Activity, title: String, messages: String, img: String, btnPositive: String, btnNegative: String, posCallback: FGCallback, negCallback: FGCallback){
    FancyGifDialog.Builder(activity)
            .setTitle(title)
            .setMessage(messages)
            .setType("confirm")
            .setPositiveBtnText(btnPositive)
            .setNegativeBtnText(btnNegative)
            .setImageResource(R.drawable.success)
            .setGifResource(img)
            .isCancellable(false)
            .OnPositiveClicked(object : FancyGifDialogListener {
                override fun onClick() {
                    posCallback.onCallback()
                }
            })
            .OnNegativeClicked(object : FancyGifDialogListener {
                override fun onClick() {
                    negCallback.onCallback()
                }
            })
            .build()
}

fun MaintenanceMessage(activity: Activity, title: String, messages: String, btnPositive: String, img: String){
    FancyGifDialog.Builder(activity)
        .setTitle(title)
        .setMessage(messages)
        .setType("maintenance")
//        .setImageResource(img)
        .setGifResource(img)
        .setPositiveBtnText(btnPositive)
        .build()
}