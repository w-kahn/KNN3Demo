package com.wang.kahn.knn3demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.Query
import com.wang.kahn.knn3demo.databinding.AddressListItemBinding
import com.wang.kahn.knn3demo.databinding.MembershipListItemBinding

class MembershipListFragment(query: LiveData<String>) :
    BaseListFragment<MembershipQuery.Data>(query) {

    private val adapter: MembershipListAdapter by lazy {
        MembershipListAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.layoutManager = LinearLayoutManager(requireContext())
        binding.list.adapter = adapter
    }

    override fun getQuery(): Query<MembershipQuery.Data> {
        return MembershipQuery(Optional.presentIfNotNull(query.value?.lowercase()))
    }

    override fun notifyData(data: MembershipQuery.Data) {
        if (data.features.isEmpty() || data.features[0].addrsFeature.isEmpty()) {
            model.setEmpty(true)
            return
        }
        model.setEmpty(false)
        adapter.submitList(data.features[0].addrsFeature)

    }

    class ViewHolder(val binding: AddressListItemBinding): RecyclerView.ViewHolder(binding.root)

    class MembershipListAdapter : ListAdapter<MembershipQuery.AddrsFeature, ViewHolder>(DiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = AddressListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
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
            holder.binding.addressEns.text = data.if_balance.toString()
            holder.binding.root.setOnClickListener {
                copyToClipboard(it.context, data.address)
                Toast.makeText(
                    it.context,
                    R.string.address_has_been_copied_to_clipboard,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MembershipQuery.AddrsFeature>() {
        override fun areItemsTheSame(
            oldItem: MembershipQuery.AddrsFeature,
            newItem: MembershipQuery.AddrsFeature
        ): Boolean {
            return oldItem.address == oldItem.address
        }

        override fun areContentsTheSame(
            oldItem: MembershipQuery.AddrsFeature,
            newItem: MembershipQuery.AddrsFeature
        ): Boolean {
            return oldItem.address == oldItem.address
        }
    }


}