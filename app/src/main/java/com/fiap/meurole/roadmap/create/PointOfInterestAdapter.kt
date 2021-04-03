package com.fiap.meurole.roadmap.create

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.hitg.domain.entity.PointOfInterest

class PointOfInterestAdapter(
    private var items: List<PointOfInterest>,
    private var clickListener: (PointOfInterest) -> Unit
) : RecyclerView.Adapter<PointOfInterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.point_of_interest_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position == 0, position == items.size - 1, clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            item: PointOfInterest,
            isFirst: Boolean,
            isLast: Boolean,
            clickListener: (PointOfInterest) -> Unit
        ) {
            val title = itemView.findViewById<TextView>(R.id.tvPoiTitle)
            val description = itemView.findViewById<TextView>(R.id.tvPoiDescription)
            val vContinuousLineTop = itemView.findViewById<View>(R.id.vContinuousLineTop)
            val vContinuousLineBottom = itemView.findViewById<View>(R.id.vContinuousLineBottom)

            title.text = item.name
            description.text = item.description
            vContinuousLineTop.visibility = if (isFirst) View.GONE else View.VISIBLE
            vContinuousLineBottom.visibility = if (isLast) View.GONE else View.VISIBLE
            itemView.setOnClickListener { clickListener(item) }
        }
    }
}