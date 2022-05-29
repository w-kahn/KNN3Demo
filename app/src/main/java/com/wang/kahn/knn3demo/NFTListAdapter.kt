package com.wang.kahn.knn3demo

import android.view.LayoutInflater
import android.view.ViewGroup
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
        Glide.with(holder.binding.root).load(nft.imageUrl)
            .placeholder(R.drawable.image_placeholder).into(holder.binding.nftThumb)
        holder.binding.nftName.text = nft.symbol
        holder.binding.nftContractAddress.text = nft.contract
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(nft)
        }
    }

    override fun getItemCount(): Int {
        return nfts.size
    }

}