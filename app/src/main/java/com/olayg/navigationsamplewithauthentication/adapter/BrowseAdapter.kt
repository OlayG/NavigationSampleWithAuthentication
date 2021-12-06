package com.olayg.navigationsamplewithauthentication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.olayg.navigationsamplewithauthentication.databinding.ItemBrowseBinding
import com.olayg.navigationsamplewithauthentication.model.Browse

class BrowseAdapter(
    private val browseList: List<Browse>,
    val itemSelected: (Browse) -> Unit
) : RecyclerView.Adapter<BrowseAdapter.BrowseViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ) = BrowseViewHolder.getInstance(parent).also { holder ->
        holder.itemView.setOnClickListener {
            itemSelected(browseList[holder.adapterPosition])
        }
    }

    override fun onBindViewHolder(holder: BrowseViewHolder, position: Int) {
        holder.bind(browseList[position])
    }

    override fun getItemCount() = browseList.size

    class BrowseViewHolder(
        private val binding: ItemBrowseBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(browse: Browse) = with(binding) {
            binding.tvTitle.text = browse.title
        }

        companion object {
            fun getInstance(parent: ViewGroup) = ItemBrowseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            ).run { BrowseViewHolder(this) }
        }
    }
}