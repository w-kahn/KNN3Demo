package com.wang.kahn.knn3demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wang.kahn.knn3demo.databinding.EventListItemBinding

class EventListAdapter(private val events: List<POAPEventsQuery.AttendEvent>) :
    RecyclerView.Adapter<EventListAdapter.ViewHolder>() {

    class ViewHolder(val binding: EventListItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EventListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = events[position]
        holder.binding.eventId.text = event.id
        holder.binding.eventName.text = event.name
        holder.binding.root.setOnClickListener {
            // TODO: add event detail
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

}