package com.example.forum.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forum.network.User

class PostViewModelFactory(
  private val user: User,
  private val postId: Long,
  private val application: Application) : ViewModelProvider.Factory {

  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
      return PostViewModel(user, postId, application) as T
    }
    throw IllegalAccessException("Unknown ViewModel class")
  }
}