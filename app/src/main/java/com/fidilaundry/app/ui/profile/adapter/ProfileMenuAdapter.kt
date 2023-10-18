package com.fidilaundry.app.ui.profile.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidilaundry.app.ui.history.model.HistoryData
import com.fidilaundry.app.R
import com.fidilaundry.app.basearch.localpref.PaperPrefs
import com.fidilaundry.app.databinding.ItemHistoryBinding
import com.fidilaundry.app.databinding.ItemProfileMenuBinding
import com.fidilaundry.app.model.response.ProfileResponse
import com.fidilaundry.app.ui.MainActivity
import com.fidilaundry.app.ui.complaint.ComplaintActivity
import com.fidilaundry.app.ui.complaint.UserComplaintActivity
import com.fidilaundry.app.ui.profile.ChangePassActivity
import com.fidilaundry.app.ui.profile.ChangeProfileActivity
import com.fidilaundry.app.ui.profile.interfaces.IProfile
import com.fidilaundry.app.ui.profile.model.ProfileMenu
import com.fidilaundry.app.util.setSafeOnClickListener
import kotlin.math.roundToInt

class ProfileMenuAdapter(private val context: Context?, private val inf: IProfile) :
    RecyclerView.Adapter<ProfileMenuAdapter.ViewHolder>() {

    lateinit var paperPref: PaperPrefs
    private var profileData: ProfileResponse.Results? = null
    private var appList: List<ProfileMenu>

    fun updateList(appList: List<ProfileMenu>) {
        this.appList = appList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProfileMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        paperPref = PaperPrefs(context!!)
        profileData = paperPref.getDataProfile()

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    inner class ViewHolder(private val binding: ItemProfileMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(position: Int) {
            val app = appList[position]
            binding.tvTitle.text = app.title

            /*Glide.with(context!!)
                .load(app.iconNotif)
                .into(binding.ivIcon)*/

//            if (profileData?.role != "customer" || profileData?.role != "member") {
//                if (app.id == 3) {
//                    itemView.visibility = View.GONE
//                    itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
//                } else {
//                    itemView.visibility = View.VISIBLE
//                    itemView.layoutParams = RecyclerView.LayoutParams(
//                        ViewGroup.LayoutParams.MATCH_PARENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT
//                    )
//                }
//            }

            itemView.setSafeOnClickListener {
                var intent: Intent? = null
                when (app.id) {
                    1 -> intent = Intent(context, ChangeProfileActivity::class.java)
                    2 -> intent = Intent(context, ChangePassActivity::class.java)
                    3 -> intent = Intent(context, ComplaintActivity::class.java)
                    4 -> inf.onLogout()
                    else -> intent = Intent(context, UserComplaintActivity::class.java)
                }

                if (intent != null)
                    context!!.startActivity(intent)
            }
        }
    }

    init {
        appList = ArrayList()
    }
}