package com.fiap.meurole.roadmapList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.hitg.domain.entity.Roadmap

class RoadmapAdapter(
    private var items: List<Roadmap>,
    private var clickListener: (Roadmap, Int) -> Unit
) : RecyclerView.Adapter<RoadmapAdapter.ViewHolder>() {

    private val placeHolderList = listOf(
        R.drawable.trip_placeholder_01,
        R.drawable.trip_placeholder_02,
        R.drawable.trip_placeholder_03,
        R.drawable.trip_placeholder_04,
        R.drawable.trip_placeholder_05,
        R.drawable.trip_placeholder_06,
        R.drawable.trip_placeholder_07,
        R.drawable.trip_placeholder_08,
        R.drawable.trip_placeholder_09,
        R.drawable.trip_placeholder_10,
        R.drawable.trip_placeholder_11
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.roadmap_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items[position],
            placeHolderList[position % placeHolderList.size],
            clickListener
        )
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Roadmap, placeHolderImage: Int, clickListener: (Roadmap, Int) -> Unit) {
            val tvTitle = itemView.findViewById<TextView>(R.id.tvPoiTitle)
            tvTitle.text = item.name
            val ivPhoto = itemView.findViewById<ImageView>(R.id.ivPhoto)
            ivPhoto.setImageResource(placeHolderImage)
            val tvUserCreator = itemView.findViewById<TextView>(R.id.tvUserCreator)
            tvUserCreator.text = item.creatorName
            tvTitle.setOnClickListener { clickListener(item, placeHolderImage) }
            ivPhoto.setOnClickListener { clickListener(item, placeHolderImage) }
            tvUserCreator.setOnClickListener { clickListener(item, placeHolderImage) }
        }
    }
}