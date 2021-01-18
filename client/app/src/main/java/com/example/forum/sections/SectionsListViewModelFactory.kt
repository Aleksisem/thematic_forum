package com.example.forum.sections

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forum.network.User

class SectionsListViewModelFactory(
  private val user: User,
  private val application: Application) : ViewModelProvider.Factory {

  @Suppress("unchecked_cast")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(SectionsListViewModel::class.java)) {
      return SectionsListViewModel(user, application) as T
    }
    throw IllegalAccessException("Unknown ViewModel class")
  }
}