package com.wang.kahn.knn3demo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wang.kahn.knn3demo.databinding.NftListItemBinding

class NFTListAdapter(private val nfts: List<NFTQuery.Holdnft>) :
    RecyclerView.Adapter<NFTListAdapter.ViewHolder>() {

    class ViewHolder(val binding: NftListItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onItemClickListener: ((NFTQuery.Holdnft) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NftListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nft = nfts[position]
        holder.binding.nftName.text = nft.symbol
        Glide.with(holder.binding.root).load(nft.imageUrl).into(holder.binding.nftThumb)
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(nft)
        }
    }

    override fun getItemCount(): Int {
        return nfts.size
    }

}