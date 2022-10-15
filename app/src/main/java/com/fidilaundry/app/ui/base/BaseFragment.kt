package com.fidilaundry.app.ui.base


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.util.IsInternetAvailable
//import com.fidilaundry.app.util.dialog.DialogNoConnection
import java.util.*

/**
 * DatabindingFragment is an abstract class for providing [DataBindingUtil].
 * provides implementations of only [ViewDataBinding] from an abstract information.
 * Do not modify this class. This is a first-level abstraction class.
 * If you want to add more specifications, make another class which extends [DatabindingFragment].
 */
abstract class BaseFragment : Fragment() {
    lateinit var paperPref: PaperPrefs

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T =  DataBindingUtil.inflate(inflater, resId, container, false)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        paperPref = PaperPrefs(requireContext())

        /*if(paperPref.getLang().toString() != resources.configuration.locale.toString()){
            changeLocale()
            val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false)
            }
            ft.detach(this).attach(this).commit()
        }
        else{
            changeLocale()
        }*/
    }

    /*private fun changeLocale(){
        val myLocale = Locale(paperPref.getLang())
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
    }*/

    override fun onResume() {
        super.onResume()

        /*if (!IsInternetAvailable(requireContext())) {
            paperPref.setNoConnection(true)
            val myRoundedBottomSheet = DialogNoConnection()
            if (myRoundedBottomSheet.isVisible) {
                myRoundedBottomSheet.dismiss()
            }
            myRoundedBottomSheet.show(childFragmentManager, myRoundedBottomSheet.tag)
        } else {
            paperPref.setNoConnection(false)
        }*/
    }

}
