package com.fiap.meurole.roadmapList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fiap.meurole.R
import com.hitg.domain.entity.Roadmap

class RoadmapAdapter(
    private var items: List<Roadmap>
) : RecyclerView.Adapter<RoadmapAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.roadmap_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Roadmap) {
            val title = itemView.findViewById<TextView>(R.id.tvTitle)
            val description = itemView.findViewById<TextView>(R.id.tvDescription)

            title.text = item.name
            description.text = item.description
        }
    }
}