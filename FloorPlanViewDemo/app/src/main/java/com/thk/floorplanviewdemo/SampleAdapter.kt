package com.thk.floorplanviewdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.thk.floorplanviewdemo.databinding.ItemSampleBinding

class SampleAdapter : ListAdapter<String, SampleAdapter.SampleViewHolder>(SampleDiffUtil()) {
    inner class SampleViewHolder(private val binding: ItemSampleBinding) : ViewHolder(binding.root) {
            fun bind(item: String) {

            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {
        val binding = ItemSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SampleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SampleDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}