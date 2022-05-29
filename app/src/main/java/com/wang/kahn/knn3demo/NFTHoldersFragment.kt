package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import com.wang.kahn.knn3demo.databinding.AddressListItemBinding

class NFTHoldersFragment(query: LiveData<String>) : BaseListFragment<NFTHolderQuery.Data>(query) {

    private val adapter by lazy {
        Adapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    override fun getQuery(): Query<NFTHolderQuery.Data> {
        return NFTHolderQuery(Optional.presentIfNotNull(query.value))
    }

    override fun notifyData(data: NFTHolderQuery.Data) {
        if (data.nfts.isEmpty() || data.nfts[0].addrsHold.isEmpty()) {
            model.setEmpty(true)
            return
        }
        model.setEmpty(false)
        adapter.submitList(data.nfts[0].addrsHold)
    }

    class ViewHolder(val binding: AddressListItemBinding) : RecyclerView.ViewHolder(binding.root)

    class Adapter : ListAdapter<NFTHolderQuery.AddrsHold,ViewHolder>(DiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding =
                AddressListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val data = getItem(position)
            holder.binding.addressText.text = data.address
            if (!data.ens.isNullOrEmpty()) {
                holder.binding.addressName.text = data.ens[0]
            } else {
                holder.binding.addressName.text = data.name ?: "No ENS"
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<NFTHolderQuery.AddrsHold>() {
        override fun areItemsTheSame(
            oldItem: NFTHolderQuery.AddrsHold,
            newItem: NFTHolderQuery.AddrsHold
        ): Boolean {
            return oldItem.address == newItem.address
        }

        override fun areContentsTheSame(
            oldItem: NFTHolderQuery.AddrsHold,
            newItem: NFTHolderQuery.AddrsHold
        ): Boolean {
            return oldItem.address == newItem.address
        }
    }

}