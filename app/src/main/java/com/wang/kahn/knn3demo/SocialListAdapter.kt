package com.wang.kahn.knn3demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wang.kahn.knn3demo.databinding.SocialListItemBinding

class SocialListAdapter(private val follows: List<SocialQuery.AddrsFollow>) :
    RecyclerView.Adapter<SocialListAdapter.ViewHolder>() {

    class ViewHolder(val binding: SocialListItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SocialListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = follows[position]
        holder.binding.addressText.text = follow.address
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(follow.address)
        }
    }

    override fun getItemCount(): Int {
        return follows.size
    }

}