package com.example.forum

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forum.network.Comment
import com.example.forum.network.Post
import com.example.forum.network.PostInfo
import com.example.forum.network.Section
import com.example.forum.post.CommentsAdapter
import com.example.forum.posts.PostsAdapter
import com.example.forum.sections.SectionsAdapter

@BindingAdapter("sectionsListData")
fun bindSectionsRecyclerView(recyclerView: RecyclerView, data: List<Section>?) {
  val adapter = recyclerView.adapter as SectionsAdapter
  adapter.submitList(data)
}

@BindingAdapter("postsListData")
fun bindPostsRecyclerView(recyclerView: RecyclerView, data: List<PostInfo>?) {
  val adapter = recyclerView.adapter as PostsAdapter
  adapter.submitList(data)
}

@BindingAdapter("commentsListData")
fun bindCommentsRecyclerView(recyclerView: RecyclerView, data: List<Comment>?) {
  val adapter = recyclerView.adapter as CommentsAdapter
  adapter.submitList(data)
}