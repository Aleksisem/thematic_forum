package com.example.forum.post

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forum.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostCreatorViewModel(private val user: User, private val section: Section, app: Application) : AndroidViewModel(app) {
  val postTitle = MutableLiveData<String>()
  val postContent = MutableLiveData<String>()
  val status = MutableLiveData<ForumApiStatus>()
  val navigateToPosts = MutableLiveData<Boolean>()

  fun savePost() {
    ForumApi.retrofitService.addPost(Post(0, postTitle.value!!, postContent.value!!, section.id, user.id, user.login, "")).enqueue(object: Callback<Unit> {
      override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        if (response.code() == ForumApiStatus.ERROR.code) {
          status.value = ForumApiStatus.ERROR
        } else {
          status.value = ForumApiStatus.CREATED
          displayPostsList()
        }
      }

      override fun onFailure(call: Call<Unit>, t: Throwable) {
        Log.e("PostCreatorViewModel", t.message!!)
      }
    })
  }

  fun displayPostsList() {
    navigateToPosts.value = true
  }

  fun displayPostsListComplete() {
    navigateToPosts.value = false
  }
}