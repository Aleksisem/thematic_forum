package com.example.forum.sections

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forum.network.ForumApi
import com.example.forum.network.ForumApiStatus
import com.example.forum.network.Section
import com.example.forum.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SectionsListViewModel(user: User, app: Application) : AndroidViewModel(app) {
  val sections = MutableLiveData<List<Section>>()
  val status = MutableLiveData<ForumApiStatus>()
  val navigateToSelectedSection = MutableLiveData<Section>()

  init {
    getSections(user)
  }

  private fun getSections(user: User) {
    ForumApi.retrofitService.getPostSections(user.id).enqueue(object: Callback<List<Section>> {
      override fun onResponse(call: Call<List<Section>>, response: Response<List<Section>>) {
        sections.value = response.body()
        status.value = ForumApiStatus.SUCCESS
      }

      override fun onFailure(call: Call<List<Section>>, t: Throwable) {
        sections.value = ArrayList()
        status.value = ForumApiStatus.ERROR
      }
    })
  }

  fun displaySectionPosts(section: Section) {
    navigateToSelectedSection.value = section
  }

  fun displaySectionPostsComplete() {
    navigateToSelectedSection.value = null
  }
}