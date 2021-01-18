package com.example.forum.posts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.forum.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsListViewModel(val user: User, section: Section, app: Application) : AndroidViewModel(app) {
  val posts = MutableLiveData<List<PostInfo>>()
  val status = MutableLiveData<ForumApiStatus>()
  val navigateToSelectedPost = MutableLiveData<PostInfo>()

  init {
    getPosts(section)
  }

  private fun getPosts(section: Section) {
    ForumApi.retrofitService.getAllPostsFromSection(section.id).enqueue(object: Callback<List<PostInfo>> {
      override fun onResponse(call: Call<List<PostInfo>>, response: Response<List<PostInfo>>) {
        posts.value = response.body()
        status.value = ForumApiStatus.SUCCESS
      }

      override fun onFailure(call: Call<List<PostInfo>>, t: Throwable) {
        posts.value = ArrayList()
        status.value = ForumApiStatus.ERROR
      }
    })
  }

  fun displayPost(post: PostInfo) {
    navigateToSelectedPost.value = post
  }

  fun displayPostComplete() {
    navigateToSelectedPost.value = null
  }

  fun deletePost(position: Int, postId: Long) {
    Log.i("PostsListViewModel", "Position: ${position}, postId: ${postId}")
    ForumApi.retrofitService.deletePost(postId).enqueue(object: Callback<Unit> {
      override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        if (response.code() == ForumApiStatus.SUCCESS.code) {
          val postsList = posts.value as MutableList<PostInfo>
          postsList.removeAt(position)
          posts.value = postsList
        }
      }
      override fun onFailure(call: Call<Unit>, t: Throwable) {
        Log.e("PostsListViewModel", t.message!!)
      }
    })
  }
}