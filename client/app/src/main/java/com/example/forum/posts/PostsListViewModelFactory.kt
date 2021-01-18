package com.example.forum.posts

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forum.network.Section
import com.example.forum.network.User

class PostsListViewModelFactory(
  private val user: User,
  private val section: Section,
  private val application: Application) : ViewModelProvider.Factory {

  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PostsListViewModel::class.java)) {
      return PostsListViewModel(user, section, application) as T
    }
    throw IllegalAccessException("Unknown ViewModel class")
  }
}