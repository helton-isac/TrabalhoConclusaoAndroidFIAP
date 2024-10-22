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
        holder.bind(items[position], clickListener)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: PointOfInterest, clickListener: (PointOfInterest) -> Unit) {
            val date = itemView.findViewById<TextView>(R.id.tvDate)
            val time = itemView.findViewById<TextView>(R.id.tvTime)
            val title = itemView.findViewById<TextView>(R.id.tvTitle)

            title.text = item.name

            itemView.setOnClickListener { clickListener(item) }
        }
    }
}