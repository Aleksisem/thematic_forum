package com.example.forum.sections

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.databinding.SectionItemBinding
import com.example.forum.databinding.SectionsListFragmentBinding
import com.example.forum.network.Section

class SectionsAdapter(private val onClickListener: OnClickListener) : ListAdapter<Section, SectionsAdapter.SectionsViewHolder>(DiffCallback) {
  companion object DiffCallback : DiffUtil.ItemCallback<Section>() {
    override fun areItemsTheSame(oldItem: Section, newItem: Section): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Section, newItem: Section): Boolean {
      return oldItem.id == newItem.id
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionsViewHolder {
    return SectionsViewHolder(SectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
  }

  override fun onBindViewHolder(holder: SectionsViewHolder, position: Int) {
    val section = getItem(position)
    holder.itemView.setOnClickListener {
      onClickListener.onClick(section)
    }
    holder.bind(section)
  }

  class OnClickListener(val clickListener: (section: Section) -> Unit) {
    fun onClick(section: Section) = clickListener(section)
  }

  class SectionsViewHolder(private var binding: SectionItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(section: Section) {
      binding.section = section
      binding.executePendingBindings()
    }
  }
}