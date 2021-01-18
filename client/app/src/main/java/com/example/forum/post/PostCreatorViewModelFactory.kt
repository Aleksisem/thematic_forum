package com.example.forum.post

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forum.network.Section
import com.example.forum.network.User

class PostCreatorViewModelFactory(
  private val user: User,
  private val section: Section,
  private val application: Application) : ViewModelProvider.Factory {

  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(PostCreatorViewModel::class.java)) {
      return PostCreatorViewModel(user, section, application) as T
    }
    throw IllegalAccessException("Unknown ViewModel class")
  }
}