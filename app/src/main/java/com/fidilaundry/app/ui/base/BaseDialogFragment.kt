package com.fidilaundry.app.ui.base

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.util.dialog.RoundedBottomSheetDialogFragment
import java.util.*

abstract class BaseDialogFragment : RoundedBottomSheetDialogFragment() {
    lateinit var paperPref: PaperPrefs

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T =  DataBindingUtil.inflate(inflater, resId, container, false)
}

