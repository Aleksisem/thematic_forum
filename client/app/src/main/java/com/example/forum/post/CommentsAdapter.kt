package com.example.forum.post

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.R
import com.example.forum.databinding.CommentItemBinding
import com.example.forum.network.Comment

class CommentsAdapter(private val viewModel: PostViewModel,
                      private val enableContextMenu: Boolean) : ListAdapter<Comment, CommentsAdapter.CommentsViewHolder>(DiffCallback) {
  companion object DiffCallback: DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
      return oldItem.id == newItem.id
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
    return CommentsViewHolder(CommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
  }

  override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
    val comment = getItem(position)
    holder.bind(comment, position, enableContextMenu)
  }

  class CommentsViewHolder(private val binding: CommentItemBinding, private val viewModel: PostViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bind(comment: Comment, position: Int, enableContextMenu: Boolean) {
      binding.comment = comment
      if (enableContextMenu) {
        binding.postInfoLayout.setOnLongClickListener {
          val pop = PopupMenu(itemView.context, itemView)
          pop.inflate(R.menu.context_menu)
          pop.setOnMenuItemClickListener { item ->
            when (item.itemId) {
              R.id.delete_menu -> {
                viewModel.deleteComment(position, binding.comment!!.id)
              }
            }
            true
          }
          pop.show()
          true
        }
      }
      binding.executePendingBindings()
    }
  }
}