package com.qibla.qiblacompass.prayertime.finddirection.presentation.views.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.qibla.qiblacompass.prayertime.finddirection.R


//class OnboardingPagerAdapter(private val data: List<OnboardingItem>) : PagerAdapter() {
//
//    override fun getCount(): Int = data.size
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        val view = LayoutInflater.from(container.context)
//            .inflate(R.layout.items_onboarding, container, false)
//        val ivOnboard: ImageView = view.findViewById(R.id.iv_onboard)
//        val tvOnboardTitle: TextView = view.findViewById(R.id.tv_onboard_title)
//        val tvOnboardDesc: TextView = view.findViewById(R.id.tv_onboard_desc)
//
//        ivOnboard.setImageResource(data[position].imageResId)
//        tvOnboardTitle.text = data[position].title
//        tvOnboardDesc.text = data[position].description
//
//        container.addView(view)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as View)
//    }
//}
class OnboardingPagerAdapter(private val onboardingItems: List<OnboardingItem>) :
    RecyclerView.Adapter<OnboardingPagerAdapter.OnboardingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.items_onboarding, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val item = onboardingItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = onboardingItems.size

    class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivOnboard: ImageView = itemView.findViewById(R.id.iv_onboard)
        private val tvOnboardTitle: TextView = itemView.findViewById(R.id.tv_onboard_title)
        private val tvOnboardDesc: TextView = itemView.findViewById(R.id.tv_onboard_desc)
        private val indicator: ImageView = itemView.findViewById(R.id.iv_onboard_indicator)

        // Bind data to views
        fun bind(item: OnboardingItem) {
            ivOnboard.setImageResource(item.imageResId)
            tvOnboardTitle.text = item.title
            tvOnboardDesc.text = item.description
            indicator.setImageResource(item.imageResIdIndicator)
        }
        // Bind data to views
//        fun bind(item: OnboardingItem) {
//            val imgTitle: ImageView = itemView.findViewById(R.id.iv_onboard)
//            val tvTitle: TextView = itemView.findViewById(R.id.tv_onboard_title)
//            val tvDetail: TextView = itemView.findViewById(R.id.tv_onboard_desc)
//        }
    }
}