package com.example.forum.posts

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.R
import com.example.forum.databinding.PostItemBinding
import com.example.forum.network.PostInfo

class PostsAdapter(private val onClickListener: OnClickListener,
                   private val viewModel: PostsListViewModel,
                   private val enableContextMenu: Boolean) : ListAdapter<PostInfo, PostsAdapter.PostsViewHolder>(DiffCallback) {

  companion object DiffCallback : DiffUtil.ItemCallback<PostInfo>() {

    override fun areItemsTheSame(oldItem: PostInfo, newItem: PostInfo): Boolean {
      return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PostInfo, newItem: PostInfo): Boolean {
      return oldItem.id == newItem.id
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
    return PostsViewHolder(PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), viewModel)
  }

  override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
    val post = getItem(position)
    holder.itemView.setOnClickListener {
      onClickListener.onClick(post)
    }
    holder.itemView.setOnLongClickListener(OnLongClickListener())
    holder.bind(post, position, enableContextMenu)
  }

  class OnClickListener(val clickListener: (post: PostInfo) -> Unit) {
    fun onClick(post: PostInfo) = clickListener(post)
  }

  class OnLongClickListener() : View.OnLongClickListener {
    override fun onLongClick(view: View?): Boolean {
      return true
    }
  }

  class PostsViewHolder(private val binding: PostItemBinding, private val viewModel: PostsListViewModel) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: PostInfo, position: Int, enableContextMenu: Boolean) {
      binding.post = post
      if (enableContextMenu) {
        binding.postCard.setOnLongClickListener {
          val pop = PopupMenu(itemView.context, itemView)
          pop.inflate(R.menu.context_menu)
          pop.setOnMenuItemClickListener { item ->
            when (item.itemId) {
              R.id.delete_menu -> {
                viewModel.deletePost(position, binding.post!!.id)
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


