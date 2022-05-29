package com.wang.kahn.knn3demo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wang.kahn.knn3demo.databinding.AddressListItemBinding

class SocialListAdapter(private val follows: List<SocialQuery.AddrsFollow>) :
    RecyclerView.Adapter<SocialListAdapter.ViewHolder>() {

    class ViewHolder(val binding: AddressListItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AddressListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val follow = follows[position]
        holder.binding.addressText.text = follow.address
        if (!follow.ens.isNullOrEmpty()) {
            holder.binding.addressEns.text = follow.ens[0]
        }

        holder.binding.addressName.text = follow.name ?: "No Name"

        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(follow.address)
        }
    }

    override fun getItemCount(): Int {
        return follows.size
    }

}